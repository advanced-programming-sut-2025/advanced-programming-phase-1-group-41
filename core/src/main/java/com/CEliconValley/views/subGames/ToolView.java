package com.CEliconValley.views.subGames;

import com.CEliconValley.controllers.subgames.ToolsController;
import com.CEliconValley.views.commands.gameCommands.InventoryAndToolCommands;

import java.util.regex.Matcher;

public class ToolView {
    static ToolsController controller = new ToolsController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = InventoryAndToolCommands.ToolsEquip.getMatcher(input))!=null){
            System.out.println(controller.equipTool(matcher));
        }else if((matcher = InventoryAndToolCommands.ShowCurrentTools.getMatcher(input))!=null){
            System.out.println(controller.showCurrentTool(matcher));
        }else if((matcher = InventoryAndToolCommands.ShowAvailableTools.getMatcher(input))!=null){
            System.out.println(controller.showAvailableTools(matcher));
        }else if((matcher = InventoryAndToolCommands.UseTool.getMatcher(input))!=null){
            System.out.println(controller.useTool(matcher));
        }else if((matcher = InventoryAndToolCommands.UpgradeTool.getMatcher(input))!=null){
            System.out.println(controller.upgradeTool(matcher));
        }
        else{
            return false;
        }
        return true;
    }
}
