package com.example.order;

public class ShippedState implements OrderState {

    private static final String stateName = "Shipped";
    
    @Override
    public void readyForShipment(Order order) {
        throw new IllegalStateException("Illegal state transition: " + order.getState().getStateName() + " -> " + stateName);
    }


    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Illegal state transition: " + order.getState().getStateName() + " -> " + stateName);        
    }

    @Override
    public String getStateName() {
        return stateName;
    }

}
