package org.example.models.skills;

public enum Skill {
    Farming,
    Mining,
    Foraging,
    Fishing
    ;

    private SkillLevel level;

    Skill() {
        this.level = SkillLevel.Zero;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public void increaseLevel() {

    }
}
