package com.telerikacademy.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;

import java.util.List;

public class ChangeMemberName implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TrelloBoardRepository trelloBoardRepository;

    public ChangeMemberName(TrelloBoardRepository trelloBoardRepository) {
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

        String oldMemberName = parameters.get(0);
        String newMemberName = parameters.get(1);

        return changeMemberName(oldMemberName, newMemberName);
    }

    private String changeMemberName(String oldMemberName, String newMemberName) {

        if (!trelloBoardRepository.getMembers().containsKey(oldMemberName)) {
            throw new InvalidInputException(String.format(
                    CommandConstants.MEMBER_NOT_FOUND_ERROR_MESSAGE, oldMemberName));
        }


        if (trelloBoardRepository.getMembers().containsKey(newMemberName)) {
            throw new InvalidInputException(String.format(
                    CommandConstants.MEMBER_EXIST_ERROR_MESSAGE, newMemberName));
        }

        Member member = trelloBoardRepository.getMembers().get(oldMemberName);
        member.setName(newMemberName);

        return String.format(
                CommandConstants.CHANGE_MEMBER_NAME_SUCCESS_MESSAGE,
                oldMemberName,
                newMemberName);

    }


}
