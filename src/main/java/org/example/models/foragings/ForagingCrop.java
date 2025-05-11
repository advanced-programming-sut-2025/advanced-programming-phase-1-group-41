package org.example.models.foragings;

public class ForagingCrop implements Foraging{
    private ForagingCropType cropType;

    @Override
    public String getChar() {
        return "FC";
    }

    @Override
    public String getName() {
        return cropType.getName();
    }
}
