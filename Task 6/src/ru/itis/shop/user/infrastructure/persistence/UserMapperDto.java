package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.domain.User;

public class UserMapperDto {

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfileDescription()
        );
    }

    public User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                null,
                userDto.getProfileDescription()
        );
    }
}