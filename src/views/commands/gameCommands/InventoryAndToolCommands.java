package views.commands.gameCommands;

import views.commands.Command;

import java.util.regex.Matcher;

public enum InventoryAndToolCommands implements Command {
    InventoryShow("\\s*inventory\\s+show\\s*"),
    InventoryTrash("\\s*inventory\\s+trash\\s+-i\\s+(?<itemName>.*)\\s+-n\\s+(?<number>.*)\\s*"),
    ToolsEquip("\\s*tools\\s+equip\\s+(?<toolName>.*)\\s*"),
    ShowCurrentTools("\\s*tools\\s+show\\s+current\\s*"),
    ShowAvailableTools("\\s*tools\\s+show\\s+available\\s*"),
    UpgradeTool("\\s*tools\\s+upgrade\\s+(?<toolName>.*)\\s*"),
    UseTool("\\s*tools\\s+use\\s+-d\\s+(?<direction>.*)\\s*"),
    CraftInfo("\\s*craftinfo\\s+-n\\s+(?<craftName>.*)\\s*")
    ;

    private final String pattern;

    InventoryAndToolCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
