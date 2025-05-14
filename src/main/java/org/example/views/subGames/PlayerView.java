package org.example.views.subGames;

import org.example.controllers.subgames.PlayerController;
import org.example.views.commands.gameCommands.GameMainCommands;
import org.example.views.commands.gameCommands.InventoryAndToolCommands;

import java.util.regex.Matcher;

public class PlayerView {
    static PlayerController controller = new PlayerController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = GameMainCommands.EnergyShow.getMatcher(input))!=null){
            System.out.println(controller.showEnergy(matcher));
        }else if((matcher = GameMainCommands.MoneyShow.getMatcher(input))!=null){
            System.out.println(controller.showMoney(matcher));
        }else if((matcher = GameMainCommands.EnergyUnlimited.getMatcher(input))!=null){
            System.out.println(controller.cheatEnergyUnlimited(matcher));
        }else if((matcher = GameMainCommands.EnergySet.getMatcher(input))!=null){
            System.out.println(controller.cheatEnergySet(matcher));
        }else if((matcher = GameMainCommands.CheatAddItem.getMatcher(input))!=null){
            System.out.println(controller.cheatAddItem(matcher));
        }else if((matcher = InventoryAndToolCommands.InventoryTrash.getMatcher(input))!=null){
            System.out.println(controller.inventoryTrash(matcher));;
        }else if((matcher = InventoryAndToolCommands.InventoryShow.getMatcher(input))!=null){
            System.out.println(controller.showInventory(matcher));
        }else if((matcher = GameMainCommands.Fishing.getMatcher(input))!=null){
            System.out.println(controller.fishing(matcher));
        }else if((matcher = GameMainCommands.Eat.getMatcher(input))!=null){
            System.out.println(controller.eat(matcher));
        }else if((matcher = GameMainCommands.SkillShow.getMatcher(input))!=null){
            System.out.println(controller.showSkill(matcher));
        }
        else{
            return false;
        }
        return true;
    }
}
