package by.jawh.authmicroservice.business.service;

import by.jawh.authmicroservice.business.dto.ProfileForEventDto;
import by.jawh.authmicroservice.business.dto.UserResponseDto;
import by.jawh.authmicroservice.common.entity.Role;
import by.jawh.authmicroservice.common.entity.UserEntity;
import by.jawh.authmicroservice.common.repository.UserRepository;
import by.jawh.authmicroservice.business.dto.UserRequestLoginDto;
import by.jawh.authmicroservice.business.dto.UserRequestRegisterDto;
import by.jawh.authmicroservice.business.mapper.UserMapper;
import by.jawh.authmicroservice.business.mapper.UpdateMapper;
import by.jawh.authmicroservice.exception.EmailAlreadyExistsException;
import by.jawh.authmicroservice.exception.UsernameAlreadyExistsException;
import by.jawh.eventsforalltopics.events.UserNotificationEvent;
import by.jawh.eventsforalltopics.events.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final KafkaService kafkaService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UpdateMapper updateMapper;
    private final RestTemplate restTemplate;
    private static final String EMAIL_EXIST_URL = "http://localhost:8080/profiles/emailExist";
    private static final String DELETE_PROFILE_URL = "http://localhost:8080/profiles/delete/";

    // CRUD ----------------------------

    @Transactional
    @Override
    public UserEntity createUser(UserRequestRegisterDto userRequestRegisterDto) {

        ResponseEntity<Boolean> emailExist = restTemplate.postForEntity(
                EMAIL_EXIST_URL,
                userRequestRegisterDto.getEmail(),
                Boolean.class
        );

        if (Boolean.TRUE.equals(emailExist.getBody())) {
            throw new EmailAlreadyExistsException("Пользователь с таким email уже существует");
        }
        if (userRepository.findByUsername(userRequestRegisterDto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Пользователь с таким именем уже существует");
        }

        UserResponseDto responseDto = userMapper.registerDtoToResponse(userRequestRegisterDto);


        UserEntity userEntity = userMapper.registerDtoToEntity(userRequestRegisterDto);
        userEntity.setRole(Role.USER);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.saveAndFlush(userEntity);
        responseDto.setId(userEntity.getId());
        responseDto.setRole(userEntity.getRole());

        kafkaService.userRegisterSendEvent(userEntity, userRequestRegisterDto);
        kafkaService.userRegisterNotificationSendEvent(userEntity, userRequestRegisterDto);
        kafkaService.userRegisteredSubscribeSendEvent(userEntity.getId());

        log.info("user was created with id: %s and username: %s"
                .formatted(userEntity.getId(), userEntity.getUsername()));
        return userEntity;
    }

    @Transactional
    @Override
    public Boolean deleteUser(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    restTemplate.delete(DELETE_PROFILE_URL + id);
                    log.info("user was deleted with id: %s and username: %s"
                            .formatted(entity.getId(), entity.getUsername()));
                    return true;
                }).orElseThrow(() -> new UsernameNotFoundException("user with id: %S not found :(".formatted(id)));
    }

    @Override
    public UserResponseDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::entityToResponseDto)
                .orElseThrow(() -> new UsernameNotFoundException("user with id: %S not found :(".formatted(id)));
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::entityToResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public UserResponseDto updateUser(Long id, UserRequestLoginDto dto) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(dto.getUsername());

        if (byUsername.isPresent() && !Objects.equals(byUsername.get().getId(), id)) {
            throw new UsernameAlreadyExistsException("Пользователь с таким именем уже существует");
        }

        return userRepository.findById(id)
                .map(entity -> {
                    updateMapper.updateEntity(entity, dto);
                    userRepository.saveAndFlush(entity);
                    log.info("user with id: %s changed some of his data"
                            .formatted(entity.getId()));
                    return userMapper.entityToResponseDto(entity);
                })
                .orElseThrow(() -> new UsernameNotFoundException("user with id: %S not found :(".formatted(id)));
    }

    // Utils --------------------------------------------------------------

    public void login(UserRequestLoginDto userRequestLoginDto) {

        UserEntity userEntity = userRepository
                .findAllByUsernameAndPassword(
                        userRequestLoginDto.getUsername(),
                        passwordEncoder.encode(userRequestLoginDto.getPassword()))
                .orElseThrow(() -> new RuntimeException("Неправельные имя пользователя или пароль"));

        URI url = URI.create("http://localhost:8080/profiles/" + userEntity.getId());

        kafkaService.userLoginNotificationSendEvent(userRequestLoginDto, url);
        log.info("user with id: %s and username: %s logged into your account"
                .formatted(userEntity.getId(), userEntity.getUsername()));
    }

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Пользователь с именем %s не найден".formatted(username)));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
