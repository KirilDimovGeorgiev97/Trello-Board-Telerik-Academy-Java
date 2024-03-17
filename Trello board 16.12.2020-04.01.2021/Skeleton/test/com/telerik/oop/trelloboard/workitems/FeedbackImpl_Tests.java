package com.telerik.oop.trelloboard.workitems;

import com.telerikacademy.oop.trelloboard.models.common.enums.FeedbackStatus;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Feedback;
import com.telerikacademy.oop.trelloboard.models.worksitems.FeedbackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class FeedbackImpl_Tests {

    private FeedbackImpl startFromDoneStatus;
    private FeedbackImpl startFromNewStatus;

    @BeforeEach
    public void before() {
        startFromDoneStatus = new FeedbackImpl(123, "Telerik Academy",
                "description", FeedbackStatus.DONE, LocalDate.now(), 2);
        startFromNewStatus = new FeedbackImpl(11, "Telerik Academy", "description",
                FeedbackStatus.NEW, LocalDate.now(), 1);
    }

    @Test
    public void constructor_should_throwException_when_ratingIsNegative() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> new FeedbackImpl(123, "title title",
                        "description", FeedbackStatus.NEW, LocalDate.now(), -1));
    }

    @Test
    public void revertStatus_should_revertStatus() {
        //Arrange & Act
        startFromDoneStatus.revertStatus();

        //Assert
        Assertions.assertEquals(FeedbackStatus.SCHEDULED, startFromDoneStatus.getStatus());
    }

    @Test
    public void revertStatus_should_throwException_when_revertFromNew() {
        //Arrange
        Feedback test = new FeedbackImpl(11, "Telerik Academy", "description",
                FeedbackStatus.NEW, LocalDate.now(), 1);

        //Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                test::revertStatus);
    }

    @Test
    public void advanceStatus_should_throwException_when_tryAdvanceFromDone() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                startFromDoneStatus::advanceStatus);
    }

    @Test
    public void advanceStatus_should_advanceStatus_when_tryAdvanceFromNew() {
        //Arrange & Act
        startFromNewStatus.advanceStatus();

         //Assert
        Assertions.assertEquals(FeedbackStatus.UNSCHEDULED, startFromNewStatus.getStatus());
    }

}
