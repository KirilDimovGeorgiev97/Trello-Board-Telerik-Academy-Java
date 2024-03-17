package com.telerikacademy.oop.trelloboard.models.contracts;

import java.time.LocalDate;

public interface ActivityHistory {
    String getDescription();
    LocalDate getMoment();
}
