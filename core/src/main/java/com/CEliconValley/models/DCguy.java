package com.CEliconValley.models;

import org.bson.types.ObjectId;

public class DCguy {
    User user;
    long timestamp;
    ObjectId gameid;

    public DCguy(ObjectId gameid, User user) {
        this.gameid = gameid;
        this.user = user;
        this.timestamp = System.currentTimeMillis();
    }

    public ObjectId getGameid() {
        return gameid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "DCguy{" +
            "gameid=" + gameid +
            ", username=" + user +
            ", timestamp=" + timestamp +
            '}';
    }
}
