package ru.yurfff.vladbook.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private String pickupLocation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> items;

    // Пустой конструктор для JPA
    public Order() {}

    // Конструктор с параметрами
    public Order(String customerName, String address, OrderStatus status, String pickupLocation, List<OrderItem> items) {
        this.customerName = customerName;
        this.address = address;
        this.status = status;
        this.pickupLocation = pickupLocation;
        this.items = items;
    }

    // Можно добавить методы для бизнес-логики, например:
    public void addItem(OrderItem item) {
        this.items.add(item);
        item.setOrder(this); // Не забываем обновить связь в OrderItem
    }

    // Можно добавить метод для изменения статуса, если потребуется
    public void updateStatus(OrderStatus newStatus) {
        // Логика проверки допустимых изменений статуса
        this.status = newStatus;
    }
}
