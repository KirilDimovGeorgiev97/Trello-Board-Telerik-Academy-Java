package com.telerikacademy.oop.trelloboard.models.contracts;

import com.telerikacademy.oop.trelloboard.models.worksitems.WorkItemBase;

import java.util.List;

public interface Board {
    //List<Object> getActivityHistory();

    List<WorkItem> getWorkItems();

    String getName();

    void setName(String name);

    void addWorkItem(WorkItem workItem);

    void removeWorkItem(String workItemsTitle);

    void removeWorkItem(int id);

    WorkItem findWorkItem(String name);

    WorkItem findWorkItem(int id);

    String toString();

    String viewInfo();

}
