package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.show.ShowAllTeams;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static java.util.Arrays.asList;

public class ShowAllTeams_Tests {

    private Command testCommand;
    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        trelloBoardFactory = new TrelloBoardFactoryImpl();
        testCommand = new ShowAllTeams(trelloBoardRepository);
    }


    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList(new String[1])));
    }


    @Test
    public void execute_should_throwException_when_getTeamsIsEmpty() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(Collections.emptyList()));
    }

    @Test
    public void execute_should_showAllTeam_when_inputIsValid() {
        // Arrange
        trelloBoardRepository.addTeam(trelloBoardFactory.createTeam("BestTeam"));

        // Assert & Act
        Assertions.assertDoesNotThrow(() -> testCommand.execute(Collections.emptyList()));
    }
}
