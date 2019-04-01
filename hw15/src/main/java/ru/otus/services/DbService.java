package ru.otus.services;

import ru.otus.entity.User;
import ru.otus.services.message.Addressee;

import java.util.List;

public interface DbService extends Addressee {
    List<User> getUsers();

    void save(User user);

    void deleteUser(Long id);
}
