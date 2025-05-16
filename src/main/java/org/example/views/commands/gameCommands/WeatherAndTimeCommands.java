package org.example.views.commands.gameCommands;

import org.example.views.commands.Command;

import java.util.regex.Matcher;

public enum WeatherAndTimeCommands implements Command {
    Time("\\s*time\\s*"),
    Date("\\s*date\\s*"),
    DateTime("\\s*datetime\\s*"),
    DayOfTheWeek("\\s*day\\s+of\\s+the\\s+week\\s*"),
    CheatAdvanceTime("\\s*cheat\\s+advance\\s+time\\s+(?<hour>-?\\d+)h\\s*"),
    CheatAdvanceDate("\\s*cheat\\s+advance\\s+date\\s+(?<day>-?\\d+)d\\s*"),
    Season("\\s*season\\s*"),
    CheatThor("\\s*cheat\\s+Thor\\s+-l\\s+(?<x>-?\\d+)\\s*,\\s*(?<y>-?\\d+)\\s*"),
    Weather("\\s*weather\\s*"),
    WeatherForecast("\\s*weather\\s+forecast\\s*"),
    CheatWeather("\\s*cheat\\s+weather\\s+set\\s+(?<type>\\S+)\\s*"),
    ;

    private final String pattern;

    WeatherAndTimeCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
