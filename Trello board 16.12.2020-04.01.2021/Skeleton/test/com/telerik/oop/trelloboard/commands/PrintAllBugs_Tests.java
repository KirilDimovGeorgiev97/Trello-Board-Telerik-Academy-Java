package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.listWorkItems.PrintAllBugs;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.enums.BugStatus;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Severity;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.worksitems.BugImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrintAllBugs_Tests {
    private TrelloBoardRepository trelloBoardRepository;
    private Command testCommand;
    private List<String> testParameters;

    @BeforeEach
    public void init() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        testCommand = new PrintAllBugs(trelloBoardRepository);
        testParameters = new ArrayList<>();

    }

    @Test
    public void execute_should_notThrowException_when_passedRequiredArguments() {
        trelloBoardRepository.addBug(new BugImpl(1,
                "Login button bug",
                "The button is not working",
                BugStatus.ACTIVE,
                LocalDate.now().plusDays(3),
                Severity.CRITICAL, PriorityType.HIGH));
        Assertions.assertDoesNotThrow(() -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        testParameters.add("Rating");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void printAllFeedbacks_Should_show_When_noBugsBacksExist() {
        Assertions.assertThrows(InvalidInputException.class,()-> testCommand.execute(testParameters));
    }
}
