package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.ChangeTeamName;
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

public class ChangeTeamName_Tests {

    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;
    private Command testCommand;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        trelloBoardFactory = new TrelloBoardFactoryImpl();
        testCommand = new ChangeTeamName(trelloBoardRepository);
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
    public void execute_should_throwException_when_teamNameToChangeIsNotFound() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("oldTeamName", "teamNameToChange")));
    }

    @Test
    public void execute_should_changeTeamName_when_inputIsValid() {
        // Arrange
        Team testTeam = trelloBoardFactory.createTeam("TeamOmega");
        trelloBoardRepository.addTeam(testTeam);

        // Act
        testCommand.execute(asList("TeamOmega", "BestTeam"));

        // Assert
        Assertions.assertEquals("BestTeam", testTeam.getName());
    }
}
