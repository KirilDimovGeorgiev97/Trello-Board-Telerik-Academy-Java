package com.telerikacademy.oop.trelloboard.core.contracts;

import com.telerikacademy.oop.trelloboard.models.contracts.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TrelloBoardRepository {

    Map<String, Team> getTeams();

    Map<String, Member> getMembers();

    Map<Integer, WorkItem> getWorkItems();

    void addTeam(Team team);

    void addWorkItem(WorkItem workItem);

    void addMember(Member member);

    void removeMember(String name);

    void removeWorkItem(int id);

    void removeTeam(String team);

    Map<Integer, Feedback> getFeedbacks();

    void addFeedback(Feedback feedback);

    void removeFeedback(int id);

    Map<Integer, Story> getStories();

    void addStory(Story story);

    void removeStory(int id);

    Map<Integer, Bug> getBugs();

    void addBug(Bug bug);

    void removeBug(int id);

    Map<Integer, Assignable> getAssignable();

    void sortWorkItems(String sortBy);
}
