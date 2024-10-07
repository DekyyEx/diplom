package ru.yurfff.vladbook.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW("Новый заказ"),
    IN_PROGRESS("В процессе выполнения"),
    COMPLETED("Завершён"),
    CANCELLED("Отменён");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public boolean canChangeTo(OrderStatus newStatus) {
        return switch (this) {
            case NEW -> newStatus == IN_PROGRESS || newStatus == CANCELLED;
            case IN_PROGRESS -> newStatus == COMPLETED || newStatus == CANCELLED;
            case COMPLETED, CANCELLED -> false;
            default -> false;
        };
    }
}
