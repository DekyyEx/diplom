package ru.yurfff.vladbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yurfff.vladbook.model.Order;
import ru.yurfff.vladbook.model.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Метод для поиска заказов по статусу
    List<Order> findByStatus(OrderStatus status);

    // Метод для поиска заказов по имени клиента
    List<Order> findByCustomerName(String customerName);

    // Метод для поиска заказов по месту получения (pickupLocation)
    List<Order> findByPickupLocation(String pickupLocation);

    // Метод для поиска заказов по статусу и имени клиента
    List<Order> findByStatusAndCustomerName(OrderStatus status, String customerName);

    // Кастомный запрос для поиска заказов по статусу
    @Query("SELECT o FROM Order o WHERE o.status = ?1")
    List<Order> findOrdersByStatus(String status);

    // Кастомный запрос для поиска заказов по названию книги
    @Query("SELECT o FROM Order o JOIN o.items i WHERE i.bookTitle = ?1")
    List<Order> findOrdersByBookTitle(String bookTitle);
}
