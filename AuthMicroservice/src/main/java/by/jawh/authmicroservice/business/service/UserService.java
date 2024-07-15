package by.jawh.authmicroservice.business.service;

import by.jawh.authmicroservice.business.dto.UserRequestLoginDto;
import by.jawh.authmicroservice.business.dto.UserRequestRegisterDto;
import by.jawh.authmicroservice.business.dto.UserResponseRegisterDto;

import java.util.List;

public interface UserService {

    UserResponseRegisterDto createUser(UserRequestRegisterDto userRequestRegisterDto);

    Boolean deleteUser(Long id);

    UserRequestRegisterDto findById(Long id);

    List<UserRequestRegisterDto> findAll();

    UserRequestLoginDto updateUser(Long id, UserRequestLoginDto dto);
}
