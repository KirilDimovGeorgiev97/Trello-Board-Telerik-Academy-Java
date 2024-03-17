package com.telerikacademy.oop.trelloboard.models;

import com.telerikacademy.oop.trelloboard.models.common.Constants;
import com.telerikacademy.oop.trelloboard.models.common.Validator;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class TeamImpl implements Team {

    private String name;
    private List<Member> members;
    private List<Board> boards;
    private List<ActivityHistoryImpl> activityHistories;

    public TeamImpl(String name) {
        Validator.validateString(name,
                Constants.TEAMBASE_NAME_MIN_LENGTH,
                Constants.TEAMBASE_NAME_MAX_LENGTH,
                String.format(Constants.TEAMNAME_ERROR_MESSAGE,
                        Constants.TEAMBASE_NAME_MIN_LENGTH,
                        Constants.TEAMBASE_NAME_MAX_LENGTH));
        this.name = name;
        this.boards = new ArrayList<>();
        this.members = new ArrayList<>();
        this.activityHistories = new ArrayList<>();
        activityHistories.add(new ActivityHistoryImpl(String.format("Team, with name %s, is created", name)));
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public void addBoard(Board board) {
        Validator.checkIfNull(board, String.format(Constants.BOARDINTEAM_ERROR_MESSAGE, name));
        if (!boards.contains(board)) {
            boards.add(board);
            activityHistories.add(new ActivityHistoryImpl(String.format("Board with name %s is added to team-%s", board.getName(), name)));
        } else {
            activityHistories.add(new ActivityHistoryImpl(String.format("Board, with name %s, already exists", board.getName())));
        }
    }

    @Override
    public Board findBoard(String name) {
        Validator.checkIfNull(name, String.format(Constants.BOARDINTEAM_ERROR_MESSAGE, name));
        Board searchedBoard;
        try {
            searchedBoard = boards.stream().filter(board -> board.getName().equals(name)).findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
        return searchedBoard;
    }

    @Override
    public void removeBoard(String boardsName) {
        Validator.checkIfNull(name, String.format(Constants.BOARDINTEAM_ERROR_MESSAGE, name));
        Board board = findBoard(boardsName);
        if (board != null) {
            boards.remove(board);
            activityHistories.add(new ActivityHistoryImpl(String.format("Board,with name %s, is removed from team-%s", boardsName, name)));
        } else
            activityHistories.add(new ActivityHistoryImpl(String.format("Board,with name %s, doesn't exist", boardsName)));
    }


    @Override
    public void addMember(Member member) {
        Validator.checkIfNull(member, "Invalid input");
        if (!members.contains(member)) {
            members.add(member);
            activityHistories.add(new ActivityHistoryImpl(String.format("Member, with name %s, is added to team-%s", member.getName(), name)));
        } else {
            activityHistories.add(new ActivityHistoryImpl(String.format("Member, with name %s, already exists", member.getName())));
        }
    }

    @Override
    public Member findMember(String name) {
        Validator.checkIfNull(name, "Invalid Input");
        Member member;
        try {
            member = members.stream().filter(m -> m.getName().equals(name)).findFirst().get();
        } catch (NoSuchElementException e) {
            return null;
        }
        return member;
    }

    @Override
    public void removeMember(String memberName) {
        Validator.checkIfNull(memberName, "Invalid input");
        Member member = findMember(memberName);
        if (member != null) {
            members.remove(member);
            activityHistories.add(new ActivityHistoryImpl(String.format("Member, with name %s, is removed from team-%s", memberName, name)));
        } else
            activityHistories.add(new ActivityHistoryImpl(String.format("Member,with name %s, doesn't exist", memberName)));
    }

    @Override
    public void setName(String name) {
        Validator.validateString(name,
                Constants.TEAMBASE_NAME_MIN_LENGTH,
                Constants.TEAMBASE_NAME_MAX_LENGTH,
                "Name is not correct");
        activityHistories.add(new ActivityHistoryImpl(String.format("The name of team-%s is changed to %s", this.name, name)));
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TeamImpl)) {
            return false;
        }
        if (((TeamImpl) obj).getName().equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String printMembers() {
        return String.format("Members:%n") +
                members.stream().
                        map(Member::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String printBoards() {
        return String.format("Boards:%n") +
                boards.stream().
                        map(Board::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String viewInfo() {
        return activityHistories
                .stream()
                .map(ActivityHistoryImpl::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String toString() {
        return String.format("-%s", name);
    }
}
