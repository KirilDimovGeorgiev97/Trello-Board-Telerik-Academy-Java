package com.telerikacademy.oop.trelloboard.commands.show;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;

import java.util.List;
import java.util.stream.Collectors;

public class ShowAllTeamMembers implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;
    private TrelloBoardRepository trelloBoardRepository;

    public ShowAllTeamMembers(TrelloBoardRepository trelloBoardRepository) {
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

        String teamName = parameters.get(0);

        return showAllTeamMembers(teamName);
    }

    private String showAllTeamMembers(String teamName) {

        if (!trelloBoardRepository.getTeams().containsKey(teamName)) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.TEAM_NOT_FOUND_ERROR_MESSAGE, teamName
                    )
            );
        }

        return String.format("All Members in team %s:%n",
                teamName) +
                trelloBoardRepository.getTeams()
                        .get(teamName)
                        .getMembers()
                        .stream()
                        .map(Member::toString)
                        .collect(Collectors.joining(""))
                + "==========";
    }

}
