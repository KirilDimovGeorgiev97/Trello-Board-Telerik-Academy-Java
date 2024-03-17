package com.telerikacademy.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.DuplicateException;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;

import java.util.List;

public class CreateBoardInTeam implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;

    public CreateBoardInTeam(TrelloBoardRepository trelloBoardRepository, TrelloBoardFactory trelloBoardFactory) {
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


        String boardName = parameters.get(0);
        String teamName = parameters.get(1);

        return createBoardInTeam(boardName, teamName);
    }

    private String createBoardInTeam(String boardName, String teamName) {
        if (!trelloBoardRepository.getTeams().containsKey(teamName))
            throw new InvalidInputException(String.format(CommandConstants.TEAM_NOT_FOUND_ERROR_MESSAGE, teamName));

        Board board = trelloBoardFactory.createBoard(boardName);
        Team team = trelloBoardRepository.getTeams().get(teamName);

       /* if(team.getBoards().contains(board)) {
            team.addBoard(board);
            return String.format(CommandConstants.BOARD_EXIST_ERROR_MESSAGE, boardName,teamName);
        }else{
            team.addBoard(board);
            return String.format(CommandConstants.BOARD_CREATED_SUCCESS_MESSAGE,boardName,teamName);
        }*/

        if (team.getBoards().contains(board)) {
            throw new DuplicateException(
                    String.format(
                            CommandConstants.BOARD_EXIST_ERROR_MESSAGE,
                            boardName,
                            teamName)
            );
        }

        team.addBoard(board);

        return String.format(CommandConstants.BOARD_CREATED_SUCCESS_MESSAGE,
                boardName, teamName);
    }
}
