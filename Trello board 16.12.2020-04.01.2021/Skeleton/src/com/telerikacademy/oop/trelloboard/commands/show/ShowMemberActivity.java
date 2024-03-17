package com.telerikacademy.oop.trelloboard.commands.show;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;

import java.util.List;

public class ShowMemberActivity implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;
    private TrelloBoardRepository trelloBoardRepository;

    public ShowMemberActivity(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() != CORRECT_NUMBER_OF_ARGUMENTS) {
            throw new InvalidInputException(
                    String.format(CommandConstants.INVALID_NUMBER_OF_ARGUMENTS,
                    CORRECT_NUMBER_OF_ARGUMENTS,
                    parameters.size()));
        }

        String name = parameters.get(0);

        return showMemberActivity(name);
    }

    private String showMemberActivity(String name) {

        if (!trelloBoardRepository.getMembers().containsKey(name)) {
            throw new InvalidInputException(
                    String.format(CommandConstants.MEMBER_NOT_FOUND_ERROR_MESSAGE, name));
        }

        return trelloBoardRepository.getMembers().get(name).viewInfo();
    }

}
