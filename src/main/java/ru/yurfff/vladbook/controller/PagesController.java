package ru.yurfff.vladbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Добро пожаловать!");
        return "home";  // Возвращает представление (HTML) с именем "home"
    }

    // Можно добавить редирект на главную страницу
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; // Перенаправление на страницу /home
    }
}
