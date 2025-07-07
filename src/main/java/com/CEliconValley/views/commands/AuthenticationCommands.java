package com.CEliconValley.views.commands;

import java.util.regex.Matcher;

public enum AuthenticationCommands implements Command {
    MenuEnter("\\s*menu\\s+enter\\s+(?<menuName>.*)\\s*"),
    MenuExit("\\s*menu\\s+exit\\s*"),
    ShowCurrentMenu("\\s*menu\\s+show\\s+(?<menuName>.*)\\s*"),
    Register("\\s*register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+(?<passwordConfirm>\\S+)\\s+" +
            "-n\\s+(?<nickname>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)\\s*"),
    PickQuestion("\\s*pick\\s+question\\s+-q\\s+(?<questionNumber>.*)\\s+-a\\s+" +
            "(?<answer>.*)\\s+-c\\s+(?<answerConfirm>.*)\\s*"),
    Login("\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)(?:\\s+(?<log>-stay-logged-in))?\\s*"),
    ForgetPassword("\\s*forget\\s+password\\s+-u\\s+(?<username>.*)\\s*"),
    Answer("\\s*answer\\s+-a\\s+(?<answer>.*)\\s*"),
    ;

    private final String pattern;

    AuthenticationCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
