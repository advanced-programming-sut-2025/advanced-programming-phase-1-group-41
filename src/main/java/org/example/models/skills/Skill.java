package org.example.models.skills;

public class Skill {
    private int level;
    private int xp;
    public Skill() {
        this.level = 0;
        this.xp = 0;
    }

    public int getLevel() {
        return level;
    }

    public boolean isMaxLevel(){
        if(level >= 4){
            return true;
        }
        return false;
    }

    public void increaseLevel() {
        this.level = Math.max(this.level + 1, 4);
    }

    public int getXp() {
        return xp;
    }

    public void increaseXp(int delta) {
        int old = this.xp;
        this.xp += delta;this.xp %= 50 + 100 * level;
        if(this.xp < old){
            increaseLevel();
        }
    }
}
