package by.jawh.authmicroservice.business.mapper;

import by.jawh.authmicroservice.common.entity.UserEntity;
import by.jawh.authmicroservice.business.dto.UserRequestLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateMapper {

    private final PasswordEncoder passwordEncoder;

    public UserEntity updateEntity(UserEntity entity, UserRequestLoginDto dto) {
        entity.setUsername(dto.getUsername());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        return entity;
    }

}
