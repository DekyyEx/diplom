package ru.yurfff.vladbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yurfff.vladbook.model.Book;
import ru.yurfff.vladbook.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> findByTitleContaining(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public List<Book> findByPriceLessThanEqual(Double price) {
        return bookRepository.findByPriceLessThanEqual(price);
    }

    public Page<Book> findByAuthorAndTitleContaining(String author, String title, Pageable pageable) {
        return bookRepository.findByAuthorAndTitleContaining(author, title, pageable);
    }

    public List<Book> findBooksByAuthorAndPriceLessThanEqual(String author, Double price) {
        return bookRepository.findBooksByAuthorAndPriceLessThanEqual(author, price);
    }

    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book with ID " + id + " not found");
        }
        book.setId(id);
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
    }
}
