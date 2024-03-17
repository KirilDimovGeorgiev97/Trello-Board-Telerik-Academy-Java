package com.telerikacademy.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.WorkItem;

import java.util.List;

public class AddCommentToWorkItem implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 4;

    private TrelloBoardRepository trelloBoardRepository;


    public AddCommentToWorkItem(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() != CORRECT_NUMBER_OF_ARGUMENTS) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.INVALID_NUMBER_OF_ARGUMENTS,
                            CORRECT_NUMBER_OF_ARGUMENTS, parameters.size()));
        }

        String author = parameters.get(0);
        String boardName = parameters.get(1);
        int workItemId = Integer.parseInt(parameters.get(2));

        if (workItemId < 0) {
            throw new InvalidInputException(String.format(CommandConstants.WORKITEM_NEGATIVE_ID_ERROR_MESSAGE, workItemId));
        }

        String commentToBeAdded = parameters.get(3);

        return addComment(author, workItemId, commentToBeAdded, boardName);

    }

    private String addComment(String author, int workItemId, String commentToBeAdded, String boardName) {

        if (!trelloBoardRepository.getWorkItems().containsKey(workItemId)) {
            throw new InvalidInputException(
                    String.format(CommandConstants.WORK_ITEM_NOT_FOUND_ERROR_MESSAGE, workItemId));
        }

        WorkItem workItem = trelloBoardRepository.getWorkItems().get(workItemId);

        if (trelloBoardRepository
                .getTeams()
                .values()
                .stream()
                .noneMatch(v -> (v.findMember(author) != null)
                        && (v.findBoard(boardName) != null)
                        && v.getBoards()
                        .stream()
                        .anyMatch(b-> b.findWorkItem(workItemId)!=null))) {
            throw new InvalidInputException(String.format(CommandConstants.MEMBER_ACCESS_TO_WORKITEM_ERROR_MESSAGE,
                    author,
                    workItem.getId(),
                    workItem.getTitle()));
        }

        Member authorToAdd = trelloBoardRepository.getMembers().get(author);

        workItem.addComment(authorToAdd, commentToBeAdded);

        return String.format(CommandConstants.ADD_COMMENT_TO_WORKITEM_SUCCESS_MESSAGE,
                commentToBeAdded,
                workItem.getClass().getSimpleName().replace("Impl", ""),
                workItemId,
                author);
    }
}