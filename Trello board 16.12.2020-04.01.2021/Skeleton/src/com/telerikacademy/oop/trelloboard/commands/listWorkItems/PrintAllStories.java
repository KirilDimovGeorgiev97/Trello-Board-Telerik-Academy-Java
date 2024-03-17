package com.telerikacademy.oop.trelloboard.commands.listWorkItems;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Story;

import java.util.List;
import java.util.stream.Collectors;

public class PrintAllStories implements Command {


    private static final int CORRECT_NUMBER_ARGUMENTS = 0;

    private TrelloBoardRepository trelloBoardRepository;

    public PrintAllStories(TrelloBoardRepository trelloBoardRepository) {
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

        return printAllStories();
    }

    private String printAllStories() {
        if (trelloBoardRepository.getStories().size() == 0) {
            throw new InvalidInputException("No items - Stories.");
        }

        return String.format("Stories:%n")
                + trelloBoardRepository.getStories()
                .values()
                .stream()
                .map(Story::toString)
                .collect(Collectors.joining(String.format("%n")));
    }
}