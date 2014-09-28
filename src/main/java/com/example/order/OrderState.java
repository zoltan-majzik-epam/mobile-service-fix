package com.example.order;

public interface OrderState {
    

    public void readyForShipment(Order order);
    public void ship(Order order);
    public String getStateName();
}
