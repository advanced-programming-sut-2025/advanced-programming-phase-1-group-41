package com.CEliconValley.controllers.subgames;

import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.HugCred;
import com.CEliconValley.models.*;

import com.CEliconValley.models.buildings.marketplaces.items.MarketplaceItems;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.locations.Farm;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class FriendshipController {
    public static void dailyUpdate(){
        ArrayList<Friendship> friendshipsList = new ArrayList<>();
        for(Player player : App.getGame().getPlayers()){
            for(Friendship friendship : player.getFriendships()){
                if(!friendshipsList.contains(friendship)){
                    friendshipsList.add(friendship);
                    friendship.dailyUpdate();
                }
            }
        }
    }
    public Result friendshipsList(Matcher matcher) {
        StringBuilder result = new StringBuilder();
        result.append("Friendships:\n");
        for(Friendship friendship : App.getGame().getCurrentPlayer().getFriendships()){
            result.append(friendship.showResult(App.getGame().getCurrentPlayer()));
        }
        result.delete(result.length() - 1, result.length());
        return new Result(true, result.toString());
    }
    public Result talk(Matcher matcher, String playername) {
        String username = matcher.group("username");
        String message = matcher.group("message");
        Player player = Finder.getPlayerByUsername(playername);
        Player player2 = Finder.findPlayerByUsername(username);
        if(player2 == null){
            return new Result(false, "Player not found");
        }
        ArrayList<String> userMessage = new ArrayList<>();
        userMessage.add(player.getUser().getUsername());
        userMessage.add(message);
        Friendship friendship = player.findFriendship(player2);
        friendship.addTalk(userMessage);
        friendship.talk();
        friendship.interact();
        friendship.increaseLevel(player);
        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
            new GameCommand("mention", "you got mention in private chat"));
        App.getServer().sendToPlayer(player2, new Gson().toJson(msg));
        return new Result(true, "Message sent successfully!");
//        if(Math.abs(player.getX() - player2.getX()) <= 1 && Math.abs(player.getY() - player2.getY()) <= 1
//                && player.isPlayerIsInVillage() && player2.isPlayerIsInVillage()){
//        }
//        return new Result(false, "You should be next to each other!");
    }
    public Result talkHistory(Matcher matcher) {
        String username = matcher.group("username");
        Player player = App.getGame().getCurrentPlayer();
        Player player2 = Finder.findPlayerByUsername(username);
        if(player2 == null){
            return new Result(false, "Player not found!");
        }
        Friendship friendship = player.findFriendship(player2);
        return new Result(true, friendship.talksHistory());
    }
    public Result gift(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        String username = matcher.group("username");
        String itemName = matcher.group("item");
        int amount = Integer.parseInt(matcher.group("amount"));
        Item item = Finder.parseItem(itemName);
        if(item == null){
            return new Result(false, "Item not found");
        }
        Slot slot = player.getInventory().getSlotByItem(item);
        if(slot == null){
            return new Result(false, "Item not found in your inventory!");
        }
        if(slot.getQuantity() < amount){
            return new Result(false, "Not enough amount in your inventory!");
        }
        Player player2 = Finder.findPlayerByUsername(username);
        if(player2 == null){
            return new Result(false, "Player not found");
        }
        Friendship friendship = player.findFriendship(player2);
        if(friendship.getLevel() <= 0){
            return new Result(false, "You and " + player2.getUser().getUsername() + " are " + friendship.getFriendshipLevel().getName()
                    + ".\nYou should be at least friends to send gifts to each other!");
        }
        Gift gift = new Gift(player, player2, new Slot(slot.getItem(), amount));
        player.getInventory().removeFromInventory(slot.getItem(), amount);
        player2.getInventory().addToInventory(slot.getItem(), amount);
        player2.addNewGift(gift);
        player2.addReceivedGift(gift);
        player.addSendGift(gift);
        friendship.interact();
        friendship.increaseLevel(player);
        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
            new GameCommand("text-mention", "you got a " +
                item.getName()+" from "+player.getUser().getUsername()));
        App.getServer().sendToPlayer(player2, new Gson().toJson(msg));
        return new Result(true, "Gift sent successfully!");
