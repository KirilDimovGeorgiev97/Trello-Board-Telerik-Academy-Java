package com.telerikacademy.oop.trelloboard.models.common.enums;

import com.telerikacademy.oop.trelloboard.models.contracts.Status;

public enum BugStatus implements Status {
    ACTIVE("Active"),
    FIXED("Fixed");

    private String value;

    BugStatus(String value) {
        this.value = value;
    }

    @Override
    public Status getPrevious() {
        if (BugStatus.values()[this.ordinal()] == ACTIVE)
            return ACTIVE;
        return BugStatus.values()[this.ordinal() - 1];
    }

    @Override
    public Status getNext() {
        if (BugStatus.values()[this.ordinal()] == FIXED)
            return FIXED;
        return BugStatus.values()[this.ordinal() + 1];
    }


    @Override
    public String toString() {
        return value;
    }
}
