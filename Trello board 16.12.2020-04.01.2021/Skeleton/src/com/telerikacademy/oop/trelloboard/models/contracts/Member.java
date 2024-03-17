package com.telerikacademy.oop.trelloboard.models.contracts;

import java.util.List;

public interface Member {

    String getName();

    void setName(String name);

    List<WorkItem> getCreatedWorkItems();

    List<Assignable> getAssignedWorkItems();

    List<ActivityHistory> getActivityHistory();

    void addCreatedItem(WorkItem item);

    void addAssignedItem(Assignable item);

    void removeCreatedItem(WorkItem item);

    void removeAssignedItem(Assignable item);

    WorkItem findCreatedWorkItem(int id);

    Assignable findAssignedWorkItem(int id);

    String viewInfo();

    String toString();
}
