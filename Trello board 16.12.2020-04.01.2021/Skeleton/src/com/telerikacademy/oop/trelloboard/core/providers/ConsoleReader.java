package com.telerikacademy.oop.trelloboard.core.providers;

import com.telerikacademy.oop.trelloboard.core.contracts.Reader;

import java.util.Scanner;

public class ConsoleReader implements Reader {

    private Scanner scanner;

    public ConsoleReader() {
        scanner = new Scanner(fakeInput());
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    private String fakeInput() {
        return String.format("CreateMember Ivaylo%n" +
                "CreateMember John1%n" +
                "ShowAllMembers%n" +
                "ShowMemberActivity Ivaylo%n" +
                "ShowMemberActivity John%n" +
                "ShowMemberActivity John1%n" +
                "CreateTeam Omega%n" +
                "CreateTeam Omega%n" +
                "CreateTeam Squad%n" +
                "CreateBoardInTeam TrelloBoard Omega%n" +
                "ShowAllTeams%n" +
                "ShowTeamActivity Omega%n" +
                "AddMemberToTeam Ivaylo Omega%n" +
                "AddMemberToTeam John1 Omega%n" +
                "ShowAllTeamMembers Omega%n" +
                "CreateBoardInTeam TrelloBoard Omega%n" +
                "ShowAllTeamBoards Omega%n" +
                "ShowBoardActivity TrelloBoard Omega%n" +
                "CreateWorkItem Feedback 3 FeedbackTitleTest1 FeedbackDescription1 New 2021-12-30 5 TrelloBoard Ivaylo%n" +
                "CreateWorkItem FeedBack 4 FeedbackTitleTest2 TestDescriptionForFeedBack4 DONE 2021-12-30 4 TrelloBoard Ivaylo%n" +
                "CreateWorkItem FeedBack 1 FeedbackTitleTest3 TestDescriptionForFeedBack1 SCHEDULED 2021-12-30 2 TrelloBoard Ivaylo%n" +
                "CreateWorkItem Story 2 StoryTitleTest1 TestDescriptionForStory2 NotDone 2021-12-30 Medium High TrelloBoard John1%n" +
                "CreateWorkItem Story 10 StoryTitleTest2 TestDescriptionForStory3 NotDone 2021-12-30 Large High TrelloBoard John1%n" +
                "CreateWorkItem Bug 8 BugTitleTest1 Description Active 2021-11-10 critical low TrelloBoard John1 %n" +
                "CreateWorkItem Bug 7 BugTitleTest2 Description3 Active 2021-11-10 minor high TrelloBoard John1%n" +
                "ChangeSize 2 large%n" +
                "ChangeRating 3 10%n" +
                "ChangeSeverity 7 major%n" +
                "ChangePriority 2 low%n" +
                "RevertStatus 3 %n" +
                "AdvanceStatus 3 %n" +
                "AssignWorkItemToMember 2 John1%n" +
                "AssignWorkItemToMember 8 John1%n" +
                "AddCommentToWorkItem Ivaylo TrelloBoard 3 Test%n" +
                "AddCommentToWorkItem Ivaylo TrelloBoard 3 Test%n" +
                "RemoveComment Ivaylo TrelloBoard 3 testComment%n" +
                "RemoveComment Ivaylo TrelloBoard 3 Test%n" +
                "RemoveWorkItem 2 John1 TrelloBoard%n" +
                "RemoveWorkItem 8 John1 TrelloBoard %n" +
                "RemoveWorkItem 4 Ivaylo TrelloBoard %n" +
                "ChangeBoardName Omega TrelloBoard NewTrelloBoard %n" +
                "ChangeMemberName Ivaylo Kiril %n" +
                "ChangeTeamName Omega NewOmega %n" +
                "AllWorkItems%n" +
                "Exit");
        //======================================================

       /* return String.format("CreateMember Ivaylo%n" +
                "CreateMember John1%n" +
                "CreateTeam Omega%n" +
                "CreateTeam Omega%n" +
                "CreateTeam Squad%n" +
                "CreateBoardInTeam TrelloBoard Omega%n" +
                "AddMemberToTeam Ivaylo Omega%n" +
                "AddMemberToTeam John1 Omega%n" +
                "CreateBoardInTeam TrelloBoard Omega%n" +
                "CreateWorkItem Feedback 3 FeedbackTitleTest1 FeedbackDescription1 New 2021-12-30 5 TrelloBoard John1%n" +
                "CreateWorkItem FeedBack 4 FeedbackTitleTest2 TestDescriptionForFeedBack4 DONE 2021-12-30 4 TrelloBoard John1%n" +
                "CreateWorkItem FeedBack 1 FeedbackTitleTest3 TestDescriptionForFeedBack1 Done 2021-12-30 2 TrelloBoard John1%n" +
                "CreateWorkItem Story 2 StoryTitleTest1 StoryDescription1 NotDone 2021-12-30 Medium High TrelloBoard Ivaylo%n" +
                "CreateWorkItem Story 10 StoryTitleTest2 StoryDescription2 NotDone 2021-12-30 Large High TrelloBoard Ivaylo%n" +
                "CreateWorkItem Story 5 StoryTitleTest3 StoryDescription3 NotDone 2021-12-30 Small High TrelloBoard Ivaylo%n" +
                "CreateWorkItem Story 9 StoryTitleTest4 StoryDescription4 NotDone 2021-12-30 Medium High TrelloBoard John1%n" +
                "CreateWorkItem Bug 8 BugTitleTest1 BugDescription1 Active 2021-11-10 critical low TrelloBoard John1%n" +
                "CreateWorkItem Bug 7 BugTitleTest2 BugDescription2 Active 2021-11-10 minor high TrelloBoard John1%n" +
                "CreateWorkItem Bug 6 BugTitleTest3 BugDescription3 Active 2021-11-10 major medium TrelloBoard Ivaylo%n" +
                "CreateWorkItem Bug 11 BugTitleTest4 BugDescription4 Active 2021-11-10 minor low TrelloBoard Ivaylo%n" +
                "AssignWorkItemToMember 2 John1%n" +
                "AssignWorkItemToMember 8 John1%n" +
                "FILTERWORKITEMSBYASSIGNEE John1%n" +
                "UnassignWorkItemToMember 2%n" +
                "FILTERWORKITEMSBYASSIGNEE John1%n" +
                "FILTERWORKITEMSBYSTATUS Feedback done%n" +
                "FILTERWORKITEMSBYSTATUSANDASSIGNE Bug active John1%n" +
                "Exit");*/

        //======================================================

        /*return String.format("CreateMember Ivaylo%n" +
                "CreateMember John1%n" +
                "CreateTeam Omega%n" +
                "CreateTeam Omega%n" +
                "CreateTeam Squad%n" +
                "CreateBoardInTeam TrelloBoard Omega%n" +
                "AddMemberToTeam Ivaylo Omega%n" +
                "AddMemberToTeam John1 Omega%n" +
                "CreateBoardInTeam TrelloBoard Omega%n" +
                "CreateWorkItem Feedback 3 FeedbackTitleTest123 FeedbackDescription1 New 2021-12-30 5 TrelloBoard%n" +
                "CreateWorkItem FeedBack 4 FeedbackTitleTest12 TestDescriptionForFeedBack4 DONE 2021-12-30 4 TrelloBoard Ivaylo%n" +
                "CreateWorkItem FeedBack 1 FeedbackTitleTest1234 TestDescriptionForFeedBack1 Done 2021-12-30 2 TrelloBoard Ivaylo%n" +
                "CreateWorkItem Story 2 StoryTitleTest1234567 StoryDescription1 NotDone 2021-12-30 Medium Low TrelloBoard Ivaylo%n" +
                "CreateWorkItem Story 10 StoryTitleTest12345 StoryDescription2 NotDone 2021-12-30 Large High TrelloBoard John1%n" +
                "CreateWorkItem Story 5 StoryTitleTest1234 StoryDescription3 NotDone 2021-12-30 Small Medium TrelloBoard John1%n" +
                "CreateWorkItem Story 9 StoryTitleTest41234567878 StoryDescription4 NotDone 2021-12-30 Medium Medium TrelloBoard John1%n" +
                "CreateWorkItem Bug 8 BugTitleTest11234567878 BugDescription1 Active 2021-11-10 critical low TrelloBoard John1%n" +
                "CreateWorkItem Bug 7 BugTitleTest2123 BugDescription2 Active 2021-11-10 minor high TrelloBoard John1%n" +
                "CreateWorkItem Bug 6 BugTitleTest322 BugDescription3 Active 2021-11-10 major medium TrelloBoard John1%n" +
                "CreateWorkItem Bug 11 BugTitleTest433 BugDescription4 Active 2021-11-10 minor low TrelloBoard Ivaylo%n" +
                "SORT priority%n" +
                "AllStories%n" +
                "AlLBugs%n" +
                "SORT size%n" +
                "AllStori
                es%n" +
                "SORT severity%n" +
                "AllBugs%n" +
                "Sort rating%n" +
                "AllFeedbacks%n" +
                "SORT title%n" +
                "AllWorkItems%n" +
                "Exit");
*/


       /* return String.format("CreateMember Ivaylo%n" +
                "CreateMember John1%n" +
                "ShowAllMembers%n" +
                "ShowMemberActivity Ivaylo%n" +
                "ShowMemberActivity John%n" +
                "ShowMemberActivity John1%n" +
                "CreateTeam Omega%n" +
                "CreateTeam Omega%n" +
                "CreateTeam Squad%n" +
                "CreateBoardInTeam TrelloBoard Omega%n" +
                "ShowAllTeams%n" +
                "ShowTeamActivity Omega%n" +
                "AddMemberToTeam Ivaylo Omega%n" +
                "AddMemberToTeam John1 Omega%n" +
                "ShowAllTeamMembers Omega%n" +
                "CreateBoardInTeam TrelloBoard Omega%n" +
                "ShowAllTeamBoards Omega%n" +
                "ShowBoardActivity TrelloBoard Omega%n" +
                "CreateWorkItem FeedBack 3 12345678910 TestDescriptionForFeedBack2 New 2021-12-30 5 TrelloBoard John1%n" +
                "AdvanceStatus 3%n" +
                "RevertStatus 3%n" +
                "CreateWorkItem FeedBack 4 123456789101112131415 TestDescriptionForFeedBack4 DONE 2021-12-30 4 TrelloBoard Ivaylo%n" +
                "CreateWorkItem FeedBack 1 123456789101112 TestDescriptionForFeedBack1 SCHEDULED 2021-12-30 2 TrelloBoard John1%n" +
                "CreateWorkItem Story 2 123456789101188 TestDescriptionForStory2 NotDone 2021-12-30 Medium High TrelloBoard John1%n" +
                "CreateWorkItem Story 10 123456789101 TestDescriptionForStory3 NotDone 2021-12-30 Large High TrelloBoard John1%n" +
                "CreateWorkItem Story 5 1234567891013456 TestDescriptionForStory5 NotDone 2021-12-30 Small High TrelloBoard John1%n" +
                "CreateWorkItem Story 9 123456789101234 TestDescriptionForStory9 NotDone 2021-12-30 Medium High TrelloBoard John1%n" +
                "CreateWorkItem Bug 8 123456789101 Description Active 2021-11-10 critical low TrelloBoard John1%n" +
                "CreateWorkItem Bug 7 1234567891058 Description3 Active 2021-11-10 minor high TrelloBoard John1%n" +
                "CreateWorkItem Bug 6 123456789101212 Description2 Active 2021-11-10 major medium TrelloBoard John1%n" +
                "CreateWorkItem Bug 11 1234567891011121314151617181 Description11 Active 2021-11-10 minor low TrelloBoard John1%n" +
                "RemoveWorkItem 8 Ivaylo TrelloBoard %n" +
                "RemoveWorkItem 4 Ivaylo TrelloBoard %n" +
                "ChangePriority 8 high%n" +
                "AssignWorkItemToMember 2 John1%n" +
                "AssignWorkItemToMember 8 John1%n" +
                "FILTERWORKITEMSBYASSIGNEE John1%n" +
                "FILTERWORKITEMSBYSTATUS Feedback done%n" +
                "FILTERWORKITEMSBYSTATUSANDASSIGNE Bug active John1%n" +
                //  "ChangeSeverity Bug 8 major%n" +
                //  "ChangeRating feedback 1 1%n" +
                //  "ChangeSize 2 Large%n" +
                "AssignWorkItemToMember 2 John1%n" +
                "AssignWorkItemToMember 8 John1%n" +
                // "FILTERWORKITEMSBYASSIGNEE John1%n" +
                // "FILTERWORKITEMSBYSTATUSANDASSIGNE Bug active John1%n" +
                // "FILTERWORKITEMSBYSTATUS Feedback Done%n" +
                "UnassignWorkItemToMember 2%n" +
                "AddCommentToWorkItem Ivaylo TrelloBoard 8 comment%n" +
                "RemoveComment Ivayl TrelloBoard 8 comment%n" +
                //"AllWorkItems%n"+
                // "AllBugs%n"+
                // "AllStories%n"+
                //  "AllFeedbacks%n"+
                //"ALLWORKITEMS %n" +
                //"AllStories%n" +
                // "AllBugs%n" +
                "SORT priority%n" +
                "SORT size%n" +
                "SORT severity%n" +
                "SORT title%n" +
                "AllBugs %n" +
                "AllStories%n" +
                //"AllBugs%n" +
                //"AllWorkItems%n"+
                "Exit");*/

    }


}
