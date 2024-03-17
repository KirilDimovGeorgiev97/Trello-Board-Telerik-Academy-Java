package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.changeWorkItem.ChangeSeverity;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.WorkItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class ChangeSeverity_Tests {

    private Command testCommand;
    private List<String> parameters;
    private TrelloBoardRepository trelloBoardRepository;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        testCommand = new ChangeSeverity(trelloBoardRepository);
        parameters = new ArrayList<>();
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
    public void execute_should_throwException_when_idNegative() {
        parameters.add("-1");
        parameters.add("Critical");
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(parameters));
    }

    @Test
    public void execute_should_throwException_when_invalidSeverityValue() {
        parameters.add("1");
        parameters.add("Critica");
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(parameters));
    }

    @Test
    public void execute_should_throwException_when_BugNotFound() {
        parameters.add("1");
        parameters.add("Critical");
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(parameters));
    }
}
