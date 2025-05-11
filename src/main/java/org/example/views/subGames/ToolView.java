package org.example.views.subGames;

import org.example.controllers.ToolsController;
import org.example.views.commands.gameCommands.GameMainCommands;
import org.example.views.commands.gameCommands.InventoryAndToolCommands;

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
        }else{
            return false;
        }
        return true;
    }
}
