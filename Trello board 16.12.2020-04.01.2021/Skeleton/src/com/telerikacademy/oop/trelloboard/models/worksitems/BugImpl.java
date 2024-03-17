package com.telerikacademy.oop.trelloboard.models.worksitems;

import com.telerikacademy.oop.trelloboard.models.common.Constants;
import com.telerikacademy.oop.trelloboard.models.common.Validator;
import com.telerikacademy.oop.trelloboard.models.common.enums.BugStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Severity;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Assignable;
import com.telerikacademy.oop.trelloboard.models.contracts.Bug;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BugImpl extends AssignableItemBase implements Bug, Assignable {

    private List<String> steps;
    private Severity severity;

    public BugImpl(int id, String title, String description, Status status, LocalDate dueDate, Severity severity,
                   PriorityType priorityType) {
        super(id, title, description, status, dueDate, priorityType);
        steps = new ArrayList<>();
        this.severity = severity;
        logEvent(String.format("Bug item, with title %s and id %d, is created", title, id));

    }

    @Override
    public List<String> getSteps() {
        return new ArrayList<>(steps);
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    @Override
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }


    @Override
    public void addSteps(String step) {
        Validator.checkIfNull(step, String.format(Constants.STEPINBUG_ERROR_MESSAGE, getId(), getTitle()));

        if (!steps.contains(step)) {
            steps.add(step);
            logEvent(String.format("Step %s is added successfully", step));
        } else
            logEvent(String.format("Step-%s already exists", step));
    }

    @Override
    public String findStep(int id) {
        Validator.validateDecimalArguments(id, "Number of step should be positive.");
        return steps.get(id);
    }

    @Override
    public void removeStep(int id) {
        Validator.validateDecimalArguments(id, "Step id should be positive.");
        if (id >= steps.size()) {
            throw new InvalidInputException(String.format("Step with id %d does not exist.", id));
        }
        String step = steps.get(id);
        logEvent(String.format("Step-%s is removed from bug: id-%d, title-%s", step, getId(), getTitle()));
        steps.remove(step);
    }

    @Override
    public void revertStatus() {
        if (getStatus().equals(BugStatus.ACTIVE)) {
            logEvent(String.format(Constants.REVERT_ALREADY_STATUS_MESSAGE, getStatus()));
            throw new InvalidInputException(String.format(Constants.REVERT_STATUS_ERROR_MESSAGE, getId(), getTitle(), getStatus()));
        } else {
            logEvent(String.format(Constants.REVERT_STATUS_MESSAGE, getStatus(), getStatus().getPrevious()));
        }
        super.setStatus(getStatus().getPrevious());
    }

    @Override
    public void advanceStatus() {
        if (getStatus().equals(BugStatus.FIXED)) {
            logEvent(String.format(Constants.ADVANCE_ALREADY_STATUS_MESSAGE, getStatus()));
            throw new InvalidInputException(String.format(Constants.ADVANCE_STATUS_ERROR_MESSAGE,
                    getId(),
                    getTitle(),
                    getStatus()));
        } else {
            logEvent(String.format(Constants.ADVANCE_STATUS_MESSAGE, getStatus(), getStatus().getNext()));
        }

        super.setStatus(getStatus().getNext());
    }

    @Override
    protected int getMinTitleLength() {
        return Constants.BUG_TITLE_MIN_LENGTH;
    }

    @Override
    protected int getMaxTitleLength() {
        return Constants.BUG_TITLE_MAX_LENGTH;
    }

    @Override
    protected int getMinDescriptionLength() {
        return Constants.BUG_DESCRIPTION_MIN_LENGTH;
    }

    @Override
    protected int getMaxDescriptionLength() {
        return Constants.BUG_DESCRIPTION_MAX_LENGTH;
    }

    @Override
    protected String titleErrorMessage() {
        return String.format(Constants.TITLE_ERROR_MESSAGE,
                Constants.BUG_TITLE_MIN_LENGTH,
                Constants.BUG_TITLE_MAX_LENGTH);
    }

    @Override
    protected String descriptionErrorMessage() {
        return String.format(Constants.DESCRIPTION_ERROR_MESSAGE,
                Constants.BUG_DESCRIPTION_MIN_LENGTH,
                Constants.BUG_DESCRIPTION_MAX_LENGTH);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BugImpl)) {
            return false;
        }
        return super.equals(obj)
                && (((BugImpl) obj).getSteps().equals(steps))
                && (((BugImpl) obj).getSeverity().equals(severity));
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());

        if (!steps.isEmpty()) {
            sb.append("Steps: ").append(System.lineSeparator());
            steps.forEach(e -> {
                sb.append(e).append(System.lineSeparator());
            });
        } else {
            sb.append(" No steps").append(System.lineSeparator());
        }

        if (getAssignee() != null) {

            sb.append(String.format(" Assignee: %s%n", getAssignee().getName()));
        }

        sb.append(" Severity: ").append(getSeverity().toString()).append(System.lineSeparator());
        sb.append(" Priority: ").append(getPriorityType().toString()).append(System.lineSeparator());
        sb.append("==========");

        return sb.toString();
    }
}
