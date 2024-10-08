package ru.yurfff.vladbook.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yurfff.vladbook.model.Order;
import ru.yurfff.vladbook.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Получение всех заказов
    @GetMapping
    public List<Order> getAllOrders() {
        List<Order> orders = orderService.findAll();
        if (orders.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No orders found");
        }
        return orders;
    }

    // Получение заказа по ID
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with id: " + id));
    }

    // Получение заказов по статусу
    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable String status) {
        List<Order> orders = orderService.findByStatus(status);
        if (orders.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No orders found with status: " + status);
        }
        return orders;
    }

    // Создание нового заказа
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@Valid @RequestBody Order order) {
        // Проверка бизнес-логики, например, если в корзине нет товаров или если заказ невалиден
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must contain items");
        }
        return orderService.save(order);
    }

    // Обновление существующего заказа по ID
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        if (!orderService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found for update with id: " + id);
        }
        return orderService.update(id, order);
    }

    // Удаление заказа по ID
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        if (!orderService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found for deletion with id: " + id);
        }
        orderService.deleteById(id);
    }
}
