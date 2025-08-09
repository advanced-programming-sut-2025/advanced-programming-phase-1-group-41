package com.CEliconValley.client.model;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.List;

public class StrategyScoreboard {
    int strategyScore = 0;

    public void setStrategyScore(int strategyScore) {
        this.strategyScore = strategyScore;
    }

    public void updateScoreboard(Table scoreboardInfoTable) {
        if(strategyScore == 0){
            updateByMoney(scoreboardInfoTable);
        }else if(strategyScore == 1){
            updateByMissions(scoreboardInfoTable);
        }else if(strategyScore == 2){
            updateBySkillLevels(scoreboardInfoTable);
        }
    }

    private void updateByMoney(Table scoreboardInfoTable){

        ArrayList<PlayerData> players = AppClient.getGameData().getPlayersData();
        List<PlayerData> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort((p1, p2) -> Double.compare(p2.getMoney(), p1.getMoney()));
        update(scoreboardInfoTable, sortedPlayers);
    }

    private void updateByMissions(Table scoreboardInfoTable){
        ArrayList<PlayerData> players = AppClient.getGameData().getPlayersData();
        List<PlayerData> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort((p1 , p2) -> Integer.compare(p2.getQuestsFinsihed(), p1.getQuestsFinsihed()));
        update(scoreboardInfoTable, sortedPlayers);
    }
    private void updateBySkillLevels(Table scoreboardInfoTable){
        ArrayList<PlayerData> players = AppClient.getGameData().getPlayersData();
        List<PlayerData> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort((p1 , p2) -> Double.compare(p2.getPlayerSkills(), p1.getPlayerSkills()));
        update(scoreboardInfoTable, sortedPlayers);
    }



    private void update(Table scoreboardInfoTable, List<PlayerData> sortedPlayers) {
        System.out.println("count "+sortedPlayers.size());
        scoreboardInfoTable.clear();
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        scoreboardInfoTable.add(new Label("kir mikham", skin));

        for (PlayerData player : sortedPlayers) {
            double playerMoney = player.getMoney();
            int playerMissions = player.getQuestsFinsihed();
            double playerSkills = (player.getFarmingSkill().getLevel()
                + player.getFishingSkill().getLevel()
                + player.getForagingSkill().getLevel()
                + player.getMiningSkill().getLevel()) / 4d;

            Label nameLabel = new Label(player.getUsername(), skin);
            assert AppClient.getUserData() != null;
            Label moneyLabel = new Label(String.valueOf((int) Math.round(playerMoney)), skin);
            Label missionsLabel = new Label(String.valueOf(playerMissions), skin);
            Label skillsLabel = new Label(String.valueOf((int) Math.round(playerSkills)), skin);
            if(player.getUsername().equals(AppClient.getUserData().getUsername())){
                nameLabel.setText("-> " + player.getUsername());
                nameLabel.setColor(Color.LIGHT_GRAY);
                moneyLabel.setColor(Color.LIGHT_GRAY);
                missionsLabel.setColor(Color.LIGHT_GRAY);
                skillsLabel.setColor(Color.LIGHT_GRAY);
            }

            scoreboardInfoTable.add(nameLabel).pad(5);
            scoreboardInfoTable.add(moneyLabel).pad(5);
            scoreboardInfoTable.add(missionsLabel).pad(5);
            scoreboardInfoTable.add(skillsLabel).pad(5);
            scoreboardInfoTable.row();
        }
    }
}
