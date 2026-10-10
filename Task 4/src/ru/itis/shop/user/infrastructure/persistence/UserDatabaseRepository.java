package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserDatabaseRepository implements UserRepository {

    @Override
    public void save(User user) {

        System.out.println("Сохраняем в базу данных...");
    }

    @Override
    public Optional<User> findById(String id) {

        System.out.println("Получаем из базы данных...");

        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {

        System.out.println("Получаем из базы данных...");

        return Optional.empty();
    }

    @Override
    public void update(User user) {

        System.out.println("Обновляем в базе данных...");
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
