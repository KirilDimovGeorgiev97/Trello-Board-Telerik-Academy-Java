package com.telerikacademy.oop.trelloboard.commands.listWorkItems;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Assignable;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterByAssignee implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;

    private Map<Integer, Assignable> assignableItems;
    private Map<String, Team> teams;

    private TrelloBoardRepository trelloBoardRepository;

    public FilterByAssignee(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
        assignableItems = trelloBoardRepository.getAssignable();
        teams = trelloBoardRepository.getTeams();
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

        String assignee = parameters.get(0);

        return filterByAssignee(assignee);
    }

    private String filterByAssignee(String assignee) {

        if (!trelloBoardRepository.getMembers().containsKey(assignee)) {
            throw new InvalidInputException(String.format(
                    CommandConstants.MEMBER_NOT_FOUND_ERROR_MESSAGE, assignee));
        }

        if (trelloBoardRepository.getMembers().get(assignee).getAssignedWorkItems().isEmpty()) {
            throw new InvalidInputException(
                    String.format(
                            "Filtered by Assignee:%nThere are no such items which satisfy the condition: filter by assignee %s", assignee));
        }

        return String.format("Filtered by Assignee %s:%n", assignee) +
                trelloBoardRepository.
                        getMembers().
                        get(assignee).getAssignedWorkItems().stream().map(Assignable::toString)
                .collect(Collectors.joining(System.lineSeparator()));



    }
}