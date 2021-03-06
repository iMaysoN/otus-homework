package ru.otus.services.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}