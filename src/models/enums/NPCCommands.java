package models.enums;

import models.Command;

import java.util.regex.Matcher;

public enum NPCCommands implements Command {
    MeetNPC(""),
    GiftToNPC(""),
    FriendshipList(""),
    QuestList(""),
    FinishQuest(""),

    ;

    public final String pattern;

    NPCCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, this.pattern);
    }
}
