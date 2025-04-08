package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum CraftigCommands implements Command {
    ShowRecipes(""),
    LearnRecipe(""),
    CraftRecipe(""),
    CheatAddRecipe(""),
    ArtisanUse(""),
    ArtisanGet(""),

    ;
    public final String pattern;

    CraftigCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
