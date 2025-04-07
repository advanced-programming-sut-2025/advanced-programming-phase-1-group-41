package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum AuthenticationCommands implements Command {
    Register(""),
    Login(""),
    ForgetPass(""),
    RandomPass("")
    ;

    public final String pattern;

    AuthenticationCommands(String pattern) {
        this.pattern = pattern;
    }


    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
