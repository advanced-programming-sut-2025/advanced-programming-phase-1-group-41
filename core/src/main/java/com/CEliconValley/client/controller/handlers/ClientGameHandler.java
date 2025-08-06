package com.CEliconValley.client.controller.handlers;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.LobbyScreen;
import com.CEliconValley.client.view.screen.FarmScreen;
import com.CEliconValley.common.GameData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Menu;
import com.CEliconValley.models.Player;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class ClientGameHandler {
    public static void handle(String type, JsonObject body, Gson gson, long timestamp) {
        switch (type) {
            case "game-data" -> {
                long now = System.currentTimeMillis();
                long sent = timestamp;
                System.out.println("Latency: " + (now - sent) + "ms");
                GameData gamedata = gson.fromJson(body, GameData.class);
                Gdx.app.postRunnable(() -> {
                    AppClient.setGameData(gamedata);
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof FarmScreen fs){
                        fs.getTimeScreen().updatePointer(AppClient.getGameData().getTime().getHour(),
                            AppClient.getGameData().getTime().convertDay()
                            ,AppClient.getGameData().getTime().getYear(),
                            Finder.getpd().getMoney()
                            );
                        fs.getTimeScreen().updateWeatherAndSeason(AppClient.getGameData().getTime(), AppClient.getGameData().getWeatherType());
                        fs.updateFarmData();

                        PlayerData pd = null;
                        for (PlayerData playersDatum : AppClient.getGameData().getPlayersData()) {
                            if(playersDatum.getUsername().equals(AppClient.getUserData().getUsername())){
                                pd = playersDatum;
                                break;
                            }
                        }
                        int clientX = fs.getHero().playerX.get();
                        int clientY = fs.getHero().playerY.get();
                        int serverX = pd.getX();
                        int serverY = pd.getY();

                        int dx = Math.abs(clientX - serverX);
                        int dy = Math.abs(clientY - serverY);

                        if (dx > 1 || dy > 1) {
                            PosDiff posDiff = new PosDiff(AppClient.getUserData().getUsername(), clientX, clientY);
                            AppClient.getClient().send(new Gson().toJson(new GameMessage<>("game-command", posDiff)));
                            System.out.println("big difference:");
                            System.out.println("client: "+fs.getHero().playerX+" "+fs.getHero().playerY);
                            System.out.println("server: "+pd.getX()+" "+pd.getY());
                        } else {
                            System.out.println("small difference:");
                            System.out.println("client: "+fs.getHero().playerX+" "+fs.getHero().playerY);
                            System.out.println("server: "+pd.getX()+" "+pd.getY());
                        }
                    }
                });
            }
            case "new-game" -> {
                GameData gamedata = gson.fromJson(body, GameData.class);
                Gdx.app.postRunnable(() -> {
                    Menu.Game.resetMenu();
                    AppClient.setMenu(Menu.Game);
                    AppClient.setGameData(gamedata);
                    Player player = gamedata.getPlayersData().get(0).getPlayer();
                    ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new FarmScreen(
                        Finder.getFarmDataById(AppClient.getGameData(), AppClient.getUserData().getUsername()).getFarm(player), player
                    ));
                });
            }
            case "pre-start-request" -> {
                PreStartRequest preStartRequest = gson.fromJson(body, PreStartRequest.class);
                if (AppClient.getMenu().getScreen() instanceof LobbyScreen view) {
                    GameMessage<PreStartResponse> response = new GameMessage<>("pre-start-response",
                        new PreStartResponse(AppClient.getUserData().getUsername(),
                            view.getFarmType()));
                    AppClient.getClient().send(gson.toJson(response));
                }
            }
        }
    }
}
