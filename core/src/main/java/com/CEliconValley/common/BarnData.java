package com.CEliconValley.common;

import com.CEliconValley.models.Player;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.BarnType;
import com.CEliconValley.models.locations.Farm;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;

@Embedded
public class BarnData {
    ArrayList<AnimalData> animalsData;
    int x;
    int y;
    int anchorX;
    int anchorY;
    int barnTypeInt;
    int capacity;

    public BarnData() {
    }

    public BarnData(Barn barn) {
        this.animalsData = new ArrayList<>();
        for (Animal animal : barn.getAnimals()) {
            this.animalsData.add(new AnimalData(animal));
        }
        this.x = barn.getX();
        this.y = barn.getY();
        this.anchorX = barn.getAnchorX();
        this.anchorY = barn.getAnchorY();
        this.barnTypeInt = barn.getBarnType().ordinal();
        this.capacity = barn.getCapacity();
    }


    public Barn getBarn(Player owner, Farm farm) {
        return new Barn(getAnimals(owner),this.anchorX, this.anchorY, BarnType.values()[this.barnTypeInt],
            this.capacity, this.x, this.y, farm);
    }

    private ArrayList<Animal> getAnimals(Player owner){
        ArrayList<Animal> animals = new ArrayList<>();
        for (AnimalData animalsDatum : animalsData) {
            animals.add(animalsDatum.getAnimal(owner));
        }
        return animals;
    }

    public int getBarnTypeInt() {
        return barnTypeInt;
    }
    public int getAnchorX(){
        return anchorX;
    }
    public int getAnchorY(){
        return anchorY;
    }
}
