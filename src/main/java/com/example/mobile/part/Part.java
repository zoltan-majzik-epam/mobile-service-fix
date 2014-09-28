package com.example.mobile.part;

public abstract class Part {

    private PartType type;
    private boolean failing;
    private String name;
    private boolean replaced;

    public boolean isReplaced() {
        return replaced;
    }
    
    public void setReplaced(boolean value) {
        this.replaced = value;
    }

    public PartType getType() {
        return type;
    }

    public boolean isFailing() {
        return failing;
    }

    public void setFailing(boolean failing) {
        this.failing = failing;
    }

    public String getName() {
        return name;
    }

    public Part(PartType type, boolean failing, String name) {
        this.type = type;
        this.failing = failing;
        this.name = name;
    }

}
