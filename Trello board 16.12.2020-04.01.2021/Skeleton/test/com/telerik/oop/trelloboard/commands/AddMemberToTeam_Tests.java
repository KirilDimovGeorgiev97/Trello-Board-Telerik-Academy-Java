package com.telerik.oop.trelloboard.commands;

import com.telerikacademy.oop.trelloboard.commands.AddMemberToTeam;
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

public class AddMemberToTeam_Tests {

    private Command testCommand;
    private TrelloBoardRepository trelloBoardRepository;
    private TrelloBoardFactory trelloBoardFactory;

    @BeforeEach
    public void before() {
        trelloBoardRepository = new TrelloBoardRepositoryImpl();
        trelloBoardFactory = new TrelloBoardFactoryImpl();
        testCommand = new AddMemberToTeam(trelloBoardRepository);
    }

    @Test
    public void execute_should_throwException_when_passedLessArguments() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(emptyList()));
    }

    @Test
    public void execute_should_throwException_when_passedMoreArguments() {
        // Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList(new String[3])));
    }

    @Test
    public void execute_should_throwException_when_cantFindMemberToAdd() {
        // Arrange & Act
        String memberName = "Master";
        String teamName = "DreamTeam";
        trelloBoardRepository.addMember(trelloBoardFactory.createMember(memberName));
        trelloBoardRepository.addTeam(trelloBoardFactory.createTeam(teamName));

        // Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList("Pesho", teamName)));
    }

    @Test
    public void execute_should_throwException_when_cantFindTeam() {
        // Arrange & Act
        String memberToAdd = "Ivan4o";
        String teamName = "DreamTeam";
        trelloBoardRepository.addMember(trelloBoardFactory.createMember(memberToAdd));
        trelloBoardRepository.addTeam(trelloBoardFactory.createTeam(teamName));

        //Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testCommand.execute(asList(memberToAdd, "PowerRangers")));
    }

    @Test
    public void execute_should_addMemberToTeam_when_inputIsValid() {
        // Arrange & Act
        String memberToAdd = "Ivan4o";
        String teamName = "DreamTeam";
        trelloBoardRepository.addMember(trelloBoardFactory.createMember(memberToAdd));
        trelloBoardRepository.addTeam(trelloBoardFactory.createTeam(teamName));
        testCommand.execute(asList(memberToAdd, teamName));

        //Assert
        Assertions.assertEquals(1, trelloBoardRepository.getTeams().size());

    }


}
