package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.AddCommentToWorkItem;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.BoardImpl;
import com.telerikacademy.oop.trelloboard.models.MemberImpl;
import com.telerikacademy.oop.trelloboard.models.TeamImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AddCommentToWorkItem_Tests {

    private TrelloBoardRepository trelloBoardRepository;
    private Command testCommand;
    private List<String> testParameters;

    @BeforeEach
    public void init() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        testCommand = new AddCommentToWorkItem(trelloBoardRepository);
        testParameters = new ArrayList<>();

    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(new ArrayList<>()));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testParameters.add("1");
        testParameters.add("Comment");
        testParameters.add("");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_MemberDoesNotExists() {
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testParameters.add("1");
        testParameters.add("Comment");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_IdNegative() {
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testParameters.add("-1");
        testParameters.add("Comment");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_WorkItemNotFound() {
        Team team = new TeamImpl("Omega");
        team.addMember(new MemberImpl("Ivaylo"));
        Board board = new BoardImpl("TrelloBoard");
        team.addBoard(board);
        trelloBoardRepository.addTeam(team);
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testParameters.add("1");
        testParameters.add("Comment");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_BoardNotFount() {
        Team team = new TeamImpl("Omega");
        team.addMember(new MemberImpl("Ivaylo"));
        trelloBoardRepository.addTeam(team);
        testParameters.add("Ivaylo");
        testParameters.add("TrelloBoard");
        testParameters.add("1");
        testParameters.add("Comment");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

}
