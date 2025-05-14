package org.example.models;

import java.util.ArrayList;

public class Friendship {
    private final Player player1;
    private final Player player2;
    private FriendshipLevel friendshipLevel;
    private int level;
    private int friendshipXp;
    private final ArrayList<ArrayList<String>> talks = new ArrayList<>();
    private int lastReadMessage = 0;
    private boolean hadInteractionToday = false;
    private boolean hadTalkingToday = false;
    private boolean hadHugToday = false;

    public Friendship(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        friendshipLevel = FriendshipLevel.Stranger;
        level = 0;
        friendshipXp = 0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public FriendshipLevel getFriendshipLevel() {
        return friendshipLevel;
    }

    public void setFriendshipLevel(FriendshipLevel friendshipLevel) {
        this.friendshipLevel = friendshipLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFriendshipXp() {
        return friendshipXp;
    }

    public void setFriendshipXp(int friendshipXp) {
        this.friendshipXp = friendshipXp;
    }

    public int getTotalFriendshipXp() {
        return friendshipXp + 100 * ((level) * (level + 1) / 2);
    }

    public void increaseLevel(Player player) {
        if(friendshipXp >= 100 * (level + 1)){
            friendshipXp -= 100 * (level + 1);
            String playerName = player1.getUser().getUsername();
            if(playerName.equals(player.getUser().getUsername())){
                playerName = player2.getUser().getUsername();
            }
            for(FriendshipLevel friendshipLevel : FriendshipLevel.values()){
                if(friendshipLevel.getLevel() == level){
                    this.friendshipLevel = friendshipLevel;
                    level++;
                }
            }
            System.out.println("You and " + playerName + " are now " + friendshipLevel.getName());
        }
    }
    public void decreaseLevel(Player player) {
        if(friendshipXp <= 0 && level >= 0){
            level--;
            for(FriendshipLevel friendshipLevel : FriendshipLevel.values()){
                if(friendshipLevel.getLevel() == level){
                    this.friendshipLevel = friendshipLevel;
                }
                if(level >= 0){
                    int xp = -friendshipXp;
                    friendshipXp = 100 * (level + 1) - xp;
                }
            }
            String playerName = player1.getUser().getUsername();
            if(playerName.equals(player.getUser().getUsername())){
                playerName = player2.getUser().getUsername();
            }
            System.out.println("You and " + playerName + " are now " + friendshipLevel.getName());
        }
    }
    public void addTalk(ArrayList<String> talk) {
        talks.add(talk);
    }
    public ArrayList<ArrayList<String>> getTalks() {
        return talks;
    }
    public void setLastReadMessage() {
        lastReadMessage = talks.size();
    }
    public void rateGift(int rate, Player player) {
        int xp = (rate - 3) * 30 + 15;
        friendshipXp += xp;
        if(xp >= 0){
            increaseLevel(player);
        } else{
            decreaseLevel(player);
        }
    }
    public void interact() {
        hadInteractionToday = true;
    }
    public void talk() {
        if(!hadTalkingToday){
            friendshipXp += 20;
        }
        hadTalkingToday = true;
    }
    public void hug() {
        if(!hadHugToday){
            friendshipXp += 60;
        }
        hadHugToday = true;
    }
    public void dailyUpdate() {
        if(!hadInteractionToday) {
            if(friendshipXp == 0 && level >= 0){
                level--;
                for(FriendshipLevel friendshipLevel : FriendshipLevel.values()){
                    if(friendshipLevel.getLevel() == level){
                        this.friendshipLevel = friendshipLevel;
                    }
                    if(level >= 0){
                        friendshipXp = 100 * (level + 1) - 10;
                    }
                }
                System.out.println(player1.getUser().getUsername() + " and " + player2.getUser().getUsername() + " are now " + friendshipLevel.getName());
            }
        }

        hadInteractionToday = false;
        hadTalkingToday = false;
        hadHugToday = false;
    }

    public String showResult(Player player) {
        StringBuilder result = new StringBuilder();
        String playerName = player1.getUser().getUsername();
        if(player.equals(player1)){
            playerName = player2.getUser().getUsername();
        }
        result.append("You and ").append(playerName).append(" are ").append(friendshipLevel.getName())
                .append(". Friendship Level: ").append(level).append(", XP:").append(friendshipXp).append("\n");
        return result.toString();
    }
    public String talksHistory() {
        StringBuilder result = new StringBuilder();
        result.append("Talk History:\n");
        if(talks.isEmpty()){
            return "";
        }
        for(ArrayList<String> talk : talks){
            result.append(talk.getFirst()).append(": ").append(talk.get(1)).append("\n");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }
    public String newMessages(){
        StringBuilder result = new StringBuilder();
        if(talks.isEmpty()){
            return "";
        }
        for(int i = lastReadMessage; !talks.isEmpty() && i < talks.size(); i++){
            result.append(talks.get(i).getFirst()).append(": ").append(talks.get(i).get(1)).append("\n");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }
}
