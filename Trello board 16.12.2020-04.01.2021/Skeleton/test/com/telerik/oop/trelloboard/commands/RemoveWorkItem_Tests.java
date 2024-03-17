package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.changeWorkItem.ChangeRating;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.commands.createAndRemoveWorkItems.RemoveWorkItem;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.BoardImpl;
import com.telerikacademy.oop.trelloboard.models.MemberImpl;
import com.telerikacademy.oop.trelloboard.models.TeamImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.*;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.*;
import com.telerikacademy.oop.trelloboard.models.worksitems.BugImpl;
import com.telerikacademy.oop.trelloboard.models.worksitems.FeedbackImpl;
import com.telerikacademy.oop.trelloboard.models.worksitems.StoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RemoveWorkItem_Tests {
    private TrelloBoardRepository trelloBoardRepository;
    private Command testCommand;
    private List<String> testParameters;

    @BeforeEach
    public void init() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        testCommand = new RemoveWorkItem(trelloBoardRepository);
        testParameters = new ArrayList<>();
        Team team = new TeamImpl("Omega");
        Member member = new MemberImpl("Ivaylo");
        Member member2 = new MemberImpl("Kiril");
        team.addMember(member);
        team.addMember(member2);
        Board board = new BoardImpl("TrelloBoard");
        Bug bug = new BugImpl(1,
                "Login button bug",
                "The button is not working",
                BugStatus.ACTIVE,
                LocalDate.now().plusDays(3),
                Severity.CRITICAL, PriorityType.HIGH);
        Feedback feedback = new FeedbackImpl(
                11,
                "Telerik Academy",
                "description",
                FeedbackStatus.NEW,
                LocalDate.now(),
                1);
        member2.addAssignedItem(bug);
        trelloBoardRepository.addWorkItem(bug);
        trelloBoardRepository.addBug(bug);
        trelloBoardRepository.addWorkItem(feedback);
        trelloBoardRepository.addFeedback(feedback);
        board.addWorkItem(bug);
        board.addWorkItem(feedback);
        member.addCreatedItem(bug);
        member.addCreatedItem(feedback);
        team.addBoard(board);
        trelloBoardRepository.addMember(member);
        trelloBoardRepository.addTeam(team);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(new ArrayList<>()));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        testParameters.add("1");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testParameters.add("Bug");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_IdNegative() {
        testParameters.add("-1");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }


    @Test
    public void execute_Should_ThrowException_When_WorkItemNotFound() {
        testParameters.add("8");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_MemberNotFound() {
        testParameters.add("8");
        testParameters.add("Ivayl");
        testParameters.add("TrelloBoard");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_BoardNotFound() {
        testParameters.add("8");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoar");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_NotThrowException_When_AssignableItemRemovedSuccessfully() {
        testParameters.add("1");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testCommand.execute(testParameters);
        Assertions.assertEquals(trelloBoardRepository.getWorkItems().size(), 1);
    }
    @Test
    public void execute_Should_NotThrowException_When_FeedbackRemovedSuccessfully() {
        testParameters.add("11");
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");;
        testCommand.execute(testParameters);
        Assertions.assertEquals(trelloBoardRepository.getWorkItems().size(), 1);
    }

}
