package org.example.views.subGames;

import org.example.controllers.AnimalController;
import org.example.controllers.FriendshipController;
import org.example.views.commands.gameCommands.AnimalCommands;
import org.example.views.commands.gameCommands.FriendShipCommands;

import java.util.regex.Matcher;

public class FriendshipView {
    static FriendshipController controller =  new FriendshipController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = FriendShipCommands.Friendships.getMatcher(input))!=null){
            System.out.println(controller.friendshipsList(matcher));
        }else if(true) {

        }else{
            return false;
        }
        return true;
    }
}
