package com.CEliconValley.models;

public enum FriendshipLevel {
    Enemy("Enemies", -1),
    Stranger("Strangers", 0),
    Friend("Just Friends", 1),
    CloseFriend("Close Friends", 2),
//    BestFriend("Best Friends", 3),
    Partner("Partners", 3),
    Spouse("Married",4),
    ;

    private final int level;
    private final String name;

    FriendshipLevel(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
    public String getName() {
        return name;
    }

}
