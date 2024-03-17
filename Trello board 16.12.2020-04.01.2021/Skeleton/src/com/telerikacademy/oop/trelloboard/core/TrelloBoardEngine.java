package com.telerikacademy.oop.trelloboard.core;

import com.telerikacademy.oop.trelloboard.commands.contracts.Command;
import com.telerikacademy.oop.trelloboard.core.contracts.*;
import com.telerikacademy.oop.trelloboard.core.factories.CommandFactoryImpl;
import com.telerikacademy.oop.trelloboard.core.factories.TrelloBoardFactoryImpl;
import com.telerikacademy.oop.trelloboard.core.providers.CommandParserImpl;
import com.telerikacademy.oop.trelloboard.core.providers.ConsoleReader;
import com.telerikacademy.oop.trelloboard.core.providers.ConsoleWriter;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.DuplicateException;
import com.telerikacademy.oop.trelloboard.models.common.exceptions.InvalidInputException;

import java.util.List;

public class TrelloBoardEngine implements Engine {

    private static final String TERMINATION_COMMAND = "Exit";
    private static final String ERROR_EMPTY_COMMAND = "Command cannot be null or empty.";

    private CommandFactory commandFactory;
    private TrelloBoardFactory trelloBoardFactory;
    private TrelloBoardRepository trelloBoardRepository;
    private CommandParser commandParser;
    private final Writer writer;
    private final Reader reader;

    public TrelloBoardEngine() {
        this.commandFactory = new CommandFactoryImpl();
        this.trelloBoardFactory = new TrelloBoardFactoryImpl();
        this.trelloBoardRepository = new TrelloBoardRepositoryImpl();
        this.commandParser = new CommandParserImpl();
        this.writer = new ConsoleWriter();
        this.reader = new ConsoleReader();
    }

    @Override
    public void start() {
        while (true) {
            try {
                String commandAsString = reader.readLine();

                if (commandAsString.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(commandAsString);

            } catch (InvalidInputException | DuplicateException |IllegalArgumentException ex) {
                writer.writeLine(ex.getMessage() != null && !ex.getMessage().isEmpty() ? ex.getMessage() : ex.toString());
            }
        }
    }

    private void processCommand(String commandAsString) {
        String commandName = commandParser.parseCommand(commandAsString);
        List<String> parameters = commandParser.parseParameters(commandAsString);
        Command command = commandFactory.createCommand(commandName, trelloBoardFactory, trelloBoardRepository);
        String result = command.execute(parameters);
        writer.writeLine(result);
    }
}
