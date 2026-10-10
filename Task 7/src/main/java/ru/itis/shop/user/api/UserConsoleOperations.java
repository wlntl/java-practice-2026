package ru.itis.shop.user.api;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.application.UserService;

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
            break;
            default: {
                System.out.println("Такого нет");
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить описание пользователя по почте");
        System.out.println("5. Получить информацию обо всех пользователях");
        System.out.println("6. Показать информацию о пользователях с заданным описанием профиля");
        System.out.println("0. Выход");
    }

    private void signUp() {
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
        System.out.println("Введите id пользователя:");
        String id = scanner.nextLine();

        Optional<UserDto> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            UserDto user = optionalUser.get();
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
        List<UserDto> users = userService.findAll();

        if (users.isEmpty()) {
            System.out.println("Пользователей нет");
        } else {
            System.out.println("Список пользователей:");
            System.out.println("---------------------------");
            for (UserDto user : users) {
                System.out.println("Id: " + user.getId());
                System.out.println("Имя: " + user.getName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Описание: " + user.getProfileDescription());
                System.out.println("---------------------------");
            }
            System.out.println("Всего пользователей: " + users.size());
        }
    }

    private void showUsersByProfileDescription() {
        System.out.println("Введите описание для поиска:");
        String profileDescription = scanner.nextLine();

        List<UserDto> users = userService.findAllByProfileDescription(profileDescription);

        if (users.isEmpty()) {
            System.out.println("Пользователи с описанием \"" + profileDescription + "\" не найдены");
        } else {
            System.out.println("Количество пользователей: " + users.size());
            System.out.println("---------------------------");
            for (UserDto user : users) {
                System.out.println("Id: " + user.getId());
                System.out.println("Имя: " + user.getName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Описание: " + user.getProfileDescription());
                System.out.println("---------------------------");
            }
        }
    }
}