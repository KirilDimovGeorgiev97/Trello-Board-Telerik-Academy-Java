
package com.telerikacademy.oop.trelloboard.models.common.enums;

import java.util.Arrays;

public enum WorkItemTypes {

    BUG("Bug"),
    FEEDBACK("Feedback"),
    STORY("Story");

    private String value;

    WorkItemTypes(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static boolean containsWorkItem(String type) {
        return Arrays.stream(WorkItemTypes.values()).anyMatch(e -> e.name().equals(type));
    }


}
