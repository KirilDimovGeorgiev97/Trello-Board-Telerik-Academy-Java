package com.telerikacademy.oop.trelloboard.models.worksitems;

import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.contracts.Assignable;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Status;

import java.time.LocalDate;

public abstract class AssignableItemBase extends WorkItemBase implements Assignable {

    private PriorityType priorityType;
    private Member assignee;

    public AssignableItemBase(int id, String title, String description, Status status, LocalDate dueDate,
                              PriorityType priorityType) {
        super(id, title, description, status, dueDate);
        this.priorityType = priorityType;
    }

    @Override
    public PriorityType getPriorityType() {
        return priorityType;
    }

    @Override
    public void setPriorityType(PriorityType priorityType) {
        this.priorityType = priorityType;
    }

    @Override
    public Member getAssignee() {
        return assignee;
    }

    @Override
    public void setAssignee(Member assignee) {
        this.assignee = assignee;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AssignableItemBase)) {
            return false;
        }

        return super.equals(obj)
                && (((AssignableItemBase) obj).getPriorityType().equals(priorityType));
    }
}
