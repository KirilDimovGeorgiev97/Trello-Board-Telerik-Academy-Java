package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.CreateMember;
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

import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class CreateMember_Tests {

    private Command testCommand;
    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        trelloBoardFactory = new TrelloBoardFactoryImpl();
        testCommand = new CreateMember(trelloBoardRepository, trelloBoardFactory);
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
    public void execute_should_createMember_when_inputIsValid() {
        // Arrange, Act
        testCommand.execute(Collections.singletonList("Telerik Academy"));

        // Assert
        Assertions.assertEquals(1, trelloBoardRepository.getMembers().size());
    }

    @Test
    public void execute_should_throwException_when_parameterIsDuplicated() {

        // Arrange, Act
        testCommand.execute(Collections.singletonList("Telerik Academy"));

        // Assert
        Assertions.assertThrows(DuplicateException.class,
                () -> testCommand.execute(Collections.singletonList("Telerik Academy")));

    }

}
