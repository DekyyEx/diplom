package ru.yurfff.vladbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yurfff.vladbook.model.Book;
import ru.yurfff.vladbook.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Получение всех книг с пагинацией
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    // Получение книги по ID
    public Book findById(Long id) throws EntityNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга не найдена с ID: " + id));
    }

    // Поиск книги по названию (частичное совпадение)
    public List<Book> findByTitleContaining(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    // Сохранение новой книги
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    // Обновление существующей книги
    public Book update(Long id, Book book) throws EntityNotFoundException {
        // Проверяем, существует ли книга перед обновлением
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Книга не найдена с ID: " + id);
        }
        book.setId(id);
        return bookRepository.save(book);
    }

    // Удаление книги по ID
    public void deleteById(Long id) throws EntityNotFoundException {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Книга не найдена с ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    // Проверка, существует ли книга с таким ID
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

    // Возврат книги в доступное состояние (available = true)
    public void setBookAvailability(Long id, boolean available) {
        Book book = findById(id);
        book.setAvailable(available);
        bookRepository.save(book);
    }

    public void returnBookToAvailable(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setAvailable(true);
            bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("Book not found with id " + id);
        }
    }

    public void removeBookFromAvailable(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setAvailable(false);  // Устанавливаем доступность в false
            bookRepository.save(book);  // Сохраняем изменения
        } else {
            throw new IllegalArgumentException("Book not found with id " + id);
        }
    }
}