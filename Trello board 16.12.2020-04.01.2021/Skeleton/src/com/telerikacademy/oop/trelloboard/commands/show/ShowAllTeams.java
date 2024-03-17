package com.telerikacademy.oop.trelloboard.commands.show;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;

import java.util.List;
import java.util.stream.Collectors;

public class ShowAllTeams implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;

    private TrelloBoardRepository trelloBoardRepository;

    public ShowAllTeams(TrelloBoardRepository trelloBoardRepository) {
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

        return showAllTeams();
    }

    private String showAllTeams() {

        if (trelloBoardRepository.getTeams().size() == 0) {
            throw new InvalidInputException("There aren't any team");
        }

        return String.format("Teams:%n") +
                trelloBoardRepository
                        .getTeams()
                        .values()
                        .stream()
                        .map(Team::toString)
                        .collect(Collectors.joining(System.lineSeparator())) + String.format("%n==========");
    }
}
