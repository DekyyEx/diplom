package ru.yurfff.vladbook.service;

public class EntityNotFoundException extends RuntimeException {

    // Конструктор, принимающий сообщение об ошибке
    public EntityNotFoundException(String message) {
        super(message);
    }

    // Конструктор, принимающий сообщение об ошибке и причину (Throwable)
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
