package com.telerikacademy.oop.trelloboard.models.common.enums;

public enum PriorityType {
    HIGH("High",3),
    MEDIUM("Medium",2),
    LOW("Low",1);

    private String value;
    private int comp;

    PriorityType(String value, int comp) {
        this.value = value;
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
