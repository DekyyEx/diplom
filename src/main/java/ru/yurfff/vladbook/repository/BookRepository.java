package ru.yurfff.vladbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yurfff.vladbook.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Метод для поиска книг по частичному вхождению в название
    List<Book> findByTitleContaining(String title);
}
