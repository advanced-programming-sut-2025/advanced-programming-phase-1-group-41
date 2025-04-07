package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum PlayerCommands implements Command {
    ShowInventory(""),
    InventoryTrash(""),
    ;

    public final String pattern;

    PlayerCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
