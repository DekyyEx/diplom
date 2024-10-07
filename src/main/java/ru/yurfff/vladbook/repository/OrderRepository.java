package ru.yurfff.vladbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yurfff.vladbook.model.Order;
import ru.yurfff.vladbook.model.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByCustomerName(String customerName);
    List<Order> findByPickupLocation(String pickupLocation);
    List<Order> findByStatusAndCustomerName(OrderStatus status, String customerName);

    @Query("SELECT o FROM Order o WHERE o.status = ?1")
    List<Order> findOrdersByStatus(String status);

    @Query("SELECT o FROM Order o JOIN o.items i WHERE i.bookTitle = ?1")
    List<Order> findOrdersByBookTitle(String bookTitle);
}
