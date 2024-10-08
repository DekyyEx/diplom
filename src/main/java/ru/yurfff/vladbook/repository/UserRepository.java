package ru.yurfff.vladbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yurfff.vladbook.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(Long id);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}
