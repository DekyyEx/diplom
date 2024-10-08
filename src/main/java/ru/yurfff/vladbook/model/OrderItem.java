package ru.yurfff.vladbook.model;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bookTitle;

    @Column(nullable = false)
    @Positive
    private BigDecimal price;

    @Column(nullable = false)
    @PositiveOrZero
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // Default constructor
    public OrderItem() {
    }

    // Constructor with parameters
    public OrderItem(String bookTitle, BigDecimal price, Integer quantity, Book book, Order order) {
        this.bookTitle = bookTitle;
        this.price = price;
        this.quantity = quantity;
        this.book = book;
        this.order = order;
    }

    // Utility method to calculate total price
    public BigDecimal getTotalPrice() {
        return price.multiply(new BigDecimal(quantity));
    }
}
