package com.CEliconValley.common;

import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.BarnType;

import java.util.ArrayList;

public class BarnData {
    ArrayList<AnimalData> animalsData;
    int x;
    int y;
    int anchorX;
    int anchorY;
    int barnTypeInt;
    int capacity;

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


    public Barn getBarn() {
        return new Barn(this.anchorX, this.anchorY, BarnType.values()[this.barnTypeInt],
            this.capacity, this.x, this.y);
    }
}
