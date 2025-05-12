package org.example.models.foragings.Nature;

import org.example.models.Colors;
import org.example.models.ObjectMap;

public class Grass implements ObjectMap {
    @Override
    public String getChar() {
        if(isFarmland){
            return Colors.colorize(94,0,"WW");
        } else{
            return Colors.colorize(2,0,"ww");
        }
    }

    @Override
    public String getName() {
        return "Grass";
    }

    private boolean isFarmland = false;

    public boolean isFarmland() {
        return isFarmland;
    }
    public void setFarmland(boolean isFarmland) {
        this.isFarmland = isFarmland;
    }
}
