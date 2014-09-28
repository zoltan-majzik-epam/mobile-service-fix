package com.example.order;

public class OrderedState implements OrderState {

    private static final String stateName = "Ordered";
    
    @Override
    public void readyForShipment(Order order) {
        order.setState(new ReadyForShipmentState());
        order.getSupplier().readyForShipping(order);
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
