package views.commands.gameCommands;

import views.commands.Command;

import java.util.regex.Matcher;

public enum FriendShipCommands implements Command {
    Friendships("\\s*friendships\\s*"),
    Talk("\\s*talk\\s+-u\\s+(?<username>.*)\\s+-m\\s+(?<message>.*)\\s*"),
    TalkHistory("\\s*talk\\s+history\\s+-u\\s+(?<username>.*)\\s*"),
    Gift("\\s*gift\\s+-u\\s+<username>.*)\\s+-i\\s+(?<item>.*)\\s+-a\\s+(?<amount>.*)\\s*"),
    GiftList("\\s*gift\\s+list\\s*"),
    GiftRate("\\s*gift\\s+rate\\s+-i\\s+(?<giftNumber>.*)\\s+-r\\s+(?<rate>.*)\\s*"),
    GiftHistory("\\s*gift\\s+history\\s+-u\\s+(?<username>.*)\\s*"),
    Hug("\\s*hug\\s+-u\\s+(?<username>.*)\\s*"),
    Flower("\\s*flower\\s+-u\\s+(?<username>.*)\\s*"),
    Marriage("\\s*ask\\s+marriage\\s+-u\\s+(?<username>.*)\\s+-r\\s+(?<ring>.*)\\s*"),
    Respond("\\s*respond\\s+(–accept|–reject)\\s+-u\\s+(?<username>.*)\\s*"),
    CheatSetFriendship("\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<animalName>.*)\\s+-c\\s+(?<amount>.*)\\s*"),
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
