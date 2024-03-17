package com.telerikacademy.oop.trelloboard.commands.changeWorkItem;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.enums.Size;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Story;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ChangeSize implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TrelloBoardRepository trelloBoardRepository;

    public ChangeSize(TrelloBoardRepository trelloBoardRepository) {
        this.trelloBoardRepository = trelloBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() != CORRECT_NUMBER_OF_ARGUMENTS) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.INVALID_NUMBER_OF_ARGUMENTS,
                            CORRECT_NUMBER_OF_ARGUMENTS,
                            parameters.size())
            );
        }

        int id = Integer.parseInt(parameters.get(0));

        if (id < 0) {
            throw new InvalidInputException(
                    String.format(CommandConstants.WORKITEM_NEGATIVE_ID_ERROR_MESSAGE, id));
        }

        String sizeValue = parameters.get(1);

        Size sizeToChange = Arrays
                .stream(Size.values())
                .anyMatch(size -> size.toString().replace(" ", "").equalsIgnoreCase(sizeValue)) ?
                Size.valueOf(sizeValue.toUpperCase(Locale.ROOT)) : null;

        if (sizeToChange == null) {
            throw new InvalidInputException(
                    String.format(CommandConstants.STORY_SIZE_VALUE_ERROR_MESSAGE, sizeToChange));
        }

        return changeSize(id, sizeToChange);
    }

    private String changeSize(int id, Size sizeToChange) {

        if (!trelloBoardRepository.getStories().containsKey(id)) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.STORY_NOT_FOUND_ERROR_MESSAGE,
                            id
                    )
            );
        }

        Story story = trelloBoardRepository.getStories().get(id);

        String currentSize = story.getSize().toString();

        story.setSize(sizeToChange);

        return String.format(CommandConstants.CHANGE_SIZE_STORY_SUCCESS_MESSAGE,
                id,
                sizeToChange.getClass().getSimpleName().replace("Impl", ""),
                currentSize,
                sizeToChange);
    }
}
