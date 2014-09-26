package com.example.mobileservice.supplier;

import com.example.mobileservice.Order;

/**
 *
 * @author vrg
 */
public class SatisfyOrderNeedsTask implements Runnable {
    private Order order;

    public SatisfyOrderNeedsTask(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        order.setStatus(Order.Status.READY_FOR_SHIPMENT);
    }
    
}
