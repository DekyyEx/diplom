package ru.yurfff.vladbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.yurfff.vladbook.model.Order;
import ru.yurfff.vladbook.model.OrderItem;
import ru.yurfff.vladbook.model.OrderStatus;
import ru.yurfff.vladbook.model.Book;
import ru.yurfff.vladbook.repository.OrderRepository;
import ru.yurfff.vladbook.repository.OrderItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BookService bookService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        BookService bookService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.bookService = bookService;
    }

    // Создание нового заказа
    @Transactional
    public Order createOrder(Order order) {
        // Проверка наличия элементов в заказе
        List<OrderItem> orderItems = order.getItems();
        if (orderItems == null || orderItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The order must contain at least one item.");
        }

        // Проверяем доступность всех книг в заказе
        for (OrderItem orderItem : orderItems) {
            Book book = orderItem.getBook();
            // Книга должна быть доступна (available == true), если нет - выбрасываем исключение
            if (!book.isAvailable()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The book " + book.getTitle() + " is no longer available.");
            }
        }

        // Связываем все OrderItems с заказом
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }

        // Сохраняем заказ и все элементы
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        // Обновляем доступность книг, которые были добавлены в заказ
        for (OrderItem orderItem : orderItems) {
            Book book = orderItem.getBook();
            bookService.removeBookFromAvailable(book.getId()); // Убираем книгу из доступных
        }

        return order;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findByStatus(String status) {
        try {
            return orderRepository.findByStatus(OrderStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order status: " + status);
        }
    }

    public List<Order> findByCustomerName(String customerName) {
        return orderRepository.findByCustomerName(customerName);
    }

    public List<Order> findByPickupLocation(String pickupLocation) {
        return orderRepository.findByPickupLocation(pickupLocation);
    }

    public List<Order> findByStatusAndCustomerName(String status, String customerName) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            return orderRepository.findByStatusAndCustomerName(orderStatus, customerName);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order status: " + status);
        }
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Long id, Order order) {
        order.setId(id);
        return orderRepository.save(order);
    }

    public void deleteById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        orderRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }
}
