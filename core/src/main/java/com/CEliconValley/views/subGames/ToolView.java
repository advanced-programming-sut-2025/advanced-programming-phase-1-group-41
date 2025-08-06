package com.CEliconValley.views.subGames;

import com.CEliconValley.common.FarmData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.controllers.subgames.ToolsController;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Player;
import com.CEliconValley.views.commands.gameCommands.InventoryAndToolCommands;
import com.google.gson.Gson;

import java.util.regex.Matcher;

public class ToolView {
    static ToolsController controller = new ToolsController();

    public static boolean check(String input, String playername) {
        Matcher matcher;
        if ((matcher = InventoryAndToolCommands.ToolsEquip.getMatcher(input)) != null) {
            System.out.println(controller.equipTool(matcher, playername));
        } else if ((matcher = InventoryAndToolCommands.ShowCurrentTools.getMatcher(input)) != null) {
            System.out.println(controller.showCurrentTool(matcher));
        } else if ((matcher = InventoryAndToolCommands.ShowAvailableTools.getMatcher(input)) != null) {
            System.out.println(controller.showAvailableTools(matcher));
        } else if ((matcher = InventoryAndToolCommands.UseTool.getMatcher(input)) != null) {
            System.out.println(controller.useTool(matcher, playername));
            GameMessage<FarmData> msg = new GameMessage<>("farm-data",
                new FarmData(Finder.getFarmByPlayer(Finder.getPlayerByUsername(playername))));
            App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(),
                new Gson().toJson(msg));
        } else if ((matcher = InventoryAndToolCommands.UpgradeTool.getMatcher(input)) != null) {
            System.out.println(controller.upgradeTool(matcher));
        } else {
            return false;
        }
        return true;
    }
}
