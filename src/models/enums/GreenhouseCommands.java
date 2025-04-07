package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum GreenhouseCommands implements Command {
    GreenhouseBuild("")
    ;

    public final String pattern;

    GreenhouseCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
