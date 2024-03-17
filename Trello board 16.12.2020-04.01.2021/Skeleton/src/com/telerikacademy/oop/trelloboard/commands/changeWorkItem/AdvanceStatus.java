package com.telerikacademy.oop.trelloboard.commands.changeWorkItem;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.WorkItem;

import java.util.List;

public class AdvanceStatus implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;

    private TrelloBoardRepository trelloBoardRepository;

    public AdvanceStatus(TrelloBoardRepository trelloBoardRepository) {
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


        return advanceStatus(id);
    }

    private String advanceStatus(int id) {

        if (!trelloBoardRepository.getWorkItems().containsKey(id)) {
            throw new InvalidInputException(
                    String.format
                            (CommandConstants.WORK_ITEM_NOT_FOUND_ERROR_MESSAGE, id));
        }

        WorkItem workItem = trelloBoardRepository.getWorkItems().get(id);

        String result = String.format(
                CommandConstants.ADVANCE_STATUS_WORKIITEM_SUCCESS_MESSAGE,
                workItem.getId(),
                workItem.getTitle(),
                workItem.getStatus(),
                workItem.getStatus().getNext());

        workItem.advanceStatus();
        return result;

    }
}
