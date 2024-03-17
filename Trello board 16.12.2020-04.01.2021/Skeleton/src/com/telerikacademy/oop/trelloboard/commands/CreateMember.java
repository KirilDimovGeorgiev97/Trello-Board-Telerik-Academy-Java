package com.telerikacademy.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.DuplicateException;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;

import java.util.List;

public class CreateMember implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;

    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;

    public CreateMember(TrelloBoardRepository trelloBoardRepository, TrelloBoardFactory trelloBoardFactory) {
        this.trelloBoardRepository = trelloBoardRepository;
        this.trelloBoardFactory = trelloBoardFactory;
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

        String memberName = parameters.get(0);

        return createMember(memberName);
    }

    private String createMember(String memberName) {

        if (trelloBoardRepository.getMembers().containsKey(memberName))
            throw new DuplicateException(String.format(CommandConstants.MEMBER_EXIST_ERROR_MESSAGE, memberName));

        trelloBoardRepository.addMember(trelloBoardFactory.createMember(memberName));

        return String.format(CommandConstants.MEMBER_CREATED_SUCCESS_MESSAGE, memberName);
    }


}
