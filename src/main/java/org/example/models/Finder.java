package org.example.models;

import org.bson.types.ObjectId;
import org.example.models.foragings.*;
import org.example.models.foragings.Nature.Mineral;
import org.example.models.foragings.Nature.MineralType;
import org.example.models.foragings.Nature.Rock;
import org.example.models.foragings.Nature.Wood;
import org.example.models.items.CraftableMachine;
import org.example.models.items.Food;
import org.example.models.items.Item;
import org.example.models.items.Slot;
import org.example.models.locations.Farm;
import org.example.models.locations.Village;
import org.example.models.tools.Tool;

import java.util.Objects;

import static org.example.models.animals.FishType.parseFish;
import static org.example.models.tools.BasicTool.parseBasicTool;

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
        if(CraftableMachine.parseCraftable(itemName)!=null){
            return CraftableMachine.parseCraftable(itemName);
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
            return new Mineral(MineralType.parseMineralType(itemName));
        }
        if(itemName.equalsIgnoreCase((new Wood()).getName())){
            return new Wood();
        }
        if(itemName.equalsIgnoreCase((new Rock()).getName())){
            return new Rock();
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
            System.out.println("checking.. "+slot.getItem().getName()+" "+name);
            if(slot.getItem() instanceof Tool){
                if(slot.getItem().getName().equalsIgnoreCase(name)){
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
}
