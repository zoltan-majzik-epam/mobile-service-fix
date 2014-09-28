package com.example.order;

import com.example.mobile.part.PartType;
import com.example.mobileservice.supplier.Supplier;


public class Order {

    private long id;
    private PartType type;
    private int quantity;
    private OrderState state;
    private OrderListener supplier;

    public Order(long id, PartType type, int quantity, OrderListener supplier) {
        super();
        this.id = id;
        this.type = type;
        this.quantity = quantity;
        this.supplier = supplier;
        this.state = new OrderedState();
    }

    public Order(long id) {
        this.id = id;
        this.state = new OrderedState();
    }

    public OrderState getState() {
        return state;
    }

    public long getId() {
        return id;
    }

    public PartType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setState(OrderState state) {
        this.state = state;
    }
    
    public OrderListener getSupplier() {
        return this.supplier;
    }

    public void readyForShipment() {
        state.readyForShipment(this);
    }

    public void ship() {
        state.ship(this);
    }

    public boolean isReadyForShipping() {
        return (state.getClass() == ReadyForShipmentState.class);
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", type=" + type + ", quantity=" + quantity + ", status=" + state.getStateName() + '}';
    }

}
