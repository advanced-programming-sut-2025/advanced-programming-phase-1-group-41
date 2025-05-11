package org.example.views.subGames;

import org.example.controllers.PlayerController;
import org.example.views.commands.gameCommands.GameMainCommands;
import org.example.views.commands.gameCommands.InventoryAndToolCommands;

import java.util.regex.Matcher;

public class PlayerView {
    static PlayerController controller = new PlayerController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = GameMainCommands.EnergyShow.getMatcher(input))!=null){
            controller.showEnergy(matcher);
        }else if((matcher = GameMainCommands.CheatAddItem.getMatcher(input))!=null){
            controller.cheatAddItem(matcher);
        }else if((matcher = InventoryAndToolCommands.InventoryTrash.getMatcher(input))!=null){
            controller.inventoryTrash(matcher);
        }else if((matcher = InventoryAndToolCommands.InventoryShow.getMatcher(input))!=null){
            controller.showInventory(matcher);
        }
        else{
            return false;
        }
        return true;
    }
}
