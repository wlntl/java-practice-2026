package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserFileRepository  implements UserRepository {

    private final String fileName;

    private final UserMapper userMapper;

    public UserFileRepository(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(userMapper.toLine(user));
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                User user = userMapper.fromLine(line);

                if (user.getId().equals(id)) {

                    return Optional.of(user);
                }
            }

            return Optional.empty();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line = reader.readLine();

            while (line != null) {

                User user = userMapper.fromLine(line);

                if (user.getEmail().equals(email)) {
                    return Optional.of(user);
                }

                line = reader.readLine();
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(User user) {
        try {

            List<String> lines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

                for (String line : lines) {
                    User existingUser = userMapper.fromLine(line);

                    if (existingUser.getId().equals(user.getId())) {
                        writer.write(userMapper.toLine(user));
                    } else {
                        writer.write(line);
                    }

                    writer.newLine();
                }
            }

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
