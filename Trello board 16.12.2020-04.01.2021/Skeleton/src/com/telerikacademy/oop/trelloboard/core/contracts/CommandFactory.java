package com.telerikacademy.oop.trelloboard.core.contracts;

import com.telerikacademy.oop.trelloboard.commands.contracts.Command;

public interface CommandFactory {

    Command createCommand(String commandTypeAsString, TrelloBoardFactory furnitureFactory, TrelloBoardRepository furnitureRepository);
}
