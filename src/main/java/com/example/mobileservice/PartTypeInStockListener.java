package com.example.mobileservice;

import com.example.mobile.part.PartType;

public interface PartTypeInStockListener {
    /**
     * "Fires" when an order is shipped, and a parttype is in the stock.
     * @param type
     */
    public void partTypeInStockListener(PartType type);
}
