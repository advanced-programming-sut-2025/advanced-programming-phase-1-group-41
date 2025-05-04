package org.example.views.commands.gameCommands;

import org.example.views.commands.Command;

import java.util.regex.Matcher;

public enum TradeCommands implements Command {
    StartTrade("\\s*start\\s+trade\\s*"),
    Trade("\\s*trade\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+" +
            "-a\\s+(?<amount>\\d+)(\\s+-p\\s+(?<price>\\d+))?(\\s+-ti\\s+(?<targetItem>\\S+)\\s+-ta\\s+(?<targetAmount>\\d+))?\\s*"),
    TradeList("\\s*trade\\s+list\\s*"),
    TradeResponse("\\s*trade\\s+response\\s+(–accept|–reject)\\s+-i\\s+(?<id>\\S+)\\s*"),
    TradeHistory("\\s*trade\\s+history\\s*"),
    ;

    private final String pattern;

    TradeCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
