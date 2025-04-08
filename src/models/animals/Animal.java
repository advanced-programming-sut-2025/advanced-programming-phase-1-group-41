package models.animals;

import models.Player;
import models.items.Slot;

public abstract class Animal {
    public abstract void doTheFuckingJob();
    protected Breed breed;
    protected Player owner;
    private int friendShip;
    private Slot slot;

    public Animal(Player owner){
        this.owner = owner;
        this.friendShip = 0;
        this.slot = null;
    }

    public int getFriendShip() {
        return friendShip;
    }

    public void increaseFriendShip(int delta) {
        this.friendShip += delta;
        if(this.friendShip > 1000) {
            this.friendShip = 1000;
        }
    }

    public Slot getSlot() {
        return slot;
    }

    public void resetSlot() {
        this.slot = null;
    }

    public Breed getBreed() {
        return breed;
    }
}
