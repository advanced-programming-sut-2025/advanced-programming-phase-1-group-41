package org.example.models;

import org.bson.types.ObjectId;
import org.example.models.items.Item;
import org.example.models.locations.Farm;

import static org.example.models.animals.FishType.parseFish;
import static org.example.models.items.CraftableItem.parseCraftable;
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
    public static Item findItem(String itemName){
        if(parseBasicTool(itemName)!=null){
            return parseBasicTool(itemName);
        }
        if(parseCraftable(itemName)!=null){
            return parseCraftable(itemName);
        }
        if(parseFish(itemName)!=null){
            return parseFish(itemName);
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
    public static Cell findCellByCoordinates(int x, int y, Farm farm){
        for(Cell cell : farm.getCells()){
            if(cell.getX() == x && cell.getY() == y){
                return cell;
            }
        }
        return null;
    }
}
