package com.telerikacademy.oop.trelloboard.core.contracts;

import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Severity;
import com.telerikacademy.oop.trelloboard.models.common.enums.Size;
import com.telerikacademy.oop.trelloboard.models.common.enums.WorkItemTypes;
import com.telerikacademy.oop.trelloboard.models.contracts.*;

import java.time.LocalDate;
import java.util.List;

public interface TrelloBoardFactory {

    Board createBoard(String name);

    WorkItem createWorkItem(WorkItemTypes workItemType, int id, String title, String description, Status status,
                            LocalDate dueDate, Severity severity, Size size, int rating, PriorityType priorityType);

    Team createTeam(String name);

    Member createMember(String name);
}
