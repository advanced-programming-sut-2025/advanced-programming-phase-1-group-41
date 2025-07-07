package com.CEliconValley.views.commands;

import java.util.regex.Matcher;

public interface Command {
    Matcher getMatcher(String input);

    static Matcher getMatcher(String input, String pattern) {
        return com.CEliconValley.models.Command.getMatcher(input, pattern);
    }

}