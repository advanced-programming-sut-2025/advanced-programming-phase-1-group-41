package com.CEliconValley.client.controller.handlers;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.LobbyScreen;
import com.CEliconValley.client.view.screen.FarmScreen;
import com.CEliconValley.common.GameData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.PreStartRequest;
import com.CEliconValley.common.messages.PreStartResponse;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Menu;
import com.CEliconValley.models.Player;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class ClientGameHandler {
    public static void handle(String type, String message, Gson gson) {
        switch (type) {
            case "game-data" -> {
                GameMessage<GameData> gameDataMessage = gson.fromJson(message, new TypeToken<GameMessage<GameData>>() {
                }.getType());
                Gdx.app.postRunnable(() -> {
                    AppClient.setGameData(gameDataMessage.body);
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof FarmScreen fs){
                        fs.getTimeScreen().updatePointer(AppClient.getGameData().getTime().getHour());
                        fs.updateFarmData();
                        PlayerData pd = null;
                        for (PlayerData playersDatum : AppClient.getGameData().getPlayersData()) {
                            if(playersDatum.getUsername().equals(AppClient.getUserData().getUsername())){
                                pd = playersDatum;
                                break;
                            }
                        }
                        if(pd.getX() != fs.getHero().playerX.get() || pd.getY() != fs.getHero().playerY.get()){
                            System.out.println("client: "+fs.getHero().playerX+" "+fs.getHero().playerY);
                            System.out.println("server: "+pd.getX()+" "+pd.getY());
                            fs.getHero().playerX.set(pd.getX());
                            fs.getHero().playerY.set(pd.getY());
                            fs.getHero().targetX.set(pd.getX());
                            fs.getHero().targetY.set(pd.getY());
                            fs.getHero().renderX = fs.getHero().targetX.get() * CELL_SIZE;
                            fs.getHero().renderY = fs.getHero().targetY.get() * CELL_SIZE;
                            fs.getHero().isMoving.set(false);
                            fs.getHero().isActing.set(false);
                            System.out.println("after: "+fs.getHero().playerX+" "+fs.getHero().playerY);
                        }
                    }
                });
//                System.out.println("Cmessage: updated gamedata");
//                System.out.println("    time: " + gameDataMessage.body.getTime());
            }
            case "new-game" -> {
                GameMessage<GameData> gameDataMessage = gson.fromJson(message, new TypeToken<GameMessage<GameData>>() {
                }.getType());
                Gdx.app.postRunnable(() -> {
                    Menu.Game.resetMenu();
                    AppClient.setMenu(Menu.Game);
                    AppClient.setGameData(gameDataMessage.body);
                    Player player = gameDataMessage.body.getPlayersData().get(0).getPlayer();
                    ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new FarmScreen(
                        Finder.getFarmDataById(AppClient.getGameData(), AppClient.getUserData().getUsername()).getFarm(player), player
                    ));
                });
            }
            case "pre-start-request" -> {
                GameMessage<PreStartRequest> gameMessage = gson.fromJson(message, new TypeToken<GameMessage<PreStartRequest>>() {
                }.getType());
                if (AppClient.getMenu().getScreen() instanceof LobbyScreen view) {
                    GameMessage<PreStartResponse> response = new GameMessage<>("pre-start-response",
                        new PreStartResponse(AppClient.getUserData().getUsername(),
                            view.getFarmType()));
                    AppClient.getClient().send(gson.toJson(response));
                }
                System.out.println("CMessage " + message);
            }
            case "game-command" -> {
//                GameMessage<GameCommand> gameMessage = gson.fromJson(message, new TypeToken<GameMessage<GameCommand>>() {
//                }.getType());
//                System.out.println("going to receive walk up");
//                if (((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof GameScreen view) {
//                    if (gameMessage.body.command.equals("walk up")) {
//                        System.out.println("received walk up");
//                        view.getHero().targetY = view.getHero().playerY + 1;
//                        view.getHero().targetX = view.getHero().playerX;
////                        if(!view.getHero().isMoving.get()){
////                            view.getHero().currentAnimation = view.getHero().walk(true, view.getHero().currentDirection);
////                        }
//                        view.getHero().isMoving.set(true);
//                    }
//                }


            }
        }
    }
}
