package com.telerikacademy.oop.trelloboard.commands.listWorkItems;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;

import java.util.List;

public class SortCommand implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;
    private TrelloBoardRepository trelloBoardRepository;

    public SortCommand(TrelloBoardRepository trelloBoardRepository) {
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

        String type = parameters.get(0);

        return sort(type);
    }

    private String sort(String type) {

        trelloBoardRepository.sortWorkItems(type);

        return String.format(CommandConstants.SORT_WORKITEMS_SUCCESS_MESSAGE, type);
    }
}
