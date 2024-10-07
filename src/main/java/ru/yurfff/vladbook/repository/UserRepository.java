package ru.yurfff.vladbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yurfff.vladbook.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    List<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailOrUsername(String email, String username);

    boolean existsByEmailOrUsername(String email, String username);
}
