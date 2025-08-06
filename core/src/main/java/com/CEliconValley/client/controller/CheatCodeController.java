package com.CEliconValley.client.controller;

import com.CEliconValley.common.GameData;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.controllers.TimeLineController;
import com.CEliconValley.controllers.WeatherController;
import com.CEliconValley.controllers.subgames.AnimalController;
import com.CEliconValley.controllers.subgames.MarketplaceController;
import com.CEliconValley.controllers.subgames.PlayerController;
import com.CEliconValley.models.App;
import com.CEliconValley.views.commands.gameCommands.FriendShipCommands;
import com.CEliconValley.views.commands.gameCommands.GameMainCommands;
import com.CEliconValley.views.commands.gameCommands.StoreCommands;
import com.CEliconValley.views.commands.gameCommands.WeatherAndTimeCommands;
import com.google.gson.Gson;

import java.util.regex.Matcher;

public class CheatCodeController {
    public static boolean cheatCodeHandler(String input, String playerName) {
        Matcher matcher;
        TimeLineController timeLineController = new TimeLineController();
        AnimalController animalController = new AnimalController();
        PlayerController playerController = new PlayerController();
        WeatherController weatherController = new WeatherController();
        MarketplaceController marketplaceController = new MarketplaceController();

        if ((matcher = WeatherAndTimeCommands.CheatAdvanceTime.getMatcher(input)) != null) {
            System.out.println(timeLineController.cheatAdvanceTime(matcher));
        } else if ((matcher = WeatherAndTimeCommands.CheatAdvanceDate.getMatcher(input)) != null) {
            System.out.println(timeLineController.cheatAdvanceDate(matcher));
        } else if((matcher = FriendShipCommands.CheatSetFriendship.getMatcher(input))!=null){
            System.out.println(animalController.cheatSetFriendship(matcher, playerName));
        } else if((matcher = GameMainCommands.CheatAddItem.getMatcher(input))!=null){
            System.out.println(playerController.cheatAddItem(matcher, playerName));
        } else if((matcher = WeatherAndTimeCommands.CheatThor.getMatcher(input))!=null){
            System.out.println(weatherController.cheatStrikeThunder(matcher, playerName));
        } else if((matcher = WeatherAndTimeCommands.CheatWeather.getMatcher(input))!=null){
            System.out.println(weatherController.cheatChangeTmrwWeather(matcher));
        } else if ((matcher = StoreCommands.CheatAddDollars.getMatcher(input)) != null) {
            System.out.println(marketplaceController.cheatAddMoney(matcher, playerName));
        }else{
            return false;
        }
        GameMessage<GameData> response = new GameMessage<>("game-data", new GameData(App.getGame()));
        App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(),new Gson().toJson(response));
        return true;
    }
}
