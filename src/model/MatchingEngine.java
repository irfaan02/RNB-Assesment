package model;

import java.util.List;

public class MatchingEngine {
    private final OrderBook orderBook;

    public MatchingEngine(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public void processOrder(Order order) {
        List<Order> logBookOrders = orderBook.GetOrdersAtBidLevel(order.getPrice(), order.getSide());
        if (logBookOrders.isEmpty()) {
            throw new IllegalStateException("No matching orders found at price point: " + order.getPrice());
        }
        int quantity = order.getQuantity();
        for (Order logBookOrder : logBookOrders) {
            if (logBookOrder.getQuantity() >= quantity) {
                logBookOrder.setQuantity(logBookOrder.getQuantity() - quantity);
                break;
            } else {
                int availableQuantity = logBookOrder.getQuantity();
                logBookOrder.setQuantity(0);
                quantity -= availableQuantity;
            }
        }
        List<Order> nonZeroQuantityList = logBookOrders.stream()
                .filter(orderQuantity -> orderQuantity.getQuantity() > 0)
                .toList();
        orderBook.SetOrdersAtBidLevel(order, nonZeroQuantityList);
    }

}
