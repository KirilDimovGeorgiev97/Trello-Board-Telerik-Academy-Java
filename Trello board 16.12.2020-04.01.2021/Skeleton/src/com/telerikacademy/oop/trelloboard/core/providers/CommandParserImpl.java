package com.telerikacademy.oop.trelloboard.core.providers;

import com.telerikacademy.oop.trelloboard.core.contracts.CommandParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandParserImpl implements CommandParser {
    @Override
    public String parseCommand(String fullCommand) {
        return fullCommand.split(" ")[0];
    }

    @Override
    public List<String> parseParameters(String fullCommand) {
        List<String> para = parseParametersWhenTextExists(fullCommand);
        return Arrays.stream(fullCommand.split("\\s+")).skip(1).collect(Collectors.toList());
    }

    private List<String> parseParametersWhenTextExists(String fullCommand) {
        List<String> para = new ArrayList<>();
        while (true){
            if (fullCommand.contains("[") && fullCommand.contains("]")) {
                String currPara = fullCommand.substring(fullCommand.indexOf("["), fullCommand.indexOf("]"));
                fullCommand = fullCommand.replace(currPara, "");
                fullCommand = fullCommand.replaceFirst("]", "");
                currPara = currPara.replace("[", "");
                para.add(currPara);
               /* int a = fullCommand.indexOf("[");
                int b = fullCommand.indexOf("]");*/
                /*String description = fullCommand.substring(fullCommand.indexOf("["), fullCommand.indexOf("]"));
                description = description.replace("[", "");
                fullCommand = fullCommand.replace(title, "");
                fullCommand = fullCommand.replaceFirst("]", "");
                title = title.replace("[", "");*/
            } else {
                break;
            }
        }

        String z = "o";
        String q = "o";
        q = "o"+1;

        return para;
    }
}
