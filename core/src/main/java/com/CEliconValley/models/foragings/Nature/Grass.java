package com.CEliconValley.models.foragings.Nature;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Colors;
import com.CEliconValley.models.ObjectMap;
import com.CEliconValley.models.locations.Farm;

public class Grass implements ObjectMap {
    @Override
    public String getChar() {
        if(isFarmland){
            return Colors.colorize(94,0,"||");
        } else if(isSand){
            return Colors.colorize(228,0,"ww");
        } else if(isThundered){
            return Colors.colorize(15,0,"ww");
        } else if(isBombed){
            return Colors.colorize(237,0,"ww");
        } else if(isGround){
            return Colors.colorize(215,0,"ww");
        }else{
            if(App.getGame() != null){
                if(App.getGame().getTime().getHour() >= 19){
                    return Colors.colorize(22,0,"ww");
                }
            }
            return Colors.colorize(40,0,"ww");
//            return Colors.colorize(2,0,"ww");
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
    private boolean isBombed;

    public Grass(){
        isGround = false;
        isFarmland = false;
        isSand = false;
        isThundered = false;
        isBombed = false;
    }

    public boolean isFarmland() {
        return isFarmland;
    }
    public void setFarmland(boolean isFarmland) {
        this.isFarmland = isFarmland;
        isGround = false;
        isSand = false;
        isThundered = false;
        isBombed = false;
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
    public void setThundered(boolean isThundered) {
        this.isThundered = isThundered;
    }
    public void setBombed(boolean bombed) {this.isBombed = bombed;}
    public Grass(int startX, int startY, Farm farm) {
        isGround = true;
        isFarmland = false;
        isSand = false;
        isBombed = false;
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
