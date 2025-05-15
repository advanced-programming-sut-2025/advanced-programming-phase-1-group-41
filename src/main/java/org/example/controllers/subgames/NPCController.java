package org.example.controllers.subgames;

import org.example.models.App;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.WeatherType;
import org.example.models.npc.npcCharacters.NPC;

import java.util.regex.Matcher;

import static org.example.models.Finder.parseNPC;

public class NPCController {
    public Result meetNpc(Matcher matcher){
        String npcName = matcher.group(1);
        NPC npc=findNPCAround(npcName);
        if(npc==null){
            return new Result(false,  npcName+" is not around");
        }
        if(!App.getGame().getWeatherType().equals(WeatherType.Sunny)){
            switch (App.getGame().getWeatherType()){
                case Rainy:
                    return new Result(true, npc.getDialogues(4));
                case Snowy:
                    return new Result(true, npc.getDialogues(5));
                case Stormy:
                    return new Result(true, npc.getDialogues(6));
            }
        }else if(App.getGame().getTime().getHour()>18){
            return new Result(true, npc.getDialogues(7));
        }
        else if(npc.getFriendShip()>=600){
            return new Result(true, npc.getDialogues(8));
        }else{
            switch (App.getGame().getTime().getSeason()){
                case Spring:
                    return new Result(true, npc.getDialogues(0));
                case Summer:
                    return new Result(true, npc.getDialogues(1));
                case Autumn:
                    return new Result(true, npc.getDialogues(2));
                case Winter:
                    return new Result(true, npc.getDialogues(3));
            }
        }
        return new Result(false, "impossible");
    }

    public Result giftToNpc(Matcher matcher){return null;}

    public Result friendshipList(Matcher matcher){return null;}

    public Result questList(Matcher matcher){return null;}

    public Result finishQuest(Matcher matcher){return null;}

    public void increaseFriendshipLevel(){}

    public void ReceiveGiftFromNpc(){}

    private NPC findNPCAround(String name){
        Player player = App.getGame().getCurrentPlayer();
        NPC npc = parseNPC(name);
        if(npc==null){
            return null;
        }
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                if (player.getX() + i == npc.getX() && player.getY() + j == npc.getY()) {
                    return npc;

                }
            }
        }
        return null;
    }
}
