package com.telerikacademy.oop.trelloboard.models.contracts;

import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;

public interface Assignable extends WorkItem {
    Member getAssignee();

    void setAssignee(Member member);

    PriorityType getPriorityType();

    void setPriorityType(PriorityType priorityType);

    String toString();

}
