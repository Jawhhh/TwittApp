package by.jawh.subscribeservice.business.mapper;

import by.jawh.eventsforalltopics.events.UserRegisteredSubscribeEvent;
import by.jawh.subscribeservice.common.entity.ProfileSubscriptionsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubscribeMapper {

    ProfileSubscriptionsEntity eventToEntity(UserRegisteredSubscribeEvent userRegisteredSubscribeEvent);

}
