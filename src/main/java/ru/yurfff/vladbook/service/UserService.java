package ru.yurfff.vladbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yurfff.vladbook.model.User;
import ru.yurfff.vladbook.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Проверка, существует ли пользователь по ID
    public boolean existsById(Long id) {
        return userRepository.existsById(id); // Исправлено условие, теперь проверяется на существование
    }

    // Поиск пользователя по username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Обновление пользователя по ID
    public User update(Long id, User user) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found"); // Можно заменить на ResponseStatusException
        }
        user.setId(id);
        return userRepository.save(user);
    }

    // Удаление пользователя по ID
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found"); // Можно заменить на ResponseStatusException
        }
        userRepository.deleteById(id);
    }

    // Получение всех пользователей
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Поиск пользователя по ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
