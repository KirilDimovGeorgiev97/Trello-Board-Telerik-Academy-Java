package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.commands.createAndRemoveWorkItems.RemoveComment;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.BugStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Severity;
import com.telerikacademy.oop.trelloboard.models.common.enums.WorkItemTypes;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;
import com.telerikacademy.oop.trelloboard.models.contracts.WorkItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class RemoveComment_Tests {

    private Command testCommand;
    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        trelloBoardFactory = new TrelloBoardFactoryImpl();
        testCommand = new RemoveComment(trelloBoardRepository);
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
                () -> testCommand.execute(asList(new String[5])));
    }

    @Test
    public void execute_should_throwException_when_idIsNegative() {
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("Ivaylo", "TestBoard", "-1", "TestComment")));
    }

    @Test
    public void execute_should_throwException_when_idIsNotFound() {
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("Ivaylo", "TestBoard", "1", "TestComment")));
    }

    @Test
    public void execute_should_throwException_when_authorIsNotFound() {

        WorkItem workItem = trelloBoardFactory.createWorkItem(WorkItemTypes.BUG, 1, "TestTileBug",
                "description", BugStatus.ACTIVE, LocalDate.now(), Severity.CRITICAL, null, 0, PriorityType.LOW);

        trelloBoardRepository.addWorkItem(workItem);

        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("Ivaylo", "TestBoard", "1", "TestComment")));
    }

    @Test
    public void execute_should_throwException_when_boardIsNotFound() {

        WorkItem workItem = trelloBoardFactory.createWorkItem(WorkItemTypes.BUG, 1, "TestTileBug",
                "description", BugStatus.ACTIVE, LocalDate.now(), Severity.CRITICAL, null, 0, PriorityType.LOW);

        Member author = trelloBoardFactory.createMember("Ivaylo");

        trelloBoardRepository.addMember(author);
        trelloBoardRepository.addWorkItem(workItem);

        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("Pesho", "TestBoard", "1", "TestComment")));
    }

    @Test
    public void execute_should_removeComment_when_inputIsValid() {

        WorkItem workItem = trelloBoardFactory.createWorkItem(WorkItemTypes.BUG, 1, "TestTileBug",
                "description", BugStatus.ACTIVE, LocalDate.now(), Severity.CRITICAL, null, 0, PriorityType.LOW);

        Team testTeam = trelloBoardFactory.createTeam("BestTeam");
        Member author = trelloBoardFactory.createMember("Ivaylo");
        Board testBoard = trelloBoardFactory.createBoard("TestBoard");
        testTeam.addMember(author);

        workItem.addComment(author,"TestComment");
        trelloBoardRepository.addMember(author);
        trelloBoardRepository.addWorkItem(workItem);
        trelloBoardRepository.addTeam(testTeam);
        testTeam.addBoard(testBoard);
        testBoard.addWorkItem(workItem);
        author.addCreatedItem(workItem);

        Assertions.assertDoesNotThrow(() -> testCommand.execute(asList("Ivaylo", "TestBoard", "1", "TestComment")));
    }


}
