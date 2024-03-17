package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.changeWorkItem.RevertStatus;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.BugStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Severity;
import com.telerikacademy.oop.trelloboard.models.common.enums.WorkItemTypes;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Bug;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class RevertStatus_Tests {

    private Command testCommand;
    private TrelloBoardFactory trelloBoardFactory;
    private TrelloBoardRepository trelloBoardRepository;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        trelloBoardFactory = new TrelloBoardFactoryImpl();
        testCommand = new RevertStatus(trelloBoardRepository);
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
    public void execute_should_throwException_when_passedInvalidId() {
        // Arrange, Act
        Bug bug = (Bug) trelloBoardFactory.createWorkItem(WorkItemTypes.BUG, 7, "TelerikAcademy", "description",
                BugStatus.ACTIVE, LocalDate.now(), Severity.CRITICAL, null, 0, PriorityType.HIGH);
        trelloBoardRepository.addWorkItem(bug);

        // Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(singletonList("8")));
    }

    @Test
    public void execute_should_throwException_when_theStatusIsAlreadyReverted() {
        // Arrange & Act
        Bug bug = (Bug) trelloBoardFactory.createWorkItem(WorkItemTypes.BUG, 7, "TelerikAcademy", "description",
                BugStatus.ACTIVE, LocalDate.now(), Severity.CRITICAL, null, 0, PriorityType.HIGH);
        trelloBoardRepository.addWorkItem(bug);

        Assertions.assertThrows(InvalidInputException.class,
        () -> testCommand.execute(singletonList("7")));

    }

    @Test
    public void execute_should_revertStatus_when_inputIsValid() {
        // Arrange & Act
       Bug bug = (Bug) trelloBoardFactory.createWorkItem(WorkItemTypes.BUG, 7,
               "TelerikAcademy", "description", BugStatus.FIXED,
               LocalDate.now(), Severity.CRITICAL, null, 0, PriorityType.HIGH);
        trelloBoardRepository.addWorkItem(bug);
        testCommand.execute(singletonList("7"));

        // Assert
        Assertions.assertEquals(BugStatus.ACTIVE, bug.getStatus());

    }
}
