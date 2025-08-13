package com.CEliconValley.views.subGames;

import com.CEliconValley.controllers.subgames.FriendshipController;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Result;
import com.CEliconValley.views.commands.gameCommands.FriendShipCommands;

import java.util.regex.Matcher;

public class FriendshipView {
    static FriendshipController controller =  new FriendshipController();
    public static boolean check(String input, String playername) {
        Matcher matcher;
        if((matcher = FriendShipCommands.Friendships.getMatcher(input)) != null){
            System.out.println(controller.friendshipsList(matcher));
        } else if((matcher = FriendShipCommands.Talk.getMatcher(input)) != null){
            System.out.println(controller.talk(matcher, playername));
        } else if((matcher = FriendShipCommands.TalkHistory.getMatcher(input)) != null){
            System.out.println(controller.talkHistory(matcher));
        } else if((matcher = FriendShipCommands.Gift.getMatcher(input)) != null){
            Result result = controller.gift(matcher, playername);
            System.out.println(result);
            App.sendRawResult(result, playername);
        } else if((matcher = FriendShipCommands.GiftList.getMatcher(input)) != null){
            System.out.println(controller.giftList(matcher));
        } else if((matcher = FriendShipCommands.GiftRate.getMatcher(input)) != null){
            System.out.println(controller.giftRate(matcher, playername));
        } else if((matcher = FriendShipCommands.GiftHistory.getMatcher(input)) != null){
            System.out.println(controller.giftHistory(matcher));
        } else if((matcher = FriendShipCommands.Hug.getMatcher(input)) != null){
            System.out.println(controller.hug(matcher, playername));
        } else if((matcher = FriendShipCommands.Flower.getMatcher(input)) != null){
            System.out.println(controller.flower(matcher));
        } else if((matcher = FriendShipCommands.Marriage.getMatcher(input)) != null){
            System.out.println(controller.propose(matcher));
        } else if((matcher = FriendShipCommands.Respond.getMatcher(input)) != null){
            System.out.println(controller.respond(matcher));
        }else if((matcher = FriendShipCommands.GoTo.getMatcher(input)) != null){
            System.out.println(controller.goToFarm(matcher));
        }
        else{
            return false;
        }
        return true;
    }
}
