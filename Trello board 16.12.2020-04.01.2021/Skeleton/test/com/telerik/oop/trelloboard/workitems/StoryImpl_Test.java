package com.telerik.oop.trelloboard.workitems;

import com.telerikacademy.oop.trelloboard.models.common.enums.*;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Story;
import com.telerikacademy.oop.trelloboard.models.worksitems.StoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class StoryImpl_Test {

    private Story story;

    @BeforeEach
    public void init() {
        story = new StoryImpl(1,
                "Login button story",
                "The button is not working",
                StoryStatus.INPROGRESS,
                LocalDate.now().plusDays(3),
                Size.MEDIUM,PriorityType.HIGH);
    }

    @Test
    public void constructor_should_throwException_when_negativeID() {
        Assertions.assertThrows(InvalidInputException.class, () -> new StoryImpl(-1,
                "Login button story",
                "The button is not working",
                StoryStatus.DONE,
                LocalDate.now().plusDays(3),
                Size.MEDIUM,PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_invalidTitle() {
        Assertions.assertThrows(InvalidInputException.class, () -> new StoryImpl(1,
                "Log",
                "The button is not working",
                StoryStatus.DONE,
                LocalDate.now().plusDays(3),
                Size.MEDIUM,PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_invalidDescription() {
        Assertions.assertThrows(InvalidInputException.class, () -> new StoryImpl(1,
                "Login button story",
                "The",
                StoryStatus.DONE,
                LocalDate.now().plusDays(3),
                Size.MEDIUM,PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_invalidStatus() {
        Assertions.assertThrows(InvalidInputException.class, () -> new StoryImpl(1,
                "Login button story",
                "The button is not working",
                FeedbackStatus.NEW,
                LocalDate.now().plusDays(3),
                Size.MEDIUM,PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_invalidDueDate() {
        Assertions.assertThrows(InvalidInputException.class, () -> new StoryImpl(1,
                "Login button story",
                "The button is not working",
                StoryStatus.DONE,
                LocalDate.now().plusDays(-3),
                Size.MEDIUM,PriorityType.HIGH));
    }

    @Test
    public void advanceStatus_should_pass_when_changeSize() {
        story.setSize(Size.LARGE);
        Assertions.assertEquals(Size.LARGE, story.getSize());
    }

    @Test
    public void advanceStatus_should_pass() {
        story.advanceStatus();
        Assertions.assertEquals(StoryStatus.DONE, story.getStatus());
    }

    @Test
    public void advanceStatus_should_throwException_when_alreadyAdvanced() {
        story.advanceStatus();
        Assertions.assertThrows(InvalidInputException.class,()->story.advanceStatus());
    }

    @Test
    public void revertStatus_should_pass() {
        story.revertStatus();
        Assertions.assertEquals(StoryStatus.NOTDONE, story.getStatus());
    }

    @Test
    public void advanceStatus_should_throwException_when_alreadyReverted() {
        story.revertStatus();
        Assertions.assertThrows(InvalidInputException.class,()->story.revertStatus());
    }

}
