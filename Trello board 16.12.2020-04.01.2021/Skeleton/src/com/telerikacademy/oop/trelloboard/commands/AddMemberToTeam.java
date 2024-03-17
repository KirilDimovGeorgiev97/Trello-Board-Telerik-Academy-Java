package com.telerikacademy.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;

import java.util.List;

public class AddMemberToTeam implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TrelloBoardRepository trelloBoardRepository;

    public AddMemberToTeam(TrelloBoardRepository trelloBoardRepository) {
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

        String memberName = parameters.get(0);
        String teamName = parameters.get(1);

        return addMemberToTeam(memberName, teamName);
    }

    private String addMemberToTeam(String memberName, String teamName) {

        if (!trelloBoardRepository.getMembers().containsKey(memberName))
            throw new InvalidInputException(
                    String.format(CommandConstants.MEMBER_NOT_FOUND_ERROR_MESSAGE, memberName));

        if (!trelloBoardRepository.getTeams().containsKey(teamName)) {
            throw new InvalidInputException(
                    String.format(CommandConstants.TEAM_NOT_FOUND_ERROR_MESSAGE, teamName));
        }

        trelloBoardRepository.getTeams().get(teamName).addMember(trelloBoardRepository.getMembers().get(memberName));

        return String.format(CommandConstants.ADD_MEMBER_TEAM_SUCCESS_MESSAGE, memberName, teamName);
    }

}
