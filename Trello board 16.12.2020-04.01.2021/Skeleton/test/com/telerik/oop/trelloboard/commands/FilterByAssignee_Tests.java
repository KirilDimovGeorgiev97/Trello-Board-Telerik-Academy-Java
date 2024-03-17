package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.listWorkItems.FilterByAssignee;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FilterByAssignee_Tests{
    private TrelloBoardRepository trelloBoardRepository;
    private Command testCommand;
    private List<String> testParameters;

    @BeforeEach
    public void init() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        testCommand = new FilterByAssignee(trelloBoardRepository);
        testParameters = new ArrayList<>();
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        testParameters.add("Ivaylo");
        testParameters.add("John1");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }

    @Test
    public void filterByAssignee_Should_ThrowException_When_AssigneeNotFound() {
        testParameters.add("Ivaylo");
        Assertions.assertThrows(InvalidInputException.class, () -> testCommand.execute(testParameters));
    }


}
