package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum ToolsCommands implements Command {
    EquipTool(""),
    ShowCurrentTool(""),
    ShowAvailableTools(""),
    UpgradeTool(""),
    UseTool(""),

    ;

    public final String pattern;

    ToolsCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
