package com.CEliconValley.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("lastuser")
public class LastUser {
    @Id
    private ObjectId id;
    private ObjectId userId;

    public LastUser() {
    }

    public LastUser(ObjectId userId) {
        this.userId = userId;
    }

    public ObjectId getId() {
        return id;
    }

    public ObjectId getUserId() {
        return userId;
    }
}
