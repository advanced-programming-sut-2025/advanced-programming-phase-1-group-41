package com.CEliconValley.models.foragings;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.ui.TerminalColors;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.locations.Farm;

import java.util.Random;

public class ForagingCrop implements Foraging, Item {

    private ForagingCropType foragingCropType;
    int typeIndex;

    @Override
    public String getChar() {
        return TerminalColors.colorize(109,0,typeIndex / 10 + "" + typeIndex % 10);
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


    public ForagingCrop(ForagingCropType foragingCropType, int typeIndex) {
        this.foragingCropType = foragingCropType;
        this.typeIndex = typeIndex;
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
    public ForagingCropType getForagingCropType() {
        return foragingCropType;
    }

    public int getTypeIndex() {
        return typeIndex;
    }
    @Override
    public int getID() {
        switch (this.foragingCropType) {
            case WildHorseradish -> { return 10610; }
            case FiddleheadFern -> { return 10611; }
            case Grape -> { return 10700; }
            case RedMushroom -> { return 10701; }
            case SpiceBerry -> { return 10702; }
            case SweetPea -> { return 10703; }
            case Blackberry -> { return 10704; }
            case Chanterelle -> { return 10705; }
            case Hazelnut -> { return 10706; }
            case PurpleMushroom -> { return 10707; }
            case WildPlum -> { return 10708; }
            case Crocus -> { return 10709; }
            case CrystalFruit -> { return 10710; }
            case Holly -> { return 10711; }

            case SnowYam -> { return 10800; }
            case WinterRoot -> { return 10801; }
            case CommonMushroom -> { return 10802; }
            case Daffodil -> { return 10803; }
            case Dandelion -> { return 10804; }
            case Leek -> { return 10805; }
            case Morel -> { return 10806; }
            case SalmonBerry -> { return 10807; }
            case SpringOnion -> { return 10808; }
        }
        throw new IllegalStateException("Unknown foragingType: " + this.foragingCropType);
    }


}
