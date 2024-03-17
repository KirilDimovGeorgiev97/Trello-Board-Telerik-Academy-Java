package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.changeWorkItem.ChangePriority;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.*;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Bug;
import com.telerikacademy.oop.trelloboard.models.contracts.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class ChangePriority_Tests {

    private Command testCommand;
    private TrelloBoardRepository repository;
    private TrelloBoardFactory factory;
    private Bug bug;

    @BeforeEach
    public void before() {
        repository = new TrelloBoardRepositoryImpl();
        factory = new TrelloBoardFactoryImpl();
        testCommand = new ChangePriority(repository);
        bug = (Bug) factory.createWorkItem(WorkItemTypes.BUG, 7, "TelerikAcademy", "description",
                BugStatus.ACTIVE, LocalDate.now(), Severity.CRITICAL, null, 0, PriorityType.HIGH);
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
    public void execute_should_throwException_when_idIsNegative() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("Bug", "-7", "low")));
    }

    @Test
    public void execute_should_throwException_when_workItemDoesNotExist() {
        // Arrange & Act
        repository.addWorkItem(bug);

        // Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("Bug", "1", "low")));
    }

    @Test
    public void execute_should_throwException_when_workItemTypeIsInvalid() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("pesho", "7", "low")));
    }

    @Test
    public void execute_should_throwException_when_priorityValueIsInvalid() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("Bug", "7", "lo")));
    }

    @Test
    public void execute_should_changePriority_when_workItemIsBug() {
        // Arrange & Act
        repository.addWorkItem(bug);
        repository.addBug(bug);
        testCommand.execute(asList("7", "low"));

        // Assert
        Assertions.assertEquals(PriorityType.LOW, bug.getPriorityType());
    }

    @Test
    public void execute_should_changePriority_when_workItemIsStory() {
        // Arrange
        Story story = (Story) factory.createWorkItem(WorkItemTypes.STORY, 7, "TelerikAcademy", "description",
                StoryStatus.DONE, LocalDate.now(), null, Size.MEDIUM, 0, PriorityType.HIGH);

        // Act
        repository.addWorkItem(story);
        repository.addStory(story);
        testCommand.execute(asList("7", "low"));

        // Assert
        Assertions.assertEquals(PriorityType.LOW, story.getPriorityType());
    }
}
