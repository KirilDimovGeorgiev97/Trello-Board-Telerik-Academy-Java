package com.telerik.oop.trelloboard.commands;


import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.commands.listWorkItems.PrintAllFeedbacks;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.enums.FeedbackStatus;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.worksitems.FeedbackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrintAllFeedBacks_Tests {
    private TrelloBoardRepository trelloBoardRepository;
    private Command testCommand;
    private List<String> testParameters;

    @BeforeEach
    public void init() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        testCommand = new PrintAllFeedbacks(trelloBoardRepository);
        testParameters = new ArrayList<>();

    }

    @Test
    public void execute_should_notThrowException_when_passedRequiredArguments() {
        trelloBoardRepository.addFeedback(new FeedbackImpl(11, "Telerik Academy", "description",
                FeedbackStatus.NEW, LocalDate.now(), 1));
        Assertions.assertDoesNotThrow(() -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        testParameters.add("Rating");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void printAllFeedbacks_Should_show_When_noFeedBacksExist() {
        Assertions.assertThrows(InvalidInputException.class,()-> testCommand.execute(testParameters));
    }
}
