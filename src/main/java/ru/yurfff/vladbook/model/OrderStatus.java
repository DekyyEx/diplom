package ru.yurfff.vladbook.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW("Новый заказ"),  // Заказ только что создан
    IN_PROGRESS("В процессе выполнения"),  // Заказ в обработке
    COMPLETED("Завершён"),  // Заказ выполнен
    CANCELLED("Отменён");  // Заказ отменён

    private final String displayName;

    // Конструктор для присваивания отображаемого имени
    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    // Проверяет, можно ли изменить статус заказа на новый
    public boolean canChangeTo(OrderStatus newStatus) {
        // В зависимости от текущего статуса проверяем, можно ли сменить статус
        return switch (this) {
            case NEW -> newStatus == IN_PROGRESS || newStatus == CANCELLED; // Новый заказ можно начать или отменить
            case IN_PROGRESS -> newStatus == COMPLETED || newStatus == CANCELLED; // Заказ в процессе можно завершить или отменить
            case COMPLETED, CANCELLED -> false; // Завершённый или отменённый заказ не может сменить статус
            default -> false; // Неизвестный статус
        };
    }
}
