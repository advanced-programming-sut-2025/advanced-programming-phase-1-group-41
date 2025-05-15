package org.example.views.commands;

import java.util.regex.Matcher;

public enum TradeCommands implements Command {
    TradeToMoney("\\s*trade\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s+-p\\s+(?<price>\\d+)\\s*"),
    TradeToItem("\\s*trade\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+-a\\s+(?<amount>\\d+)\\s+-ti\\s+(?<targetItem>\\S+)\\s+-ta\\s+(?<targetAmount>\\d+)\\s*"),
    Trade("\\s*trade\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>\\S+)\\s+-i\\s+(?<item>\\S+)\\s+" +
            "-a\\s+(?<amount>\\d+)\\s+-p\\s+(?<price>\\d+)\\s+-ti\\s+(?<targetItem>\\S+)\\s+-ta\\s+(?<targetAmount>\\d+)\\s*"),
    TradeList("\\s*trade\\s+list\\s*"),
    TradeResponse("\\s*trade\\s+response\\s+(accept|reject)\\s+-i\\s+(?<id>\\d+)\\s*"),
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
