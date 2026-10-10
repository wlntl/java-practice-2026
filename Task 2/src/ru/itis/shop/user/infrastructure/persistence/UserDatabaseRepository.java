package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.Optional;

public class UserDatabaseRepository implements UserRepository {

    @Override
    public void save(User user) {

        System.out.println("Сохраняем в базу данных...");

    }

    @Override
    public Optional<User> findById(String id) {

        System.out.println("Получаем из базы данных...");

        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {

        System.out.println("Получаем из базы данных...");

        return null;
    }

    public void update(User user) {

        System.out.println("Обновляем в базе данных...");

    }
}
