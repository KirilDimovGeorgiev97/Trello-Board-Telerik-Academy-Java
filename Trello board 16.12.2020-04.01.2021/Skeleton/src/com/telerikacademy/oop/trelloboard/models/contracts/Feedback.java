package com.telerikacademy.oop.trelloboard.models.contracts;

public interface Feedback extends WorkItem {

    int getRating();

    void setRating(int rating);

}
