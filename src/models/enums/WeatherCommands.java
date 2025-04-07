package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum WeatherCommands implements Command {
    StrikeThunder(""),

    PredictTmrwWeather(""),

    GetCurrentWeather(""),

    ApplyEfficiency(""),

    CheatStrikeThunder(""),

    CheatChangeTmrwWeather("")

    ;
    public final String pattern;

    WeatherCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
