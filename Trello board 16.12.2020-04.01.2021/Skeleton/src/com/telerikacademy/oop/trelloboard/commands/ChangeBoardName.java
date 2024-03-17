package com.telerikacademy.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;

import java.util.List;

public class ChangeBoardName implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 3;

    private TrelloBoardRepository trelloBoardRepository;

    public ChangeBoardName(TrelloBoardRepository trelloBoardRepository) {
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
        String oldBoardName = parameters.get(1);
        String newBoardName = parameters.get(2);

        return changeBoardName(teamName, oldBoardName, newBoardName);
    }

    private String changeBoardName(String teamName, String oldBoardName, String newBoardName) {

        if (!trelloBoardRepository.getTeams().containsKey(teamName)) {
            throw new InvalidInputException(String.format(CommandConstants.TEAM_NOT_FOUND_ERROR_MESSAGE, teamName));
        }

        Team team = trelloBoardRepository.getTeams().get(teamName);


        if (team.findBoard(newBoardName) != null) {
            throw new InvalidInputException(String.format(
                    CommandConstants.BOARD_NOT_FOUND_ERROR_MESSAGE, oldBoardName));
        }

        if (team.findBoard(oldBoardName) == null) {
            throw new InvalidInputException(String.format(
                    CommandConstants.BOARD_EXIST_ERROR_MESSAGE, newBoardName, teamName));
        }

        Board board = team.findBoard(oldBoardName);
        board.setName(newBoardName);

        return String.format(
                CommandConstants.CHANGE_BOARD_NAME_SUCCESS_MESSAGE,
                teamName,
                oldBoardName,
                newBoardName);
    }

}
