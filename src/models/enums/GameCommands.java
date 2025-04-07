package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum GameCommands implements Command {
    NewGame(""),
    GameMap(""),
    LoadGame(""),
    PassToNextUser(""),

    ;

    public final String pattern;

    GameCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
