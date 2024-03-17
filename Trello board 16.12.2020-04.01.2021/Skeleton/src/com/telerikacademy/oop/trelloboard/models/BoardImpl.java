package com.telerikacademy.oop.trelloboard.models;

import com.telerikacademy.oop.trelloboard.models.common.Constants;
import com.telerikacademy.oop.trelloboard.models.common.Validator;
import com.telerikacademy.oop.trelloboard.models.contracts.ActivityHistory;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class BoardImpl implements Board {

    private String name;
    private List<WorkItem> workItems;
    private List<ActivityHistory> activityHistory;

    public BoardImpl(String name) {
        Validator.validateString(name,
                Constants.BOARD_NAME_MIN_LENGTH,
                Constants.BOARD_NAME_MAX_LENGTH,
                String.format(
                        Constants.BOARDNAME_ERROR_MESSAGE,
                        Constants.BOARD_NAME_MIN_LENGTH,
                        Constants.BOARD_NAME_MAX_LENGTH));
        this.name = name;
        this.workItems = new ArrayList<>();
        this.activityHistory = new ArrayList<>();
        activityHistory.add(new ActivityHistoryImpl(String.format("Board, with name %s, is created.", name)));
    }


    @Override
    public List<WorkItem> getWorkItems() {
        return new ArrayList<>(workItems);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addWorkItem(WorkItem workItem) {
        Validator.checkIfNull(workItem, "Invalid input");
        if (!workItems.contains(workItem)) {
            workItems.add(workItem);
            activityHistory.add(new ActivityHistoryImpl(String.format("Work item, with id %s, is added to board %s", workItem.getId(), name)));
        } else
            activityHistory.add(new ActivityHistoryImpl(String.format("Work item, with id %s, already exists", workItem.getId())));
    }

    @Override
    public WorkItem findWorkItem(String title) {
        Validator.checkIfNull(title, "Input is invalid");
        return workItems.stream().filter(w -> w.getTitle().equals(title)).findFirst().get();
    }

    @Override
    public WorkItem findWorkItem(int id) {
        Validator.validateDecimalArguments(id, "Input is invalid");
        WorkItem workItem;
        try {
            workItem = workItems.stream().filter(w -> w.getId() == id).findFirst().get();
        } catch (NoSuchElementException ex) {
            return null;
        }
        return workItem;
    }

    @Override
    public void removeWorkItem(String title) {
        Validator.checkIfNull(title, "Invalid input");
        WorkItem workItem = findWorkItem(title);
        if (workItem != null) {
            workItems.remove(workItem);
            activityHistory.add(new ActivityHistoryImpl(String.format("Work item, with title %s, is removed from board %s", title, name)));
        } else
            activityHistory.add(new ActivityHistoryImpl(String.format("Work item, with title %s, doesn't exist", title)));
    }

    @Override
    public void removeWorkItem(int id) {
        Validator.validateDecimalArguments(id, "Input is invalid");
        WorkItem workItem = findWorkItem(id);
        if (workItem != null) {
            workItems.remove(workItem);
            activityHistory.add(new ActivityHistoryImpl(String.format("Work item, with id %d, is removed from board %s", id, name)));
        } else
            activityHistory.add(new ActivityHistoryImpl(String.format("Work item, with id %d, doesn't exist", id)));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BoardImpl)) {
            return false;
        }
        if (((BoardImpl) obj).getName().equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String viewInfo() {
        return String.format("Activity history of board %s", name)
                + System.lineSeparator()
                + activityHistory
                .stream()
                .map(ActivityHistory::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public void setName(String name) {
        Validator.validateString(
                name,
                Constants.BOARD_NAME_MIN_LENGTH,
                Constants.BOARD_NAME_MAX_LENGTH,
                "Name is not correct");
        activityHistory.add(new ActivityHistoryImpl(String.format("Board, with name %s is changed to %s.", this.name, name)));
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("-%s%n", getName());
    }
}
