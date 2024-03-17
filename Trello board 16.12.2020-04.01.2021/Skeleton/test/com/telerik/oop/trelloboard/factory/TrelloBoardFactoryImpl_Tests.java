package com.telerik.oop.trelloboard.factory;

import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.*;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;
import com.telerikacademy.oop.trelloboard.models.worksitems.BugImpl;
import com.telerikacademy.oop.trelloboard.models.worksitems.FeedbackImpl;
import com.telerikacademy.oop.trelloboard.models.worksitems.StoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TrelloBoardFactoryImpl_Tests {

    private TrelloBoardFactory trelloBoardFactory;

    @BeforeEach
    public void init() {
        trelloBoardFactory = new TrelloBoardFactoryImpl();
    }


    @Test
    public void createTeam_should_create_team_withValidName() {
        Team team = trelloBoardFactory.createTeam("Omega");
        Assertions.assertEquals(team.getName(), "Omega");
    }

    @Test
    public void createMember_should_create_team_withValidName() {
        Member member = trelloBoardFactory.createMember("Ivaylo");
        Assertions.assertEquals(member.getName(), "Ivaylo");
    }

    @Test
    public void createBoard_should_create_team_withValidName() {
        Board board = trelloBoardFactory.createBoard("TrelloBoard");
        Assertions.assertEquals(board.getName(), "TrelloBoard");
    }


    @Test
    public void createBug_should_create_team_withValidArguments() {
        BugImpl bug = (BugImpl) trelloBoardFactory.createWorkItem(WorkItemTypes.BUG,
                8,
                "TitleToTest",
                "Description",
                BugStatus.ACTIVE,
                LocalDate.parse("2021-11-10"),
                Severity.CRITICAL,
                null,
                -1,
                PriorityType.LOW);
        Assertions.assertTrue(bug instanceof BugImpl);
        Assertions.assertEquals(8, bug.getId());
        Assertions.assertEquals(bug.getTitle(), "TitleToTest");
        Assertions.assertEquals(bug.getDescription(), "Description");
        Assertions.assertEquals(bug.getStatus(), BugStatus.ACTIVE);
        Assertions.assertEquals(bug.getDueDate(), LocalDate.parse("2021-11-10"));
        Assertions.assertEquals(bug.getSeverity(), Severity.CRITICAL);
        Assertions.assertEquals(bug.getPriorityType(), PriorityType.LOW);
    }


    @Test
    public void createStory_should_create_team_withValidArguments() {
        StoryImpl story = (StoryImpl) trelloBoardFactory.createWorkItem(WorkItemTypes.STORY,
                8,
                "TitleToTest",
                "Description",
                StoryStatus.DONE,
                LocalDate.parse("2021-11-10"),
                null,
                Size.MEDIUM,
                -1,
                PriorityType.LOW);
        Assertions.assertTrue(story instanceof StoryImpl);
        Assertions.assertEquals(8, story.getId());
        Assertions.assertEquals(story.getTitle(), "TitleToTest");
        Assertions.assertEquals(story.getDescription(), "Description");
        Assertions.assertEquals(story.getStatus(), StoryStatus.DONE);
        Assertions.assertEquals(story.getDueDate(), LocalDate.parse("2021-11-10"));
        Assertions.assertEquals(story.getSize(), Size.MEDIUM);
        Assertions.assertEquals(story.getPriorityType(), PriorityType.LOW);
    }


    @Test
    public void createFeedBack_should_create_team_withValidArguments() {
       FeedbackImpl feedback = (FeedbackImpl) trelloBoardFactory.createWorkItem(WorkItemTypes.FEEDBACK,
                8,
                "TitleToTest",
                "Description",
               FeedbackStatus.NEW,
                LocalDate.parse("2021-11-10"), null, null, 5, null);
        Assertions.assertTrue(feedback instanceof FeedbackImpl);
        Assertions.assertEquals(8, feedback.getId());
        Assertions.assertEquals(feedback.getTitle(), "TitleToTest");
        Assertions.assertEquals(feedback.getDescription(), "Description");
        Assertions.assertEquals(feedback.getStatus(), FeedbackStatus.NEW);
        Assertions.assertEquals(feedback.getDueDate(), LocalDate.parse("2021-11-10"));
        Assertions.assertEquals(feedback.getRating(), 5);

    }


}
