package com.CEliconValley.common;

import com.CEliconValley.models.Friendship;
import com.CEliconValley.models.FriendshipLevel;
import com.CEliconValley.models.Player;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;


@Embedded
public class FriendshipData {
    String player1Name;
    String player2Name;
    int friendshipLevelInt;
    int level;
    int friendshipXp;
    ArrayList<ArrayList<String>> talks;
    int lastReadMessage;
    boolean hadInteractionToday;
    boolean hadTalkingToday;
    boolean hadHugToday;
    boolean hadTradeToday;
    boolean hadRejectedTradeToday;
    boolean gaveFlower;
    boolean areMarried ;
    String proposerName ;
    public FriendshipData(Friendship friendship) {
        this.player1Name = friendship.getPlayer1().getUser().getUsername();
        this.player2Name = friendship.getPlayer2().getUser().getUsername();
        this.friendshipLevelInt = friendship.getFriendshipLevel().ordinal();
        this.level = friendship.getLevel();
        this.friendshipXp = friendship.getFriendshipXp();
        this.talks = new ArrayList<>();
        for (ArrayList<String> talk : friendship.getTalks()) {
            this.talks.add(new ArrayList<>(talk));
        }
        this.lastReadMessage = friendship.getLastReadMessage();
        this.hadInteractionToday = friendship.isHadInteractionToday();
        this.hadTalkingToday = friendship.isHadTalkingToday();
        this.hadHugToday = friendship.isHadHugToday();
        this.hadTradeToday = friendship.isHadTradeToday();
        this.hadRejectedTradeToday = friendship.isHadRejectedTradeToday();
        this.gaveFlower = friendship.isGaveFlower();
        this.areMarried = friendship.isAreMarried();
        this.proposerName = friendship.getProposer() == null ? null :
            friendship.getProposer().getUser().getUsername();
    }


    // TODO for retreiving data it has to be loaded after creating the players!
    public Friendship getFriendship(Player player1, Player player2, Player proposer) {
        FriendshipLevel fsl = FriendshipLevel.values()[this.friendshipLevelInt];
        return new Friendship(
            proposer, player1, player2, level, lastReadMessage, hadTradeToday, hadTalkingToday,
            hadRejectedTradeToday, hadInteractionToday, hadHugToday, gaveFlower, friendshipXp,
            fsl, areMarried
        );
    }
}
