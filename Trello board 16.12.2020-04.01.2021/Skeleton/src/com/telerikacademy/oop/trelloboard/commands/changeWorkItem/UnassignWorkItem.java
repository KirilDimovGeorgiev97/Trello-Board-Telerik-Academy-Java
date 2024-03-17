package com.telerikacademy.oop.trelloboard.commands.changeWorkItem;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Assignable;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;

import java.util.List;

public class UnassignWorkItem implements Command {

    private static final int CORRECT_NUMBER_OF_PARAMETERS = 1;

    private TrelloBoardRepository trelloBoardRepository;

    public UnassignWorkItem(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() != CORRECT_NUMBER_OF_PARAMETERS) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.INVALID_NUMBER_OF_ARGUMENTS,
                            CORRECT_NUMBER_OF_PARAMETERS,
                            parameters.size()
                    )
            );
        }

        int id = Integer.parseInt(parameters.get(0));

        if (id < 0) {
            throw new InvalidInputException(String.format(CommandConstants.WORKITEM_NEGATIVE_ID_ERROR_MESSAGE, id));

        }

        return unassignWorkItem(id);
    }

    private String unassignWorkItem(int id) {

        if (!trelloBoardRepository.getAssignable().containsKey(id)) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.WORK_ITEM_NOT_FOUND_ERROR_MESSAGE,
                            id)
            );
        }

        Assignable workItem = trelloBoardRepository.getAssignable().get(id);

        if (workItem.getAssignee() == null) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.UNASSIGN_ALREADY_ERROR_MESSAGE,
                            workItem.getId(),
                            workItem.getTitle()));
        }

        Member member = workItem.getAssignee();
        Team team = trelloBoardRepository.getTeams().entrySet().
                stream().
                filter(k -> k.getValue().findMember(member.getName()) != null).
                findFirst().
                get().
                getValue();

        trelloBoardRepository.getAssignable().get(id).setAssignee(null);
        member.removeAssignedItem(workItem);

        return String.format(
                CommandConstants.UNASSIGN_WORKITEM_SUCCESS,
                workItem.getId(),
                workItem.getTitle(),
                member.getName(),
                team.getName());
    }
}

