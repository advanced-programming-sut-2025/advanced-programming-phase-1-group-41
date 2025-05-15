package org.example.models.items.craftableitems;

import org.example.models.foragings.ForagingCropType;
import org.example.models.foragings.Nature.Mushroom;
import org.example.models.items.Eatable;
import org.example.models.items.Item;

public class DriedMushroom implements Eatable, Item {
    Mushroom mushroom;

    public DriedMushroom() {
    }

    public DriedMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    public Mushroom getMushroom() {
        return mushroom;
    }

    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    @Override
    public double getEnergy() {
        return 50;
    }

    @Override
    public String getName() {
        return "DriedMushroom";
    }

    @Override
    public double getPrice() {
        return 25+7.5*mushroom.getPrice();
    }

    @Override
    public String getChar() {
        return "DM";
    }
}
