package com.CEliconValley.common;

import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.locations.Village;
import com.CEliconValley.models.npc.npcCharacters.NPC;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;

@Embedded
public class VillageData {
    ArrayList<CellData> cellsData;
    ArrayList<MarketPlaceData> markplacesData;
    ArrayList<BuildingData> buildingsData;
    ArrayList<CellData> transferCellsData;
    ArrayList<CellData> startPointsData;
    ArrayList<NPCData> NPCsData;
    public VillageData(Village village) {
        cellsData = new ArrayList<>();
        markplacesData = new ArrayList<>();
        buildingsData = new ArrayList<>();
        transferCellsData = new ArrayList<>();
        startPointsData = new ArrayList<>();
        NPCsData = new ArrayList<>();
        village.getCells().forEach(cell -> {
            cellsData.add(new CellData(cell));
        });
        village.getTransferCells().forEach(cell -> {
            transferCellsData.add(new CellData(cell));
        });
        village.getStartPoints().forEach(cell -> {
            startPointsData.add(new CellData(cell));
        });
        for (Building building : village.getBuildings()) {
            if(building instanceof Marketplace marketplace){
                markplacesData.add(new MarketPlaceData(marketplace));
            }
            buildingsData.add(new BuildingData(building));
        }
        for (NPC npc : village.getNPCs()) {
            NPCsData.add(new NPCData(npc));
        }
    }
}
