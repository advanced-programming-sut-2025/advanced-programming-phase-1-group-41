package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.foragings.ForagingCrop;
import com.CEliconValley.models.foragings.ForagingCropType;
import com.CEliconValley.models.items.Eatable;
import com.CEliconValley.models.items.Item;

public enum Mushroom implements Item, Eatable {
    CommonMushroom(ForagingCropType.CommonMushroom),
    RedMushroom(ForagingCropType.RedMushroom),
    PurpleMushroom(ForagingCropType.PurpleMushroom);

    private final ForagingCropType foragingCropType;

    Mushroom(ForagingCropType foragingCropType) {
        this.foragingCropType = foragingCropType;
    }


    @Override
    public String getChar() {
        return "ms";
    }

    @Override
    public String getName() {
        return foragingCropType.getName();
    }

    @Override
    public double getPrice() {
        return foragingCropType.getBaseSellPrice();
    }

    public ForagingCropType getCropType() {
        return foragingCropType;
    }

    public static ForagingCrop parseItem(String item) {
        for (Mushroom value : Mushroom.values()) {
            if(value.getName().equalsIgnoreCase(item)){
                return new ForagingCrop(value.foragingCropType);
            }
        }
        return null;
    }

    public static Mushroom parseMushroom(String item) {
        for (Mushroom value : Mushroom.values()) {
            if(value.getName().equalsIgnoreCase(item)){
                return value;
            }
        }
        return null;
    }

    @Override
    public double getEnergy() {
        return foragingCropType.getEnergy();
    }
}
