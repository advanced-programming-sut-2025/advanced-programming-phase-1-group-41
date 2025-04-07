package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum ProfileCommands implements Command {
    ChangeUsername(""),
    ChangePassword(""),
    ChangeEmail(""),
    ChangeNickname(""),
    ShowInfo("")
    ;

    public final String pattern;

    ProfileCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
