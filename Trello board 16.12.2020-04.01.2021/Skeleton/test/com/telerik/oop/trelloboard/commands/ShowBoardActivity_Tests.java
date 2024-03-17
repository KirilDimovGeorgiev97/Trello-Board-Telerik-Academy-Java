package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.show.ShowBoardActivity;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class ShowBoardActivity_Tests {

    private Command testCommand;
    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        testCommand = new ShowBoardActivity(trelloBoardRepository);
        trelloBoardFactory = new TrelloBoardFactoryImpl();
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
        // Arrange, Act & Assert
        String teamName = "TestTeam";
        String boardName = "TestBoard";

        Team team = (trelloBoardFactory.createTeam(teamName));
        trelloBoardRepository.addTeam(team);
        team.addBoard(trelloBoardFactory.createBoard(boardName));


        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList(boardName, "ThisTeamDoesNotExist")));
    }

    @Test
    public void execute_should_throwException_when_boardIsNotFound() {
        // Arrange
        String teamName = "TestTeam";
        String boardName = "TestBoard";

        Team team = trelloBoardFactory.createTeam(teamName);
        trelloBoardRepository.addTeam(team);
        team.addBoard(trelloBoardFactory.createBoard(boardName));

        // Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("YouCannotFindThisBoard", teamName)));
    }

    @Test
    public void execute_should_printBoardActivity_when_inputIsValid() {
        String teamName = "TestTeam";
        String boardName = "TestBoard";

        Team team = trelloBoardFactory.createTeam(teamName);
        trelloBoardRepository.addTeam(team);
        team.addBoard(trelloBoardFactory.createBoard(boardName));

        // Assert & Act
        Assertions.assertDoesNotThrow(() -> testCommand.execute(asList("TestBoard", "TestTeam")));
    }
}
