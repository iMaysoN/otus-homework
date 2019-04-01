package ru.otus.repostory;

import ru.otus.domain.UserSimple;

import java.util.List;

public interface UserRepository {

    List<UserSimple> findAll();

    long create(String name);
}
