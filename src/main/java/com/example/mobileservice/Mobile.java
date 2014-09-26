package com.example.mobileservice;

/**
 *
 * @author vrg
 */
public class Mobile {
    public Manufacturer manufacturer;
    public String model;
    public Part display;
    public Part motherBoard;
    public Part keyboard;
    public Part microphone;
    public Part speaker;
    public Part volumeButtons;
    public Part powerSwitch;
    public String otherInfo;

    @Override
    public String toString() {
        return manufacturer + " " + model + ", other Info: " + otherInfo;
    }
    
    
}
