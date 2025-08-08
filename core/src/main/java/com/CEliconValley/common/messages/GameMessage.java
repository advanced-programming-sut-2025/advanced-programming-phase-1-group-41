package com.CEliconValley.common.messages;

public class GameMessage<T> {
    public String type;
    public T body;
    public long timestamp;

    public GameMessage(String type, T body) {
        this.type = type;
        this.body = body;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "GameMessage{" +
            "type='" + type + '\'' +
            ", timestamp=" + timestamp +
            ", body=" + body +
            '}';
    }
}
