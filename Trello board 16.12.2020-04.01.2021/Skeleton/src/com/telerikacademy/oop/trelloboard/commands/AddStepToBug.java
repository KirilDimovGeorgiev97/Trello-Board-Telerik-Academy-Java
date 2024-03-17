package com.telerikacademy.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.WorkItemTypes;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AddStepToBug implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 4;

    private TrelloBoardRepository trelloBoardRepository;

    public AddStepToBug(TrelloBoardRepository trelloBoardRepository) {
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

        String stepValue = parameters.get(3);

        return addStepToBug(id, stepValue, author, boardName);
    }

    private String addStepToBug(int id, String stepValue, String author, String boardName) {

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

        bug.addSteps(stepValue);

        return String.format(CommandConstants.BUG_ADD_STEP_SUCCESS_MESSAGE,
                stepValue,
                bug.getId(),
                bug.getTitle(),
                author);
    }
}
