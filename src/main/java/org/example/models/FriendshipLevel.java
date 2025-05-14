package org.example.models;

public enum FriendshipLevel {
    Stranger(0),
    Friend(1),
    CloseFriend(2),
    BestFriend(3),
    Spouse(4),
    ;
    private int level;
    FriendshipLevel(int level) {
        this.level = level;
    }
}
