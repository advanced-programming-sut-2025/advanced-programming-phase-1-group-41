package com.CEliconValley.client.view.screen.maps;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.CellData;
import com.CEliconValley.common.VillageData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.foragings.Nature.Obstacle;
import com.CEliconValley.models.locations.Village;
import com.CEliconValley.models.locations.Location;

public class VillageMap implements Location {
    public VillageData villageData;

    public VillageMap(VillageData villageData) {
        this.villageData = villageData;
    }

    public void updateVillageData(){
        this.villageData = AppClient.getGameData().getVillageData();
    }

    public boolean canMoveTo(int x, int y){
        for (CellData cd : villageData.getCellsData()) {
            if (cd.getX() == x && cd.getY() == y) {
                Cell cell = cd.extractData();
                if (cell.getObjectMap() instanceof Lake ||( cell.getObjectMap() instanceof Grass grass && !grass.isGround() )
                    ||cell.getObjectMap() instanceof Wall ||cell.getObjectMap() instanceof Obstacle) {
                    System.out.println(cd.getObjectName()+" "+cell.getX()+" "+cell.getY());
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
