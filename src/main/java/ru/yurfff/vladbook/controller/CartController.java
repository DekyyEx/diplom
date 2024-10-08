package ru.yurfff.vladbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yurfff.vladbook.model.Order;
import ru.yurfff.vladbook.model.OrderItem;
import ru.yurfff.vladbook.service.CartService;
import ru.yurfff.vladbook.service.BookService;
import ru.yurfff.vladbook.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final BookService bookService;
    private final OrderService orderService;

    @Autowired
    public CartController(CartService cartService, BookService bookService, OrderService orderService) {
        this.cartService = cartService;
        this.bookService = bookService;
        this.orderService = orderService;
    }

    // Получение всех элементов корзины
    @GetMapping
    public ResponseEntity<List<OrderItem>> getCartItems() {
        List<OrderItem> cartItems = cartService.getAllCartItems();
        return ResponseEntity.ok(cartItems);
    }

    // Удаление элемента из корзины (и возврат книги в доступные)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id) {
        // Получаем элемент корзины
        OrderItem orderItem = cartService.getCartItemById(id);

        if (orderItem == null) {
            return ResponseEntity.notFound().build(); // Возвращаем 404, если элемент не найден
        }

        // Удаляем книгу из корзины
        cartService.removeFromCart(id);

        // Возвращаем книгу в доступные книги
        bookService.returnBookToAvailable(orderItem.getBook().getId());

        return ResponseEntity.noContent().build();
    }

    // Добавление книги в корзину
    @PostMapping("/add")
    public ResponseEntity<OrderItem> addToCart(@RequestParam Long bookId, @RequestParam Integer quantity) {
        // Проверяем наличие книги
        if (bookId == null || quantity == null || quantity <= 0) {
            return ResponseEntity.badRequest().build(); // Если некорректные данные, возвращаем 400
        }

        // Сохраняем книгу в корзину
        OrderItem addedItem = cartService.addToCart(bookId, quantity);

        // Удаляем книгу из доступных
        bookService.removeBookFromAvailable(bookId);

        return ResponseEntity.ok(addedItem);
    }

    // Оформление заказа (завершаем процесс корзины)
    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@RequestBody Order order) {
        // Проверяем, есть ли элементы в корзине
        if (order.getItems() == null || order.getItems().isEmpty()) {
            return ResponseEntity.badRequest().build(); // Если корзина пуста, возвращаем 400 Bad Request
        }

        // Оформляем заказ
        Order placedOrder = orderService.createOrder(order);

        // Очистка корзины
        cartService.clearCart();

        // Возвращаем успешно оформленный заказ
        return ResponseEntity.ok(placedOrder);
    }
}
