package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum AnimalCommands implements Command {
    Build(""),
    BuyAnimal(""),
    Pet(""),
    CheatSetFriendship(""),
    AnimalsList(""),
    SephardAnimals(""),
    FeedHay(""),
    ProducesList(""),
    CollectProduct(""),
    SellAnimal(""),

    ;

    public final String pattern;

    AnimalCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
