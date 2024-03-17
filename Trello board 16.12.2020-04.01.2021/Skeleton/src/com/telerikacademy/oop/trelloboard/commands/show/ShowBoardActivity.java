package com.telerikacademy.oop.trelloboard.commands.show;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;

import java.util.List;

public class ShowBoardActivity implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TrelloBoardRepository trelloBoardRepository;

    public ShowBoardActivity(TrelloBoardRepository trelloBoardRepository) {
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

        String boardName = parameters.get(0);
        String teamName = parameters.get(1);

        return showBoardActivity(boardName,teamName);
    }

    private String showBoardActivity(String boardName,String teamName) {

        if(!trelloBoardRepository.getTeams().containsKey(teamName)) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.TEAM_NOT_FOUND_ERROR_MESSAGE, teamName));
        }

        Team team = trelloBoardRepository.getTeams().get(teamName);
        Board board = team.findBoard(boardName);

        if(board == null){
            throw new InvalidInputException(String.format(CommandConstants.BOARD_NOT_FOUND_ERROR_MESSAGE,boardName));
        }

        return board.viewInfo();
    }
}
