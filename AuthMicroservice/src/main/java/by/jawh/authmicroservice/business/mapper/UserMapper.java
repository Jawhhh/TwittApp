package by.jawh.authmicroservice.business.mapper;

import by.jawh.authmicroservice.common.entity.UserEntity;
import by.jawh.authmicroservice.business.dto.UserRequestRegisterDto;
import by.jawh.authmicroservice.business.dto.UserResponseRegisterDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

//unmappedTargetPolicy = IGNORE
@Mapper(
        componentModel = SPRING
        )
public interface UserMapper {

    UserRequestRegisterDto entityToDto(UserEntity userEntity);

    UserEntity dtoToEntity(UserRequestRegisterDto userRequestRegisterDto);

    UserResponseRegisterDto requestToResponse(UserRequestRegisterDto requestDto);
}
