package ru.yurfff.vladbook.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yurfff.vladbook.model.Book;
import ru.yurfff.vladbook.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks(Pageable pageable) {
        return (List<Book>) bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    @GetMapping("/by-author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        List<Book> books = bookService.findByAuthor(author);
        if (books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No books found for this author");
        }
        return books;
    }

    @GetMapping("/search/title")
    public List<Book> searchBooksByTitle(@RequestParam String title) {
        return bookService.findByTitleContaining(title);
    }

    @GetMapping("/search/price")
    public List<Book> searchBooksByPrice(@RequestParam Double price) {
        return bookService.findByPriceLessThanEqual(price);
    }

    @GetMapping("/search/author-title")
    public Page<Book> searchBooksByAuthorAndTitle(
            @RequestParam String author,
            @RequestParam String title,
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookService.findByAuthorAndTitleContaining(author, title, pageable);
    }

    @GetMapping("/search/author-price")
    public List<Book> searchBooksByAuthorAndPrice(
            @RequestParam String author,
            @RequestParam Double price) {
        return bookService.findBooksByAuthorAndPriceLessThanEqual(author, price);
    }

    @PostMapping
    public Book createBook(@Valid @RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        if (!bookService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found for update");
        }
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        if (!bookService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found for deletion");
        }
        bookService.deleteById(id);
    }
}
