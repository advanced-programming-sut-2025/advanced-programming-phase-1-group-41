package com.CEliconValley.common;

import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.buildings.animalContainer.CoopType;
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


    public Coop getCoop() {
        return new Coop(this.anchorX, this.anchorY, CoopType.values()[this.coopTypeInt],
            this.capacity, this.x, this.y);
    }
}
