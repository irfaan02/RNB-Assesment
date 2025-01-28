package test;

import enums.Side;
import model.MatchingEngine;
import model.Order;
import model.OrderBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchEngineTest {
        private OrderBook orderBook;
        private MatchingEngine matchingEngine;

        @BeforeEach
        void setUp() {
            orderBook = new OrderBook();
            matchingEngine = new MatchingEngine(orderBook);
        }

        @Test
        void processOrderNoMatchingOrders() {
            Order order = new Order(100L, 10, Side.BUY);
            assertThrows(IllegalStateException.class,
                    () -> matchingEngine.processOrder(order));
        }

        @Test
        void processOrderWhenSingleMatchingOrderWithExactQuantity() {
            Order existingOrder = new Order(100L, 10, Side.BUY);
            List<Order> initialOrders = new ArrayList<>();
            initialOrders.add(existingOrder);
            orderBook.SetOrdersAtBidLevel(existingOrder, initialOrders);
            Order incomingOrder = new Order(100L, 10, Side.BUY);
            matchingEngine.processOrder(incomingOrder);
            List<Order> remainingOrders = orderBook.GetOrdersAtBidLevel(100L, Side.BUY);
            assertTrue(remainingOrders.isEmpty());
        }

        @Test
        void processOrderWhenSingleMatchingOrderWithExcessQuantity() {
            Order existingOrder = new Order(100L, 10, Side.SELL);
            List<Order> initialOrders = new ArrayList<>();
            initialOrders.add(existingOrder);
            orderBook.SetOrdersAtBidLevel(existingOrder, initialOrders);
            Order incomingOrder = new Order(100L, 5, Side.SELL);
            matchingEngine.processOrder(incomingOrder);
            List<Order> remainingOrders = orderBook.GetOrdersAtBidLevel(100L, Side.SELL);
            assertEquals(1, remainingOrders.size());
            assertEquals(5, remainingOrders.getFirst().getQuantity());
        }

        @Test
        void processOrderWhenMultipleMatchingOrders() {
            Order existingOrder1 = new Order(100L, 10, Side.SELL);
            Order existingOrder2 = new Order(100L, 10, Side.SELL);
            List<Order> initialOrders = new ArrayList<>();
            initialOrders.add(existingOrder1);
            initialOrders.add(existingOrder2);
            orderBook.SetOrdersAtBidLevel(existingOrder1, initialOrders);
            Order incomingOrder = new Order(100L, 15, Side.SELL);
            matchingEngine.processOrder(incomingOrder);
            List<Order> remainingOrders = orderBook.GetOrdersAtBidLevel(100L, Side.SELL);
            assertEquals(1, remainingOrders.size());
            assertEquals(5, remainingOrders.getFirst().getQuantity());
            assertEquals(0, existingOrder1.getQuantity());
        }

}
