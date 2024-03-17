package com.telerikacademy.oop.trelloboard.core.factories;

import com.telerikacademy.oop.trelloboard.commands.*;
import com.telerikacademy.oop.trelloboard.commands.changeWorkItem.*;
import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.commands.createAndRemoveWorkItems.CreateWorkItem;
import com.telerikacademy.oop.trelloboard.commands.createAndRemoveWorkItems.RemoveComment;
import com.telerikacademy.oop.trelloboard.commands.createAndRemoveWorkItems.RemoveWorkItem;
import com.telerikacademy.oop.trelloboard.commands.enums.CommandType;
import com.telerikacademy.oop.trelloboard.commands.listWorkItems.*;
import com.telerikacademy.oop.trelloboard.commands.show.*;
import com.telerikacademy.oop.trelloboard.core.contracts.CommandFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;

import java.util.Locale;


public class CommandFactoryImpl implements CommandFactory {

    private static final String INVALID_COMMAND = "Invalid command name: %s!";

    @Override
    public Command createCommand(String commandTypeAsString, TrelloBoardFactory trelloBoardFactory, TrelloBoardRepository trelloBoardRepository) {
        CommandType commandType = null;
        try {
            commandType = CommandType.valueOf(commandTypeAsString.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException(String.format(INVALID_COMMAND, commandTypeAsString));
        }

        switch (commandType) {
            case CREATEMEMBER:
                return new CreateMember(trelloBoardRepository, trelloBoardFactory);
            case SHOWALLMEMBERS:
                return new ShowAllMembers(trelloBoardRepository);
            case SHOWMEMBERACTIVITY:
                return new ShowMemberActivity(trelloBoardRepository);
            case CREATETEAM:
                return new CreateTeam(trelloBoardRepository, trelloBoardFactory);
            case SHOWALLTEAMS:
                return new ShowAllTeams(trelloBoardRepository);
            case SHOWTEAMACTIVITY:
                return new ShowTeamActivity(trelloBoardRepository);
            case ADDMEMBERTOTEAM:
                return new AddMemberToTeam(trelloBoardRepository);
            case SHOWALLTEAMMEMBERS:
                return new ShowAllTeamMembers(trelloBoardRepository);
            case CREATEBOARDINTEAM:
                return new CreateBoardInTeam(trelloBoardRepository, trelloBoardFactory);
            case SHOWALLTEAMBOARDS:
                return new ShowAllTeamBoards(trelloBoardRepository);
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivity(trelloBoardRepository);
            case CREATEWORKITEM:
                return new CreateWorkItem(trelloBoardRepository, trelloBoardFactory);
            case REMOVEWORKITEM:
                return new RemoveWorkItem(trelloBoardRepository);
            case CHANGETEAMNAME:
                return new ChangeTeamName(trelloBoardRepository);
            case CHANGEMEMBERNAME:
                return new ChangeMemberName(trelloBoardRepository);
            case CHANGEBOARDNAME:
                return new ChangeBoardName(trelloBoardRepository);
            case ADVANCESTATUS:
                return new AdvanceStatus(trelloBoardRepository);
            case REVERTSTATUS:
                return new RevertStatus(trelloBoardRepository);
            case CHANGEPRIORITY:
                return new ChangePriority(trelloBoardRepository);
            case CHANGESEVERITY:
                return new ChangeSeverity(trelloBoardRepository);
            case CHANGERATING:
                return new ChangeRating(trelloBoardRepository);
            case CHANGESIZE:
                return new ChangeSize(trelloBoardRepository);
            case ASSIGNWORKITEMTOMEMBER:
                return new AssignWorkItemToMember(trelloBoardRepository);
            case UNASSIGNWORKITEMTOMEMBER:
                return new UnassignWorkItem(trelloBoardRepository);
            case ADDCOMMENTTOWORKITEM:
                return new AddCommentToWorkItem(trelloBoardRepository);
            case ADDSTEPTOBUG:
                return new AddStepToBug(trelloBoardRepository);
            case REMOVESTEPTOBUG:
                return new RemoveStepToBug(trelloBoardRepository);
            case ALLWORKITEMS:
                return new PrintAllWorkItems(trelloBoardRepository);
            case ALLBUGS:
                return new PrintAllBugs(trelloBoardRepository);
            case ALLSTORIES:
                return new PrintAllStories(trelloBoardRepository);
            case ALLFEEDBACKS:
                return new PrintAllFeedbacks(trelloBoardRepository);
            case FILTERWORKITEMSBYSTATUS:
                return new FilterWorkItemsByStatus(trelloBoardRepository);
            case REMOVECOMMENT:
                return new RemoveComment(trelloBoardRepository);
            case FILTERWORKITEMSBYASSIGNEE:
                return new FilterByAssignee(trelloBoardRepository);
            case FILTERWORKITEMSBYSTATUSANDASSIGNE:
                return new FilterByStatusAndAssignee(trelloBoardRepository);
            case SORT:
                return new SortCommand(trelloBoardRepository);
            default:
                throw new InvalidInputException(String.format(INVALID_COMMAND, commandTypeAsString));
        }
    }
}
