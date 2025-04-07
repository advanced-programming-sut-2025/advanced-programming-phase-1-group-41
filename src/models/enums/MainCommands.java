package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum MainCommands implements Command {
    Logout(""),
    ChangeMenu("")
    ;

    public final String pattern;

    MainCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
