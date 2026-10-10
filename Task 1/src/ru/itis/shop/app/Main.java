package ru.itis.shop.app;

import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.infrastructure.persistence.UserDatabaseRepository;
import ru.itis.shop.user.infrastructure.persistence.UserFileRepository;

public class Main{
    public static void main(String[] args) {

        UserFileRepository userFileRepository = new UserFileRepository("users.txt");
        UserDatabaseRepository userDatabaseRepository = new UserDatabaseRepository();

        UserConsoleOperations operations = new UserConsoleOperations(userFileRepository);

        while (true) {
            try {
                operations.showMenu();
            } catch (java.util.NoSuchElementException e) {
                break;
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}