package com.CEliconValley.models.foragings;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.locations.Farm;

import java.util.Random;

public class ForagingCrop implements Foraging, Item {

    private ForagingCropType foragingCropType;
    int typeIndex;

    @Override
    public String getChar() {
        return Colors.colorize(109,0,typeIndex / 10 + "" + typeIndex % 10);
    }

    @Override
    public String getName() {
        return foragingCropType.getName();
    }

    @Override
    public double getPrice() {
        return foragingCropType.getBaseSellPrice();
    }

    public ForagingCrop(ForagingCropType foragingCropType) {
        this.foragingCropType = foragingCropType;
    }

    public ForagingCrop(int x, int y, Farm farm) {
        Random rand = new Random();
        while (true){
            int type = rand.nextInt(ForagingCropType.values().length);
            typeIndex = type;
            foragingCropType = ForagingCropType.values()[type];
//            if(App.getGame() == null){
//                break;
//            }
            if(foragingCropType.getGrowingSeason().equals(App.getGame().getTime().getSeason())){
                break;
            }
        }
        Cell cell = Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        cell.setObjectMap(this);
    }


}
