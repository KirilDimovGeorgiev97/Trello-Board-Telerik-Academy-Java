package com.telerik.oop.trelloboard.workitems;

import com.telerikacademy.oop.trelloboard.models.BoardImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Size;
import com.telerikacademy.oop.trelloboard.models.common.enums.StoryStatus;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.worksitems.StoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BoardImpl_Tests {
    private Board board;
    @BeforeEach
    public void init(){
        board = new BoardImpl("TestBoard");
    }

    @Test
    public void constructor_should_throwException_when_invalidName(){
        Assertions.assertThrows(InvalidInputException.class,()-> new BoardImpl(null));
    }

    @Test
    public void constructor_should_throwException_when_invalidNameMinLength(){
        Assertions.assertThrows(InvalidInputException.class,()-> new BoardImpl("Ab"));
    }

    @Test
    public void constructor_should_throwException_when_invalidNameMaxLength(){
        Assertions.assertThrows(InvalidInputException.class,()-> new BoardImpl("AbcdefghijklmnOp"));
    }

    @Test
    public void getWorkItems_should_return_shallowCopy(){
        board.addWorkItem(new StoryImpl(1,
                "Login button story",
                "The button is not working",
                StoryStatus.DONE,
                LocalDate.now().plusDays(3),
                Size.MEDIUM, PriorityType.HIGH));

        int size = board.getWorkItems().size();

        board.getWorkItems().add(new StoryImpl(2,
                "Login button story 2",
                "The button is not working 2",
                StoryStatus.DONE,
                LocalDate.now().plusDays(3),
                Size.MEDIUM, PriorityType.HIGH));

        Assertions.assertEquals(size,board.getWorkItems().size());
    }

    @Test
    public void addMember_should_not_add_when_memberAlreadyExists(){
        int a = board.getWorkItems().size();
        board.addWorkItem(new StoryImpl(1,
                "Login button story",
                "The button is not working",
                StoryStatus.DONE,
                LocalDate.now().plusDays(3),
                Size.MEDIUM, PriorityType.HIGH));
        board.addWorkItem(new StoryImpl(1,
                "Login button story",
                "The button is not working",
                StoryStatus.DONE,
                LocalDate.now().plusDays(3),
                Size.MEDIUM, PriorityType.HIGH));
        Assertions.assertNotEquals(a,board.getWorkItems().size()+2);
    }

    @Test
    public void addStep_should_throwException_when_invalidMember(){ ;
        Assertions.assertThrows(InvalidInputException.class,()->board.addWorkItem(null));
    }

    @Test
    public void removeStep_should_throwException_when_invalidMember(){
        Assertions.assertThrows(InvalidInputException.class,()-> board.removeWorkItem(null));
    }

    @Test
    public void findStep_should_throwException_when_invalidMember(){
        Assertions.assertThrows(InvalidInputException.class,()-> board.findWorkItem(null));
    }

}
