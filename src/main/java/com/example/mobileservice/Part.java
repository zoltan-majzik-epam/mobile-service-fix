package com.example.mobileservice;

/**
 *
 * @author vrg
 */
public class Part {
    public PartType type;
    public boolean failing;
    public String name;

    public Part(PartType type, boolean failing, String name) {
        this.type = type;
        this.failing = failing;
        this.name = name;
    }
    
    
}
