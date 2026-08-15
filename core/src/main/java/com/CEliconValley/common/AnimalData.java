package com.CEliconValley.common;

import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.animals.BarnOrCageSize;
import com.CEliconValley.models.animals.Breed;
import com.CEliconValley.models.animals.animalKinds.*;
import com.CEliconValley.models.items.Products.Product;
import com.CEliconValley.models.items.Products.ProductType;
import dev.morphia.annotations.Embedded;


@Embedded
public class AnimalData {
    String name;
    String animalType;
    int x;
    int y;
    int buyPrice;
    boolean isPetToday;
    boolean isFedToday;
    boolean isHome;
    String productName;
    int daysUntilProduce;
    int sizeNeededInt;
    int breedInt;
    String ownerName;
    int friendShip;

    public AnimalData() {
    }

    public AnimalData(Animal animal) {
        this.name = animal.getName();
        this.animalType = animal.getAnimalType();
        this.x = animal.getX();
        this.y = animal.getY();
        this.buyPrice = animal.getBuyPrice();
        this.isPetToday = animal.isPetToday();
        this.isFedToday = animal.isFedToday();
        this.isHome = animal.isHome();
        this.productName = animal.getProduct() == null ? null : animal.getProduct().getName();
        this.daysUntilProduce = animal.getDaysUntilProduce();
        this.sizeNeededInt = animal.getSizeNeeded().ordinal();
        this.breedInt = animal.getBreed().ordinal();
        this.ownerName = animal.getName();
        this.friendShip = animal.getFriendShip();
    }

    public Animal getAnimal(Player owner) {
        Product product = ProductType.parseProductType(this.productName);
        BarnOrCageSize sizeNeeded = BarnOrCageSize.values()[this.sizeNeededInt];
        Breed breed = Breed.values()[this.breedInt];
        switch (this.animalType){
            case "Chicken" -> {
                return new Chicken(breed, this.buyPrice, this.daysUntilProduce, this.friendShip,
                    this.isFedToday, this.isHome, this.isPetToday,
                    this.name, owner, product, sizeNeeded,
                    this.x, this.y);
            }
            case "Cow" -> {
                return new Cow(breed, this.buyPrice, this.daysUntilProduce, this.friendShip,
                    this.isFedToday, this.isHome, this.isPetToday,
                    this.name, owner, product, sizeNeeded,
                    this.x, this.y);
            }
            case "Dino" -> {
                return new Dino(breed, this.buyPrice, this.daysUntilProduce, this.friendShip,
                    this.isFedToday, this.isHome, this.isPetToday,
                    this.name, owner, product, sizeNeeded,
                    this.x, this.y);
            }
            case "Duck" -> {
                return new Duck(breed, this.buyPrice, this.daysUntilProduce, this.friendShip,
                    this.isFedToday, this.isHome, this.isPetToday,
                    this.name, owner, product, sizeNeeded,
                    this.x, this.y);
            }
            case "Goat" -> {
                return new Goat(breed, this.buyPrice, this.daysUntilProduce, this.friendShip,
                    this.isFedToday, this.isHome, this.isPetToday,
                    this.name, owner, product, sizeNeeded,
                    this.x, this.y);
            }
            case "Pig" -> {
                return new Pig(breed, this.buyPrice, this.daysUntilProduce, this.friendShip,
                    this.isFedToday, this.isHome, this.isPetToday,
                    this.name, owner, product, sizeNeeded,
                    this.x, this.y);
            }case "Rabbit" -> {
                return new Rabbit(breed, this.buyPrice, this.daysUntilProduce, this.friendShip,
                    this.isFedToday, this.isHome, this.isPetToday,
                    this.name, owner, product, sizeNeeded,
                    this.x, this.y);
            }
            case "Sheep" -> {
                return new Sheep(breed, this.buyPrice, this.daysUntilProduce, this.friendShip,
                    this.isFedToday, this.isHome, this.isPetToday,
                    this.name, owner, product, sizeNeeded,
                    this.x, this.y);
            }
        }
        return null;
    }


    public String getAnimalType() {
        return animalType;
    }

    public int getBreedInt() {
        return breedInt;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getDaysUntilProduce() {
        return daysUntilProduce;
    }

    public int getFriendShip() {
        return friendShip;
    }

    public boolean isFedToday() {
        return isFedToday;
    }

    public boolean isHome() {
        return isHome;
    }

    public boolean isPetToday() {
        return isPetToday;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getProductName() {
        return productName;
    }

    public int getSizeNeededInt() {
        return sizeNeededInt;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
