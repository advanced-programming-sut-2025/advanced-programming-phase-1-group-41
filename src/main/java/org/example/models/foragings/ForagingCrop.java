package org.example.models.foragings;

public class ForagingCrop implements Foraging{
    private ForagingCropType foragingCropType;

    @Override
    public String getChar() {
        return "FC";
    }

    @Override
    public String getName() {
        return foragingCropType.getName();
    }
}
