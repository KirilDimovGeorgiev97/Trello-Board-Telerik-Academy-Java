package com.telerikacademy.oop.trelloboard.commands.changeWorkItem;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Assignable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ChangePriority implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TrelloBoardRepository trelloBoardRepository;

    public ChangePriority(TrelloBoardRepository trelloBoardRepository) {
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
            throw new InvalidInputException(String.format(CommandConstants.WORKITEM_NEGATIVE_ID_ERROR_MESSAGE, id));
        }

        String priorityValue = parameters.get(1);
        PriorityType priorityType = Arrays
                .stream(PriorityType.values())
                .anyMatch(priority -> priority.toString().replace(" ", "").equalsIgnoreCase(priorityValue)) ?
                PriorityType.valueOf(priorityValue.toUpperCase(Locale.ROOT)) : null;

        if (priorityType == null) {
            throw new InvalidInputException(
                    String.format(CommandConstants.ASSIGNABLE_PRIORITY_VALUE_ERROR_MESSAGE, priorityValue));
        }

        return changePriority(id, priorityType);
    }

    private String changePriority(int id, PriorityType priorityType) {

        if (!trelloBoardRepository.getAssignable().containsKey(id))
            throw new InvalidInputException(
                    String.format(CommandConstants.WORK_ITEM_NOT_FOUND_ERROR_MESSAGE, id));


        Assignable assignableItem = trelloBoardRepository.getAssignable().get(id);
        PriorityType currPriorityType = assignableItem.getPriorityType();
        assignableItem.setPriorityType(priorityType);

        return String.format(CommandConstants.CHANGE_PRIORITY_SUCCESS_MESSAGE,
                id,
                assignableItem.getClass().getSimpleName().replace("Impl", ""),
                currPriorityType,
                priorityType.toString());
    }
}
