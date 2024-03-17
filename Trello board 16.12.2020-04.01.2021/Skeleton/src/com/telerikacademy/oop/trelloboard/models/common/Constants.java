package com.telerikacademy.oop.trelloboard.models.common;

public class Constants {
    public static final int TEAMBASE_NAME_MIN_LENGTH = 5;
    public static final int TEAMBASE_NAME_MAX_LENGTH = 15;

    public static final int BOARD_NAME_MIN_LENGTH = 5;
    public static final int BOARD_NAME_MAX_LENGTH = 15;

    public static final int MEMBER_NAME_MIN_VALUE = 5;
    public static final int MEMBER_NAME_MAX_VALUE = 15;

    public static final int BUG_TITLE_MIN_LENGTH = 10;
    public static final int BUG_TITLE_MAX_LENGTH = 50;
    public static final int BUG_DESCRIPTION_MIN_LENGTH = 10;
    public static final int BUG_DESCRIPTION_MAX_LENGTH = 500;

    public static final int STORY_TITLE_MIN_LENGTH = 10;
    public static final int STORY_TITLE_MAX_LENGTH = 50;
    public static final int STORY_DESCRIPTION_MIN_LENGTH = 10;
    public static final int STORY_DESCRIPTION_MAX_LENGTH = 500;

    public static final int FEEDBACK_TITLE_MIN_LENGTH = 10;
    public static final int FEEDBACK_TITLE_MAX_LENGTH = 50;
    public static final int FEEDBACK_DESCRIPTION_MIN_LENGTH = 10;
    public static final int FEEDBACK_DESCRIPTION_MAX_LENGTH = 500;

    public static final String TITLE_ERROR_MESSAGE = "Title length must be between %d and %d characters.";
    public static final String DESCRIPTION_ERROR_MESSAGE = "Description length must be between %d and %d characters.";
    public static final String TEAMNAME_ERROR_MESSAGE = "Name of a team must be between %d and %d.";
    public static final String BOARDNAME_ERROR_MESSAGE = "Name of a board must be between %d and %d.";
    public static final String MEMBERNAME_ERROR_MESSAGE = "Name of a member must be between %d and %d.";
    public static final String STEPINBUG_ERROR_MESSAGE = "The inserted step of a bug,with id-%d and title-%s, is invalid.";
    public static final String BOARDINTEAM_ERROR_MESSAGE = "The inserted board of a team,with name-%s, is invalid.";

    public static final String MEMBER_NOT_FOUND_ERROR_MESSAGE = "Author with name %s is not found in work item with id %d and title %s.";

    public static final String ADVANCE_STATUS_MESSAGE = "Status is advanced from %s to %s.";
    public static final String REVERT_STATUS_MESSAGE = "Status is reverted from %s to %s.";

    public static final String ADVANCE_ALREADY_STATUS_MESSAGE = "Status is already advanced to %s.";
    public static final String REVERT_ALREADY_STATUS_MESSAGE = "Status is already reverted to %s.";

    public static final String REVERT_STATUS_ERROR_MESSAGE = "Status of work item, with id %d and title %s, is reverted already to %s.";
    public static final String ADVANCE_STATUS_ERROR_MESSAGE = "Status of work item, with id %d and title %s, is advanced already to %s.";

    public static final String ADD_WORKITEM_SUCCESS_MESSAGE = "Work item with id %d and title %s is added successfully.";
    public static final String REMOVE_WORKITEM_SUCCESS_MESSAGE = "Work item with id %d and title %s is removed successfully.";

    public static final String NULL_WORKITEM_ERROR_MESSAGE = "Work item is null.";

    public static final String COMMENT_EXIST_ERROR_MESSAGE = "Comment: [%s] - is already written by %s";
    public static final String COMMENT_NOT_FOUND_MESSAGE = "Comment: [%s] to remove - not found.";

}
