package by.jawh.emailmicro.mapper;

import by.jawh.emailmicro.business.dto.UserMailSendDto;
import by.jawh.eventsforalltopics.events.UserNotificationEvent;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EventToDtoMapper {

    UserMailSendDto eventToDto(UserNotificationEvent userNotificationEvent);
}
