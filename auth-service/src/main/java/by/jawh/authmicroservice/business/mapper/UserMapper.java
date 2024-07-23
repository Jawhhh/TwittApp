package by.jawh.authmicroservice.business.mapper;

import by.jawh.authmicroservice.business.dto.UserRequestLoginDto;
import by.jawh.authmicroservice.common.entity.UserEntity;
import by.jawh.authmicroservice.business.dto.UserRequestRegisterDto;
import by.jawh.authmicroservice.business.dto.UserResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(
        componentModel = SPRING
        )
public interface UserMapper {

    UserRequestRegisterDto entityToRequestDto(UserEntity userEntity);

    UserResponseDto entityToResponseDto(UserEntity userEntity);

    UserEntity loginDtoToEntity(UserRequestLoginDto userRequestRegisterDto);

    UserEntity registerDtoToEntity(UserRequestRegisterDto userRequestRegisterDto);

    UserResponseDto registerDtoToResponse(UserRequestRegisterDto requestDto);
}
