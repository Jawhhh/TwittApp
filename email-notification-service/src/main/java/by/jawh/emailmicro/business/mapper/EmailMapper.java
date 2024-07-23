package by.jawh.emailmicro.business.mapper;

import by.jawh.emailmicro.business.dto.UserMailSendDto;
import by.jawh.eventsforalltopics.events.UserNotificationEvent;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EmailMapper {

    UserMailSendDto eventToDto(UserNotificationEvent userNotificationEvent);
}
