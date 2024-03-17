package com.telerikacademy.oop.trelloboard.commands.changeWorkItem;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Assignable;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;

import java.util.List;

public class AssignWorkItemToMember implements Command {

    private static final int CORRECT_NUMBER_OF_PARAMETERS = 2;

    private TrelloBoardRepository trelloBoardRepository;


    public AssignWorkItemToMember(TrelloBoardRepository trelloBoardRepository) {
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
        String memberToAssignWorkItem = parameters.get(1);


        return assignWorkItemToMember(id, memberToAssignWorkItem);
    }

    private String assignWorkItemToMember(int id, String memberName) {


        if (trelloBoardRepository.getTeams().entrySet().stream().noneMatch(k -> k.getValue().findMember(memberName) != null)) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.MEMBER_NOT_FOUND_ERROR_MESSAGE,
                            memberName
                    )
            );
        }

        if (!trelloBoardRepository.getAssignable().containsKey(id)) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.WORK_ITEM_NOT_FOUND_ERROR_MESSAGE,
                            id)
            );
        }

        Member member = trelloBoardRepository.getMembers().get(memberName);
        Assignable workItem = trelloBoardRepository.getAssignable().get(id);
        Team team = trelloBoardRepository.getTeams()
                .entrySet()
                .stream()
                .filter(k -> k.getValue().findMember(memberName) != null)
                .findFirst().get()
                .getValue();

        trelloBoardRepository.getAssignable().get(id).setAssignee(member);
        member.addAssignedItem(workItem);

        return String.format(
                CommandConstants.ASSIGN_WORKITEM_SUCCESS_MESSAGE,
                workItem.getId(),
                workItem.getTitle(),
                member.getName(),
                team.getName());
    }

}
