package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum PlantCommands implements Command {
    CraftInfo(""),
    PlantSeed(""),
    ShowPlant(""),
    Fertilize(""),
    HowMuchWater(""),
    Harvest(""),


    ;

    public final String pattern;

    PlantCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
