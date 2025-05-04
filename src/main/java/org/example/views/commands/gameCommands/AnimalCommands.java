package org.example.views.commands.gameCommands;

import org.example.views.commands.Command;

import java.util.regex.Matcher;

public enum AnimalCommands implements Command {
    BuyAnimal("\\s*buy\\s+animal\\s+-a\\s+(?<animal>.*)\\s+-n\\s+(?<name>.*)\\s*"),
    Pet("\\s*pet\\s+-n\\s+(?<name>.*)\\s*"),
    Animals("\\s*animals\\s*"),
    ShepherdAnimals("\\s*shepherd\\s+animals\\s+-n\\s+(?<animalName>.*)\\s+-l\\s+(?<x>.*)\\s*,\\s*(?<y>.*)\\s*"),
    FeedHay("\\s*feed\\s+hay\\s+-n\\s+(?<animalName>.*)\\s*"),
    SellAnimal("\\s*sell\\s+animal\\s+-n\\s+(?<name>.*)\\s*"),
    ;

    private final String pattern;

    AnimalCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
