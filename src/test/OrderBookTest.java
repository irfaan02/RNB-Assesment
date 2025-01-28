package test;

import enums.Side;
import model.Order;
import model.OrderBook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OrderBookTest {
    private OrderBook orderBook;
    @Before
    public void setUp() {
        orderBook = new OrderBook();
    }
    @Test
    public void testAddingOneOrder(){
        Order order = new Order(1,1, Side.BUY);
        orderBook.addOrder(order);
        List<Order> orders = orderBook.GetOrdersAtBidLevel(1, Side.BUY);
        assertEquals(1, orders.size());
        assertEquals(order,orders.getFirst());
    }
    @Test
    public void testAddingTwoOrderATSamePriceLevel(){
        Order order = new Order(1,1, Side.BUY);
        Order orderTwo = new Order(1,1, Side.BUY);
        orderBook.addOrder(order);
        orderBook.addOrder(orderTwo);
        List<Order> orders = orderBook.GetOrdersAtBidLevel(1, Side.BUY);
        assertEquals(2, orders.size());
        assertEquals(order,orders.getFirst());
        assertEquals(orderTwo,orders.getLast());
    }
    @Test
    public void testAddingTwoOrderATDifferentPriceLevel(){
        Order order = new Order(1,1, Side.BUY);
        Order orderTwo = new Order(2,1, Side.BUY);
        orderBook.addOrder(order);
        orderBook.addOrder(orderTwo);
        List<Order> ordersAtBidLevelOne = orderBook.GetOrdersAtBidLevel(1, Side.BUY);
        List<Order> ordersAtBidLevelTwo = orderBook.GetOrdersAtBidLevel(2, Side.BUY);
        assertEquals(1, ordersAtBidLevelOne.size());
        assertEquals(order,ordersAtBidLevelOne.getFirst());
        assertEquals(1,ordersAtBidLevelTwo.size());
        assertEquals(orderTwo,ordersAtBidLevelTwo.getFirst());
    }

    @Test
    public void testAddingTwoOrderOnDifferentSide(){
        Order order = new Order(1,1, Side.BUY);
        Order orderTwo = new Order(2,1, Side.SELL);
        orderBook.addOrder(order);
        orderBook.addOrder(orderTwo);
        List<Order> ordersAtBidLevelOne = orderBook.GetOrdersAtBidLevel(1, Side.BUY);
        List<Order> ordersAtBidLevelTwo = orderBook.GetOrdersAtBidLevel(2, Side.SELL);
        assertEquals(1, ordersAtBidLevelOne.size());
        assertEquals(order,ordersAtBidLevelOne.getFirst());
        assertEquals(1,ordersAtBidLevelTwo.size());
        assertEquals(orderTwo,ordersAtBidLevelTwo.getFirst());
    }
    @Test
    public void testDeleteSingleOrder(){
        Order order = new Order(1,1, Side.BUY);
        orderBook.addOrder(order);
        orderBook.deleteOrder(order.getOrderId());
        List<Order> orders = orderBook.GetOrdersAtBidLevel(1, Side.BUY);
        assertTrue(orders.isEmpty());
    }
    @Test
    public void testDeleteWhenOrderIdDoesNotExist(){
        assertThrows(RuntimeException.class,()->orderBook.deleteOrder("123"));
    }
    @Test
    public void testModifyWhenOrderIdDoesNotExist(){
        assertThrows(RuntimeException.class,()->orderBook.modifyOrder("123",1));
    }
    @Test
    public void testModifyWhenQuantityIsLessThanZero(){
        assertThrows(IllegalArgumentException.class,()->orderBook.modifyOrder("123",-1));
    }
    @Test
    public void testModifyOrder(){
        Order orderOne = new Order(1,1, Side.BUY);
        Order orderTwo = new Order(2,1, Side.BUY);
        orderBook.addOrder(orderOne);
        orderBook.addOrder(orderTwo);
        orderBook.modifyOrder(orderOne.getOrderId(),20);
        List<Order> orders = orderBook.GetOrdersAtBidLevel(1, Side.BUY);
        Assert.assertEquals(20,orders.getLast().getQuantity());
        Assert.assertEquals(orderOne.getOrderId(),orders.getLast().getOrderId());
    }
}