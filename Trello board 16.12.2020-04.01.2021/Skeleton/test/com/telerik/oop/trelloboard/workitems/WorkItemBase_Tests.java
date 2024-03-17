package com.telerik.oop.trelloboard.workitems;

import com.telerikacademy.oop.trelloboard.models.ActivityHistoryImpl;
import com.telerikacademy.oop.trelloboard.models.MemberImpl;
import com.telerikacademy.oop.trelloboard.models.common.enums.PriorityType;
import com.telerikacademy.oop.trelloboard.models.common.enums.Size;
import com.telerikacademy.oop.trelloboard.models.common.enums.StoryStatus;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;
import com.telerikacademy.oop.trelloboard.models.contracts.ActivityHistory;
import com.telerikacademy.oop.trelloboard.models.contracts.Member;
import com.telerikacademy.oop.trelloboard.models.contracts.WorkItem;
import com.telerikacademy.oop.trelloboard.models.worksitems.StoryImpl;
import com.telerikacademy.oop.trelloboard.models.worksitems.WorkItemBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkItemBase_Tests {

    private Member testMember;
    private Map<Member, List<String>> comments;
    private WorkItem testItem;

    @BeforeEach
    public void before() {
        testMember = new MemberImpl("Pesho");
        comments = new HashMap<>();
        testItem = new StoryImpl(123, "Telerik Academy",
                "description", StoryStatus.DONE, LocalDate.now(), Size.SMALL, PriorityType.HIGH);
    }

    @Test
    public void constructor_should_throwException_when_descriptionIsNull() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> new StoryImpl(123, "sasdasdasdas", null, StoryStatus.DONE, LocalDate.now().plusDays(3), Size.MEDIUM, PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_descriptionLengthIsLessThanRequired() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> new StoryImpl(123, "title title", "test", StoryStatus.DONE, LocalDate.now().plusDays(3), Size.MEDIUM, PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_descriptionLengthIsBiggerThanRequired() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> new StoryImpl(123, "titletitle",
                        new String(new char[501]), StoryStatus.DONE, LocalDate.now().plusDays(3), Size.SMALL, PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throw_Exception_when_titleIsNull() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> new StoryImpl(123, null, "description", StoryStatus.DONE, LocalDate.now().plusDays(3), Size.MEDIUM, PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_titleLengthIsLessThanRequired() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> new StoryImpl(123, "title", "description", StoryStatus.DONE, LocalDate.now().plusDays(3), Size.MEDIUM, PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_titleLengthIsBiggerThanRequired() {
        //Arrange, Act, Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> new StoryImpl(123, new String(new char[51]),
                        "description", StoryStatus.DONE, LocalDate.now().plusDays(3), Size.MEDIUM, PriorityType.HIGH));
    }

    @Test
    public void constructor_should_throwException_when_IdIsNegative() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> new StoryImpl(-123, "title title", "description",
                        StoryStatus.INPROGRESS, LocalDate.now().plusDays(3), Size.MEDIUM, PriorityType.HIGH));
    }

    @Test
    public void removeComment_should_removeComment_when_passedPresentComments() {
        // Arrange
        WorkItemBase item = new StoryImpl(123, "title title",
                "description", StoryStatus.DONE, LocalDate.now().plusDays(3), Size.SMALL, PriorityType.HIGH);
        item.addComment(testMember, "test");

        // Act
        item.removeComment(testMember, "test");

        // Assert
        Assertions.assertEquals(0, item.getComments().get(testMember).size());
    }

    @Test
    public void removeActivityHistory_should_removeActivityHistory_when_passedPresent_ActivityHistory() {
        //Arrange
        List<String> activityHistoryTest = new ArrayList<>();
        activityHistoryTest.add("test");

        //Act
        activityHistoryTest.remove("test");

        //Assert
        Assertions.assertEquals(0, activityHistoryTest.size());
    }

    @Test
    public void containsMessage_should_return_false_when_commentDoesNotExist() {
        // Arrange & Act
        testItem.addComment(testMember, "true");

        // Assert
        Assertions.assertFalse(comments.entrySet().stream().anyMatch(c -> c.getValue().contains("false")));
    }

    @Test
    public void addComment_should_add_comment() {
        // Arrange & Act
        if (!comments.containsKey(testMember)) {
            comments.put(testMember, new ArrayList<>());
        }
        comments.get(testMember).add("test message");

        // Assert
        Assertions.assertEquals(1, comments.values()
                .stream().mapToInt(List::size).count());
    }

    @Test
    public void getActivityHistory_should_return_shallowCopy() {
        //Arrange & Act
        List<ActivityHistory> supposedShallowCopy = testItem.getActivityHistory();
        testItem.getActivityHistory().add(new ActivityHistoryImpl("Test"));

        //Assert
        Assertions.assertEquals(1, supposedShallowCopy.size());
    }


}
