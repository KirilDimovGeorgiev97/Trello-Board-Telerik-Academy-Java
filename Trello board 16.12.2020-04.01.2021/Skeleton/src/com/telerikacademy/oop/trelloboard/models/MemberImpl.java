package com.telerikacademy.oop.trelloboard.models;

import com.telerikacademy.oop.trelloboard.models.common.Constants;
import com.telerikacademy.oop.trelloboard.models.common.Validator;
import com.telerikacademy.oop.trelloboard.models.contracts.ActivityHistory;
import com.telerikacademy.oop.trelloboard.models.contracts.Assignable;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MemberImpl implements Member {

    private String name;
    private List<WorkItem> createdWorkItems;
    private List<Assignable> assignedWorkItems;
    private List<ActivityHistory> activityHistory;

    public MemberImpl(String name) {
        Validator.validateString(name,
                Constants.MEMBER_NAME_MIN_VALUE,
                Constants.MEMBER_NAME_MAX_VALUE,
                "Name must be between 5 and 15 characters.");
        this.name = name;
        this.createdWorkItems = new ArrayList<>();
        this.assignedWorkItems = new ArrayList<>();
        activityHistory = new ArrayList<>();
        activityHistory.add(new ActivityHistoryImpl(String.format("New member, with name %s, is created.", name)));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<WorkItem> getCreatedWorkItems() {
        return new ArrayList<>(createdWorkItems);
    }

    @Override
    public List<Assignable> getAssignedWorkItems() {
        return new ArrayList<>(assignedWorkItems);
    }

    @Override
    public List<ActivityHistory> getActivityHistory() {
        return new ArrayList<>(activityHistory);
    }

    @Override
    public void addCreatedItem(WorkItem item) {
        Validator.checkIfNull(item, Constants.NULL_WORKITEM_ERROR_MESSAGE);
        activityHistory.add(new ActivityHistoryImpl(
                String.format(
                        Constants.ADD_WORKITEM_SUCCESS_MESSAGE,
                        item.getId(),
                        item.getTitle())));

        createdWorkItems.add(item);
    }

    @Override
    public void addAssignedItem(Assignable item) {
        Validator.checkIfNull(item, Constants.NULL_WORKITEM_ERROR_MESSAGE);
        activityHistory.add(new ActivityHistoryImpl(
                String.format(
                        Constants.ADD_WORKITEM_SUCCESS_MESSAGE,
                        item.getId(),
                        item.getTitle())));
        assignedWorkItems.add(item);
    }

    @Override
    public void removeCreatedItem(WorkItem item) {
        Validator.checkIfNull(item, Constants.NULL_WORKITEM_ERROR_MESSAGE);
        activityHistory.add(new ActivityHistoryImpl(String.format(Constants.REMOVE_WORKITEM_SUCCESS_MESSAGE,item.getId(),item.getTitle())));
        createdWorkItems.remove(item);
    }

    @Override
    public void removeAssignedItem(Assignable item) {
        Validator.checkIfNull(item, Constants.NULL_WORKITEM_ERROR_MESSAGE);
        activityHistory.add(new ActivityHistoryImpl(String.format(Constants.REMOVE_WORKITEM_SUCCESS_MESSAGE,item.getId(),item.getTitle())));
        assignedWorkItems.remove(item);
    }

    @Override
    public WorkItem findCreatedWorkItem(int id) {
        Validator.validateDecimalArguments(id, "Input is invalid");
        WorkItem workItem;
        try {
            workItem = createdWorkItems.stream().filter(w -> w.getId() == id).findFirst().get();
        } catch (NoSuchElementException ex) {
            return null;
        }
        return workItem;
    }

    @Override
    public Assignable findAssignedWorkItem(int id) {
        Validator.validateDecimalArguments(id, "Input is invalid");
        Assignable workItem;
        try {
            workItem = assignedWorkItems.stream().filter(w -> w.getId() == id).findFirst().get();
        } catch (NoSuchElementException ex) {
            return null;
        }

        return workItem;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MemberImpl)) {
            return false;
        }
        if (((MemberImpl) obj).getName().equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public void setName(String name) {
        Validator.validateString(name,
                Constants.MEMBER_NAME_MIN_VALUE,
                Constants.MEMBER_NAME_MAX_VALUE,
                String.format(
                        Constants.MEMBERNAME_ERROR_MESSAGE,
                        Constants.MEMBER_NAME_MIN_VALUE,
                        Constants.MEMBER_NAME_MAX_VALUE));
        activityHistory.add(new ActivityHistoryImpl(String.format(
                "Member, with name %s, is changed to %s.", this.name, name)));
        this.name = name;
    }

    @Override
    public String viewInfo() {
        StringBuilder sb = new StringBuilder(String.format(
                "Activity history of member %s: ", name) + System.lineSeparator());
        activityHistory.forEach(a -> sb.append(a.toString()));
        sb.append(String.format("%n=========="));

        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("-%s%n", name);
    }
}
