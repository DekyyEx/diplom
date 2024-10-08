package ru.yurfff.vladbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // Перенаправляет пользователя на страницу с книгами
    @GetMapping("/books")
    public String redirectToBooksPage() {
        return "books";  // Здесь мы отдаем страницу "books.html" или соответствующий фрагмент
    }

    // Перенаправляет пользователя на страницу корзины
    @GetMapping("/cart")
    public String redirectToCartPage() {
        return "cart";   // Здесь мы отдаем страницу "cart.html"
    }

    // Перенаправляет пользователя на страницу заказов
    @GetMapping("/orders")
    public String redirectToOrdersPage() {
        return "orders"; // Здесь мы отдаем страницу "orders.html"
    }
}
