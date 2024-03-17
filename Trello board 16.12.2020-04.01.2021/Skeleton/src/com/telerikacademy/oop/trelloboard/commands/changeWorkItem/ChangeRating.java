package com.telerikacademy.oop.trelloboard.commands.changeWorkItem;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Feedback;

import java.util.List;

public class ChangeRating implements Command {

    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TrelloBoardRepository trelloBoardRepository;

    public ChangeRating(TrelloBoardRepository trelloBoardRepository) {
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
        int ratingToChange = Integer.parseInt(parameters.get(1));

        if (id < 0) {
            throw new InvalidInputException(
                    String.format(CommandConstants.WORKITEM_NEGATIVE_ID_ERROR_MESSAGE, id));
        }

        if (ratingToChange < 0) {
            throw new InvalidInputException(
                    String.format(CommandConstants.FEEDBACK_CHANGE_RATING_ERROR_MESSAGE, id));
        }

        return changeRating(id, ratingToChange);
    }

    private String changeRating(int id, int ratingToChange) {

        if (!trelloBoardRepository.getFeedbacks().containsKey(id)) {
            throw new InvalidInputException(
                    String.format(
                            CommandConstants.FEEDBACK_NOT_FOUND_ERROR_MESSAGE,
                            id
                    )
            );
        }

        Feedback feedback = trelloBoardRepository.getFeedbacks().get(id);

        int currentRating = feedback.getRating();

        feedback.setRating(ratingToChange);

        return String.format(
                CommandConstants.CHANGE_RATING_FEEDBACK_SUCCESS_MESSAGE,
                feedback.getId(),
                "Feedback",
                currentRating,
                ratingToChange);
    }

}
