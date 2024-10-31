package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

//        userService.createUsersTable();

        userService.saveUser("name_1", "last_name_1", (byte) 20);
        userService.saveUser("name_2", "last_name_2", (byte) 30);
        userService.saveUser("name_3", "last_name_3", (byte) 40);

//        userService.removeUserById(2);

//        userService.getAllUsers();

//        userService.cleanUsersTable();
//        userService.dropUsersTable();
    }
}