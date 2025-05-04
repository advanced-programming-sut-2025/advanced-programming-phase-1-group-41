package org.example.views.commands;

import org.example.models.App;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {
    Matcher getMatcher(String input);

    static Matcher getMatcher(String input, String pattern) {
        return org.example.models.Command.getMatcher(input, pattern);
    }

}