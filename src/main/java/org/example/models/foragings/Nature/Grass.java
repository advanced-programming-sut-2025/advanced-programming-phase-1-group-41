package org.example.models.foragings.Nature;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.ObjectMap;
import org.example.models.locations.Farm;

public class Grass implements ObjectMap {
    @Override
    public String getChar() {
        if(isFarmland){
            return Colors.colorize(94,0,"||");
        } else if(isSand){
            return Colors.colorize(228,0,"ww");
        } else if(isThundered){
            return Colors.colorize(15,0,"ww");
        } else if(isGround){
            return Colors.colorize(215,0,"ww");
        }else{
            return Colors.colorize(2,0,"ww");
        }
    }

    @Override
    public String getName() {
        return "Grass";
    }

    private boolean isFarmland;
    private boolean isGround;
    private boolean isSand;
    private boolean isThundered;

    public Grass(){
        isGround = false;
        isFarmland = false;
        isSand = false;
        isThundered = false;
    }

    public boolean isFarmland() {
        return isFarmland;
    }
    public void setFarmland(boolean isFarmland) {
        this.isFarmland = isFarmland;
        isGround = false;
        isSand = false;
        isThundered = false;
    }
    public boolean isSand() {return isSand;}

    public boolean isGround() {
        return isGround;
    }
    public void setGround(boolean isGround) {
        this.isGround = isGround;
    }
    public void setSand(boolean isSand) {
        this.isSand = isSand;
    }
    public boolean isThundered() {
        return isThundered;
    }
    public void setThundered(boolean isThundered) {
        this.isThundered = isThundered;
    }
    public Grass(int startX, int startY, Farm farm) {
        isGround = true;
        isFarmland = false;
        isSand = false;
//        for(int i = startX; i < startX + 13; i++) {
//            Objects.requireNonNull(Finder.findCellByCoordinates(i, startY, farm)).setObjectMap(this);
//        }
//        startX += 12;
//        for(int i = startY; i > startY - 12; i--) {
//            Objects.requireNonNull(Finder.findCellByCoordinates(startX , i, farm)).setObjectMap(this);
//        }
//        startY -= 12;
//        for(int i = startX; i < startX + 13; i++) {
//            Objects.requireNonNull(Finder.findCellByCoordinates(i, startY, farm)).setObjectMap(this);
//        }
//        startX += 12;
//        for(int i = startY; i < startY + 13; i++) {
//            Objects.requireNonNull(Finder.findCellByCoordinates(startX, i, farm)).setObjectMap(this);
//        }
//        startX -= 12;
//        for(int i = startY; i > startY - 13; i--) {
//            Objects.requireNonNull(Finder.findCellByCoordinates(startX, i, farm)).setObjectMap(this);
//        }
//        startY -= 12;
//        for(int i = startX; i < startX + 13; i++) {
//            Objects.requireNonNull(Finder.findCellByCoordinates(i, startY, farm)).setObjectMap(this);
//        }
//        startY += 12;
//        for(int i = startX + 1; i > startX - 12; i--) {
//            Objects.requireNonNull(Finder.findCellByCoordinates(i, startY, farm)).setObjectMap(this);
//        }
//        startX -= 12;
//        for(int i = startY; i > startY - 13; i--) {
//            Objects.requireNonNull(Finder.findCellByCoordinates(startX, i, farm)).setObjectMap(this);
//        }
    }
}
