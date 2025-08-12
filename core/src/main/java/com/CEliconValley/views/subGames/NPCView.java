package com.CEliconValley.views.subGames;

import com.CEliconValley.controllers.subgames.NPCController;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Result;
import com.CEliconValley.views.commands.gameCommands.NPCCommands;

import java.util.regex.Matcher;

public class NPCView {
    static NPCController controller =  new NPCController();
    public static boolean check(String input, String playername){
        Matcher matcher;
        if((matcher = NPCCommands.MeetNPC.getMatcher(input))!=null){
            System.out.println(controller.meetNpc(matcher));
        }else if((matcher = NPCCommands.GiftNPC.getMatcher(input))!=null){
            Result result = controller.giftToNpc(matcher, playername);
            System.out.println(result);
            App.sendRawResult(result, playername);
        }else if((matcher = NPCCommands.FriendshipNPCList.getMatcher(input))!=null){
            System.out.println(controller.friendshipList(matcher));
        }else if((matcher = NPCCommands.QuestsList.getMatcher(input))!=null){
            System.out.println(controller.questList(matcher));
        }else if((matcher = NPCCommands.QuestFinish.getMatcher(input))!=null){
            System.out.println(controller.finishQuest(matcher, playername));
        }  else{
            return false;
        }
        return true;
    }
}
