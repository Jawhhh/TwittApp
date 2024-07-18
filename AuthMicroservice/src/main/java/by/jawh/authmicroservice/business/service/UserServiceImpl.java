package by.jawh.authmicroservice.business.service;

import by.jawh.authmicroservice.business.dto.UserResponseDto;
import by.jawh.authmicroservice.common.entity.Role;
import by.jawh.authmicroservice.common.entity.UserEntity;
import by.jawh.authmicroservice.common.repository.UserRepository;
import by.jawh.authmicroservice.business.dto.UserRequestLoginDto;
import by.jawh.authmicroservice.business.dto.UserRequestRegisterDto;
import by.jawh.authmicroservice.business.mapper.UserMapper;
import by.jawh.authmicroservice.business.mapper.UpdateMapper;
import by.jawh.authmicroservice.exception.EmailAlreadyExistException;
import by.jawh.authmicroservice.exception.UsernameAlreadyExistException;
import by.jawh.eventsforalltopics.events.UserRegisteredEvent;
import jakarta.ws.rs.InternalServerErrorException;
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

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UpdateMapper updateMapper;
    private final RestTemplate restTemplate;
    private static final String EMAIL_ALREADY_EXIST_URL = "http://localhost:8080/profiles/emailExist";

    // CRUD ----------------------------

    @Transactional
    @Override
    public UserResponseDto createUser(UserRequestRegisterDto userRequestRegisterDto) {

        ResponseEntity<Boolean> emailExist = restTemplate.postForEntity(
                EMAIL_ALREADY_EXIST_URL,
                userRequestRegisterDto.getEmail(),
                Boolean.class
        );

        if(Boolean.TRUE.equals(emailExist.getBody())) {
            throw new EmailAlreadyExistException("Пользователь с таким email уже существует");
        }
        if (userRepository.findByUsername(userRequestRegisterDto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException("Пользователь с таким именем уже существует");
        }

        UserResponseDto responseDto = userMapper.registerDtoToResponse(userRequestRegisterDto);

        return Optional.of(userRequestRegisterDto)
                .map(dto -> {
                    UserEntity userEntity = userMapper.registerDtoToEntity(dto);
                    userEntity.setRole(Role.USER);
                    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
                    userRepository.saveAndFlush(userEntity);
                    responseDto.setId(userEntity.getId());
                    responseDto.setRole(userEntity.getRole());

                    ProducerRecord<String, UserRegisteredEvent> record = new ProducerRecord<>(
                            "user-registered-events-topic",
                            userEntity.getId().toString(),
                            new UserRegisteredEvent(
                                    userEntity.getId(),
                                    dto.getFirstname(),
                                    dto.getLastname(),
                                    dto.getEmail(),
                                    dto.getBornDate()
                            ));

                    record.headers().add("messageId", UUID.randomUUID().toString().getBytes());
                    kafkaTemplate.send(record);
                    return responseDto;
                })
                .orElseThrow(() -> new InternalServerErrorException("Something happened... idk what"));
    }

    @Transactional
    @Override
    public Boolean deleteUser(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
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
            throw new UsernameAlreadyExistException("Пользователь с таким именем уже существует");
        }

        return userRepository.findById(id)
                .map(entity -> {
                    updateMapper.updateEntity(entity, dto);
                    userRepository.saveAndFlush(entity);
                    return userMapper.entityToResponseDto(entity);
                })
                .orElseThrow(() -> new UsernameNotFoundException("user with id: %S not found :(".formatted(id)));
    }

    // Utils --------------------------------------------------------------

    public Boolean login(UserRequestLoginDto dto) {
        return userRepository.findAllByUsernameAndPassword(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword())).isEmpty(); // true - если зарегестрированного пользователя с такими данными нет
    }

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Пользователь с именем %s не найден".formatted(username)));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

//      TODO if not uses -> delete
//    public UserEntity getCurrent() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        return getByUsername(username);
//    }
//
//    @Deprecated
//    public void getAdmin() {
//        var user = getCurrent();
//        user.setRole(Role.ADMIN);
//        userRepository.saveAndFlush(user);

//    }
}
