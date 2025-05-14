package org.example.models.animals;

import org.example.models.App;
import org.example.models.Player;
import org.example.models.animals.animalKinds.*;
import org.example.models.items.Products.Product;
import org.example.models.items.Products.ProductType;

public abstract class Animal {
    public abstract void doTheFuckingJob();
    protected String name;
    protected int x;
    protected int y;
    protected int buyPrice;
    protected boolean isPetToday;
    protected boolean isFedToday;
    protected boolean isHome=true;
    protected Product product;
   protected int daysUntilProduce;

    public BarnOrCageSize getSizeNeeded() {
        return sizeNeeded;
    }

    protected BarnOrCageSize sizeNeeded;
    protected Breed breed;
    protected Player owner;
    private int friendShip;
    //TODO Corps

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }


    public Animal(Player owner, String name, int buyPrice, BarnOrCageSize sizeNeeded) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sizeNeeded = sizeNeeded;
        this.owner = owner;
        this.friendShip = 0;
        this.x=-10;
        this.y=-10;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public boolean isPetToday() {
        return isPetToday;
    }

    public void setPetToday(boolean petToday) {
        isPetToday = petToday;
    }

    public boolean isFedToday() {
        return isFedToday;
    }

    public void setFedToday(boolean fedToday) {
        isFedToday = fedToday;
    }

    public void setSizeNeeded(BarnOrCageSize sizeNeeded) {
        this.sizeNeeded = sizeNeeded;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getDaysUntilProduce() {
        return daysUntilProduce;
    }
    public boolean canGiveProduct() {
        if(daysUntilProduce==0) {
            daysUntilProduce=3;
            return true;
        }
        daysUntilProduce--;
        return false;
    }
    public void setDaysUntilProduce(int daysUntilProduce) {
        this.daysUntilProduce = daysUntilProduce;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public void setFriendShip(int friendShip) {
        this.friendShip = friendShip;
    }
    public boolean isHome(){
        return isHome;
    }
    public void setHome(boolean home){
        isHome = home;
    }
    public String getChar(){
        return "";
    }


    public Breed getBreed() {
        return breed;
    }

    public int getSellPrice() {
        return (int) (buyPrice * ((friendShip / 1000f) + 0.3));
    }

    public static Animal parseAnimal(String animal,String name) {
        return switch (animal){
            case "cow" -> new Cow(App.getGame().getCurrentPlayer(), name);
            case "rabbit" -> new Rabbit(App.getGame().getCurrentPlayer(), name);
            case "chicken" -> new Chicken(App.getGame().getCurrentPlayer(),name );
            case "pig" -> new Pig(App.getGame().getCurrentPlayer(), name);
            case "dino" -> new Dino(App.getGame().getCurrentPlayer(), name);
            case "goat" -> new Goat(App.getGame().getCurrentPlayer(), name);
            case "sheep" -> new Sheep(App.getGame().getCurrentPlayer(), name);
            case "duck" -> new Duck(App.getGame().getCurrentPlayer(), name);
            default -> null;
        };
    }
}
