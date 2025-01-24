package model;

import enums.Side;

import java.util.*;

public class OrderBook {
   private Map<Long, List<Order>> limitOrderBookBuy =new TreeMap<>(Comparator.reverseOrder());
   private Map<Long, List<Order>> limitOrderBookSell =new TreeMap<>();
   private Map<String,Order> orderIndex = new HashMap<>();

    public void addOrder(Order order){
        Map<Long, List<Order>> bidLevelPriceAndOrder = order.getSide().equals(Side.BUY) ? limitOrderBookBuy : limitOrderBookSell;
        bidLevelPriceAndOrder.computeIfAbsent(order.getPrice(), key -> new LinkedList<>()).addLast(order);
        orderIndex.put(order.getOrderId(), order);
    }

    public void deleteOrder(String orderId) {
        Optional.ofNullable(orderIndex.get(orderId))
                .ifPresentOrElse(this::removeOrderFromBidLevel,()->{throw new RuntimeException("model.Order ID does not exist: "+orderId);});
    }

    private void removeOrderFromBidLevel(Order order) {
        Map<Long, List<Order>> bidLevelPriceAndOrder = order.getSide().equals(Side.BUY)
                ? limitOrderBookBuy
                : limitOrderBookSell;

        List<Order> orders = bidLevelPriceAndOrder.get(order.getPrice());
        if (orders != null) {
            orders.remove(order);
            if (orders.isEmpty()) {
                bidLevelPriceAndOrder.remove(order.getPrice());
            }
        }
    }

    public void modifyOrder(String orderId,int quantity){
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        Optional.ofNullable(orderIndex.get(orderId)).ifPresentOrElse(order->{
        deleteOrder(orderId);
        order.setQuantity(quantity);
        addOrder(order);},()-> {throw new RuntimeException("model.Order ID does not exist: "+orderId);});
    }

    public List<Order> OrdersAtBidLevel(long bidLevel, Side side){
        Map<Long, List<Order>> bidLevelPriceAndOrder = side.equals(Side.BUY)? limitOrderBookBuy : limitOrderBookSell;
        return bidLevelPriceAndOrder.get(bidLevel)==null?new LinkedList<>():bidLevelPriceAndOrder.get(bidLevel);
    }

}
