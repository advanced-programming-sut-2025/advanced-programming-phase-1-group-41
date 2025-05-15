package org.example.models.foragings.Nature;

import org.example.models.foragings.Crop;
import org.example.models.foragings.CropType;
import org.example.models.items.Eatable;
import org.example.models.items.Item;

public enum Vegetable implements Item, Eatable {
    Carrot(CropType.Carrot),
    Cauliflower(CropType.Cauliflower),
    Garlic(CropType.Garlic),
    GreenBean(CropType.GreenBean),
    Kale(CropType.Kale),
    Parsnip(CropType.Parsnip),
    Potato(CropType.Potato),
    Rhubarb(CropType.Rhubarb),
    Corn(CropType.Corn),
    HotPepper(CropType.HotPepper),
    Radish(CropType.Radish),
    RedCabbage(CropType.RedCabbage),
    SummerSquash(CropType.SummerSquash),
    Tomato(CropType.Tomato),
    Amaranth(CropType.Amaranth),
    Artichoke(CropType.Artichoke),
    Beet(CropType.Beet),
    BokChoy(CropType.BokChoy),
    Broccoli(CropType.Broccoli),
    Eggplant(CropType.Eggplant),
    Pumpkin(CropType.Pumpkin),
    Yam(CropType.Yam),
    Powdermelon(CropType.Powdermelon);
    ;

    private final CropType cropType;

    Vegetable(CropType cropType) {
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
        for (Vegetable value : Vegetable.values()) {
            if(value.getName().equalsIgnoreCase(item)){
                return new Crop(value.cropType);
            }
        }
        return null;
    }

    public static Vegetable parseVegetable(String item) {
        for (Vegetable value : Vegetable.values()) {
            if(value.getName().equalsIgnoreCase(item)){
                return value;
            }
        }
        return null;
    }

    @Override
    public double getEnergy() {
        return cropType.getEnergy();
    }
}
