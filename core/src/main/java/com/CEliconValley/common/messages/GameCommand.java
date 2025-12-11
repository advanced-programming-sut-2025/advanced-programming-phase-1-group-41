package com.CEliconValley.common.messages;

public class GameCommand {
    public String playerName;
    public String command;

    public GameCommand(String command, String playerName) {
        this.command = command;
        this.playerName = playerName;
    }
}
