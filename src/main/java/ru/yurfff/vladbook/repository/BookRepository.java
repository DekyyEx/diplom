package ru.yurfff.vladbook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yurfff.vladbook.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);

    List<Book> findByTitleContaining(String title);

    List<Book> findByPriceLessThanEqual(Double price);

    Page<Book> findByAuthorAndTitleContaining(String author, String title, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.author = ?1 AND b.price <= ?2")
    List<Book> findBooksByAuthorAndPriceLessThanEqual(String author, Double price);
}
