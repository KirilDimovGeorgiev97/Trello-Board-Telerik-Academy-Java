package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.AddCommentToWorkItem;
import com.telerikacademy.oop.trelloboard.commands.ChangeBoardName;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.CommandParser;
import com.telerikacademy.oop.trelloboard.core.providers.CommandParserImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class ACOMMANDPARSER_TESTS {


    private CommandParser commandParser;

    @BeforeEach
    public void init() {
        commandParser = new CommandParserImpl();
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        List<String> para = commandParser.parseParameters("CreateWorkItem Feedback 3 [Feedback Title Test 1] [Feedback Description 1] New 2021-12-30 5 TrelloBoard Ivaylo");
    }

}
