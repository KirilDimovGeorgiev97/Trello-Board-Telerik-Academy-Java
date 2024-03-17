package com.telerikacademy.oop.trelloboard.commands.listWorkItems;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Bug;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrintAllBugs implements Command {

    private static final int CORRECT_NUMBER_ARGUMENTS = 0;


    private TrelloBoardRepository trelloBoardRepository;

    public PrintAllBugs(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() != CORRECT_NUMBER_ARGUMENTS) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.INVALID_NUMBER_OF_ARGUMENTS,
                            CORRECT_NUMBER_ARGUMENTS,
                            parameters.size()
                    )
            );
        }


        return printAllBugs();
    }

    private String printAllBugs() {

        if (trelloBoardRepository.getBugs().size() == 0) {
            throw new InvalidInputException("No items - Bugs.");
        }

        return String.format("Bugs:%n")+
                trelloBoardRepository.getBugs().values()
                        .stream()
                        .map(Bug::toString)
                        .collect(Collectors.joining(String.format("%n")));
    }
}
