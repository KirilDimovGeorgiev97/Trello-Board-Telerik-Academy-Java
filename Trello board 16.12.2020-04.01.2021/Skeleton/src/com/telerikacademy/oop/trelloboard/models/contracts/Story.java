package com.telerikacademy.oop.trelloboard.models.contracts;

import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Size;
import com.telerikacademy.oop.trelloboard.models.common.enums.StoryStatus;

public interface Story extends WorkItem, Assignable {
     Size getSize();

     PriorityType getPriorityType();

     void setPriorityType(PriorityType priorityType);

     void setSize(Size size);

}
