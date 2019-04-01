package ru.otus.services;

import ru.otus.entity.User;

import java.util.List;

public interface FrontendService extends Addressee {
    void saveUser(User user);

    void deleteUser(User user);

    void getUsers();

    void sendUsers(List<User> users);
}
