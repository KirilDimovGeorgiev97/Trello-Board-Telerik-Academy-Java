package com.telerikacademy.oop.trelloboard.commands.enums;

public enum CommandType {
    CREATEMEMBER,
    SHOWALLMEMBERS,
    SHOWMEMBERACTIVITY,
    CREATETEAM,
    SHOWALLTEAMS,
    SHOWTEAMACTIVITY,
    ADDMEMBERTOTEAM,
    SHOWALLTEAMMEMBERS,
    CREATEBOARDINTEAM,
    SHOWALLTEAMBOARDS,
    SHOWBOARDACTIVITY,
    CREATEWORKITEM,
    REMOVEWORKITEM,
    ADVANCESTATUS,
    REVERTSTATUS,
    CHANGETEAMNAME,
    CHANGEMEMBERNAME,
    CHANGEBOARDNAME,
    CHANGEPRIORITY,
    CHANGESEVERITY,
    CHANGERATING,
    CHANGESIZE,
    ASSIGNWORKITEMTOMEMBER,
    UNASSIGNWORKITEMTOMEMBER,
    ADDSTEPTOBUG,
    REMOVESTEPTOBUG,
    ADDCOMMENTTOWORKITEM,
    ALLWORKITEMS,
    ALLBUGS,
    ALLSTORIES,
    ALLFEEDBACKS,
    FILTERWORKITEMSBYSTATUS,
    FILTERWORKITEMSBYASSIGNEE,
    FILTERWORKITEMSBYSTATUSANDASSIGNE,
    REMOVECOMMENT,
    SORT;
}
