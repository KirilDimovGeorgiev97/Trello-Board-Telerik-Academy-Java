package com.telerikacademy.oop.trelloboard.models.contracts;

import com.telerikacademy.oop.trelloboard.models.common.enums.BugStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Severity;

import java.util.List;

public interface Bug extends WorkItem, Assignable {
    List<String> getSteps();

    Severity getSeverity();

    PriorityType getPriorityType();

    void addSteps(String step);

    void removeStep(int id);

    String findStep(int id);

    void setPriorityType(PriorityType priorityType);

    void setSeverity(Severity severity);
}
