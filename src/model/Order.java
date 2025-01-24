package model;

import enums.Side;

import java.util.Objects;
import java.util.UUID;

public class Order {
    private final String orderId;
    private final long price;
    private int quantity;
    private final Side side;

    public Order(long price, int quantity, Side side) {
        orderId = String.valueOf(UUID.randomUUID());
        this.price = price;
        this.quantity = quantity;
        this.side = side;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public long getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Side getSide() {
        return side;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order order)) return false;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId);
    }
}
