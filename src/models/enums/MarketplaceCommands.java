package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum MarketplaceCommands implements Command {
    ShowAllProducts(""),
    ShowAllAvailableProducts(""),
    PurchaseProduct(""),
    CheatAddMoney(""),

    ;

    public final String pattern;

    MarketplaceCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
