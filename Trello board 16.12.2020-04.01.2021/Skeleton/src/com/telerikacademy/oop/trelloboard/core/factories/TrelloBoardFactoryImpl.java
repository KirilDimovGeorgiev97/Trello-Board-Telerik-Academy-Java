package com.telerikacademy.oop.trelloboard.core.factories;

import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardFactory;
import com.telerikacademy.oop.trelloboard.models.BoardImpl;
import com.telerikacademy.oop.trelloboard.models.MemberImpl;
import com.telerikacademy.oop.trelloboard.models.TeamImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Severity;
import com.telerikacademy.oop.trelloboard.models.common.enums.Size;
import com.telerikacademy.oop.trelloboard.models.common.enums.WorkItemTypes;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.*;
import com.telerikacademy.oop.trelloboard.models.worksitems.BugImpl;
import com.telerikacademy.oop.trelloboard.models.worksitems.FeedbackImpl;
import com.telerikacademy.oop.trelloboard.models.worksitems.StoryImpl;

import java.time.LocalDate;

public class TrelloBoardFactoryImpl implements TrelloBoardFactory {

    @Override
    public Team createTeam(String name) {
        return new TeamImpl(name);
    }

    @Override
    public Member createMember(String name) {
        return new MemberImpl(name);
    }

    @Override
    public Board createBoard(String name) {
        return new BoardImpl(name);
    }

    @Override
    public WorkItem createWorkItem(WorkItemTypes workItemType, int id, String title, String description, Status status,
                                   LocalDate dueDate, Severity severity, Size size, int rating,
                                   PriorityType priorityType) {

        switch (workItemType) {
            case BUG:
                return new BugImpl(id, title, description, status, dueDate, severity, priorityType);
            case STORY:
                return new StoryImpl(id, title, description, status, dueDate, size, priorityType);
            case FEEDBACK:
                return new FeedbackImpl(id, title, description, status, dueDate, rating);
            default:
                throw new InvalidInputException();
        }
    }

}
