package com.telerikacademy.oop.trelloboard.commands.listWorkItems;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.enums.BugStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.FeedbackStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.StoryStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.WorkItemTypes;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Status;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;
import com.telerikacademy.oop.trelloboard.models.contracts.WorkItem;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterWorkItemsByStatus implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TrelloBoardRepository trelloBoardRepository;
    private Map<Integer, WorkItem> workItems;
    private Map<String, Team> teams;

    public FilterWorkItemsByStatus(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
        workItems = trelloBoardRepository.getWorkItems();
        teams = trelloBoardRepository.getTeams();
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
        String typeStatus = parameters.get(1);
        Status status = null;
        switch (workItemType) {
            case BUG:
                status = Arrays
                        .stream(BugStatus.values())
                        .anyMatch(bugStatus -> bugStatus.toString().replace(" ", "")
                                .equalsIgnoreCase(typeStatus)) ?
                        BugStatus.valueOf(typeStatus.toUpperCase(Locale.ROOT)) : null;
                break;
            case FEEDBACK:
                status = Arrays
                        .stream(FeedbackStatus.values())
                        .anyMatch(feedbackStatus -> feedbackStatus.toString().replace(" ", "")
                                .equalsIgnoreCase(typeStatus)) ?
                        FeedbackStatus.valueOf(typeStatus.toUpperCase(Locale.ROOT)) : null;
                break;
            case STORY:
                status = Arrays
                        .stream(StoryStatus.values())
                        .anyMatch(storyStatus -> storyStatus.toString().replace(" ", "")
                                .equalsIgnoreCase(typeStatus)) ?
                        StoryStatus.valueOf(typeStatus.toUpperCase(Locale.ROOT)) : null;
                break;

        }

        if (status == null) {
            throw new InvalidInputException(String.format(
                    CommandConstants.INVALID_INPUT_ERROR_MESSAGE,
                    typeStatus,
                    "filter work items by status."));
        }

        return filterByStatus(status);
    }

    private String filterByStatus(Status status) {

        String result = String.format("Filtered by Status %s:%n", status.toString()) +
                workItems.values()
                        .stream()
                        .filter(workItem -> workItem.getStatus().equals(status))
                        .map(WorkItem::toString).collect(Collectors.joining(System.lineSeparator()));

        if (result.equals(String.format("Filtered by Status %s:%n", status.toString()))) {
            throw new InvalidInputException(
                    String.format(
                            "There are no such items which satisfy the condition: filter by status %s", status));
        }

        return result;
    }
}

