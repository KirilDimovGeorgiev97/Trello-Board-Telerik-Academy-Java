package com.telerikacademy.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Bug;

import java.util.List;

public class RemoveStepToBug implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 4;

    private TrelloBoardRepository trelloBoardRepository;

    public RemoveStepToBug(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
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

        int id = Integer.parseInt(parameters.get(0));
        String author = parameters.get(1);

        if (id < 0) {
            throw new InvalidInputException(String.format(CommandConstants.WORKITEM_NEGATIVE_ID_ERROR_MESSAGE, id));
        }

        String boardName = parameters.get(2);

        int stepId = Integer.parseInt(parameters.get(3))-1;

        return removeStepToBug(id, stepId, author, boardName);
    }

    private String removeStepToBug(int id, int stepId, String author, String boardName) {

        if (!trelloBoardRepository.getBugs().containsKey(id)) {
            throw new InvalidInputException(
                    String.format(CommandConstants.WORK_ITEM_NOT_FOUND_ERROR_MESSAGE, id));
        }

        Bug bug = trelloBoardRepository.getBugs().get(id);

        if (trelloBoardRepository
                .getTeams()
                .values()
                .stream()
                .noneMatch(v -> (v.findMember(author) != null)
                        && (v.findBoard(boardName) != null)
                        && v.getBoards()
                        .stream()
                        .anyMatch(b-> b.findWorkItem(id)!=null))) {
            throw new InvalidInputException(String.format(CommandConstants.MEMBER_ACCESS_TO_WORKITEM_ERROR_MESSAGE,
                    author,
                    bug.getId(),
                    bug.getTitle()));
        }

        bug.removeStep(stepId);

        return String.format(CommandConstants.BUG_REMOVE_STEP_SUCCESS_MESSAGE,
                stepId,
                bug.getId(),
                bug.getTitle(),
                author);
    }
}
