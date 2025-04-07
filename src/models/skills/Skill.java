package models.skills;

public enum Skill {
    Farming,
    Mining,
    Foraging,
    Fishing
    ;

    SkillLevel level;

    Skill() {
        this.level = SkillLevel.Zero;
    }
}
