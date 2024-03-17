package com.telerik.oop.trelloboard.workitems;

import com.telerikacademy.oop.trelloboard.models.BoardImpl;
import com.telerikacademy.oop.trelloboard.models.MemberImpl;
import com.telerikacademy.oop.trelloboard.models.TeamImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.Board;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TeamImpl_Tests {
    private Team team;
    @BeforeEach
    public void init(){
        team = new TeamImpl("TestTeam");
    }

    @Test
    public void constructor_should_throwException_when_invalidName(){
        Assertions.assertThrows(InvalidInputException.class,()-> new TeamImpl(null));
    }

    @Test
    public void constructor_should_throwException_when_invalidNameMinLength(){
        Assertions.assertThrows(InvalidInputException.class,()-> new TeamImpl("Ab"));
    }

    @Test
    public void constructor_should_throwException_when_invalidNameMaxLength(){
        Assertions.assertThrows(InvalidInputException.class,()-> new TeamImpl("AbcdefghijklmnOp"));
    }

    @Test
    public void getMembers_should_return_shallowCopy(){
        team.addMember(new MemberImpl("TestMember"));
        int size = team.getMembers().size();
        team.getMembers().add(new MemberImpl("TestMember2"));
        Assertions.assertEquals(size,team.getMembers().size());
    }

    @Test
    public void getBoards_should_return_shallowCopy(){
        team.addBoard(new BoardImpl("TestBoard"));
        int size = team.getBoards().size();
        team.getBoards().add(new BoardImpl("TestBoard2"));
        Assertions.assertEquals(size,team.getBoards().size());
    }


    @Test
    public void addMember_should_not_add_when_memberAlreadyExists(){
        int a = team.getMembers().size();
        team.addMember(new MemberImpl("TestMember"));
        team.addMember(new MemberImpl("TestMember"));
        Assertions.assertNotEquals(a,team.getMembers().size()+2);
    }

    @Test
    public void addStep_should_throwException_when_invalidMember(){ ;
        Assertions.assertThrows(InvalidInputException.class,()->team.addMember(null));
    }

    @Test
    public void removeStep_should_throwException_when_invalidMember(){
        Assertions.assertThrows(InvalidInputException.class,()-> team.removeMember(null));
    }

    @Test
    public void findStep_should_throwException_when_invalidMember(){
        Assertions.assertThrows(InvalidInputException.class,()-> team.findMember(null));
    }

    @Test
    public void addBoard_should_not_add_when_boardAlreadyExists(){
        int a = team.getBoards().size();
        team.addBoard(new BoardImpl("TestBoard"));
        team.addBoard(new BoardImpl("TestBoard"));
        Assertions.assertNotEquals(a,team.getMembers().size()+2);
    }

    @Test
    public void addBoard_should_throwException_when_invalidBoard(){ ;
        Assertions.assertThrows(InvalidInputException.class,()->team.addBoard(null));
    }

    @Test
    public void removeBoard_should_throwException_when_invalidBoard(){
        Assertions.assertThrows(InvalidInputException.class,()-> team.removeBoard(null));
    }

    @Test
    public void findBoard_should_throwException_when_invalidBoard(){
        Assertions.assertThrows(InvalidInputException.class,()-> team.findBoard(null));
    }


}
