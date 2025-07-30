package com.CEliconValley.common.messages;

public class GameMessage<T> {
    public String type; // e.g., "login", "auth", "lobby_join", "game_data"
    public T body;

    public GameMessage(String type, T body) {
        this.type = type;
        this.body = body;
    }

    @Override
    public String toString() {
        return "GameMessage{" +
            "body=" + body +
            ", type='" + type + '\'' +
            '}';
    }
}
