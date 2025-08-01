package com.CEliconValley.common.messages;

public class MakeLobbyInfo {
    public String lobbyName;
    public boolean isVisible;
    public boolean isPrivate;
    public String password;
    public String admin;
    public MakeLobbyInfo(boolean isPrivate, boolean isVisible, String lobbyName, String password, String admin) {
        this.isPrivate = isPrivate;
        this.isVisible = isVisible;
        this.lobbyName = lobbyName;
        this.password = password;
        this.admin = admin;
    }

}
