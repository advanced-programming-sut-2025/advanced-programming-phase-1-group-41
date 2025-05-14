package org.example.models;

public class Friendship {
    private final Player player1;
    private final Player player2;
    private FriendshipLevel friendshipLevel;
    private int level;
    private int friendshipXp;

    public Friendship(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        friendshipLevel = FriendshipLevel.Stranger;
        level = 0;
        friendshipXp = 0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public FriendshipLevel getFriendshipLevel() {
        return friendshipLevel;
    }

    public void setFriendshipLevel(FriendshipLevel friendshipLevel) {
        this.friendshipLevel = friendshipLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFriendshipXp() {
        return friendshipXp;
    }

    public void setFriendshipXp(int friendshipXp) {
        this.friendshipXp = friendshipXp;
    }
}
