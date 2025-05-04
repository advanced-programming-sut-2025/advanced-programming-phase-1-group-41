package org.example.views.commands;

import java.util.regex.Matcher;

public enum MainMenuCommands implements Command {
    MenuEnter("\\s*menu\\s+enter\\s+(?<menuName>.*)\\s*"),
    MenuExit("\\s*menu\\s+exit\\s*"),
    ShowCurrentMenu("\\s*menu\\s+show\\s+(?<menuName>.*)\\s*"),
    UserLogout("\\s*user\\s+logout\\s*"),
    ;

    private final String pattern;

    MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
