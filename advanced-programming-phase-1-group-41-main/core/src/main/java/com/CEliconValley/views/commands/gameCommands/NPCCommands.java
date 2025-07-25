package com.CEliconValley.views.commands.gameCommands;

import com.CEliconValley.views.commands.Command;

import java.util.regex.Matcher;

public enum NPCCommands implements Command {
    MeetNPC("\\s*meet\\s+NPC\\s+(?<npcName>\\S+)\\s*"),
    GiftNPC("\\s*gift\\s+NPC\\s+(?<npcName>\\S+)\\s+-i\\s+(?<item>.*)\\s*"),
    FriendshipNPCList("\\s*friendship\\s+NPC\\s+list\\s*"),
    QuestsList("\\s*quests\\s+list\\s*"),
    QuestFinish("\\s*quests\\s+finish\\s+-i\\s+(?<index>\\d+)\\s*"),
    ;

    private final String pattern;

    NPCCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
