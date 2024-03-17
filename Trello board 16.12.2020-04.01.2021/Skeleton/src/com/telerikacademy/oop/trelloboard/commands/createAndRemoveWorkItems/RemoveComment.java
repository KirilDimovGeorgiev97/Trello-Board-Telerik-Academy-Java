package com.telerikacademy.oop.trelloboard.commands.createAndRemoveWorkItems;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.WorkItem;

import java.util.List;

public class RemoveComment implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 4;

    private TrelloBoardRepository trelloBoardRepository;

    public RemoveComment(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() != CORRECT_NUMBER_OF_ARGUMENTS) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.INVALID_NUMBER_OF_ARGUMENTS,
                            CORRECT_NUMBER_OF_ARGUMENTS,
                            parameters.size()));
        }

        String author = parameters.get(0);
        String boardName = parameters.get(1);
        int workItemId = Integer.parseInt(parameters.get(2));
        String commentToBeRemove = parameters.get(3);

        if (workItemId < 0) {
            throw new InvalidInputException(
                    String.format(CommandConstants.WORKITEM_NEGATIVE_ID_ERROR_MESSAGE, workItemId));
        }


        return removeComment(author, workItemId, commentToBeRemove, boardName);
    }

    private String removeComment(String author, int workItemId, String commentToBeRemove, String boardName) {

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
                        .anyMatch(b -> b.findWorkItem(workItemId) != null))) {
            throw new InvalidInputException(String.format(CommandConstants.MEMBER_ACCESS_TO_WORKITEM_ERROR_MESSAGE,
                    author,
                    workItem.getId(),
                    workItem.getTitle()));
        }

        Member authorToRemoveComment = trelloBoardRepository.getMembers().get(author);

        workItem.removeComment(authorToRemoveComment, commentToBeRemove);

        return String.format(CommandConstants.REMOVE_COMMENT_SUCCESS_MESSAGE,
                commentToBeRemove,
                workItem.getClass().getSimpleName().replace("Impl", ""),
                workItemId,
                author);
    }
}
