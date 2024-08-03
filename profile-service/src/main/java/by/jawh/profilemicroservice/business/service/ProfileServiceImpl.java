package by.jawh.profilemicroservice.business.service;

import by.jawh.profilemicroservice.common.entity.ProfileEntity;
import by.jawh.profilemicroservice.common.repository.ProfileRepository;
import by.jawh.profilemicroservice.exception.EmailAlreadyExistException;
import by.jawh.profilemicroservice.exception.ProfileNotFoundException;
import by.jawh.profilemicroservice.business.dto.ProfileRequestDto;
import by.jawh.profilemicroservice.business.dto.ProfileResponseDto;
import by.jawh.profilemicroservice.business.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {


    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final MinioService minioService;

    @Override
    public ProfileResponseDto findById(Long id) {
        return profileRepository.findById(id)
                .map(profileMapper::entityToResponseDto)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found :(".formatted(id)));
    }

    @Override
    public List<ProfileResponseDto> findAll() {
        return profileRepository.findAll().stream()
                .map(profileMapper::entityToResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public Boolean editProfile(Long id, ProfileRequestDto profileRequestDto) {
        ProfileEntity profileEntity = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found :(".formatted(id)));

        Optional<ProfileEntity> checkByEmailEntity = profileRepository.findByEmail(profileRequestDto.getEmail());

        if(checkByEmailEntity.isPresent() && !Objects.equals(checkByEmailEntity.get().getId(), id)) {
            throw new EmailAlreadyExistException("a user with such an email has already been registered");
        }

        profileMapper.updateProfileFromDto(profileRequestDto, profileEntity);
        profileRepository.saveAndFlush(profileEntity);
        log.info("user with id: %s has changed some data about his profile".formatted(id));
        return true;
    }


    @Transactional
    @Override
    public Boolean deleteProfile(Long id) {
        return profileRepository.findById(id)
                .map(entity -> {
                    profileRepository.delete(entity);
                    profileRepository.flush();
                    log.info("profile with id: %s was deleted".formatted(id));
                    return true;
                })
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found :(".formatted(id)));
    }

    @Transactional
    public String uploadAvatar(Long id, MultipartFile avatar) throws Exception {
        ProfileEntity profileEntity = profileRepository.findById(id)
                .orElseThrow(() ->
                        new ProfileNotFoundException("profile with id: %s not found :(".formatted(id)));

        String bucketName = "avatars";
        String objectName = id + "/" + avatar.getOriginalFilename();

        minioService.uploadFile(bucketName, objectName, avatar);

        profileEntity.setAvatarUrl(minioService.getObjectUrl(bucketName, objectName));

        profileRepository.saveAndFlush(profileEntity);
        log.info("user with id: %s upload new avatar");
        return profileEntity.getAvatarUrl();
    }
}


