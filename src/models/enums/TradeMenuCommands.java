package models.enums;


import models.Command;

import java.util.regex.Matcher;

public enum TradeMenuCommands implements Command {
    TradeItem(""),
    TradeList(""),
    TradeResponse(""),
    TradeHistory(""),
    ExitTradeMenu(""),

    ;

    public final String pattern;

    TradeMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
