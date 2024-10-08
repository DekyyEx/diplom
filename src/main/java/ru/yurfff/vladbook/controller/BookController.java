package ru.yurfff.vladbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yurfff.vladbook.model.Book;
import ru.yurfff.vladbook.service.BookService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
@Validated
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Получение всех книг с пагинацией
    @GetMapping
    public ResponseEntity<Page<Book>> getBooks(Pageable pageable) {
        Page<Book> bookPage = bookService.findAll(pageable);
        if (bookPage.isEmpty()) {
            return ResponseEntity.noContent().build();  // Возвращаем 204 если книги нет
        }
        return ResponseEntity.ok(bookPage);  // Возвращаем 200 и список книг
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        // Получаем книгу по ID
        Optional<Book> bookOpt = Optional.ofNullable(bookService.findById(id));

        // Если книга найдена, возвращаем её, иначе выбрасываем исключение
        if (bookOpt.isPresent()) {
            return ResponseEntity.ok(bookOpt.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга не найдена");
        }
    }

    // Поиск книги по названию
    @GetMapping("/search/title")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        List<Book> books = bookService.findByTitleContaining(title);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();  // Возвращаем 204 если ничего не найдено
        }
        return ResponseEntity.ok(books);  // Возвращаем 200 и список найденных книг
    }

    // Создание новой книги
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);  // Возвращаем 201 и созданную книгу
    }

    // Обновление книги по ID
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        if (!bookService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга не найдена для обновления");
        }
        Book updatedBook = bookService.update(id, book);
        return ResponseEntity.ok(updatedBook);  // Возвращаем 200 и обновленную книгу
    }

    // Удаление книги по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга не найдена для удаления");
        }
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();  // Возвращаем 204 (No Content) после успешного удаления
    }
}
