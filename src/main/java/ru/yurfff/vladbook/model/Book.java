package ru.yurfff.vladbook.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Author is mandatory")
    @Column(nullable = false)
    private String author;

    @Column
    private String isbn;

    @PositiveOrZero(message = "Price must be greater than or equal to zero")
    @Column(nullable = false)
    private BigDecimal price;

    @Setter
    @Column(nullable = false)
    private Boolean available = true;

    @Setter
    @Getter
    @Column
    private int publishedYear;

    // Конструктор по умолчанию
    public Book() {
    }

    // Конструктор с обязательными параметрами
    public Book(String title, String author, String isbn, BigDecimal price, int publishedYear, Boolean available) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.publishedYear = publishedYear;
        this.available = available != null ? available : true;  // Значение по умолчанию для доступности
    }

    public boolean isAvailable() {
        return available;
    }
}
