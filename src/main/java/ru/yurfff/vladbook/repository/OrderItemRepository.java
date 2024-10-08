package ru.yurfff.vladbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yurfff.vladbook.model.Book;
import ru.yurfff.vladbook.model.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Метод для поиска OrderItem по книге
    List<OrderItem> findByBook(Book book);
}
