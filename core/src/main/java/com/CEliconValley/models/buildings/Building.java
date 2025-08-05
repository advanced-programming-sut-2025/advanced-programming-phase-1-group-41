package com.CEliconValley.models.buildings;

import com.CEliconValley.models.ObjectMap;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.npc.npchomes.AbigailHome;

public interface Building extends ObjectMap {
    int getX();
    int getY();
    int getAnchorX();
    int getAnchorY();

    public static Building parseBuilding(String buildingName) {
        if(buildingName.equals(new AbigailHome().getName())){
            return new AbigailHome();
        }
        if(buildingName.equals(new Barn().getName())){
            return new Barn();
        }
        if(buildingName.equals(new Coop().getName())){
            return new Coop();
        }
        return null;
    }
}
