package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.show.ShowTeamActivity;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.TrelloBoardRepositoryImpl;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class ShowTeamActivity_Tests {
    private Command testCommand;
    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        testCommand = new ShowTeamActivity(trelloBoardRepository);
        trelloBoardFactory = new TrelloBoardFactoryImpl();
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(emptyList()));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {


        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList(new String[2])));
    }

    @Test
    public void execute_should_throwException_when_cantFindTheTeam() {
        // Arrange & Act
        String teamName = "PowerRangers";
        trelloBoardRepository.addTeam(trelloBoardFactory.createTeam(teamName));

        // Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(singletonList("Pesho's Team")));
    }

    @Test
    public void execute_should_showMemberActivity_when_inputIsValid() {
        // Arrange & Act
        String teamName = "Ivan4o";
        trelloBoardRepository.addTeam(trelloBoardFactory.createTeam(teamName));

        // Assert
        Assertions.assertDoesNotThrow(() -> testCommand.execute(singletonList("Ivan4o")));
    }
}
