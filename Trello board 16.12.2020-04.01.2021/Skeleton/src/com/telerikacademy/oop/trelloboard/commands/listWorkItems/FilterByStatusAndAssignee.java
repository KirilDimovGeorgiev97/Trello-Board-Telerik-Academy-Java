package com.telerikacademy.oop.trelloboard.commands.listWorkItems;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.enums.BugStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.StoryStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.WorkItemTypes;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Assignable;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Status;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterByStatusAndAssignee implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 3;

    private TrelloBoardRepository trelloBoardRepository;
    private Map<Integer, Assignable> assignableItems;

    public FilterByStatusAndAssignee(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
        assignableItems = trelloBoardRepository.getAssignable();
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() != CORRECT_NUMBER_OF_ARGUMENTS) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.INVALID_NUMBER_OF_ARGUMENTS,
                            CORRECT_NUMBER_OF_ARGUMENTS,
                            parameters.size()
                    )
            );
        }

        if (!WorkItemTypes.containsWorkItem(parameters.get(0).toUpperCase(Locale.ROOT))) {
            throw new InvalidInputException(CommandConstants.INVALID_WORK_ITEM_ERROR_MESSAGE);
        }

        WorkItemTypes workItemType = WorkItemTypes.valueOf(parameters.get(0).toUpperCase(Locale.ROOT));
        String typeStatus = parameters.get(1).toUpperCase(Locale.ROOT);
        String memberName = parameters.get(2);
        Status status = null;

        switch (workItemType) {
            case BUG:
                status = Arrays
                        .stream(BugStatus.values())
                        .anyMatch(bugStatus -> bugStatus.toString().replace(" ", "").equalsIgnoreCase(typeStatus)) ?
                        BugStatus.valueOf(typeStatus) : null;
                break;
            case STORY:
                status = Arrays
                        .stream(StoryStatus.values())
                        .anyMatch(storyStatus -> storyStatus.toString().replace(" ", "").equalsIgnoreCase(typeStatus)) ?
                        StoryStatus.valueOf(typeStatus) : null;
                break;
            case FEEDBACK:
                throw new InvalidInputException(String.format(
                        CommandConstants.INVALID_INPUT_ERROR_MESSAGE,
                        WorkItemTypes.FEEDBACK.toString()
                        , "Filter by status and assignee"));
        }

        if (status == null) {
            throw new InvalidInputException(String.format(
                    CommandConstants.INVALID_INPUT_ERROR_MESSAGE,
                    typeStatus,
                    "filter work items by status and assignee."));
        }

        return filterWorkItemsByStatusAndAssignee(status, memberName);
    }

    private String filterWorkItemsByStatusAndAssignee(Status status, String nameMember) {

        if (trelloBoardRepository.getTeams()
                .entrySet()
                .stream()
                .noneMatch(e -> e.getValue().findMember(nameMember) != null)) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.MEMBER_NOT_IN_ANY_TEAM_ERROR_MESSAGE, nameMember));
        }

        Member member = trelloBoardRepository.getMembers().get(nameMember);

        if (trelloBoardRepository.getMembers().get(nameMember).getAssignedWorkItems().isEmpty()) {
            throw new InvalidInputException(
                    String.format(
                            "Filtered by Status and Assignee:%nThere are no such items which satisfy the condition: filter by status and assignee %s", nameMember));
        }

        return String.format("Filtered by Status and Assignee:%n") +
                trelloBoardRepository.
                        getMembers().
                        get(nameMember).getAssignedWorkItems().stream()
                        .filter(e -> e.getStatus().equals(status))
                        .map(Assignable::toString)
                        .collect(Collectors.joining(System.lineSeparator()));

    }
}