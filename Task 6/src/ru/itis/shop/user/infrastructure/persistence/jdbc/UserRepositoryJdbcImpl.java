package ru.itis.shop.user.infrastructure.persistence.jdbc;

import ru.itis.shop.infrastructure.persistence.jdbc.RowMapper;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.infrastructure.persistence.UserMapperDto;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;
    private final UserMapperDto userMapperDto = new UserMapperDto();

    private final RowMapper<User> userRowMapper = row -> new User(
            row.getInt("id"),
            row.getString("name"),
            row.getString("email"),
            row.getString("password"),
            row.getString("profiledescription")
    );

    private final RowMapper<UserDto> userDtoRowMapper = row -> new UserDto(
            row.getInt("id"),
            row.getString("name"),
            row.getString("email"),
            row.getString("profiledescription")
    );

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User user) {
        String sql = "insert into users (name, email, password, profiledescription) values (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getProfileDescription());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Не хватает данных для создания пользователя");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Не хватает id для создания пользователя");
                }
            }

            System.out.println("Пользователь зарегистрирован");

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(userRowMapper.mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(userRowMapper.mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return Optional.empty();
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, profiledescription = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getProfileDescription());
            statement.setInt(5, user.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Пользователь с id " + user.getId() + " не найден");
            }

            System.out.println("Данные обновлены");

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                users.add(userRowMapper.mapRow(resultSet));
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return users;
    }

    @Override
    public List<User> findAllByProfileDescription(String profileDescription) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE profiledescription = ? ORDER BY id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, profileDescription);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(userRowMapper.mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return users;
    }

    // Метод для получения DTO (можно использовать в сервисе)
    public List<UserDto> findAllDto() {
        List<UserDto> users = new ArrayList<>();
        String sql = "SELECT id, name, email, profiledescription FROM users ORDER BY id";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                users.add(userDtoRowMapper.mapRow(resultSet));
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return users;
    }

    public Optional<UserDto> findByIdDto(Integer id) {
        String sql = "SELECT id, name, email, profiledescription FROM users WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(userDtoRowMapper.mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return Optional.empty();
    }
}