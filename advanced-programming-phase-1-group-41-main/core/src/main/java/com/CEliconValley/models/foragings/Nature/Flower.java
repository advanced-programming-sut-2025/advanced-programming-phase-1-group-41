package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.CropType;
import com.CEliconValley.models.items.Item;

public enum Flower implements Item {
    BlueJazz(CropType.BlueJazz),
    Caulieflower(CropType.Cauliflower),
    Tulip(CropType.Tulip),
    Poppy(CropType.Poppy),
    Sunflower(CropType.Sunflower),
    FairyRose(CropType.FairyRose),
    SummerSpangle(CropType.SummerSpangle)
    ;

    private final CropType cropType;

    Flower(CropType cropType) {
        this.cropType = cropType;
    }


    @Override
    public String getChar() {
        return "fl";
    }

    @Override
    public String getName() {
        return cropType.getName();
    }

    @Override
    public double getPrice() {
        return cropType.getBaseSellPrice();
    }

    public CropType getCropType() {
        return cropType;
    }

    public static Crop parseItem(String item) {
        for (Flower value : Flower.values()) {
            if(value.getName().equalsIgnoreCase(item)){
                return new Crop(value.cropType);
            }
        }
        return null;
    }

}
