package com.telerikacademy.oop.trelloboard.models.common.enums;

import com.telerikacademy.oop.trelloboard.models.contracts.Status;
import com.telerikacademy.oop.trelloboard.models.contracts.Story;

public enum StoryStatus implements Status {
    NOTDONE("Not done"),
    INPROGRESS("In progress"),
    DONE("Done");

    private String value;

    StoryStatus(String value){
        this.value = value;
    }
    @Override
    public Status getPrevious() {
        if(StoryStatus.values()[this.ordinal()] == NOTDONE)
            return NOTDONE;
        return StoryStatus.values()[this.ordinal()-1];
    }

    @Override
    public Status getNext() {
        if(StoryStatus.values()[this.ordinal()] == DONE)
            return DONE;
        return StoryStatus.values()[this.ordinal()+1];
    }


    @Override
    public String toString() {
        return value;
    }
}
