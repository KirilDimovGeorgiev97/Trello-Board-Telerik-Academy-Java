package com.telerikacademy.oop.trelloboard.models;

import com.telerikacademy.oop.trelloboard.models.common.Validator;
import com.telerikacademy.oop.trelloboard.models.contracts.ActivityHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ActivityHistoryImpl implements ActivityHistory {
    private String description;
    private LocalDate moment;

    public ActivityHistoryImpl(String description) {
        setDescription(description);
        moment = LocalDate.now();
    }

    @Override
    public String getDescription() {
        return description;
    }

    private void setDescription(String description){
        Validator.checkIfNull(description,"Invalid input");
        this.description=description;
    }

    @Override
    public LocalDate getMoment() {
        return moment;
    }

    @Override
    public String toString() {
        LocalDateTime now = this.moment.atTime((LocalTime.now()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String res = now.format(formatter);
        return String.format("[%s] %s",res,this.description);
    }

}
