package com.example.order;

public class ReadyForShipmentState implements OrderState {

    private static final String stateName = "Ready for shipment";
    
    @Override
    public void readyForShipment(Order order) {
        throw new IllegalStateException("Illegal state transition: " + order.getState().getStateName() + " -> " + stateName);
    }

    @Override
    public void ship(Order order) {
        System.out.println("Order shipped: " + order);
        order.setState(new ShippedState());        
    }

    @Override
    public String getStateName() {
        return stateName;
    }

}
