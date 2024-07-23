package by.jawh.authmicroservice.business.service;

import by.jawh.authmicroservice.business.dto.UserRequestLoginDto;
import by.jawh.authmicroservice.business.dto.UserRequestRegisterDto;
import by.jawh.authmicroservice.business.dto.UserResponseDto;
import by.jawh.authmicroservice.common.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity createUser(UserRequestRegisterDto userRequestRegisterDto);

    Boolean deleteUser(Long id);

    UserResponseDto findById(Long id);

    List<UserResponseDto> findAll();

    UserResponseDto updateUser(Long id, UserRequestLoginDto dto);
}
