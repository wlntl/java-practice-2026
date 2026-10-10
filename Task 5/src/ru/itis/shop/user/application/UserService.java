package ru.itis.shop.user.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String name, String email, String password, String profileDescription) {
        User user = new User(name, email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getPassword().equals(password);
        } else return false;
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(Integer.valueOf(id));
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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllByProfileDescription(String profileDescription) {
        return userRepository.findAllByProfileDescription(profileDescription);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