//        if(Math.abs(player.getX() - player2.getX()) <= 1 && Math.abs(player.getY() - player2.getY()) <= 1
//                && player.isPlayerIsInVillage() && player2.isPlayerIsInVillage()){
//        }
//        return new Result(false, "You should be next to each other!");
    }
    public Result giftList(Matcher matcher) {
        StringBuilder result = new StringBuilder();
        int a = 1;
        for(Gift gift : App.getGame().getCurrentPlayer().getNewGifts()){
            result.append(a++).append(". ").append(gift.toString()).append("\n");
        }
        if(result.isEmpty()){
            result.append("No new gifts :(");
            return new Result(false, result.toString());
        } else{
            result.delete(result.length() - 1, result.length());
        }
        return new Result(true, result.toString());
    }
    public Result giftRate(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        int giftNumber = Integer.parseInt(matcher.group("giftNumber"));
        int rate = Integer.parseInt(matcher.group("rate"));
        if(rate <= 0 || rate > 5){
            return new Result(false, "Gift rate must be between 1 and 5");
        }
        if(giftNumber <= 0 || giftNumber > player.getNewGifts().size()){
            return new Result(false, "No gift found with that number");
        }
        Gift gift = player.getNewGifts().get(giftNumber - 1);
        player.removeNewGift(gift);
        player.findFriendship(gift.getFrom()).rateGift(rate, player);
        StringBuilder result = new StringBuilder();
        int a = 1;
        for(Gift gift1 : player.getNewGifts()){
            result.append(a++).append(". ").append(gift1.toString()).append("\n");
        }

        App.sendResult(new Result(true, "gift rated successfully"), playername);

        if(!result.isEmpty()){
            result.delete(result.length() - 1, result.length());
            return new Result(true, "Gift rated successfully. New Gits List:\n" + result.toString());
        }
        return new Result(true, "Gift rated successfully.");
    }
    public Result giftHistory(Matcher matcher) {
        String username = matcher.group("username");
        Player player = Finder.findPlayerByUsername(username);
        if(player == null){
            return new Result(false, "Player not found!");
        }
        StringBuilder result = new StringBuilder();
        for(Gift gift : App.getGame().getCurrentPlayer().getReceivedGifts()){
            if(gift.getFrom().equals(player)){
                result.append(gift).append("\n");
            }
        }
        for(Gift gift : App.getGame().getCurrentPlayer().getSendGifts()){
            if(gift.getTo().equals(player)){
                result.append(gift.toString2()).append("\n");
            }
        }
        if(result.isEmpty()){
            result.append("No gift history with ").append(username);
        } else{
            result.delete(result.length() - 1, result.length());
        }
        return new Result(true, result.toString());
    }
    public Result hug(Matcher matcher, String playername) {
        String username = matcher.group("username");
        Player player2 = Finder.findPlayerByUsername(username);
        Player player = Finder.getPlayerByUsername(playername);
        if(player2 == null){
            return new Result(false, "Player not found!");
        }
        Friendship friendship = player.findFriendship(player2);
        if(friendship.getLevel() <= 1){
            return new Result(false, "You and " + player2.getUser().getUsername() + " are " + friendship.getFriendshipLevel().getName()
                    + ".\nYou should be at least close friends to hug each other!");
        }
        friendship.hug();
        friendship.interact();
        friendship.increaseLevel(player);
        Result result =new Result(true, "You hugged " + player2.getUser().getUsername() + " =D");
        App.sendResult(result, playername);
        App.sendResult(result, username);
        int kir = 2;
        int otherKir = 2;
        if(player.getX() > player2.getX()){
            kir = 4;
            otherKir = 2;
        }else{
            kir = 2;
            otherKir = 4;
        }
        GameMessage<HugCred> msg1 = new GameMessage<>("hug", new HugCred(kir, true, playername));
        GameMessage<HugCred> msg2 = new GameMessage<>("hug", new HugCred(otherKir, false, playername));
        App.getServer().sendToPlayer(player , new Gson().toJson(msg1));
        App.getServer().sendToPlayer(player2 , new Gson().toJson(msg2));
        return result;
//        if(Math.abs(player.getX() - player2.getX()) <= 1 && Math.abs(player.getY() - player2.getY()) <= 1
//                && player.isPlayerIsInVillage() && player2.isPlayerIsInVillage()){
//        }
//        return new Result(false, "You should be next to each other!");
    }
    public Result flower(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        String username = matcher.group("username");
        Player player2 = Finder.findPlayerByUsername(username);
        if(player2 == null){
            return new Result(false, "Player not found!");
        }
        Slot slot = player.getInventory().getSlotByItem(MarketplaceItems.Bouquet);
        if(slot == null){
            return new Result(false, "Bouquet not found in your inventory!");
        }
        Friendship friendship = player.findFriendship(player2);
        if(friendship.getFriendshipXp() < 300){
            return new Result(false, "You are not enough friends to give flower.");
        }
//        String flowerName = matcher.group("flowerName");
//        Crop flower = Flower.parseItem(flowerName);
//        if(flower == null){
//            return new Result(false, "Flower not found!");
//        }
        player.getInventory().removeFromInventory(MarketplaceItems.Bouquet, 1);
        player2.getInventory().addToInventory(MarketplaceItems.Bouquet, 1);
        friendship.giveFlower();
        friendship.interact();
        friendship.increaseLevel(player);
        App.sendResult(new Result(true, "you received a flower from "+playername), username);
        return new Result(true, "Awww, you gave " + player2.getUser().getUsername() + " a bouquet :))");
    }
    public Result propose(Matcher matcher, String playername) {
        Player player = Finder.getPlayerByUsername(playername);
        String username = matcher.group("username");
        Player player2 = Finder.findPlayerByUsername(username);
        if(player2 == null){
            return new Result(false, "Player not found!");
        }
        if(!player.getUser().getGender().equals(Gender.Male)){
            return new Result(false, "I'm sorry but you can't propose, you should be male.");
        }
        if(player2.getUser().getGender().equals(Gender.Male)){
            return new Result(false, "I'm sorry but are you LGBTQ+ or smth?!");
        }
        Friendship friendship = player.findFriendship(player2);
        if(friendship.getFriendshipXp() < 400){
            return new Result(false, "You are not enough close yet to propose.");
        }
        Slot slot = player.getInventory().getSlotByItem(MarketplaceItems.WeddingRing);
        if(slot == null){
            return new Result(false, "You don't have a ring!");
        }
        friendship.propose(player);

        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
            new GameCommand("propose-mode", playername));
        App.getServer().sendToPlayer(player2 , new Gson().toJson(msg));

        return new Result(true, "You proposed " + player2.getUser().getUsername() + " :b");
