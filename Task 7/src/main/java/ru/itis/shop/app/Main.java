package ru.itis.shop.app;

import ru.itis.shop.infrastructure.persistence.jdbc.DriverManagerDataSource;
import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.jdbc.UserRepositoryJdbcImpl;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.DataSource;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        ru.itis.shop.util.PropertiesReader propertiesReader = new ru.itis.shop.util.PropertiesReader("application.properties");

        Properties properties = propertiesReader.loadProperties();


        DataSource dataSource = new DriverManagerDataSource(
                System.getenv().getOrDefault("DB_URL", properties.getProperty("db.url")),
                System.getenv().getOrDefault("DB_USER", properties.getProperty("db.user")),
                System.getenv().getOrDefault("DB_PASSWORD", properties.getProperty("db.password"))
        );

        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);
        UserService userService = new UserService(userRepository);
        UserConsoleOperations console = new UserConsoleOperations(userService);

        while (true) {
            try {
                console.showMenu();
            } catch (java.util.NoSuchElementException e) {
                break;
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}