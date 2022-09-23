package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("name1", "lastname1", (byte)1);
        userService.saveUser("name2", "lastname2", (byte)2);
        userService.saveUser("name3", "lastname3", (byte)3);
        userService.saveUser("name4", "lastname4", (byte)4);
        System.out.println(userService.getAllUsers());
        userService.removeUserById(1L);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
