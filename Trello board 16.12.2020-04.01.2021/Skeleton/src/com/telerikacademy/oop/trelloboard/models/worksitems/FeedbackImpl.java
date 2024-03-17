package com.telerikacademy.oop.trelloboard.models.worksitems;


import com.telerikacademy.oop.trelloboard.models.common.Constants;
import com.telerikacademy.oop.trelloboard.models.common.Validator;
import com.telerikacademy.oop.trelloboard.models.contracts.Feedback;
import com.telerikacademy.oop.trelloboard.models.contracts.Status;

import java.time.LocalDate;
import java.util.Objects;


public class FeedbackImpl extends WorkItemBase implements Feedback {

    private int rating;

    public FeedbackImpl(int id, String title, String description, Status status, LocalDate dueDate, int rating) {
        super(id, title, description, status, dueDate);
        setRating(rating);
        logEvent(String.format("Feedback item, with title %s and id %d, is created", title, id));
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public void revertStatus() {
        Status currentStatus = getStatus();
        setStatus(getStatus().getPrevious());
        logEvent(String.format("Status is changed from %s to %s",
                currentStatus, getStatus()));
    }

    @Override
    public void advanceStatus() {
        Status currentStatus = getStatus();
        setStatus(getStatus().getNext());
        logEvent(String.format("Status is changed from %s to %s",
                currentStatus, getStatus()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof FeedbackImpl)) return false;
        return super.equals(obj) && (((FeedbackImpl) obj).getRating() == rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating);
    }

    @Override
    protected int getMinTitleLength() {
        return Constants.FEEDBACK_TITLE_MIN_LENGTH;
    }

    @Override
    protected int getMaxTitleLength() {
        return Constants.FEEDBACK_TITLE_MAX_LENGTH;
    }

    @Override
    protected int getMinDescriptionLength() {
        return Constants.FEEDBACK_DESCRIPTION_MIN_LENGTH;
    }

    @Override
    protected int getMaxDescriptionLength() {
        return Constants.FEEDBACK_DESCRIPTION_MAX_LENGTH;
    }


    @Override
    protected String titleErrorMessage() {
        return String.format(Constants.TITLE_ERROR_MESSAGE,
                Constants.FEEDBACK_TITLE_MIN_LENGTH,
                Constants.FEEDBACK_TITLE_MAX_LENGTH);
    }

    @Override
    protected String descriptionErrorMessage() {
        return String.format(Constants.DESCRIPTION_ERROR_MESSAGE,
                Constants.FEEDBACK_DESCRIPTION_MIN_LENGTH,
                Constants.FEEDBACK_DESCRIPTION_MAX_LENGTH);
    }

    @Override
    public void setRating(int rating) {
        Validator.validateDecimalArguments(rating, "Rating cannot be negative");
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("%s Rating: %d" +
                "%n==========",
                super.toString(), getRating());
    }


}
