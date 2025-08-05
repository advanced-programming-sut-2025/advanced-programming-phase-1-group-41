package com.CEliconValley.models.buildings;

import com.CEliconValley.models.ObjectMap;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.buildings.marketplaces.*;
import com.CEliconValley.models.npc.npchomes.*;

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
        if(buildingName.equals(new Blacksmith().getName())){
            return new Blacksmith();
        }
        if(buildingName.equals(new Bridge().getName())){
            return new Bridge();
        }
        if(buildingName.equals(new CarpenterShop().getName())){
            return new CarpenterShop();
        }
        if(buildingName.equals(new Cottage().getName())){
            return new Cottage();
        }
        if(buildingName.equals(new Door().getName())){
            return new Door();
        }
        if(buildingName.equals(new FishShop().getName())){
            return new FishShop();
        }
        if(buildingName.equals(new GeneralStore().getName())){
            return new GeneralStore();
        }
        if(buildingName.equals(new Greenhouse().getName())){
            return new Greenhouse();
        }
        if(buildingName.equals(new HarveyHome().getName())){
            return new HarveyHome();
        }
        if(buildingName.equals(new Jojamart().getName())){
            return new Jojamart();
        }
        if(buildingName.equals(new LiaHome().getName())){
            return new LiaHome();
        }
        if(buildingName.equals(new MarnieRanch().getName())){
            return new MarnieRanch();
        }if(buildingName.equals(new RobinHome().getName())){
            return new RobinHome();
        }if(buildingName.equals(new Saloon().getName())){
            return new Saloon();
        }if(buildingName.equals(new SebastienHome().getName())){
            return new SebastienHome();
        }if(buildingName.equals(new ShippingBin().getName())){
            return new ShippingBin();
        }if(buildingName.equals(new Wall().getName())){
            return new Wall();
        }if(buildingName.equals(new Well().getName())){
            return new Well();
        }
        return null;
    }
}
