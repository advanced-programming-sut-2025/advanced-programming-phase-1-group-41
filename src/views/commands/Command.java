package views.commands;

import models.App;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {
    Matcher getMatcher(String input);

    static Matcher getMatcher(String input, String pattern) {
        return models.Command.getMatcher(input, pattern);
    }

}