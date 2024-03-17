package com.telerikacademy.oop.trelloboard.commands.common;

public class CommandConstants {


    //Success messages
    public static final String MEMBER_CREATED_SUCCESS_MESSAGE = "Member %s is created successfully.";
    public static final String TEAM_CREATED_SUCCESS_MESSAGE = "Team with name %s is created successfully";
    public static final String ADD_MEMBER_TEAM_SUCCESS_MESSAGE = "Member with name %s is added successfully to team,with name %s.";
    public static final String BOARD_CREATED_SUCCESS_MESSAGE = "Board with name %s is created successfully and added to team-%s";
    public static final String WORKITEM_CREATED_SUCCESS_MESSAGE = "Work item with id %d and title %s is created successfully by %s and added to board %s.";
    public static final String ASSIGN_WORKITEM_SUCCESS_MESSAGE = "Work item with id %d and title %s is assigned to %s from team %s";
    public static final String ADD_COMMENT_TO_WORKITEM_SUCCESS_MESSAGE = "Comment:[%s] is added to %s work item with ID %d by %s.";
    public static final String ADVANCE_STATUS_WORKIITEM_SUCCESS_MESSAGE = "Status of work item with Id %d and Title %s is advanced from %s to %s";
    public static final String REVERT_STATUS_WORKIITEM_SUCCESS_MESSAGE = "Status of work item, with id %d and title %s, is reverted from %s to %s";
    public static final String CHANGE_TEAM_NAME_SUCCESS_MESSAGE = "The name of the team is changed from %s to %s.";
    public static final String CHANGE_BOARD_NAME_SUCCESS_MESSAGE = "The name of the board of the team %s is changed from %s to %s.";
    public static final String CHANGE_MEMBER_NAME_SUCCESS_MESSAGE = "The name of the member is changed from %s to %s.";
    public static final String CHANGE_PRIORITY_SUCCESS_MESSAGE = "Priority is changed! Work item with ID: %d, Type: %s [Before: %s After: %s]";
    public static final String CHANGE_SEVERITY_BUG_SUCCESS_MESSAGE = "Severity is changed! Work item with ID: %d, Type: %s [Before: %s After: %s]";
    public static final String CHANGE_SIZE_STORY_SUCCESS_MESSAGE = "Size is changed! Work item with ID: %d, Type: %s [Before: %s After: %s]";
    public static final String CHANGE_RATING_FEEDBACK_SUCCESS_MESSAGE = "Rating is changed! Work item with ID: %d, Type: %s [Before: %s After: %s]";
    public static final String SORT_WORKITEMS_SUCCESS_MESSAGE = "Work items are sorted by %s successfully";
    public static final String UNASSIGN_WORKITEM_SUCCESS = "Work item with id %d and title %s is unassigned from member %s from team %s";
    public static final String WORK_ITEM_REMOVE_SUCCESS_MESSAGE = "Work item with id %d removed successfully.";
    public static final String BUG_ADD_STEP_SUCCESS_MESSAGE = "Step:[%s] is successfully added to bug with id %d and title %s by %s.";
    public static final String BUG_REMOVE_STEP_SUCCESS_MESSAGE = "Step with id %d is successfully removed to bug with id %d and title %s by %s.";
    public static final String REMOVE_COMMENT_SUCCESS_MESSAGE = "Comment:[%s] is removed from %s work item with ID %d by %s.";

    // Error messages
    public static final String INVALID_NUMBER_OF_ARGUMENTS = "Invalid number of arguments. Expected: %d, Received: %d";
    public static final String INVALID_INPUT_ERROR_MESSAGE = "Invalid argument %s for command %s.";
    public static final String INVALID_COMMAND_ERROR_MESSAGE = "Invalid command name: %s";
    public static final String TEAM_EXISTS_ERROR_MESSAGE = "Team with name %s already exist.";
    public static final String TEAM_NOT_FOUND_ERROR_MESSAGE = "Team %s not found.";
    public static final String MEMBER_EXIST_ERROR_MESSAGE = "Member with %s name already exist.";
    public static final String MEMBER_NOT_FOUND_ERROR_MESSAGE = "Member with name %s is not found.";
    public static final String MEMBER_NOT_IN_ANY_TEAM_ERROR_MESSAGE = "Member with name %s is not found in any team.";
    public static final String BOARD_EXIST_ERROR_MESSAGE = "Board with name %s already exist in team %s";
    public static final String BOARD_NOT_FOUND_ERROR_MESSAGE = "Board %s is not found.";
    public static final String MEMBER_ACCESS_TO_BOARD_ERROR_MESSAGE = "Member %s has no access to board %s.";
    public static final String WORKITEM_NEGATIVE_ID_ERROR_MESSAGE = "Id must positive, not negative %d.";
    public static final String BUG_EXIST_ERROR_MESSAGE = "Work item: %s - already exist.";
    public static final String BUG_NOT_FOUND_ERROR_MESSAGE = "Work item bug with id %d - is not found.";
    public static final String BUG_SEVERITY_VALUE_ERROR_MESSAGE = "Bug does not have such value %s.";
    public static final String STORY_EXIST_ERROR_MESSAGE = "Work item: %s - already exist.";
    public static final String STORY_NOT_FOUND_ERROR_MESSAGE = "Work item story with id %d - is not found.";
    public static final String STORY_SIZE_VALUE_ERROR_MESSAGE = "Size does not have such value %s.";
    public static final String ASSIGNABLE_PRIORITY_VALUE_ERROR_MESSAGE = "Assignable item can not have such value %s.";
    public static final String FEEDBACK_EXIST_ERROR_MESSAGE = "Work item: %s - already exist.";
    public static final String FEEDBACK_CHANGE_RATING_ERROR_MESSAGE = "Feedback must have positive rating, not negative %d.";
    public static final String FEEDBACK_NOT_FOUND_ERROR_MESSAGE = "Work item feedback with id %d - is not found.";
    public static final String UNASSIGN_ALREADY_ERROR_MESSAGE = "Work item with id %d and title %s already unassigned.";
    public static final String WORK_ITEM_NOT_FOUND_ERROR_MESSAGE = "Work item with id %d is not found.";
    public static final String WORK_ITEM_DUPLOCATE_ERROR_MESSAGE = "Work item with id %d and title %s already exists.";
    public static final String INVALID_WORK_ITEM_ERROR_MESSAGE = "Invalid work item type - %s for command %s";
    public static final String MEMBER_ACCESS_TO_WORKITEM_ERROR_MESSAGE = "Member %s does not have access to work item with id %d and title %s.";
    public static final String WORK_ITEM_NOT_FOUND_IN_BOARD = "Work item with ID: %d in Board: %s - is not found";
    public static final String WORKITEM_WAS_NOT_CREATED_BY_ERROR_MSG = "Work item with ID: %d - was not created by %s";

}
