package com.telerikacademy.oop.trelloboard.models.common.enums;

public enum Severity {
    CRITICAL("Critical", 3),
    MAJOR("Major", 2),
    MINOR("Minor", 1);

    private String value;
    private int comp;

    Severity(String value, int comp){
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
