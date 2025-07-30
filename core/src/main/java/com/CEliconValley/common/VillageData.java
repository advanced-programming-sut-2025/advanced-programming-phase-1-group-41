package com.CEliconValley.common;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.ObjectMap;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.marketplaces.Marketplace;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.locations.Village;
import com.CEliconValley.models.npc.npcCharacters.NPC;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;
import java.util.HashMap;

@Embedded
public class VillageData {
    ArrayList<CellData> cellsData;
    ArrayList<MarketPlaceData> markplacesData;
    ArrayList<BuildingData> buildingsData;
//    ArrayList<CellData> transferCellsData;
//    ArrayList<CellData> startPointsData;
    ArrayList<NPCData> NPCsData;

    public VillageData() {
    }

    public VillageData(Village village) {
        cellsData = new ArrayList<>();
        markplacesData = new ArrayList<>();
        buildingsData = new ArrayList<>();
//        transferCellsData = new ArrayList<>();
//        startPointsData = new ArrayList<>();
        NPCsData = new ArrayList<>();
        village.getCells().forEach(cell -> {
            cellsData.add(new CellData(cell));
        });
//        village.getTransferCells().forEach(cell -> {
//            transferCellsData.add(new CellData(cell));
//        });
//        village.getStartPoints().forEach(cell -> {
//            startPointsData.add(new CellData(cell));
//        });
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

    public Village getVillage(ArrayList<Player> players) {
        Village village = new Village(true);
        // set the ground!
        for (int i = 0; i < village.getCells().size(); i++) {
            Cell cell = village.getCells().get(i);
            if(cell.getObjectMap() instanceof Building) continue;
            CellData cellData = cellsData.get(i);
            ObjectMap objectMap = cell.getObjectMap();
            if(objectMap == null) {
                System.out.println("null "+cellData.objectName);
            }else{
                cell.setObjectMap(objectMap);
            }
        }
        // set stock!
        for (Building building : village.getBuildings()) {
            if(building instanceof Marketplace marketplace){
                for (MarketPlaceData marketPlaceData : markplacesData) {
                    if(marketPlaceData.getName().equals(marketplace.getName())){
                        marketplace.setItemsForSale(getItemsForSale(marketPlaceData));
                        break;
                    }
                }
            }
        }
        // set NPCs!
        for (NPC npc : village.getNPCs()) {
            for (NPCData npcData : NPCsData) {
                if(npc.getName().equals(npc.getName())){
                    npc.setDaysToUnlockQ3(npcData.getDaysToUnlockQ3());
                    npc.setFriendShip(getFriendShips(npc, npcData, players));
                    npc.setIsTalkedToday(getIsTalkedToday(npc, npcData, players));
                    npc.setIsGiftedToday(getIsGiftedToday(npc, npcData, players));
                    break;
                }
            }
        }
        return village;
    }

    private HashMap<Player, Integer> getFriendShips(NPC npc,
                                                    NPCData npcData, ArrayList<Player> players) {
        HashMap<Player, Integer> friendShips = new HashMap<>();
        for (String string : npcData.getFriendShipData().keySet()) {
            Player player = PlayerFinder.getPlayerByName(players, string);
            friendShips.put(player, npcData.getFriendShipData().get(string));
        }
        return friendShips;
    }
    private HashMap<Player, Boolean> getIsTalkedToday(NPC npc,
            NPCData npcData,ArrayList<Player> players) {
        HashMap<Player, Boolean> isTalkedToday = new HashMap<>();
        for (String string : npcData.getIsTalkedTodayData().keySet()) {
            Player player = PlayerFinder.getPlayerByName(players, string);
            isTalkedToday.put(player, npcData.getIsTalkedTodayData().get(string));
        }
        return isTalkedToday;
    }
    private HashMap<Player, Boolean> getIsGiftedToday(NPC npc,
                                                      NPCData npcData,ArrayList<Player> players) {
        HashMap<Player, Boolean> isGiftedToday = new HashMap<>();
        for (String string : npcData.getIsGiftedTodayData().keySet()) {
            Player player = PlayerFinder.getPlayerByName(players, string);
            isGiftedToday.put(player, npcData.getIsGiftedTodayData().get(string));
        }
        return isGiftedToday;
    }

    private ArrayList<Slot> getItemsForSale(MarketPlaceData marketPlaceData) {
        ArrayList<Slot> itemsForSale = new ArrayList<>();
        marketPlaceData.getItemsForSaleData().forEach(slotData -> {
            itemsForSale.add(slotData.getSlot());
        });
        return itemsForSale;
    }
}