//        if(Math.abs(player.getX() - player2.getX()) <= 1 && Math.abs(player.getY() - player2.getY()) <= 1
//                && player.isPlayerIsInVillage() && player2.isPlayerIsInVillage()){
//        }
//        return new Result(false, "You should be next to each other!");
    }
    public Result respond(Matcher matcher, String playername) {
        String username = matcher.group("username");
        Player player = Finder.getPlayerByUsername(playername);
        Player player2 = Finder.findPlayerByUsername(username);
        if(player2 == null){
            return new Result(false, "Player not found!");
        }
        String respond = matcher.group(1);
        if(respond == null){
            return new Result(false, "Invalid respond!");
        }
//        respond = respond.substring(1);
        Friendship friendship = player.findFriendship(player2);
        if(friendship.getProposer() == null){
            return new Result(false, "He didn't propose you yet O_o");
        }
        if(friendship.getProposer().equals(player)){
            return new Result(true, "Bro you wanna " + respond + " yourself XD");
        }
        if(respond.equals("reject")){
            friendship.reject();
            player2.setDepressionDaysLeft(7);
            player2.setEnergy(player.getEnergy() / 2);

            App.sendResult(new Result(false, playername+" rejected your offer"), username);
            App.sendResult(new Result(false, "you rejected the offer"), playername);

            return new Result(true, "You rejected " + player2.getUser().getUsername() + " ;((\npoor " + player2.getUser().getUsername() + " D:");
        }
        player.getInventory().addToInventory(MarketplaceItems.WeddingRing, 1);
        player2.getInventory().removeFromInventory(MarketplaceItems.WeddingRing, 1);
        friendship.marry();
        friendship.interact();
        friendship.increaseLevel(player);

        setupCutscene(player, player2);

        return new Result(true, "You are so great for each other!");
    }


    public void setupCutscene(Player bride, Player groom) {
        groom.setPlayerIsInVillage(true);
        bride.setPlayerIsInVillage(true);
        String groomName = groom.getUser().getUsername();
        String brideName = bride.getUser().getUsername();
        Gson gson = new Gson();
        GameMessage<GameCommand> response = new GameMessage<>("game-command",
            new GameCommand("go-to-village", ":)"));
        App.getServer().sendToPlayername(groom.getUser().getUsername(), gson.toJson(response));

        GameMessage<GameCommand> updateVillage = new GameMessage<>("game-command",
            new GameCommand("update-village", ":)"));
        App.getServer().sendToPlayername(groom.getUser().getUsername(), gson.toJson(updateVillage));
        GameMessage<GameCommand> response2 = new GameMessage<>("game-command",
            new GameCommand("go-to-village", ":)"));
        App.getServer().sendToPlayername(bride.getUser().getUsername(), gson.toJson(response2));

        GameMessage<GameCommand> updateVillage2 = new GameMessage<>("game-command",
            new GameCommand("update-village", ":)"));
        App.getServer().sendToPlayername(bride.getUser().getUsername(), gson.toJson(updateVillage2));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        GameMessage<GameCommand> left = new GameMessage<>("game-command",
            new GameCommand("go-left", ":)"));
        App.getServer().sendToPlayername(groom.getUser().getUsername(), gson.toJson(left));
        GameMessage<GameCommand> right = new GameMessage<>("game-command",
            new GameCommand("go-right", ":)"));
        App.getServer().sendToPlayername(bride.getUser().getUsername(), gson.toJson(right));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        GameMessage<GameCommand> propose = new GameMessage<>("game-command",
            new GameCommand("propose", ":)"));
        App.getServer().sendToPlayer(groom, gson.toJson(propose));
        GameMessage<GameCommand> otherpropose = new GameMessage<>("game-command",
            new GameCommand("other-propose", groom.getUser().getUsername()));
        App.getServer().sendToPlayer(bride, gson.toJson(otherpropose));

        App.sendResult(new Result(true, brideName+" accepted your offer"), brideName);
        App.sendResult(new Result(true, "you are now married to "+groomName), groomName);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        GameMessage<GameCommand> kiss = new GameMessage<>("game-command",
            new GameCommand("kiss", ":)"));
        GameMessage<GameCommand> otherkiss = new GameMessage<>("game-command",
            new GameCommand("other-kiss", brideName));
        App.getServer().sendToPlayer(bride, gson.toJson(kiss));
        App.getServer().sendToPlayer(groom, gson.toJson(otherkiss));

    }

    public Result goToFarm(Matcher matcher){
        String username = matcher.group(1).trim();
        Player to = App.getGame().getPlayerByUsername(username);
        if(to == null){
            return new Result(false, "Player not found!");
        }
        Player player = App.getGame().getCurrentPlayer();
        if(to.equals(player)){
            setFarm(player);
            return new Result(true,"returning to your farm..");
        }
        for (Friendship friendship : player.getFriendships()) {
            if(friendship.getPlayer1().equals(player)){
                if(friendship.getPlayer2().equals(to)){
                    System.out.println("going to new farm..");
                    if(friendship.isAreMarried()){
                        setFarm(to);
                        return new Result(true,"going to "+to.getUser().getUsername()+" farm.");
                    }else{
                        return new Result(false,"your not married");
                    }
                }
            }else{
                if(friendship.getPlayer1().equals(to)){
                    System.out.println("going to new farm..");
                    if(friendship.isAreMarried()){
                        setFarm(to);
                        return new Result(true,"going to "+to.getUser().getUsername()+" farm.");
                    }else{
                        return new Result(false,"your not married");
                    }
                }
            }
        }
        return new Result(false,"smth went wrong");
    }

    private void setFarm(Player which){
        for (Farm farm : App.getGame().getFarms()) {
            if(farm.getId() == which.getFarmId()){
                App.getGame().setCurrentFarmId(farm.getId(), which);
                which.setX(farm.getStartPoints().get(0).getX());
                which.setY(farm.getStartPoints().get(0).getY());
            }
        }

    }
}
