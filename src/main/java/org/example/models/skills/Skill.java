package org.example.models.skills;

public enum Skill {
    Farming,
    Mining,
    Foraging,
    Fishing
    ;

    private int level;

    Skill() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void increaseLevel() {
        this.level++;
    }
}
