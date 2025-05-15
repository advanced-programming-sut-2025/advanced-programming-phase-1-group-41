package org.example.models.foragings.Nature;

import org.example.models.foragings.Crop;
import org.example.models.foragings.CropType;
import org.example.models.foragings.ForagingCrop;
import org.example.models.foragings.ForagingCropType;
import org.example.models.items.Eatable;
import org.example.models.items.Item;

public enum Mushroom implements Item {
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

}
