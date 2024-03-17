package com.telerikacademy.oop.trelloboard.commands.changeWorkItem;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.enums.Severity;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Bug;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ChangeSeverity implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TrelloBoardRepository trelloBoardRepository;


    public ChangeSeverity(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() != CORRECT_NUMBER_OF_ARGUMENTS) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.INVALID_NUMBER_OF_ARGUMENTS,
                            CORRECT_NUMBER_OF_ARGUMENTS,
                            parameters.size()
                    )
            );
        }

        int id = Integer.parseInt(parameters.get(0));

        if (id < 0) {
            throw new InvalidInputException(
                    String.format(CommandConstants.WORKITEM_NEGATIVE_ID_ERROR_MESSAGE, id));
        }

        String severityValue = parameters.get(1);
        Severity severityToChange = Arrays
                .stream(Severity.values())
                .anyMatch(severity -> severity.toString().replace(" ", "")
                        .equalsIgnoreCase(severityValue)) ?
                Severity.valueOf(severityValue.toUpperCase(Locale.ROOT)) : null;

        if (severityToChange == null) {
            throw new InvalidInputException(
                    String.format(CommandConstants.BUG_SEVERITY_VALUE_ERROR_MESSAGE, severityValue));
        }

        return changeSeverity(id, severityToChange);
    }

    private String changeSeverity(int id, Severity severityToChange) {

        if (trelloBoardRepository.getBugs().size() == 0) {
            throw new InvalidInputException("No work items - Bugs.");
        }

        if (!trelloBoardRepository.getBugs().containsKey(id)) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.BUG_NOT_FOUND_ERROR_MESSAGE,
                            id
                    )
            );
        }

        Bug bug = trelloBoardRepository.getBugs().get(id);

        String currentSeverity = bug.getSeverity().toString();

        bug.setSeverity(severityToChange);

        return String.format(
                CommandConstants.CHANGE_SEVERITY_BUG_SUCCESS_MESSAGE,
                bug.getId(),
                "Bug",
                currentSeverity,
                severityToChange
        );
    }
}