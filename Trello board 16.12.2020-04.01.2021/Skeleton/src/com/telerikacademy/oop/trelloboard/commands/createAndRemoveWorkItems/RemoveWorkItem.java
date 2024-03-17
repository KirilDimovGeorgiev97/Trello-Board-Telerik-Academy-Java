package com.telerikacademy.oop.trelloboard.commands.createAndRemoveWorkItems;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Assignable;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;

import java.util.List;

public class RemoveWorkItem implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 3;

    private TrelloBoardRepository trelloBoardRepository;


    public RemoveWorkItem(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (CORRECT_NUMBER_OF_ARGUMENTS != parameters.size()) {
            throw new InvalidInputException(
                    String.format(CommandConstants.INVALID_NUMBER_OF_ARGUMENTS,
                            CORRECT_NUMBER_OF_ARGUMENTS,
                            parameters.size()));
        }


        int id = Integer.parseInt(parameters.get(0));
        String creator = parameters.get(1);
        String boardName = parameters.get(2);

        if (id < 0) {
            throw new InvalidInputException(String.format(CommandConstants.WORKITEM_NEGATIVE_ID_ERROR_MESSAGE, id));
        }

        return removeWorkItem(id, creator, boardName);
    }

    private String removeWorkItem(int id, String creator, String boardName) {

        if (!trelloBoardRepository.getWorkItems().containsKey(id)) {
            throw new InvalidInputException(String.format
                    (CommandConstants.WORK_ITEM_NOT_FOUND_ERROR_MESSAGE, id));
        }

        if (trelloBoardRepository.getTeams()
                .values()
                .stream()
                .map(Team::getBoards)
                .noneMatch(e -> e.stream().anyMatch(b -> b.findWorkItem(id) != null
                        && b.getName().equals(boardName)))) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.WORK_ITEM_NOT_FOUND_IN_BOARD, id, boardName));
        }

        if (trelloBoardRepository.getMembers().get(creator).findCreatedWorkItem(id) == null) {
            throw new InvalidInputException(String.format(
                    CommandConstants.WORKITEM_WAS_NOT_CREATED_BY_ERROR_MSG, id, creator));
        }

        Member creatorName = trelloBoardRepository.getMembers().get(creator);
        creatorName.removeCreatedItem(trelloBoardRepository.getWorkItems().get(id));

        if (trelloBoardRepository.getAssignable().containsKey(id)) {
            Assignable assignableItem = trelloBoardRepository.getAssignable().get(id);
            if (assignableItem.getAssignee() != null) {
                Member member = trelloBoardRepository
                        .getMembers()
                        .get(assignableItem.getAssignee().getName());
                member.removeAssignedItem(assignableItem);
            }
            trelloBoardRepository.removeWorkItem(id);
            trelloBoardRepository.removeBug(id);
            trelloBoardRepository.removeStory(id);
        } else {
            trelloBoardRepository.removeWorkItem(id);
            trelloBoardRepository.removeFeedback(id);
        }


        return String.format(CommandConstants.WORK_ITEM_REMOVE_SUCCESS_MESSAGE, id);
    }

}
