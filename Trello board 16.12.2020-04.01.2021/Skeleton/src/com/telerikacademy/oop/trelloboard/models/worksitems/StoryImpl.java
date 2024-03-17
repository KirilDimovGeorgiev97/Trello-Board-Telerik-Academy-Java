package com.telerikacademy.oop.trelloboard.models.worksitems;

import com.telerikacademy.oop.trelloboard.models.common.Constants;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Size;
import com.telerikacademy.oop.trelloboard.models.common.enums.StoryStatus;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Assignable;
import com.telerikacademy.oop.trelloboard.models.contracts.Status;
import com.telerikacademy.oop.trelloboard.models.contracts.Story;

import java.time.LocalDate;

public class StoryImpl extends AssignableItemBase implements Story, Assignable {

    private Size size;

    public StoryImpl(int id, String title, String description, Status status, LocalDate dueDate, Size size,
                     PriorityType priorityType) {
        super(id, title, description, status, dueDate, priorityType);
        this.size = size;
        logEvent(String.format("Story item, with title %s and id %d, is created", title, id));
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    protected int getMinTitleLength() {
        return Constants.STORY_TITLE_MIN_LENGTH;
    }

    @Override
    protected int getMaxTitleLength() {
        return Constants.STORY_TITLE_MAX_LENGTH;
    }

    @Override
    protected int getMinDescriptionLength() {
        return Constants.STORY_DESCRIPTION_MIN_LENGTH;
    }

    @Override
    protected int getMaxDescriptionLength() {
        return Constants.STORY_DESCRIPTION_MAX_LENGTH;
    }

    @Override
    protected String titleErrorMessage() {
        return String.format(Constants.TITLE_ERROR_MESSAGE,
                Constants.STORY_TITLE_MIN_LENGTH,
                Constants.STORY_TITLE_MAX_LENGTH);
    }

    @Override
    protected String descriptionErrorMessage() {
        return String.format(Constants.DESCRIPTION_ERROR_MESSAGE,
                Constants.STORY_DESCRIPTION_MIN_LENGTH,
                Constants.STORY_DESCRIPTION_MAX_LENGTH);
    }

    @Override
    public void revertStatus() {
        if (getStatus().equals(StoryStatus.NOTDONE)) {
            logEvent(String.format(Constants.REVERT_ALREADY_STATUS_MESSAGE, getStatus()));

            throw new InvalidInputException(
                    String.format(Constants.REVERT_STATUS_ERROR_MESSAGE,
                            getId(),
                            getTitle(),
                            getStatus()));
        } else {
            logEvent(String.format(Constants.REVERT_STATUS_MESSAGE, getStatus(), getStatus().getPrevious()));
        }

        super.setStatus(getStatus().getPrevious());
    }

    @Override
    public void advanceStatus() {
        if (getStatus().equals(StoryStatus.DONE)) {
            logEvent(String.format(Constants.ADVANCE_ALREADY_STATUS_MESSAGE, getStatus()));

            throw new InvalidInputException(
                    String.format(
                            Constants.ADVANCE_STATUS_ERROR_MESSAGE,
                            getId(),
                            getTitle(),
                            getStatus()));
        } else {
            logEvent(String.format(Constants.ADVANCE_STATUS_MESSAGE, getStatus(), getStatus().getNext()));
        }
        super.setStatus(getStatus().getNext());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof StoryImpl)) {
            return false;
        }
        return super.equals(obj) && (((StoryImpl) obj).getSize().equals(size));
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(super.toString());

        sb.append(" Size: ").append(getSize().toString());
        sb.append(System.lineSeparator());
        sb.append(" Priority: ").append(getPriorityType().toString());

        if (getAssignee() != null) {
            sb.append(System.lineSeparator());
            sb.append(String.format(" Assignee: %s", getAssignee().getName()));
        }
        sb.append(String.format("%n=========="));

        return sb.toString();
    }
}
