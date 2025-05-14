package org.example.models.foragings.Nature;

import org.example.models.foragings.Crop;
import org.example.models.foragings.CropType;
import org.example.models.items.Item;

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

    public static Crop parseItem(String item) {
        for (Flower value : Flower.values()) {
            if(value.getName().equalsIgnoreCase(item)){
                return new Crop(value.cropType);
            }
        }
        return null;
    }

}
