package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.changeWorkItem.ChangeRating;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeRating_Tests {

    private TrelloBoardRepository trelloBoardRepository;
    private Command testCommand;
    private List<String> testParameters;

    @BeforeEach
    public void init() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        testCommand = new ChangeRating(trelloBoardRepository);
        testParameters = new ArrayList<>();

    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(new ArrayList<>()));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        testParameters.add("1");
        testParameters.add("2");
        testParameters.add("Feedback");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_IdNegative() {
        testParameters.add("-1");
        testParameters.add("2");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_RatingNegative() {
        testParameters.add("1");
        testParameters.add("-2");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_Should_ThrowException_When_WorkItemNotFound() {
        testParameters.add("1");
        testParameters.add("2");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }


}
