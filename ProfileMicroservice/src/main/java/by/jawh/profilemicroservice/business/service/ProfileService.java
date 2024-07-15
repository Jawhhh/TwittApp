package by.jawh.profilemicroservice.business.service;

import by.jawh.profilemicroservice.business.dto.ProfileRequestDto;
import by.jawh.profilemicroservice.business.dto.ProfileResponseDto;

import java.util.List;

public interface ProfileService {

    ProfileResponseDto findById(Long id);

    List<ProfileResponseDto> findAll();

    Boolean editProfile(Long id, ProfileRequestDto profileRequestDto);

    Boolean deleteProfile(Long id);
}
