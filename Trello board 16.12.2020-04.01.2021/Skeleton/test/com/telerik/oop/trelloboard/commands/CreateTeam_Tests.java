package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.CreateTeam;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.DuplicateException;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class CreateTeam_Tests {

    private Command testCommand;
    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        trelloBoardFactory = new TrelloBoardFactoryImpl();
        testCommand = new CreateTeam(trelloBoardRepository, trelloBoardFactory);
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
                () -> testCommand.execute(asList(new String[2])));
    }

    @Test
    public void execute_should_throw_Exception_when_teamAlreadyExists() {
        // Arrange & Act
        trelloBoardRepository.addTeam(trelloBoardFactory.createTeam("BestTeamEver"));

        // Assert
        Assertions.assertThrows(DuplicateException.class,
                () -> testCommand.execute(singletonList("BestTeamEver")));
    }

    @Test
    public void execute_should_createATeam_when_inputIsValid() {
        // Arrange & Act
        testCommand.execute(singletonList("TestTeam"));

        //Assert
        Assertions.assertEquals(1, trelloBoardRepository.getTeams().size());
    }
}
