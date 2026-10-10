package ru.itis.shop.user.api;

import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public void showMenu() {
        printUserMenu();

        if (!scanner.hasNextLine()) {
            System.exit(0);
        }
        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "3": {
                findUserById();
            }
            break;
            case "4": {
                updateUser();
            }
            break;
            case "5": {
                showAllUsers();
            }
            break;
            case "6": {
                showUsersByProfileDescription();
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить данные пользователя");
        System.out.println("5. Показать всех пользователей");
        System.out.println("6. Найти пользователей по описанию профиля");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите имя:");
        String name = scanner.nextLine();
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(name, email, password, profileDescription);
    }

    private void signIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение");
        } else {
            System.out.println("Email или пароль не верны");
        }
    }

    private void findUserById() {
        System.out.println("Введите ID пользователя:");
        String id = scanner.nextLine();

        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println("Пользователь найден:");
            System.out.println("Email: " + user.getEmail());
        } else {
            System.out.println("Пользователь с ID " + id + " не найден");
        }
    }

    private void updateUser() {
        System.out.println("Введите email пользователя, данные которого хотите обновить:");
        String email = scanner.nextLine();

        if (userService.findByEmail(email).isEmpty()) {
            System.out.println("Пользователь с email " + email + " не найден");
            return;
        }
        System.out.println("Введите новое описание профиля:");
        String newProfileDescription = scanner.nextLine();

        userService.updateProfileDescription(email, newProfileDescription);
    }

    private void showAllUsers() {
        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            System.out.println("Пользователей нет");
        } else {
            System.out.println("Список пользователей:");
            System.out.println("---------------------------");
            for (User user : users) {
                System.out.println("Id: " + user.getId());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Описание: " + user.getProfileDescription());
                System.out.println("---------------------------");
            }
        }
    }

    private void showUsersByProfileDescription() {
        System.out.println("Введите описание профиля для поиска:");
        String profileDescription = scanner.nextLine();

        List<User> users = userService.findAllByProfileDescription(profileDescription);

        if (users.isEmpty()) {
            System.out.println("Пользователи с описанием \"" + profileDescription + "\" не найдены");
        } else {
            System.out.println("Количество пользователей: " + users.size());
            System.out.println("---------------------------");
            for (User user : users) {
                System.out.println("Id: " + user.getId());
                System.out.println("Имя: " + user.getName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Описание: " + user.getProfileDescription());
                System.out.println("---------------------------");
            }
        }
    }
}
