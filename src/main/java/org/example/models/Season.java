package org.example.models;

public enum Season {
    Spring("Spring"), Summer("Summer"), Autumn("Autumn"), Winter("Winter"), Special("Special");

    public final String name;

    Season(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
