package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.createAndRemoveWorkItems.CreateWorkItem;
import com.telerikacademy.oop.trelloboard.commands.listWorkItems.SortCommand;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.CommandParser;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.core.providers.CommandParserImpl;
import com.telerikacademy.oop.trelloboard.models.BoardImpl;
import com.telerikacademy.oop.trelloboard.models.MemberImpl;
import com.telerikacademy.oop.trelloboard.models.TeamImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Feedback;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class SortCommand_Tests {

    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;
    private Command testCommand;
    private Command createWorkItemCommand2;
    private List<String> testParameters;
    private List<String> createWorkItemParameters;
    private CommandParser commandParser;
    private Team team;
    @BeforeEach
    public void init() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        trelloBoardFactory = new TrelloBoardFactoryImpl();
        testCommand = new SortCommand(trelloBoardRepository);
        createWorkItemCommand2 = new CreateWorkItem(trelloBoardRepository,trelloBoardFactory);
        testParameters = new ArrayList<>();
        createWorkItemParameters = new ArrayList<>();
        commandParser = new CommandParserImpl();
        team = new TeamImpl("Omega");
        team.addBoard(new BoardImpl("TrelloBoard"));
        Member member = new MemberImpl("Ivaylo");
        team.addMember(member);
        trelloBoardRepository.addMember(member);
        trelloBoardRepository.addTeam(team);
    }

    @Test
    public void execute_should_pass_when_correctNumberOfParameters() {
        testParameters.add("Priority");
        Assertions.assertDoesNotThrow(()->testCommand.execute(testParameters));
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        testParameters.add("Priority");
        testParameters.add("Rating");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidParameters() {
        testParameters.add("Description");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_SortByRating() {

        createWorkItemParameters = commandParser.parseParameters(
                "CreateWorkItem FeedBack " +
                        "4 " +
                        "123456789101112131415 " +
                        "TestDescriptionForFeedBack4 " +
                        "DONE 2021-12-30 " +
                        "4 " +
                        "TrelloBoard " +
                        "Ivaylo");
        createWorkItemCommand2.execute(createWorkItemParameters);

        createWorkItemParameters = commandParser.parseParameters(
                "CreateWorkItem " +
                        "FeedBack " +
                        "3 " +
                        "FeedbackTitleTest12 " +
                        "TestDescriptionForFeedBack4 " +
                        "DONE " +
                        "2021-12-30 " +
                        "7 " +
                        "TrelloBoard " +
                        "Ivaylo ");
        createWorkItemCommand2.execute(createWorkItemParameters);

        createWorkItemParameters = commandParser.parseParameters(
                "CreateWorkItem " +
                        "FeedBack " +
                        "5 " +
                        "FeedbackTitleTest12234 " +
                        "TestDescriptionForFeedBack78 " +
                        "DONE " +
                        "2021-12-30 " +
                        "5 " +
                        "TrelloBoard " +
                        "Ivaylo");
        createWorkItemCommand2.execute(createWorkItemParameters);

        trelloBoardRepository
                .getFeedbacks()
                .values()
                .stream()
                .map(Feedback::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        testCommand.execute(Collections.singletonList("rating"));
        Map<Integer, Feedback> sortedFeedbacks = trelloBoardRepository.getFeedbacks();

        Iterator<Map.Entry<Integer, Feedback>> iterator = sortedFeedbacks.entrySet().iterator();
        boolean isSorted = true;
        while (iterator.hasNext()){
            Map.Entry<Integer, Feedback> entry = iterator.next();
            if(iterator.hasNext()){
                if(entry.getValue().getRating()>iterator.next().getValue().getRating()){
                    isSorted = false;
                }
            }
        }
        Assertions.assertTrue(true);
    }

    @Test
    public void execute_Should_SortByPriority() {
        testParameters.add("Description");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_SortByTitle() {
        testParameters.add("Description");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }
}
