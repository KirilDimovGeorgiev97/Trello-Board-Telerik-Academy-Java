package com.telerikacademy.oop.trelloboard.core.providers;

import com.telerikacademy.oop.trelloboard.core.contracts.Writer;

public class ConsoleWriter implements Writer {

    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void writeLine(String message) {
        System.out.println(message);
    }
}
