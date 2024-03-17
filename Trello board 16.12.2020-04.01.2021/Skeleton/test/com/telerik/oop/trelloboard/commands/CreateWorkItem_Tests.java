package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.createAndRemoveWorkItems.CreateWorkItem;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.BugStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Severity;
import com.telerikacademy.oop.trelloboard.models.common.enums.WorkItemTypes;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.DuplicateException;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;
import com.telerikacademy.oop.trelloboard.models.contracts.WorkItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static java.util.Arrays.asList;

public class CreateWorkItem_Tests {

    private Command testCommand;
    private TrelloBoardRepository repository;
    private TrelloBoardFactory factory;
    private Board trelloBoard;
    private Team team;

    @BeforeEach
    public void before() {
        repository = new TrelloBoardRepositoryImpl();
        factory = new TrelloBoardFactoryImpl();
        testCommand = new CreateWorkItem(repository, factory);
        team = factory.createTeam("BestTeam");
        repository.addTeam(team);
        trelloBoard = factory.createBoard("TrelloBoard");
        team.addBoard(trelloBoard);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(Collections.emptyList()));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList(new String[11])));
    }

    @Test
    public void execute_should_throwException_when_parametersAreInvalid() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("Pesho", "1", "TestTitleForFeedBack",
                        "TestDescriptionForFeedBack", "New", "2021-12-30", "5", "TrelloBoar")));
    }

    @Test
    public void execute_should_throwException_when_itemAlreadyExist() {
        // Arrange & Act
        WorkItem bug = factory.createWorkItem(WorkItemTypes.BUG, 7, "TelerikAcademy", "description",
                BugStatus.ACTIVE, LocalDate.now(), Severity.CRITICAL, null, 0, PriorityType.HIGH);

        repository.addWorkItem(bug);

        // Assert
        Assertions.assertThrows(DuplicateException.class,
                () -> testCommand.execute(asList("bug", "7", "TelerikAcademy",
                        "description", "Active", "2021-12-30", "critical", "low", "TrelloBoar", "Ivaylo")));
    }

    @Test
    public void execute_should_throwException_when_boardDoesntExist() {
        // Arrange,Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("bug", "1", "TelerikAcademy",
                        "description", "Active", "2021-12-30", "critical", "low", "TestBoard", "Ivaylo")));
    }

    @Test
    public void execute_should_passSuccessfully_when_tryCreateBug() {
        Member creator = factory.createMember("Ivaylo");
        repository.addMember(creator);
        team.addMember(creator);

        Assertions.assertDoesNotThrow(() -> testCommand.execute(asList("bug", "1", "TelerikAcademy",
                        "description", "Active", "2021-12-30", "critical", "low", "TrelloBoard", "Ivaylo")));
    }

    @Test
    public void execute_should_passSuccessfully_when_tryCreateStory() {
        Member creator = factory.createMember("Ivaylo");
        repository.addMember(creator);
        team.addMember(creator);

        Assertions.assertDoesNotThrow(() -> testCommand.execute(asList("story", "1", "TelerikAcademy",
                "description", "NotDone", "2021-12-30", "small", "low", "TrelloBoard", "Ivaylo")));
    }

    @Test
    public void execute_should_passSuccessfully_when_tryCreateFeedback() {
        Member creator = factory.createMember("Ivaylo");
        repository.addMember(creator);
        team.addMember(creator);

        Assertions.assertDoesNotThrow(() -> testCommand.execute(asList("feedback", "1", "TelerikAcademy",
                "description", "new", "2021-12-30", "3", "TrelloBoard", "Ivaylo")));
    }


}
