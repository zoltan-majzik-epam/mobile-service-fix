package com.example.mobileservice.supplier;

import com.example.mobileservice.Order;
import com.example.mobileservice.PartType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author vrg
 */
public class Supplier {

    private static final AtomicLong counter = new AtomicLong();

    private Queue<Order> ordersToSatisfy = new LinkedList<>();
    private Queue<Order> satisfiedOrders = new LinkedList<>();
    private Map<Long, Order> orderById = new HashMap<>();
    
    private TaskScheduler scheduler = TaskScheduler.INSTANCE;

    /**
     * Orders parts of the specified type and the specified quantity.
     *
     * @param partType
     * @param quantity
     * @return orderId
     */
    public Long orderPart(PartType partType, int quantity) {
        if (partType == null) {
            throw new IllegalArgumentException("partType cannot be null");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity should be at least 1");
        }
        Order order = new Order(counter.incrementAndGet());
        order.setType(partType);
        order.setQuantity(quantity);
        orderById.put(order.getId(), order);
        System.out.println("New order: " + order);
        scheduler.scheduleTaskToRandomTime(new SatisfyOrderNeedsTask(order), 1, 10, TimeUnit.SECONDS);
        return order.getId();
    }
    
    public boolean isReadyForShipment(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderId cannot be null");
        }
        Order order = orderById.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("No such order Id: " + orderId);
        }
        return order.getStatus() == Order.Status.READY_FOR_SHIPMENT;
    }

    public Order shipOrder(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderId cannot be null");
        }
        Order order = orderById.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("No such order Id: " + orderId);
        }
        try {
            order.setStatus(Order.Status.SHIPPED);
        } catch (IllegalStateException ex) {
            throw new IllegalArgumentException("Order should be in READY_FOR_SHIPMENT state in order to be shipped successfully.", ex);
        }
        System.out.println("Order shipped: " + order);
        return order;
    }

}
