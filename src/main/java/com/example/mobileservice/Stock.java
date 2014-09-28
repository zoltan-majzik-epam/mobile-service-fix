package com.example.mobileservice;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.example.mobile.part.PartType;
import com.example.mobileservice.supplier.Supplier;
import com.example.order.Order;
import com.example.order.OrderListener;

public class Stock implements OrderListener {

    private static Stock instance;

    private volatile Map<PartType, Integer> stock;
    private volatile Map<PartType,Long> orderIdByPartType;
    private Supplier supplier;
    private Set<PartTypeInStockListener> partInStockListeners;

    private Stock() {
        stock = new HashMap<>();
        supplier = new Supplier();
        orderIdByPartType = new HashMap<>();
        partInStockListeners = new HashSet<>();
    }

    public static Stock getInstance() {
        if (instance == null) {
            synchronized (Stock.class) {
                if (instance == null) {
                    instance = new Stock();
                }
            }
        }
        return instance;
    }

    
    private synchronized void addToStock(PartType type, Integer count) {
        Integer stockCount = stock.get(type);
        if (stockCount == null || stockCount == 0) {
            stock.put(type, count);
        } else {
            stock.put(type, stockCount + count);
        }
    }
    
    public synchronized boolean inStock(PartType type) {
        boolean inStock = true;
        Integer stockCount = stock.get(type);
        if (stockCount == null || stockCount == 0) {
            inStock = false;
        }
        return inStock;
    }

    /**
     * Get one part from stock...
     * @param type
     */
    public synchronized void decrementStock(PartType type) {
        if (!inStock(type)) {
            Integer stockCount = stock.get(type);
            stock.put(type, stockCount - 1);
        }
    }

    
    public synchronized void addOrderForMissingPart(PartType type) {
        if (!orderIdByPartType.containsKey(type)) {
            orderIdByPartType.put(type, null);
        }
    }
    
    public synchronized void orderParts() {
        if (!orderIdByPartType.isEmpty()) {
            for (Map.Entry<PartType, Long> entry : orderIdByPartType.entrySet()) {
                Long orderId = entry.getValue();
                if (orderId == null) {
                    orderId = supplier.orderPart(entry.getKey(), 5);
                    supplier.addOrderListener(this, entry.getKey());
                    entry.setValue(orderId);
                } 
            }
        }
    }
    
    
    public synchronized void addPartInStockListener(PartTypeInStockListener listener) {
        partInStockListeners.add(listener);
    }
    
    public synchronized void removePartInStockListener(PartTypeInStockListener listener) {
        partInStockListeners.remove(listener);
    }
    
    private void callPartInStockListeners(PartType type) {
        if (!partInStockListeners.isEmpty()) {
            for (PartTypeInStockListener partInStockListener : partInStockListeners) {
                partInStockListener.partTypeInStockListener(type);
            }
        }
    }
    

    /**
     * Will fire when one order is ready for shipping. Then ship it, and update the stock.
     * Also, notice the listeners, that the given part is in stock.
     */
    @Override
    public void readyForShipping(Order order) {
        Order shippedOrder = supplier.shipOrder(order);
        addToStock(shippedOrder.getType(), shippedOrder.getQuantity());
        orderIdByPartType.remove(shippedOrder.getType());
        
        callPartInStockListeners(shippedOrder.getType());
    }

}
