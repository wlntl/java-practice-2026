package ru.itis.shop.user.application;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.infrastructure.persistence.UserMapperDto;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;
    private final UserMapperDto userMapperDto = new UserMapperDto();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String name, String email, String password, String profileDescription) {
        User user = new User(name, email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
    }

    public Optional<UserDto> findById(Integer id) {
        return userRepository.findById(id).map(userMapperDto::toDto);
    }

    public Optional<UserDto> findById(String id) {
        try {
            Integer userId = Integer.valueOf(id);
            return findById(userId);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public void updateProfileDescription(String email, String newProfileDescription) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfileDescription(newProfileDescription);
            userRepository.update(user);
            System.out.println("Данные обновлены");
        } else {
            System.out.println("Пользователь с email " + email + " не найден");
        }
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapperDto::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> findAllByProfileDescription(String profileDescription) {
        return userRepository.findAllByProfileDescription(profileDescription).stream()
                .map(userMapperDto::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapperDto::toDto);
    }
}