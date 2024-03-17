package com.telerikacademy.oop.trelloboard.models.common.enums;

import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Status;

public enum FeedbackStatus implements Status {
    NEW("New"),
    UNSCHEDULED("Unscheduled"),
    SCHEDULED("Scheduled"),
    DONE("Done");


    private String value;

    FeedbackStatus(String value) {
        this.value = value;
    }

    @Override
    public Status getPrevious() {
        switch (this) {
            case DONE:
                return SCHEDULED;
            case SCHEDULED:
                return UNSCHEDULED;
            case UNSCHEDULED:
                throw new InvalidInputException("You cannot revert status from Unscheduled to New.");
            case NEW:
                throw new InvalidInputException("Status is already New");
            default:
                throw new InvalidInputException();
        }
    }

    @Override
    public Status getNext() {
        switch (this) {
            case NEW:
                return UNSCHEDULED;
            case UNSCHEDULED:
                return SCHEDULED;
            case SCHEDULED:
                return DONE;
            case DONE:
                throw new InvalidInputException("Status is already Done.");
            default:
                throw new InvalidInputException();
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
