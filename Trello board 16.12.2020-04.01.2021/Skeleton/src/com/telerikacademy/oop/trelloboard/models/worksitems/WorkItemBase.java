package com.telerikacademy.oop.trelloboard.models.worksitems;

import com.telerikacademy.oop.trelloboard.models.ActivityHistoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.Constants;
import com.telerikacademy.oop.trelloboard.models.common.Validator;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.ActivityHistory;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Status;
import com.telerikacademy.oop.trelloboard.models.contracts.WorkItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WorkItemBase implements WorkItem {

    private int id;
    private String title;
    private String description;
    private Map<Member, List<String>> comments;
    private List<ActivityHistory> activityHistory;
    private Status status;
    private LocalDate dueDate;

    public WorkItemBase(int id, String title, String description, Status status, LocalDate dueDate) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setStatus(status);
        setDueDate(dueDate);
        comments = new HashMap<>();
        activityHistory = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Map<Member, List<String>> getComments() {
        return new HashMap<>(comments);
    }

    @Override
    public List<ActivityHistory> getActivityHistory() {
        return new ArrayList<>(activityHistory);
    }

    @Override
    public void addComment(Member author, String message) {
        Validator.checkIfNull(author, "Author cannot be null.");
        Validator.checkIfNull(message, "Comment message to add cannot be null.");

        if (!comments.containsKey(author)) {
            comments.put(author, new ArrayList<>());
            logEvent(String.format("Comment by %s is added.", author));
        }

        if (comments.get(author).contains(message)) {
            logEvent(String.format("Comment: [%s] - already exist.", message));
            throw new InvalidInputException(
                    String.format(
                            Constants.COMMENT_EXIST_ERROR_MESSAGE, message, author.getName()));
        }

        comments.get(author).add(message);
    }

    @Override
    public void removeComment(Member author, String message) {
        Validator.checkIfNull(message, "Comment message to remove cannot be null.");
        Validator.checkIfNull(author, "Author cannot be null.");

        if(!comments.containsKey(author)){
            throw new InvalidInputException(String.format(Constants.MEMBER_NOT_FOUND_ERROR_MESSAGE,author.getName(),id,title));
        }

        if (!comments.get(author).contains(message)) {

            throw new InvalidInputException(
                    String.format(
                            Constants.COMMENT_NOT_FOUND_MESSAGE, message)
                    );
        }

        comments.get(author).remove(message);
        logEvent(String.format("Comment written by %s is removed.", author.getName()));
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WorkItemBase)) {
            return false;
        }
        if (((WorkItemBase) obj).getTitle().equals(title) &&
                ((WorkItemBase) obj).getDescription().equals(description) &&
                ((WorkItemBase) obj).getStatus().equals(status) &&
                ((WorkItemBase) obj).getComments().equals(comments) &&
                ((WorkItemBase) obj).getActivityHistory().equals(activityHistory)) {
            return true;
        }

        return true;
    }

    @Override
    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return String.format(" Type: %s%n" +
                        " Title: %s%n" +
                        " Id: %d%n" +
                        " Description: %s%n" +
                        " Status: %s%n" +
                        " Due date: %s%n",
                getClass().getSimpleName().replace("Impl", ""),
                getTitle(),
                getId(),
                getDescription(),
                getStatus().toString(),
                getDueDate().toString());
    }

    protected abstract int getMinTitleLength();

    protected abstract int getMaxTitleLength();

    protected abstract int getMinDescriptionLength();

    protected abstract int getMaxDescriptionLength();

    protected abstract String titleErrorMessage();

    protected abstract String descriptionErrorMessage();

    protected void logEvent(String event) {
        activityHistory.add(new ActivityHistoryImpl(event));
    }

    protected void setStatus(Status status) {
        Validator.validateStatus(this.getClass().getSimpleName(), status);
        this.status = status;
    }

    private void setDueDate(LocalDate dueDate) {
        if (dueDate.isBefore(LocalDate.now())) {
            throw new InvalidInputException("DueDate cannot be in the past.");
        }

        this.dueDate = dueDate;
    }

    private void setId(int id) {
        Validator.validateDecimalArguments(id, "Id cannot be negative");
        this.id = id;
    }

    private void setTitle(String title) {
        Validator.validateString(title,
                getMinTitleLength(),
                getMaxTitleLength(),
                titleErrorMessage());

        this.title = title;
    }

    private void setDescription(String description) {
        Validator.validateString(description,
                getMinDescriptionLength(),
                getMaxDescriptionLength(),
                descriptionErrorMessage());

        this.description = description;
    }
}
