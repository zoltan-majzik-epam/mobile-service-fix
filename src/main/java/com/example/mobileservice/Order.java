package com.example.mobileservice;

/**
 *
 * @author vrg
 */
public class Order {

    public enum Status {

        ORDERED,
        READY_FOR_SHIPMENT,
        SHIPPED
    }

    private long id;
    private PartType type;
    private int quantity;
    private Status status;

    public Order(long id) {
        this.id = id;
        this.status = Status.ORDERED;
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public PartType getType() {
        return type;
    }

    public void setType(PartType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(Status newStatus) {
        switch (newStatus) {
            case READY_FOR_SHIPMENT:
                if (status != Status.ORDERED) {
                    throw new IllegalStateException("Illegal state transition: " + status + " -> " + newStatus);
                }
                break;
            case SHIPPED:
                if (status != Status.READY_FOR_SHIPMENT) {
                    throw new IllegalStateException("Illegal state transition: " + status + " -> " + newStatus);
                }
                break;
            default:
                throw new IllegalStateException("Illegal state transition: " + status + " -> " + newStatus);
        }
        status = newStatus;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", type=" + type + ", quantity=" + quantity + ", status=" + status + '}';
    }
    
    
}
