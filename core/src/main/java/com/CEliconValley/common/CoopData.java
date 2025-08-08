package com.CEliconValley.common;

import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.buildings.animalContainer.CoopType;
import com.CEliconValley.models.locations.Farm;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;

@Embedded
public class CoopData {
    ArrayList<AnimalData> animalsData;
    int x;
    int y;
    int anchorX;
    int anchorY;
    int coopTypeInt;
    int capacity;

    public CoopData() {
    }

    public CoopData(Coop coop) {
        this.animalsData = new ArrayList<>();
        for (Animal animal : coop.getAnimals()) {
            this.animalsData.add(new AnimalData(animal));
        }
        this.x = coop.getX();
        this.y = coop.getY();
        this.anchorX = coop.getAnchorX();
        this.anchorY = coop.getAnchorY();
        this.coopTypeInt = coop.getCoopType().ordinal();
        this.capacity = coop.getCapacity();
    }


    public Coop getCoop(Player owner, Farm farm) {
        return new Coop(getAnimals(owner),this.anchorX, this.anchorY, CoopType.values()[this.coopTypeInt],
            this.capacity, this.x, this.y, farm);
    }

    private ArrayList<Animal> getAnimals(Player owner) {
        ArrayList<Animal> animals = new ArrayList<>();
        if(this.animalsData == null){
            return animals;
        }
        for (AnimalData animalData : this.animalsData) {
            animals.add(animalData.getAnimal(owner));
        }
        return animals;
    }

    public int getAnchorX() {
        return anchorX;
    }

    public int getAnchorY() {
        return anchorY;
    }

    public int getCoopTypeInt() {
        return coopTypeInt;
    }

    public ArrayList<AnimalData> getAnimalsData() {
        return animalsData;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
