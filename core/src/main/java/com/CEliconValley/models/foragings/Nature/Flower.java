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
    public int getID() {
        switch (this.cropType) {
            case BlueJazz -> { return 10000; }
            case Carrot -> { return 10001; }
            case Cauliflower -> { return 10002; }
            case CoffeeBean -> { return 10003; }
            case Garlic -> { return 10004; }
            case GreenBean -> { return 10005; }
            case Kale -> { return 10006; }
            case Parsnip -> { return 10007; }
            case Potato -> { return 10008; }
            case Rhubarb -> { return 10009; }
            case Strawberry -> { return 10010; }
            case Tulip -> { return 10011; }

            case Rice -> { return 10100; }
            case Blueberry -> { return 10101; }
            case Corn -> { return 10102; }
            case Hops -> { return 10103; }
            case HotPepper -> { return 10104; }
            case Melon -> { return 10105; }
            case Poppy -> { return 10106; }
            case Radish -> { return 10107; }
            case RedCabbage -> { return 10108; }
            case Starfruit -> { return 10109; }
            case SummerSpangle -> { return 10110; }
            case SummerSquash -> { return 10111; }

            case Sunflower -> { return 10200; }
            case Tomato -> { return 10201; }
            case Wheat -> { return 10202; }
            case Amaranth -> { return 10203; }
            case Artichoke -> { return 10204; }
            case Beet -> { return 10205; }
            case BokChoy -> { return 10206; }
            case Broccoli -> { return 10207; }
            case Cranberries -> { return 10208; }
            case Eggplant -> { return 10209; }
            case FairyRose -> { return 10210; }
            case Grape -> { return 10211; }

            case Pumpkin -> { return 10300; }
            case Yam -> { return 10301; }
            case SweetGemBerry -> { return 10302; }
            case Powdermelon -> { return 10303; }
            case AncientFruit -> { return 10304; }
        }

        throw new IllegalStateException("Unknown cropType: " + this.cropType);
    }

}
