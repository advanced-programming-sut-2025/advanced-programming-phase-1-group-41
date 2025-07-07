package com.CEliconValley.views.commands.gameCommands;

import com.CEliconValley.views.commands.Command;

import java.util.regex.Matcher;

public enum StoreCommands implements Command {
    MenuEnter("\\s*menu\\s+enter\\s+(?<menuName>.*)\\s*"),
    MenuExit("\\s*menu\\s+exit\\s*"),
    ShowCurrentMenu("\\s*menu\\s+show\\s+(?<menuName>.*)\\s*"),
    ShowAllProducts("\\s*show\\s+all\\s+products\\s*"),
    ShowAllAvailableProducts("\\s*show\\s+all\\s+available\\s+products\\s*"),
    Purchase("\\s*purchase\\s+(?<productName>.+?)(?=\\s+-n\\s+\\d+|$)\\s*(-n\\s+(?<count>\\d+))?\\s*"),
    CheatAddDollars("\\s*cheat\\s+add\\s+(?<count>\\d+)\\s+dollars\\s*"),
    Sell("\\s*sell\\s+(?<productName>.+?)(?=\\s+-n\\s+\\d+|$)\\s*(-n\\s+(?<count>\\d+))?\\s*"),
    ;

    private final String pattern;

    StoreCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
