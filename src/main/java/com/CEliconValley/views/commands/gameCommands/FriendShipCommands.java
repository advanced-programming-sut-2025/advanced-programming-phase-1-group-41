package com.CEliconValley.views.commands.gameCommands;

import com.CEliconValley.views.commands.Command;

import java.util.regex.Matcher;

public enum FriendShipCommands implements Command {
    Friendships("\\s*friendships\\s*"),
    Talk("\\s*talk\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<message>.*)\\s*"),
    TalkHistory("\\s*talk\\s+history\\s+-u\\s+(?<username>\\S+)\\s*"),
    Gift("\\s*gift\\s+-u\\s+(?<username>\\S+)\\s+-i\\s+(?<item>.+)\\s+-a\\s+(?<amount>\\S+)\\s*"),
    GiftList("\\s*gift\\s+list\\s*"),
    GiftRate("\\s*gift\\s+rate\\s+-i\\s+(?<giftNumber>\\S+)\\s+-r\\s+(?<rate>\\S+)\\s*"),
    GiftHistory("\\s*gift\\s+history\\s+-u\\s+(?<username>\\S+)\\s*"),
    Hug("\\s*hug\\s+-u\\s+(?<username>\\S+)\\s*"),
    Flower("\\s*flower\\s+-u\\s+(?<username>\\S+)\\s*"),
//    Flower("\\s*flower\\s+-u\\s+(?<username>\\S+)\\s+-f\\s+(?<flowerName>\\S+)\\s*"),
    Marriage("\\s*ask\\s+marriage\\s+-u\\s+(?<username>\\S+)\\s*"),
//    Marriage("\\s*ask\\s+marriage\\s+-u\\s+(?<username>\\S+)\\s+-r\\s+(?<ring>\\S+)\\s*"),
    Respond("\\s*respond\\s+(accept|reject)\\s+-u\\s+(?<username>\\S+)\\s*"),
    GoTo("\\s*go\\s+to\\s+-u\\s+(?<farmName>.+)\\s*"),
    CheatSetFriendship("\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<animalName>\\S+)\\s+-c\\s+(?<amount>\\S+)\\s*"),
    ;

    private final String pattern;

    FriendShipCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        return Command.getMatcher(input, pattern);
    }
}
