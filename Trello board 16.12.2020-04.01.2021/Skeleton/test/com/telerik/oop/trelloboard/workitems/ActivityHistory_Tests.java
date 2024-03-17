package com.telerik.oop.trelloboard.workitems;

import com.telerikacademy.oop.trelloboard.models.ActivityHistoryImpl;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.ActivityHistory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ActivityHistory_Tests {

    @Test
    public void constructor_should_throwException_when_invalidDescription() {
        Assertions.assertThrows(InvalidInputException.class, () -> new ActivityHistoryImpl(null));
    }

    @Test
    public void constructor_should_work_when_validDescription() {
        Assertions.assertDoesNotThrow(()-> new ActivityHistoryImpl("description"));
    }

    @Test
    public void getDescription_should_work_when_validDescription() {
        String description = "description";
        ActivityHistory a = new ActivityHistoryImpl("description");
        Assertions.assertEquals(a.getDescription(), description);
    }

    @Test
    public void getMoment_should_work_when_activityHistory() {
        ActivityHistory activityHistory = new ActivityHistoryImpl("description");
        LocalDate localDate = activityHistory.getMoment();
        Assertions.assertEquals(activityHistory.getMoment(), localDate);
    }

}
