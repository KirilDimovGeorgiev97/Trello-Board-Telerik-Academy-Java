package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.CreateBoardInTeam;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.DuplicateException;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class CreateBoardInTeam_Tests {

    private Command testCommand;
    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        trelloBoardFactory = new TrelloBoardFactoryImpl();
        testCommand = new CreateBoardInTeam(trelloBoardRepository, trelloBoardFactory);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(emptyList()));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList(new String[3])));
    }

    @Test
    public void execute_should_throwException_when_teamIsNotFound() {
        // Arrange & Act
        String teamName = "TheBestTeam";
        String boardName = "JustDoIt";

        Team team = trelloBoardFactory.createTeam(teamName);
        Board board = trelloBoardFactory.createBoard(boardName);

        trelloBoardRepository.addTeam(team);
        team.addBoard(board);

        //Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList(boardName, "TheBestTeamEver")));
    }

    @Test
    public void execute_should_throwException_when_boardAlreadyExists() {
        // Arrange & Act
        String teamName = "TheBestTeam";
        String boardName = "JustDoIt";

        Team team = trelloBoardFactory.createTeam(teamName);
        Board board = trelloBoardFactory.createBoard(boardName);

        trelloBoardRepository.addTeam(team);
        team.addBoard(board);

        //Assert
        Assertions.assertThrows(DuplicateException.class,
                () -> testCommand.execute(asList(boardName, teamName)));
    }

    @Test
    public void execute_should_createBoard_when_inputIsValid() {
        // Arrange & Act
        String teamName = "TheBestTeam";
        String boardName = "JustDoIt";
        Team team = trelloBoardFactory.createTeam(teamName);
        trelloBoardRepository.addTeam(team);
        testCommand.execute(asList(boardName, teamName));

        //Assert
        Assertions.assertEquals(1, trelloBoardRepository.getTeams()
                .values()
                .stream()
                .map(Team::getBoards).count());
    }
}
