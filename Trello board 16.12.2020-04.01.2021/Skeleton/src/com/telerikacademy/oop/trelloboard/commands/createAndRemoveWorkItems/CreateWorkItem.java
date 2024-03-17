package com.telerikacademy.oop.trelloboard.commands.createAndRemoveWorkItems;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.enums.*;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.DuplicateException;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CreateWorkItem implements Command {
    private static final int MIN_CORRECT_NUMBER_OF_ARGUMENTS = 9;
    private static final int MAX_CORRECT_NUMBER_OF_ARGUMENTS = 10;
    private static String statusValue;

    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;

    public CreateWorkItem(TrelloBoardRepository trelloBoardRepository, TrelloBoardFactory trelloBoardFactory) {
        this.trelloBoardRepository = trelloBoardRepository;
        this.trelloBoardFactory = trelloBoardFactory;
    }

    @Override
    public String execute(List<String> parameters) {

        if (MAX_CORRECT_NUMBER_OF_ARGUMENTS < parameters.size()) {
            throw new InvalidInputException(
                    String.format(CommandConstants.INVALID_NUMBER_OF_ARGUMENTS,
                            MIN_CORRECT_NUMBER_OF_ARGUMENTS,
                            parameters.size()));
        }

        if (parameters.size() < MIN_CORRECT_NUMBER_OF_ARGUMENTS) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.INVALID_NUMBER_OF_ARGUMENTS,
                            MAX_CORRECT_NUMBER_OF_ARGUMENTS,
                            parameters.size()));
        }

        statusValue = parameters.get(4);

        if (!WorkItemTypes.containsWorkItem(parameters.get(0).toUpperCase(Locale.ROOT))) {
            throw new InvalidInputException(CommandConstants.INVALID_WORK_ITEM_ERROR_MESSAGE);
        }

        WorkItemTypes workItemType = WorkItemTypes.valueOf(parameters.get(0).toUpperCase(Locale.ROOT));
        int id = Integer.parseInt(parameters.get(1));
        String title = parameters.get(2);
        String description = parameters.get(3);
        LocalDate dueDate = LocalDate.parse(parameters.get(5));
        String boardName = "";
        Status status;
        PriorityType priorityType;
        String creator;

        switch (workItemType) {
            case BUG:
                status = Arrays
                        .stream(BugStatus.values())
                        .anyMatch(bugStatus -> bugStatus.toString().replace(" ", "")
                                .equalsIgnoreCase(parameters.get(4))) ?
                        BugStatus.valueOf(parameters.get(4).toUpperCase(Locale.ROOT)) : null;
                Severity severity = Severity.valueOf(parameters.get(6).toUpperCase(Locale.ROOT));
                priorityType = PriorityType.valueOf(parameters.get(7).toUpperCase(Locale.ROOT));
                boardName = parameters.get(8);
                creator = parameters.get(9);
                return createWorkItem(workItemType,
                        id, title, description,
                        status, dueDate, severity, null, -1,
                        priorityType, boardName, creator);
            case STORY:
                status = Arrays
                        .stream(StoryStatus.values())
                        .anyMatch(storyStatus -> storyStatus.toString().replace(" ", "")
                                .equalsIgnoreCase(parameters.get(4))) ?
                        StoryStatus.valueOf(parameters.get(4).toUpperCase(Locale.ROOT)) : null;
                Size size = Size.valueOf(parameters.get(6).toUpperCase(Locale.ROOT));
                priorityType = PriorityType.valueOf(parameters.get(7).toUpperCase(Locale.ROOT));
                boardName = parameters.get(8);
                creator = parameters.get(9);
                return createWorkItem(workItemType,
                        id, title, description,
                        status, dueDate, null, size, -1,
                        priorityType, boardName, creator);
            case FEEDBACK:
                status = Arrays
                        .stream(FeedbackStatus.values())
                        .anyMatch(feedbackStatus -> feedbackStatus.toString().replace(" ", "")
                                .equalsIgnoreCase(parameters.get(4))) ?
                        FeedbackStatus.valueOf(parameters.get(4).toUpperCase(Locale.ROOT)) : null;
                boardName = parameters.get(7);
                creator = parameters.get(8);
                return createWorkItem(workItemType,
                        id, title, description,
                        status, dueDate, null, null, Integer.parseInt(parameters.get(6)),
                        null, boardName, creator);
            default:
                throw new InvalidInputException(
                        String.format(
                                CommandConstants.INVALID_WORK_ITEM_ERROR_MESSAGE,
                                parameters.get(0),
                                "create work item"));
        }
    }

    private String createWorkItem(WorkItemTypes workItemType, int id, String title, String description, Status status,
                                  LocalDate dueDate, Severity severity, Size size, int rating,
                                  PriorityType priorityType, String boardName, String creator) {

        if (status == null) {
            throw new InvalidInputException(String.format(
                    CommandConstants.INVALID_INPUT_ERROR_MESSAGE,
                    statusValue,
                    "filter work items by status."));
        }

        if (trelloBoardRepository.getWorkItems().containsKey(id)) {
            throw new DuplicateException(String.format(
                    CommandConstants.WORK_ITEM_DUPLOCATE_ERROR_MESSAGE,
                    id,
                    trelloBoardRepository
                            .getWorkItems()
                            .get(id).getTitle()));
        }

        if (trelloBoardRepository.getTeams().entrySet().stream().
                noneMatch(k -> k.getValue().findBoard(boardName) != null
                        && k.getValue().findMember(creator) != null)) {
            throw new InvalidInputException(String.format(CommandConstants.MEMBER_ACCESS_TO_BOARD_ERROR_MESSAGE, creator,boardName));
        }

        Board board = trelloBoardRepository.getTeams()
                .values()
                .stream()
                .map(team -> team.findBoard(boardName)).findFirst().get();

        WorkItem workItem = trelloBoardFactory.createWorkItem(workItemType, id, title, description, status,
                dueDate, severity, size, rating,
                priorityType);

        trelloBoardRepository.addWorkItem(workItem);

        if (board.findWorkItem(workItem.getId()) != null) {
            board.addWorkItem(workItem);
            throw new DuplicateException(
                    String.format(
                            CommandConstants.WORK_ITEM_DUPLOCATE_ERROR_MESSAGE,
                            workItem.getId(),
                            workItem.getTitle()));
        }

        board.addWorkItem(workItem);
        trelloBoardRepository.getMembers().get(creator).addCreatedItem(workItem);

        switch (workItemType) {
            case BUG:
                trelloBoardRepository.addBug(((Bug) workItem));
                break;
            case STORY:
                trelloBoardRepository.addStory(((Story) workItem));
                break;
            case FEEDBACK:
                trelloBoardRepository.addFeedback(((Feedback) workItem));
                break;
        }

        return String.format(CommandConstants.WORKITEM_CREATED_SUCCESS_MESSAGE,
                id,
                title,
                creator,
                boardName);
    }

}
