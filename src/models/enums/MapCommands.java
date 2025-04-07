package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum MapCommands implements Command {
    Walk(""),
    PrintMap(""),
    HelpReadingMap(""),

    ;

    public final String pattern;

    MapCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
