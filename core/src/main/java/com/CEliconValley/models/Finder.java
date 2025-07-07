package com.CEliconValley.models;

import com.CEliconValley.models.buildings.marketplaces.items.*;
import com.CEliconValley.models.foragings.*;
import com.CEliconValley.models.foragings.Nature.*;
import com.CEliconValley.models.items.*;
import org.bson.types.ObjectId;
import com.CEliconValley.models.buildings.ShippingBin;
import com.CEliconValley.models.buildings.Well;
import com.CEliconValley.models.items.Products.ProductType;
import com.CEliconValley.models.items.craftableitems.CraftableNames;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Village;
import com.CEliconValley.models.npc.npcCharacters.NPC;
import com.CEliconValley.models.tools.FishingRodLevel;
import com.CEliconValley.models.tools.NormalTools;
import com.CEliconValley.models.tools.Tool;

import java.util.ArrayList;
import java.util.Objects;

import static com.CEliconValley.models.animals.FishType.parseFish;
import static com.CEliconValley.models.tools.BasicTool.parseBasicTool;

public class Finder {
    public static User getUserByUsername(String username){
        for (User user : App.users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
    public static Item parseItem(String itemName){
        if(parseBasicTool(itemName)!=null){
            return parseBasicTool(itemName);
        }
        if(NormalTools.parseNormalTool(itemName)!=null){
            return NormalTools.parseNormalTool(itemName);
        }
        if(CraftableMachine.parseCraftable(itemName)!=null){
            return CraftableMachine.parseCraftable(itemName);
        }
        if(FishingRodLevel.parseFishingRod(itemName)!=null){
            return FishingRodLevel.parseFishingRod(itemName);
        }
        if(itemName.equalsIgnoreCase(new Troutsoup().getName())){
            return new Troutsoup();
        }
        if(parseFish(itemName)!=null){
            return parseFish(itemName);
        }
        if(Food.parseFood(itemName)!=null){
            return Food.parseFood(itemName);
        }
        if(CropType.parseCropType(itemName)!=null){
            return new Crop(Objects.requireNonNull(CropType.parseCropType(itemName)));
        }
        if(ForagingCropType.parseForagingCropType(itemName)!=null){
            return new ForagingCrop(Objects.requireNonNull(ForagingCropType.parseForagingCropType(itemName)));
        }
        if(SeedType.parseSeedType(itemName)!=null){
            return new Seed(SeedType.parseSeedType(itemName));
        }
        if(FruitType.parseFruitType(itemName)!=null){
            return new Fruit(FruitType.parseFruitType(itemName));
        }
        if(FertilizerType.parseFertilizerType(itemName)!=null){
            return new Fertilizer(FertilizerType.parseFertilizerType(itemName));
        }
        if(MineralType.parseMineralType(itemName)!=null){
            return new Mineral(Objects.requireNonNull(MineralType.parseMineralType(itemName)));
        }
        if(itemName.equalsIgnoreCase((new Wood()).getName())){
            return new Wood();
        }
        if(itemName.equalsIgnoreCase((new Rock()).getName())){
            return new Rock();
        }
        if(itemName.equalsIgnoreCase((new Well()).getName())){
            return new Well();
        }
        if(itemName.equalsIgnoreCase((new ShippingBin()).getName())){
            return new ShippingBin();
        }
        if(itemName.equalsIgnoreCase((new Fiber()).getName())){
            return new Fiber();
        }
        if(CraftableNames.parseItem(itemName)!=null){
            return CraftableNames.parseItem(itemName);
        }
        if(CraftableItem.parseCraftable(itemName)!=null){
            return CraftableItem.parseCraftable(itemName);
        }
        if(MarketplaceItems.parseItem(itemName)!=null){
            return MarketplaceItems.parseItem(itemName);
        }
        if(SaloonItems.parseItem(itemName)!=null){
            return SaloonItems.parseItem(itemName);
        }
        if(FishShopItems.parseItem(itemName)!=null){
            return FishShopItems.parseItem(itemName);
        }
        if(GeneralStoreItems.parseItem(itemName)!=null){
            return GeneralStoreItems.parseItem(itemName);
        }
        if(ProductType.parseProductType(itemName)!=null){
            return ProductType.parseProductType(itemName);
        }
        if(Flower.parseItem(itemName)!=null){
            return Flower.parseItem(itemName);
        }
        if(Mushroom.parseItem(itemName)!=null){
            return Mushroom.parseItem(itemName);
        }
        // TODO etc

        return null;

    }
    public static User getUserById(ObjectId id){
        for (User user : App.users) {
            if(user.get_id().equals(id)){
                return user;
            }
        }
        return null;
    }

    public static Tool getToolByName(String name){
        for (Slot slot : App.getGame().getCurrentPlayer().getInventory().getSlots()) {
            if(slot.getItem() == null){
                continue;
            }
//            System.out.println("checking.. "+slot.getItem().getName()+" "+name);
            if(slot.getItem().getName().equalsIgnoreCase(name)){
                if(slot.getItem() instanceof Tool){
                    return (Tool) slot.getItem();
                }
            }
        }
        return null;
    }

    public static Cell findCellByCoordinates(int x, int y, Farm farm){
        for(Cell cell : farm.getCells()){
            if(cell.getX() == x && cell.getY() == y){
                return cell;
            }
        }
        return null;
    }
    public static Cell findCellByCoordinatesVillage(int x, int y, Village village){
        for(Cell cell : village.getCells()){
            if(cell.getX() == x && cell.getY() == y){
                return cell;
            }
        }
        return null;
    }

    public static Farm findFarmByPlayer(Player player){
        for(Farm farm:App.getGame().getFarms()){
            if(farm.getId()==player.getFarmId()){
                return farm;
            }
        }
        return null;
    }
    public static Player findPlayerByFarm(Farm farm){
        for(Player player : App.getGame().getPlayers()){
            if(farm.getId() == player.getFarmId()){
                return player;
            }
        }
        return null;
    }
    public static ArrayList<Player> findPlayersByFarm(Farm farm){
        ArrayList<Player> players = new ArrayList<>();
        for(Player player : App.getGame().getPlayers()){
            if(farm.getId() == player.getInFarmId()){
                players.add(player);
            }
        }
        return players;
    }
    public static Player findPlayerByUsername(String username){
        for(Player player : App.getGame().getPlayers()){
            if(player.getUser().getUsername().equals(username)){
                return player;
            }
        }
        return null;
    }
    public static NPC parseNPC(String name){
        for(NPC npc:App.getGame().getVillage().getNPCs()){
            if(npc.getName().equalsIgnoreCase(name)){
                return npc;
            }
        }
        return null;
    }
}
