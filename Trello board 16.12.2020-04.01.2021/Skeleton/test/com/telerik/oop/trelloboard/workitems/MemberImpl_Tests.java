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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class MemberImpl_Tests {

    private Member testMember;
    WorkItem testItem;

    @BeforeEach
    public void before() {
        testMember = new MemberImpl("Telerik");
        testItem = new StoryImpl(123, "Telerik Academy",
                "description", StoryStatus.DONE, LocalDate.now().plusDays(3), Size.SMALL, PriorityType.HIGH);
    }

    @Test
    public void constructor_should_throwException_when_nameIsNull() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> new MemberImpl(null));
    }

    @Test
    public void constructor_should_throwException_when_nameLengthIsLessThanRequired() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> new MemberImpl("a"));
    }

    @Test
    public void constructor_should_throwException_when_nameLengthIsBiggerThanRequired() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> new MemberImpl("dasdzcvzvxzsadsadsa"));
    }

    @Test
    public void findItem_should_throwException_when_passedNegativeId() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testMember.findCreatedWorkItem(-1));

    }

    @Test
    public void findItem_should_returnNull_when_workItemIsNotFound() {
        // Arrange, Act & Assert
        Assertions.assertNull(testMember.findCreatedWorkItem(testItem.getId()));
    }

    @Test
    public void findWorkItem_should_returnItem_when_inputIsValid() {
        // Arrange & Act
        testMember.addCreatedItem(testItem);

        Assertions.assertEquals(testItem, testMember.findCreatedWorkItem(testItem.getId()));
    }

    @Test
    public void addItem_should_throwException_when_passedNull() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testMember.addCreatedItem(null));
    }

   /* @Test
    public void removeItem_should_throwException_when_passedNull() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testMember.removeItem(null));
    }

    @Test
    public void removeItem_should_removeItem_when_inputIsValid() {
        // Arrange & Act
        testMember.addCreatedItem(testItem);
        testMember.removeItem(testItem);

        // Assert
        Assertions.assertEquals(0, testMember.getWorkItems().size());
    }*/

    @Test
    public void getActivityHistory_should_returnShallowCopy() {
        //Arrange
        ActivityHistory test = new ActivityHistoryImpl("test");
        List<ActivityHistory> supposedShallowCopy = testMember.getActivityHistory();

        //Act
        testMember.getActivityHistory().add(test);

        //Assert
        Assertions.assertEquals(1, supposedShallowCopy.size());
    }

    @Test
    public void getWorkItems_should_returnShallowCopy() {
        // Arrange
        List<WorkItem> supposedShallowCopy = testMember.getCreatedWorkItems();

        // Act
        testMember.addCreatedItem(testItem);

        // Assert
        Assertions.assertEquals(0, supposedShallowCopy.size());

    }

    @Test
    public void setName_should_throwException_when_lengthIsLessThanRequired() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testMember.setName("a"));
    }
    @Test
    public void setName_should_throwException_when_lengthIsBiggerThanRequired() {
        //Arrange, Act & Assert
        Assertions.assertThrows(InvalidInputException.class,
                () -> testMember.setName(new String(new char[16])));
    }

    @Test
    public void setName_should_changeName_when_inputIsValid() {
        //Arrange & Act
        testMember.setName("George");

        //Assert
        Assertions.assertEquals("George", testMember.getName());
    }


}