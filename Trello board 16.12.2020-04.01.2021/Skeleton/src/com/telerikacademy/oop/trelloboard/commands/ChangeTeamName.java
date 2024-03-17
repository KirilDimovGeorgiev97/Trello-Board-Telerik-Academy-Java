package com.telerikacademy.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;

import java.util.List;

public class ChangeTeamName implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TrelloBoardRepository trelloBoardRepository;

    public ChangeTeamName(TrelloBoardRepository trelloBoardRepository) {
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

        String oldTeamName = parameters.get(0);
        String newTeamName = parameters.get(1);

        return changeTeamName(oldTeamName, newTeamName);
    }

    private String changeTeamName(String oldTeamName, String newTeamName) {

        if (!trelloBoardRepository.getTeams().containsKey(oldTeamName)) {
            throw new InvalidInputException(
                    String.format(CommandConstants.TEAM_NOT_FOUND_ERROR_MESSAGE, oldTeamName));
        }

        if (trelloBoardRepository.getMembers().containsKey(newTeamName)) {
            throw new InvalidInputException(String.format(
                    CommandConstants.TEAM_EXISTS_ERROR_MESSAGE, newTeamName));
        }

        Team team = trelloBoardRepository.getTeams().get(oldTeamName);
        team.setName(newTeamName);
        trelloBoardRepository.getTeams().remove(oldTeamName, team);
        trelloBoardRepository.getTeams().put(newTeamName, team);

        return String.format(
                CommandConstants.CHANGE_TEAM_NAME_SUCCESS_MESSAGE,
                oldTeamName,
                newTeamName);
    }
}
