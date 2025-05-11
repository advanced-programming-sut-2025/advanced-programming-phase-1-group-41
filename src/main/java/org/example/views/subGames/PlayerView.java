package org.example.views.subGames;

import org.example.controllers.PlayerController;
import org.example.views.commands.gameCommands.GameMainCommands;

import java.util.regex.Matcher;

public class PlayerView {
    static PlayerController controller = new PlayerController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = GameMainCommands.EnergyShow.getMatcher(input))!=null){
            controller.showEnergy(matcher);
        }
        else{
            return false;
        }
        return true;
    }
}
