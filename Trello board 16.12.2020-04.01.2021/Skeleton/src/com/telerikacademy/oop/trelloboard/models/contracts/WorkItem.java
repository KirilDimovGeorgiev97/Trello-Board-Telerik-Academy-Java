package com.telerikacademy.oop.trelloboard.models.contracts;



import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface WorkItem {

    int getId();

    String getTitle();

    String getDescription();

    Map<Member, List<String>> getComments();

    List<ActivityHistory> getActivityHistory();

    void addComment(Member author, String message);

    void removeComment(Member author, String message);

    Status getStatus();

    LocalDate getDueDate();

    void revertStatus();

    void advanceStatus();

    String toString();

}
