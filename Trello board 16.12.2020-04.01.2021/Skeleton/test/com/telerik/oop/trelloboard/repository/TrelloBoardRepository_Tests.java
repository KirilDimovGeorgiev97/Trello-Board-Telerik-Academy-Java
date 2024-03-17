package com.telerik.oop.trelloboard.repository;

import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.commands.listWorkItems.SortCommand;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.*;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrelloBoardRepository_Tests {

    private Map<String, Team> teams;
    private Map<String, Member> members;
    private Map<Integer, WorkItem> workItems;
    private Map<Integer, Feedback> feedbacks;
    private Map<Integer, Story> stories;
    private Map<Integer, Bug> bugs;
    private TrelloBoardFactory trelloBoardFactory;
    private TrelloBoardRepository trelloBoardRepository;
    private Feedback testFeedback;
    private Story testStory;

    @BeforeEach
    public void before() {
        teams = new LinkedHashMap<>();
        members = new LinkedHashMap<>();
        workItems = new LinkedHashMap<>();
        feedbacks = new LinkedHashMap<>();
        bugs = new LinkedHashMap<>();
        stories = new LinkedHashMap<>();
        trelloBoardFactory = new TrelloBoardFactoryImpl();
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        testFeedback = (Feedback) trelloBoardFactory.createWorkItem(WorkItemTypes.FEEDBACK, 1,
                "Feedback1TestTitle", "description1", FeedbackStatus.DONE,
                LocalDate.now().plusDays(2), null, null, 1, null);
        testStory = (Story) trelloBoardFactory.createWorkItem(WorkItemTypes.STORY, 7,
                "StoryTestTitle", "description", StoryStatus.DONE,
                LocalDate.now().plusDays(2), null, Size.SMALL, 1, PriorityType.LOW);
    }

    @Test
    public void removeTeam_should_removeTeam_when_inputIsValid() {
        // Arrange & Act
        Team testTeam = trelloBoardFactory.createTeam("BestTeam");
        trelloBoardRepository.addTeam(testTeam);
        trelloBoardRepository.removeTeam("BestTeam");

        // Assert
        Assertions.assertEquals(0, teams.size());
    }

    @Test
    public void getFeedbacks_should_returnShallowCopy() {
        //Arrange & Act
        Map<Integer, Feedback> supposedShallowCopy = trelloBoardRepository.getFeedbacks();
        Feedback feedback = (Feedback) trelloBoardFactory.createWorkItem(WorkItemTypes.FEEDBACK, 1,
                "FeedbackTestTitle", "description", FeedbackStatus.DONE,
                LocalDate.now().plusDays(2), null, null, 1, null);

        feedbacks.put(1, feedback);

        //Assert
        Assertions.assertEquals(0, supposedShallowCopy.size());
    }

    @Test
    public void addFeedback_should_putAllFeedbacks() {
        // Arrange
        Feedback feedback2 = (Feedback) trelloBoardFactory.createWorkItem(WorkItemTypes.FEEDBACK, 12,
                "Feedback2TestTitle", "description2", FeedbackStatus.NEW,
                LocalDate.now().plusDays(2), null, null, 1, null);

        // Act
        trelloBoardRepository.addFeedback(testFeedback);
        trelloBoardRepository.addFeedback(feedback2);

        // Assert
        Assertions.assertEquals(2, trelloBoardRepository.getFeedbacks().size());
    }

    @Test
    public void removeFeedback_should_removeFeedback() {
        // Arrange & Act
        trelloBoardRepository.addFeedback(testFeedback);
        trelloBoardRepository.removeFeedback(testFeedback.getId());

        // Assert
        Assertions.assertEquals(0, trelloBoardRepository.getFeedbacks().size());
    }

    @Test
    public void getStories_should_returnShallowCopy() {
        // Arrange
        Map<Integer, Story> supposedShallowCopy = trelloBoardRepository.getStories();

        // Act
        trelloBoardRepository.addStory(testStory);

        // Assert
        Assertions.assertEquals(0, supposedShallowCopy.size());
    }

    @Test
    public void removeStory_should_removeStory() {
        // Arrange & Act
        trelloBoardRepository.addStory(testStory);
        trelloBoardRepository.removeStory(testStory.getId());

        // Assert
        Assertions.assertEquals(0, trelloBoardRepository.getStories().size());
    }

    @Test
    public void removeBug_should_removeBug() {
        // Arrange
        Bug testBug = (Bug) trelloBoardFactory.createWorkItem(WorkItemTypes.BUG, 8,
                "BugTestTitle", "description", BugStatus.ACTIVE,
                LocalDate.now().plusDays(2), Severity.CRITICAL, null, 1, PriorityType.LOW);

        // Act
        trelloBoardRepository.addBug(testBug);
        trelloBoardRepository.removeBug(testBug.getId());

        // Assert
        Assertions.assertEquals(0, trelloBoardRepository.getBugs().size());
    }

    @Test
    public void getAssignable_should_returnShallowCopy() {
        // Arrange
        Map<Integer, Assignable> supposedShallowCopy = trelloBoardRepository.getAssignable();

        // Act
        trelloBoardRepository.addStory(testStory);

        // Assert
        Assertions.assertEquals(0, supposedShallowCopy.size());
    }

    @Test
    public void sortWorkItems_should_throwException_when_workItemTypeIsInvalid() {
        // Arrange
        Command testCommand = new SortCommand(trelloBoardRepository);

        // Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(Collections.singletonList("Pesho")));
    }

    @Test
    public void removeMember_should_removeMember() {
        // Arrange
        trelloBoardRepository.addMember(trelloBoardFactory.createMember("Ivan4o"));

        // Act
        trelloBoardRepository.removeMember("Ivan4o");

        // Assert
        Assertions.assertEquals(0, trelloBoardRepository.getMembers().size());
    }

    @Test
    public void removeWorkItem_should_removeWorkItem() {
        // Arrange
        trelloBoardRepository.addWorkItem(testFeedback);

        // Act
        trelloBoardRepository.removeWorkItem(testFeedback.getId());

        // Assert
        Assertions.assertEquals(0, trelloBoardRepository.getWorkItems().size());
    }
}
