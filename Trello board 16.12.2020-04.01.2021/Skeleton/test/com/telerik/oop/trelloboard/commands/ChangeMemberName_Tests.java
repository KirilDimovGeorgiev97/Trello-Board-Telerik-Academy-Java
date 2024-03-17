package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.ChangeMemberName;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class ChangeMemberName_Tests {
    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;
    private Command testCommand;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        trelloBoardFactory = new TrelloBoardFactoryImpl();
        testCommand = new ChangeMemberName(trelloBoardRepository);
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
                () -> testCommand.execute(asList(new String[4])));
    }

    @Test
    public void execute_should_throwException_when_memberIsNotFound() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("Pesho", "Gosho")));
    }

    @Test
    public void execute_should_changeMemberName_when_inputIsValid() {
        // Arrange
        Member testMember = trelloBoardFactory.createMember("Pesho");
        trelloBoardRepository.addMember(testMember);

        // Act
        testCommand.execute(asList("Pesho", "Gosho"));

        // Assert
        Assertions.assertEquals("Gosho", testMember.getName());
    }

}
