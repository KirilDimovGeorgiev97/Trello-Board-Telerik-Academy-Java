package com.telerikacademy.oop.trelloboard.core;

import com.telerikacademy.oop.trelloboard.commands.common.CommandConstants;
import com.telerikacademy.oop.trelloboard.core.contracts.TrelloBoardRepository;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.*;

import java.util.*;
import java.util.stream.Collectors;

public class TrelloBoardRepositoryImpl implements TrelloBoardRepository {

    private Map<String, Team> teams;
    private Map<String, Member> members;
    private Map<Integer, WorkItem> workItems;
    private Map<Integer, Feedback> feedbacks;
    private Map<Integer, Story> stories;
    private Map<Integer, Bug> bugs;

    public TrelloBoardRepositoryImpl() {
        teams = new LinkedHashMap<>();
        members = new LinkedHashMap<>();
        workItems = new LinkedHashMap<>();
        feedbacks = new LinkedHashMap<>();
        bugs = new LinkedHashMap<>();
        stories = new LinkedHashMap<>();
    }

    @Override
    public Map<String, Team> getTeams() {
        return new LinkedHashMap<>(teams);
    }

    @Override
    public Map<String, Member> getMembers() {
        return new LinkedHashMap<>(members);
    }

    @Override
    public Map<Integer, WorkItem> getWorkItems() {
        return new LinkedHashMap<>(workItems);
    }

    @Override
    public void addTeam(Team team) {
        teams.put(team.getName(), team);
    }

    @Override
    public void addWorkItem(WorkItem workItem) {
        workItems.put(workItem.getId(), workItem);
    }

    @Override
    public void addMember(Member member) {
        members.put(member.getName(), member);
    }

    @Override
    public void removeTeam(String team) {
        teams.remove(team, teams.get(team));
    }

    @Override
    public Map<Integer, Feedback> getFeedbacks() {

        return new LinkedHashMap<>(feedbacks);
    }

    @Override
    public void addFeedback(Feedback feedback) {
        feedbacks.put(feedback.getId(), feedback);
    }

    @Override
    public void removeFeedback(int id) {
        feedbacks.remove(id, feedbacks.get(id));
    }

    @Override
    public Map<Integer, Story> getStories() {
        return new LinkedHashMap<>(stories);
    }

    @Override
    public void addStory(Story story) {
        stories.put(story.getId(), story);
    }

    @Override
    public void removeStory(int id) {
        stories.remove(id, stories.get(id));
    }

    @Override
    public Map<Integer, Bug> getBugs() {
        return new LinkedHashMap<>(bugs);
    }

    @Override
    public void addBug(Bug bug) {
        bugs.put(bug.getId(), bug);
    }

    @Override
    public void removeBug(int id) {
        bugs.remove(id, bugs.get(id));
    }

    @Override
    public Map<Integer, Assignable> getAssignable() {
        Map<Integer, Assignable> assignable = new LinkedHashMap<>(getStories());
        assignable.putAll(bugs);

        return new LinkedHashMap<>(assignable);
    }

    @Override
    public void sortWorkItems(String sortBy) {
        switch (sortBy.toUpperCase(Locale.ROOT)) {
            case "TITLE":
                workItems = workItems.entrySet()
                        .stream()
                        .sorted(Comparator.comparing(item -> item.getValue().getTitle().length()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                break;
            case "PRIORITY":
                stories = stories.entrySet()
                        .stream()
                        .sorted(Comparator.comparing(i -> i.getValue().getPriorityType().getComp()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

                bugs = bugs.entrySet()
                        .stream()
                        .sorted(Comparator.comparing(i -> i.getValue().getPriorityType().getComp()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                break;
            case "SEVERITY":
                bugs = bugs.entrySet()
                        .stream()
                        .sorted(Comparator.comparing(i -> i.getValue().getSeverity().getComp())).
                                collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                break;
            case "SIZE":
                stories = stories.entrySet()
                        .stream()
                        .sorted(Comparator.comparing(i -> i.getValue().getSize().getComp()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                break;
            case "RATING":
                feedbacks = feedbacks.entrySet()
                        .stream()
                        .sorted(Comparator.comparing(i -> i.getValue().getRating()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                break;
            default:
                throw new InvalidInputException(
                        String.format(CommandConstants.INVALID_INPUT_ERROR_MESSAGE, sortBy, "Sort"));
        }
    }

    @Override
    public void removeMember(String name) {
        members.remove(name, members.get(name));
    }

    @Override
    public void removeWorkItem(int id) {
        workItems.remove(id, workItems.get(id));
    }


}
