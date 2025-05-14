package org.example.views.subGames;

import org.example.controllers.AnimalController;
import org.example.views.commands.gameCommands.AnimalCommands;
import org.example.views.commands.gameCommands.FriendShipCommands;

import java.util.regex.Matcher;

public class FriendshipView {
    static AnimalController controller =  new AnimalController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = FriendShipCommands.Friendships.getMatcher(input))!=null){
            System.out.println(controller.build(matcher));
        }else if((matcher = AnimalCommands.BuyAnimal.getMatcher(input))!=null) {

        }else{
            return false;
        }
        return true;
    }
}
