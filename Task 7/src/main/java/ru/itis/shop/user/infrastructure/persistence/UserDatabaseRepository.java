package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserDatabaseRepository implements UserRepository {

    @Override
    public void save(User user) {
        throw new IllegalStateException("Не реализовано");
    }

    @Override
    public Optional<User> findById(Integer id) {
        throw new IllegalStateException("Не реализовано");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        throw new IllegalStateException("Не реализовано");
    }

    @Override
    public void update(User user) {
        throw new IllegalStateException("Не реализовано");
    }

    @Override
    public List<User> findAll() {
        throw new IllegalStateException("Не реализовано");
    }

    @Override
    public List<User> findAllByProfileDescription(String profileDescription) {
        throw new IllegalStateException("Не реализовано");
    }
}
