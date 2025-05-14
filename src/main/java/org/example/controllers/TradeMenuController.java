package org.example.controllers;

import org.example.models.*;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.regex.Matcher;

public class TradeMenuController {
    public Result tradeToItem(Matcher matcher){
        String username = matcher.group("username");
        String type = matcher.group("type");
        Player trader = App.getGame().getCurrentPlayer();
        Player target = Finder.findPlayerByUsername(username);
        if(target == null){
            return new Result(false, "Player not found!");
        }
        boolean isRequest = type.equals("request");
        if(!type.equals("request") && !type.equals("offer")) {
            return new Result(false, "Unknown trade type!");
        }

        String itemName = matcher.group("item");
        int amount = Integer.parseInt(matcher.group("amount"));
        String targetItemName = matcher.group("targetItem");
        int targetAmount = Integer.parseInt(matcher.group("targetAmount"));
        if(Finder.parseItem(itemName) == null || Finder.parseItem(targetItemName) == null){
            return new Result(false, "Unknown item!");
        }
        Item item = Finder.parseItem(itemName);
        Item targetItem = Finder.parseItem(targetItemName);
        Slot firstSlot = new Slot(item, amount);
        Slot secondSlot = new Slot(targetItem, targetAmount);
        Trade trade = new Trade(trader, target, firstSlot, secondSlot, isRequest);
        target.getTradesList().add(trade);

        return new Result(true, "Trade added successfully!");
    }

    public Result tradeToMoney(Matcher matcher){
        String username = matcher.group("username");
        String type = matcher.group("type");
        Player trader = App.getGame().getCurrentPlayer();
        Player target = Finder.findPlayerByUsername(username);
        if(target == null){
            return new Result(false, "Player not found!");
        }
        boolean isRequest = type.equals("request");
        if(!type.equals("request") && !type.equals("offer")) {
            return new Result(false, "Unknown trade type!");
        }

        String itemName = matcher.group("item");
        int amount = Integer.parseInt(matcher.group("amount"));
        int price = Integer.parseInt(matcher.group("price"));
        if(Finder.parseItem(itemName) == null){
            return new Result(false, "Unknown item!");
        }
        Item item = Finder.parseItem(itemName);
        Slot firstSlot = new Slot(item, amount);
        Trade trade = new Trade(trader, target, firstSlot, price, isRequest);
        target.getTradesList().add(trade);
        target.getNewTradesList().add(trade);
        trader.getTotalTradesList().add(trade);

        return new Result(true, "Trade added successfully!");
    }

    public Result tradeList(Matcher matcher){
        StringBuilder tradeList = new StringBuilder();
        for(int i = 0; i < App.getGame().getCurrentPlayer().getTradesList().size(); i++){
            Trade trade = App.getGame().getCurrentPlayer().getTradesList().get(i);
            tradeList.append(i + 1).append(". ").append(trade.toString()).append("\n");
        }
        if(tradeList.isEmpty()){
            return new Result(false, "No trades available!");
        }
        tradeList.delete(tradeList.length() - 1, tradeList.length());
        return new Result(true, tradeList.toString());
    }

    public Result tradeResponse(Matcher matcher){return null;}

    public Result tradeHistory(Matcher matcher){return null;}

    public Result exitTradeMenu(Matcher matcher){return null;}
}
