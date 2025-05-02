package views.commands;

import java.util.regex.Matcher;

public enum ProfileCommands implements Command {
    MenuEnter("\\s*menu\\s+enter\\s+(?<menuName>.*)\\s*"),
    MenuExit("\\s*menu\\s+exit\\s*"),
    ShowCurrentMenu("\\s*menu\\s+show\\s+(?<menuName>.*)\\s*"),
    ChangeUsername("\\s*change\\s+username\\s+-u\\s+(?<username>.*)\\s*"),
    ChangeNickname("\\s*change\\s+nickname\\s+-u\\s+(?<nickname>.*)\\s*"),
    ChangeEmail("\\s*change\\s+email\\s+-e\\s+(?<email>.*)\\s*"),
    ChangePassword("\\s*change\\s+password\\s+-p\\s+(?<newPassword>.*)\\s+-o\\s+(?<oldPassword>.*)\\s*"),
    UserInfo("\\s*user\\s+info\\s*"),
    ;

    private final String pattern;

    ProfileCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
