package org.example.controllers.subgames;

import org.example.models.*;
import org.example.models.buildings.marketplaces.items.MarketplaceItems;
import org.example.models.items.Item;
import org.example.models.items.Slot;
import org.example.models.locations.Farm;

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
    public Result talk(Matcher matcher) {
        String username = matcher.group("username");
        String message = matcher.group("message");
        Player player = App.getGame().getCurrentPlayer();
        Player player2 = Finder.findPlayerByUsername(username);
        if(player2 == null){
            return new Result(false, "Player not found");
        }
        if(Math.abs(player.getX() - player2.getX()) <= 1 && Math.abs(player.getY() - player2.getY()) <= 1
                && player.isPlayerIsInVillage() && player2.isPlayerIsInVillage()){
            ArrayList<String> userMessage = new ArrayList<>();
            userMessage.add(player.getUser().getUsername());
            userMessage.add(message);
            Friendship friendship = player.findFriendship(player2);
            friendship.addTalk(userMessage);
            friendship.talk();
            friendship.interact();
            friendship.increaseLevel(player);
            return new Result(true, "Message sent successfully!");
        }
        return new Result(false, "You should be next to each other!");
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
    public Result gift(Matcher matcher) {
        String username = matcher.group("username");
        String itemName = matcher.group("item");
        int amount = Integer.parseInt(matcher.group("amount"));
        Item item = Finder.parseItem(itemName);
        if(item == null){
            return new Result(false, "Item not found");
        }
        Slot slot = App.getGame().getCurrentPlayer().getInventory().getSlotByItem(item);
        if(slot == null){
            return new Result(false, "Item not found in your inventory!");
        }
        if(slot.getQuantity() < amount){
            return new Result(false, "Not enough amount in your inventory!");
        }
        Player player = App.getGame().getCurrentPlayer();
        Player player2 = Finder.findPlayerByUsername(username);
        if(player2 == null){
            return new Result(false, "Player not found");
        }
        Friendship friendship = player.findFriendship(player2);
        if(friendship.getLevel() <= 0){
            return new Result(false, "You and " + player2.getUser().getUsername() + " are " + friendship.getFriendshipLevel().getName()
                    + ".\nYou should be at least friends to send gifts to each other!");
        }
        if(Math.abs(player.getX() - player2.getX()) <= 1 && Math.abs(player.getY() - player2.getY()) <= 1
                && player.isPlayerIsInVillage() && player2.isPlayerIsInVillage()){
            Gift gift = new Gift(player, player2, new Slot(slot.getItem(), amount));
            player.getInventory().removeFromInventory(slot.getItem(), amount);
            player2.getInventory().addToInventory(slot.getItem(), amount);
            player2.addNewGift(gift);
            player2.addReceivedGift(gift);
            player.addSendGift(gift);
            friendship.interact();
            friendship.increaseLevel(player);
            return new Result(true, "Gift sent successfully!");
        }
        return new Result(false, "You should be next to each other!");
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
    public Result giftRate(Matcher matcher) {
        int giftNumber = Integer.parseInt(matcher.group("giftNumber"));
        int rate = Integer.parseInt(matcher.group("rate"));
        if(rate <= 0 || rate > 5){
            return new Result(false, "Gift rate must be between 1 and 5");
        }
        Player player = App.getGame().getCurrentPlayer();
        if(giftNumber <= 0 || giftNumber > player.getNewGifts().size()){
            return new Result(false, "No gift found with that number");
        }
        Gift gift = player.getNewGifts().get(giftNumber - 1);
        player.removeNewGift(gift);
        App.getGame().getCurrentPlayer().findFriendship(gift.getFrom()).rateGift(rate, player);
        StringBuilder result = new StringBuilder();
        int a = 1;
        for(Gift gift1 : App.getGame().getCurrentPlayer().getNewGifts()){
            result.append(a++).append(". ").append(gift1.toString()).append("\n");
        }
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
    public Result hug(Matcher matcher) {
        String username = matcher.group("username");
        Player player2 = Finder.findPlayerByUsername(username);
        Player player = App.getGame().getCurrentPlayer();
        if(player2 == null){
            return new Result(false, "Player not found!");
        }
        Friendship friendship = player.findFriendship(player2);
        if(friendship.getLevel() <= 1){
            return new Result(false, "You and " + player2.getUser().getUsername() + " are " + friendship.getFriendshipLevel().getName()
                    + ".\nYou should be at least close friends to hug each other!");
        }
        if(Math.abs(player.getX() - player2.getX()) <= 1 && Math.abs(player.getY() - player2.getY()) <= 1
                && player.isPlayerIsInVillage() && player2.isPlayerIsInVillage()){
            friendship.hug();
            friendship.interact();
            friendship.increaseLevel(player);
            return new Result(true, "You hugged " + player2.getUser().getUsername() + " =D");
        }
        return new Result(false, "You should be next to each other!");
    }
    public Result flower(Matcher matcher) {
        String username = matcher.group("username");
        Player player = App.getGame().getCurrentPlayer();
        Player player2 = Finder.findPlayerByUsername(username);
        if(player2 == null){
            return new Result(false, "Player not found!");
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
        Slot slot = App.getGame().getCurrentPlayer().getInventory().getSlotByItem(MarketplaceItems.Bouquet);
        if(slot == null){
            return new Result(false, "Bouquet not found in your inventory!");
        }
        if(Math.abs(player.getX() - player2.getX()) <= 1 && Math.abs(player.getY() - player2.getY()) <= 1
                && player.isPlayerIsInVillage() && player2.isPlayerIsInVillage()){
            player.getInventory().removeFromInventory(MarketplaceItems.Bouquet, 1);
            player2.getInventory().addToInventory(MarketplaceItems.Bouquet, 1);
            friendship.giveFlower();
            friendship.interact();
            friendship.increaseLevel(player);
            return new Result(true, "Awww, you gave " + player2.getUser().getUsername() + " a bouquet :))");
        }
        return new Result(false, "You should be next to each other!");
    }
    public Result propose(Matcher matcher) {
        String username = matcher.group("username");
        Player player = App.getGame().getCurrentPlayer();
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
        Slot slot = App.getGame().getCurrentPlayer().getInventory().getSlotByItem(MarketplaceItems.WeddingRing);
        if(slot == null){
            return new Result(false, "Bouquet not found in your inventory!");
        }
        if(Math.abs(player.getX() - player2.getX()) <= 1 && Math.abs(player.getY() - player2.getY()) <= 1
                && player.isPlayerIsInVillage() && player2.isPlayerIsInVillage()){
            friendship.propose(player);
            return new Result(true, "You proposed " + player2.getUser().getUsername() + " :b");
        }
        return new Result(false, "You should be next to each other!");
    }
    public Result respond(Matcher matcher) {
        String username = matcher.group("username");
        Player player = App.getGame().getCurrentPlayer();
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
            return new Result(true, "You rejected " + player2.getUser().getUsername() + " ;((\npoor " + player2.getUser().getUsername() + " D:");
        }
        player.getInventory().addToInventory(MarketplaceItems.WeddingRing, 1);
        player2.getInventory().removeFromInventory(MarketplaceItems.WeddingRing, 1);
        friendship.marry();
        friendship.interact();
        friendship.increaseLevel(player);
        return new Result(true, "You are so great for each other!");
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
                which.setX(farm.getStartPoints().getFirst().getX());
                which.setY(farm.getStartPoints().getFirst().getY());
            }
        }

    }
}
