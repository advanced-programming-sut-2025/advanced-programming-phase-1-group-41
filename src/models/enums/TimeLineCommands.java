package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum TimeLineCommands implements Command {
    GetTime(""),
    GetDate(""),
    GetDateAndTime(""),
    GetDayOfTheWeek(""),
    GetSeason(""),
    CheatAdvanceTime(""),
    CheatAdvanceDate(""),
    ;

    public final String pattern;

    TimeLineCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
