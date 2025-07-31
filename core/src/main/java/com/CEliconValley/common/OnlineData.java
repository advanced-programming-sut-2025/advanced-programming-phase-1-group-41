package com.CEliconValley.common;

import java.util.Objects;

public class OnlineData {
    public String username;
    public boolean inLobby;

    public OnlineData(String username, boolean inLobby) {
        this.username = username;
        this.inLobby = inLobby;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OnlineData that = (OnlineData) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
