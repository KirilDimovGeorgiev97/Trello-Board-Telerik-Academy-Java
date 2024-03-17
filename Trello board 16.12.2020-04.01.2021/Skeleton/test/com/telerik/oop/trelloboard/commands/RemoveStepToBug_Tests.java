package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.AddStepToBug;
import com.telerikacademy.oop.trelloboard.commands.RemoveStepToBug;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.BoardImpl;
import com.telerikacademy.oop.trelloboard.models.MemberImpl;
import com.telerikacademy.oop.trelloboard.models.TeamImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.BugStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Severity;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.Bug;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;
import com.telerikacademy.oop.trelloboard.models.worksitems.BugImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RemoveStepToBug_Tests {

    private TrelloBoardRepository trelloBoardRepository;
    private Command testCommand;
    private List<String> testParameters;

    @BeforeEach
    public void init() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        testCommand = new RemoveStepToBug(trelloBoardRepository);
        testParameters = new ArrayList<>();
        Team team = new TeamImpl("Omega");
        team.addMember(new MemberImpl("Ivaylo"));
        Board board = new BoardImpl("TrelloBoard");
        Bug bug = new BugImpl(1,
                "Login button bug",
                "The button is not working",
                BugStatus.ACTIVE,
                LocalDate.now().plusDays(3),
                Severity.CRITICAL, PriorityType.HIGH);
        bug.addSteps("Step1");
        trelloBoardRepository.addBug(bug);
        board.addWorkItem(bug);
        team.addBoard(board);
        trelloBoardRepository.addTeam(team);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(new ArrayList<>()));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        testParameters.add("8");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testParameters.add("1");
        testParameters.add("Bug");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_MemberDoesNotExists() {
        testParameters.add("1");
        testParameters.add("Ivayl");
        testParameters.add("TrelloBoard");
        testParameters.add("1");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_IdNegative() {
        testParameters.add("-8");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testParameters.add("1");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_WorkItemNotFound() {
        testParameters.add("8");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testParameters.add("1");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_BoardNotFount() {
        testParameters.add("1");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoar");
        testParameters.add("1");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }
    @Test
    public void execute_Should_ThrowException_When_StepIdNegative() {
        testParameters.add("1");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testParameters.add("-1");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }
    @Test
    public void execute_Should_ThrowException_When_StepIdNotExists() {
        testParameters.add("1");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testParameters.add("2");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }
}
