package com.telerikacademy.oop.trelloboard.models.common.enums;

public enum Size {
    LARGE("Large", 3),
    MEDIUM("Medium", 2),
    SMALL("Small", 1);

    private String value;
    private int comp;

    Size(String value, int comp){
        this.value=value;
        this.comp = comp;
    }

    public int getComp() {
        return comp;
    }

    @Override
    public String toString() {
        return value;
    }
}
