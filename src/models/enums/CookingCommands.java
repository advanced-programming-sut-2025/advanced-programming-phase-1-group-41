package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum CookingCommands implements Command {
    PickFromRef(""),
    PutInRef(""),
    ShowRef(""),
    LearnRecipe(""),
    PrepareFood(""),

    ;

    public final String pattern;

    CookingCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
