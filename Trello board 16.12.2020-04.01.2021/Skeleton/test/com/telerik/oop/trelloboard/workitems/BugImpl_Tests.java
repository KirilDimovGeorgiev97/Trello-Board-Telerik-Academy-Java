package com.telerik.oop.trelloboard.workitems;

import com.telerikacademy.oop.trelloboard.models.MemberImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.BugStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.FeedbackStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Severity;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Bug;
import com.telerikacademy.oop.trelloboard.models.contracts.Status;
import com.telerikacademy.oop.trelloboard.models.worksitems.BugImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.oop.trelloboard.models.common.enums.Severity.CRITICAL;

public class BugImpl_Tests {

    private BugImpl bug;

    @BeforeEach
    public void init() {
         bug = new BugImpl(1,
                "Login button bug",
                "The button is not working",
                BugStatus.ACTIVE,
                LocalDate.now().plusDays(3),
                Severity.CRITICAL, PriorityType.HIGH);
    }

    @Test
    public void constructor_should_throwException_when_negativeID() {
        Assertions.assertThrows(InvalidInputException.class, () -> new BugImpl(-1,
                "Login button bug",
                "The button is not working",
                BugStatus.ACTIVE,
                LocalDate.now().plusDays(3),
                Severity.CRITICAL, PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_invalidTitle() {
        Assertions.assertThrows(InvalidInputException.class, () -> new BugImpl(1,
                "Log",
                "The button is not working",
                BugStatus.ACTIVE,
                LocalDate.now().plusDays(3),
                Severity.CRITICAL, PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_invalidDescription() {

        Assertions.assertThrows(InvalidInputException.class, () -> new BugImpl(1,
                "Login button bug",
                "The",
                BugStatus.ACTIVE,
                LocalDate.now().plusDays(3),
                Severity.CRITICAL, PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_invalidStatus() {
        Assertions.assertThrows(InvalidInputException.class, () -> new BugImpl(1,
                "Login button bug",
                "The button is not working",
                FeedbackStatus.NEW,
                LocalDate.now().plusDays(3),
                Severity.CRITICAL, PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_invalidDueDate() {
        Assertions.assertThrows(InvalidInputException.class, () -> new BugImpl(1,
                "Login button bug",
                "The button is not working",
                BugStatus.ACTIVE,
                LocalDate.now().plusDays(-3),
                Severity.CRITICAL, PriorityType.HIGH));
    }

    @Test
    public void getSteps_should_pass_when_returnShallowCopy(){
        List<String> steps = bug.getSteps();
        steps.add("Asds");
        bug.getSteps().add("ASds");
        Assertions.assertNotEquals(steps,bug.getSteps());
    }

    @Test
    public void addStep_should_not_add_when_stepAlreadyExists(){
        int a = bug.getSteps().size();
        bug.addSteps("1. Click on logging button");
        bug.addSteps("1. Click on logging button");
        Assertions.assertNotEquals(a,bug.getSteps().size()+2);
    }

    @Test
    public void addStep_should_when_severityChange(){
        bug.setSeverity(Severity.MAJOR);
        Assertions.assertEquals(Severity.MAJOR,bug.getSeverity());
    }

    @Test
    public void addStep_should_throwException_when_invalidStep(){
        Assertions.assertThrows(InvalidInputException.class,()->bug.addSteps(null));
    }

    @Test
    public void addStep_should_pass_when_validStep(){
        bug.addSteps("2. go to code");
        Assertions.assertEquals("2. go to code",bug.findStep(0));
    }

    @Test
    public void removeStep_should_throwException_when_invalidStep(){
        Assertions.assertThrows(InvalidInputException.class,()-> bug.removeStep(-1));
    }

    @Test
    public void findStep_should_throwException_when_invalidStep(){
        Assertions.assertThrows(InvalidInputException.class,()-> bug.findStep(-1));
    }

    @Test
    public void advanceStatus_should_pass() {
        bug.advanceStatus();
        Assertions.assertEquals(BugStatus.FIXED, bug.getStatus());
    }

    @Test
    public void advanceStatus_should_throwException_when_alreadyAdvanced() {
        bug.advanceStatus();
        Assertions.assertThrows(InvalidInputException.class,()->bug.advanceStatus());
    }

    @Test
    public void revertStatus_should_pass() {
        bug.advanceStatus();
        bug.revertStatus();
        Assertions.assertEquals(BugStatus.ACTIVE, bug.getStatus());
    }


    @Test
    public void advanceStatus_should_throwException_when_alreadyReverted() {
        Assertions.assertThrows(InvalidInputException.class,()->bug.revertStatus());
    }
}
