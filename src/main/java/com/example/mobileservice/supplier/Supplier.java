package com.example.mobileservice.supplier;

import com.example.mobile.part.PartType;
import com.example.order.Order;
import com.example.order.OrderListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class Supplier implements OrderListener {

    private static final AtomicLong counter = new AtomicLong();

    private Map<Long, Order> orderById;    
    private TaskScheduler scheduler;
    private Map<PartType, OrderListener> orderListeners;
    
    public Supplier() {
        orderById = new HashMap<>();
        scheduler = TaskScheduler.getInstance();
        orderListeners = new HashMap<>();
    }
    
    
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
        
        Order order = new Order(counter.incrementAndGet(), partType, quantity, this);
        orderById.put(order.getId(), order);
        System.out.println("New order: " + order);
        scheduler.scheduleTaskToRandomTime(new SatisfyOrderNeedsTask(order), 1, 10, TimeUnit.SECONDS);
        return order.getId();
    }
    
    public void addOrderListener(OrderListener listener, PartType type) {
        orderListeners.put(type, listener);
    }

    public Order shipOrder(Order order) {
        try {
            order.ship();
        } catch (IllegalStateException ex) {
            throw new IllegalArgumentException("Order should be in READY_FOR_SHIPMENT state in order to be shipped successfully.", ex);
        }
        return order;
    }
    


    @Override
    public void readyForShipping(Order order) {
        PartType type = order.getType();
        OrderListener listener = orderListeners.get(type);
        if (listener != null) {
            listener.readyForShipping(order);
            orderListeners.remove(type);
        }
    }

}
