package by.jawh.profilemicroservice.business.mapper;

import by.jawh.eventsforalltopics.events.UserRegisteredEvent;
import by.jawh.profilemicroservice.common.entity.ProfileEntity;
import by.jawh.profilemicroservice.business.dto.ProfileRequestDto;
import by.jawh.profilemicroservice.business.dto.ProfileResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING)
public interface ProfileMapper {

    ProfileEntity eventToEntity(UserRegisteredEvent userRegisteredEvent);

    ProfileResponseDto RequestDtoToResponseDto(ProfileRequestDto profileRequestDto);

    ProfileEntity ResponseDtoToEntity(ProfileResponseDto profileResponseDto);

    ProfileEntity RequestDtoToEntity(ProfileRequestDto profileRequestDto);

    ProfileResponseDto entityToResponseDto(ProfileEntity profileEntity);

    ProfileRequestDto entityToRequestDto(ProfileEntity profileEntity);

    void updateProfileFromDto(ProfileRequestDto dto, @MappingTarget ProfileEntity entity);
}
