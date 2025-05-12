package org.example.models.animals;

import org.example.models.App;
import org.example.models.Player;
import org.example.models.animals.animalKinds.*;
import org.example.models.items.Slot;

public abstract class Animal {
    public abstract void doTheFuckingJob();
    protected String name;
    protected int buyPrice;

    public BarnOrCageSize getSizeNeeded() {
        return sizeNeeded;
    }

    protected BarnOrCageSize sizeNeeded;
    protected Breed breed;
    protected Player owner;
    private int friendShip;
    private Slot slot;
    //TODO Corps

    public Animal(Player owner, String name, int buyPrice, BarnOrCageSize sizeNeeded) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sizeNeeded = sizeNeeded;
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

    public String getName() {return name;}

    public int getBuyPrice() {return buyPrice;}

    public Slot getSlot() {
        return slot;
    }

    public void resetSlot() {
        this.slot = null;
    }

    public Breed getBreed() {
        return breed;
    }

    public int getSellPrice() {
        return (int) (buyPrice * ((friendShip / 1000f) + 0.3));
    }

    public static Animal parseAnimal(String animal,String name) {
        return switch (animal){
            case "Cow" -> new Cow(App.getGame().getCurrentPlayer(), name);
            case "Rabbit" -> new Rabbit(App.getGame().getCurrentPlayer(), name);
            case "Chicken" -> new Chicken(App.getGame().getCurrentPlayer(),name );
            case "Pig" -> new Pig(App.getGame().getCurrentPlayer(), name);
            case "dino" -> new Dino(App.getGame().getCurrentPlayer(), name);
            case "goat" -> new Goat(App.getGame().getCurrentPlayer(), name);
            case "sheep" -> new Sheep(App.getGame().getCurrentPlayer(), name);
            case "duck" -> new Duck(App.getGame().getCurrentPlayer(), name);
            default -> null;
        };
    }
}
