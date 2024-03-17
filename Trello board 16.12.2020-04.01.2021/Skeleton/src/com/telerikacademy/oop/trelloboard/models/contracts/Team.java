package com.telerikacademy.oop.trelloboard.models.contracts;

import java.util.List;

public interface Team {

    String getName();

    void setName(String name);

    List<Member> getMembers();

    List<Board> getBoards();

    void addBoard(Board board);

    void removeBoard(String board);

    Board findBoard(String name);

    void addMember(Member member);

    void removeMember(String member);

    Member findMember(String name);

    String printMembers();

    String printBoards();

    String toString();

    String viewInfo();
}
