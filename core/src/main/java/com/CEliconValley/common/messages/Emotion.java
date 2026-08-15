package com.CEliconValley.common.messages;

import dev.morphia.annotations.Embedded;

import java.util.Objects;

@Embedded
public class Emotion {
    public boolean isEmote;
    public int index;
    public String username;

    public Emotion(){}

    public Emotion(int index, boolean isEmote, String username) {
        this.index = index;
        this.isEmote = isEmote;
        this.username = username;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Emotion emotion = (Emotion) o;
        return isEmote == emotion.isEmote && index == emotion.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isEmote, index);
    }
}
