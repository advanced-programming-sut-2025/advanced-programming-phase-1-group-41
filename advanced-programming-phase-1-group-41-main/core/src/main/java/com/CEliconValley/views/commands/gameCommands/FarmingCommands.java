package com.CEliconValley.views.commands.gameCommands;

import com.CEliconValley.views.commands.Command;

import java.util.regex.Matcher;

public enum FarmingCommands implements Command {
    CraftInfo("\\s*craftinfo\\s+-n\\s+(?<craftName>.*)\\s*"),
    GreenhouseBuild("\\s*greenhouse\\s+build\\s*"),
    Plant("\\s*plant\\s+-s\\s+(?<seed>.*)\\s+-d\\s+(?<direction>.*)\\s*"),
    ShowPlant("\\s*showplant\\s+-l\\s+(?<x>.*)\\s*,\\s*(?<y>.*)\\s*"),
    Fertilize("\\s*fertilize\\s+-f\\s+(?<fertilizer>.*)\\s+-d\\s+(?<direction>.*)\\s*"),
    HowMuchWater("\\s*howmuch\\s+water\\s*"),
    ;

    private final String pattern;

    FarmingCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
