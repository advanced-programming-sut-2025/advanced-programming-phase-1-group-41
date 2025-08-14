package com.CEliconValley.client.controller.handlers;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.model.NPCSprite;
import com.CEliconValley.client.model.PlayerSprite;
import com.CEliconValley.client.view.LobbyScreen;
import com.CEliconValley.client.view.screen.*;
import com.CEliconValley.common.*;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.*;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.Objects;

import static com.CEliconValley.client.view.screen.VillageScreen.CELL_SIZE;

public class ClientGameHandler {
    public static void handle(String type, JsonObject body, Gson gson, long timestamp) {
        if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
            .getScreen()  instanceof GameScreen gs){
            if(gs.isGameFinished) return;
        }
        Gdx.app.postRunnable(() -> {
            if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                .getScreen()  instanceof GameScreen gs){
                if(gs instanceof FarmScreen fs){
                    fs.getInventoryRenderer().updateInventory();
                }
                if (gs instanceof BarnScreen barnScreen) {
                    barnScreen.updateAnimalSprites(Finder.getbdByid(barnScreen.getId()));
                } else if (gs instanceof CoopScreen coopScreen) {
                    coopScreen.updateAnimalSprites(Finder.getcdByid(coopScreen.getId()));
                }
                updateTime(gs);
                gs.updateChat();
//                gs.friendshipStageHandler.updateChat();
                if(gs instanceof VillageScreen vs){
                    vs.updatePlayers();
                    vs.villageMap.updateVillageData();
                }
                }
        });
        switch (type) {
            case "npc-data" -> {
                NPCData npcData = gson.fromJson(body, NPCData.class);
                for (int i = 0; i < AppClient.getGameData().getVillageData().getNPCsData().size(); i++) {
                    NPCData npcd = AppClient.getGameData().getVillageData().getNPCsData().get(i);
                    if(npcd.getName().equals(npcData.getName())){
                        AppClient.getGameData().getVillageData().getNPCsData().set(i, npcData);
                    }
                }
            }
            case "player-data" -> {
                PlayerData playerData = gson.fromJson(body, PlayerData.class);
                if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                    .getScreen() instanceof GameScreen gs){
                    if(gs.isGameFinished) return;
                }
                Gdx.app.postRunnable(() -> {
                    for (int i = 0; i < AppClient.getGameData().getPlayersData().size(); i++) {
                        PlayerData pd = AppClient.getGameData().getPlayersData().get(i);
                        if(pd.getUsername().equals(playerData.getUsername())) {
                            AppClient.getGameData().getPlayersData().set(i, playerData);
//                            System.out.println("updating "+playerData.getUsername());
                            break;
                        }
                    }
                    });
            }
            case "farm-data" -> {
                FarmData farmData = gson.fromJson(body, FarmData.class);
                if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                    .getScreen() instanceof GameScreen gs){
                    if(gs.isGameFinished) return;
                }
                Gdx.app.postRunnable(() -> {
                    for (int i = 0; i < AppClient.getGameData().getFarmsData().size(); i++) {
                        FarmData fd = AppClient.getGameData().getFarmsData().get(i);
                        if(fd.getId() == farmData.getId()){
                            AppClient.getGameData().getFarmsData().set(i, farmData);
                            System.out.println("updating farm "+i);
                            break;
                        }
                    }
                    if(Finder.getfd().getId() == farmData.getId()){
                        if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                            .getScreen() instanceof FarmScreen fs){
                            if(fs.isGameFinished) return;
                            fs.updateFarmData();
                            System.out.println("updated farmdata");
                        }
                    }
                });
            }
            case "game-data" -> {
                long now = System.currentTimeMillis();
                long sent = timestamp;
                System.out.println("Latency: " + (now - sent) + "ms");
                GameData gamedata = gson.fromJson(body, GameData.class);
                Gdx.app.postRunnable(() -> {
                    AppClient.setGameData(gamedata);
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof GameScreen gs){
                        gs.updateScoreboard();
                        if(gs instanceof VillageScreen vs){
                            vs.updatenpcData();
                        }
                    }
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof FarmScreen fs){
                        if(fs.isGameFinished) return;
                        updateTime(fs);
                        fs.updateFarmData();
                        PlayerData pd = null;
                        for (PlayerData playersDatum : AppClient.getGameData().getPlayersData()) {
                            if(playersDatum.getUsername().equals(AppClient.getUserData().getUsername())){
                                pd = playersDatum;
                                break;
                            }
                        }
                        System.out.println("current tool : "+pd.getCurrentToolName());
                        int clientX = fs.getHero().playerX.get();
                        int clientY = fs.getHero().playerY.get();
                        int serverX = pd.getX();
                        int serverY = pd.getY();

                        int dx = Math.abs(clientX - serverX);
                        int dy = Math.abs(clientY - serverY);

                        if (dx > 0 || dy > 0) {
                            PosDiff posDiff = new PosDiff(AppClient.getUserData().getUsername(), clientX, clientY);
                            AppClient.getClient().send(new Gson().toJson(new GameMessage<>("pos-diff", posDiff)));
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
                    Player player = Finder.getpd().getPlayer();
                    System.out.println("farid "+player.getFarmId());
                    FarmScreen farmScreen = new FarmScreen(
                        Finder.getFarmDataById(AppClient.getGameData(), AppClient.getUserData().getUsername()).getFarm(player), player
                    );
                    ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(farmScreen);
                    if(player.isPlayerIsInVillage()){
                        PlayerActs.changeScreen(new VillageScreen(Objects.requireNonNull(Finder.getpd()).getPlayer(), farmScreen));
                    }
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
            case "new-vote" -> {
                VoteMessage vote = gson.fromJson(body, VoteMessage.class);
                if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof GameScreen screen){
                    screen.handleVote(screen.getStage(), vote.target);
                }
            }
            case "rock-anime" -> {
                BreakAnimCred cred = gson.fromJson(body, BreakAnimCred.class);
                CellData cellData = cred.cellData;
                if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                    .getScreen()  instanceof GameScreen gs){
                    if(gs instanceof FarmScreen screen){
                        // todo shahab break animation
                    }
                }

            }
            case "message-cred" -> {
                if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                    .getScreen()  instanceof GameScreen gs){
                    MessageCred cred = gson.fromJson(body, MessageCred.class);
                    AppClient.getGameData().setPlayerMessages(cred.playerMessages);
                    gs.updateChat();
                }
            }
            case "hug" -> {
                if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                    .getScreen()  instanceof GameScreen gs){
                    HugCred cred = gson.fromJson(body, HugCred.class);
                    if(cred.straight){
                        gs.getHero().currentDirection = cred.direction;
                        gs.getHero().currentAnimation = gs.getHero().hug();
                        gs.getHero().stateTime = 0;
                        gs.onRepeat = false;
                        gs.getHero().isActing.set(true);
                    }else{
                        if(gs instanceof VillageScreen vs){
                            for (PlayerSprite playerSprite : vs.playerSprites) {
                                if(playerSprite.getPlayerData().getUsername().equals(cred.name)){
                                    playerSprite.currentDirection = cred.direction;
                                    playerSprite.currentAnimation = gs.getHero().hug();
                                    playerSprite.stateTime = 0;
                                    playerSprite.isActing = true;
                                    System.out.println("setting the actor");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            case "game-result" -> {
                ResultSender result = gson.fromJson(body, ResultSender.class);
                System.out.println("Expected Result: "+result.message);
                if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof GameScreen screen){
                    System.out.println(result);
                    Gdx.app.postRunnable(()->{
                        if(screen.friendshipMode){
                            screen.friendshipStageHandler.setMessage(result);
                            System.out.println(result);
                        }
                    });
                }
            }
            case "game-command" -> {
                GameCommand gamecommand = gson.fromJson(body, GameCommand.class);
                System.out.println("received a command "+gamecommand.command);
                if(gamecommand.command.equals("update-vote")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof GameScreen screen){
                        screen.howManyVotedLabel.setText("Vote: "+gamecommand.playerName+" / "+AppClient.getGameData().getPlayersData().size());
                    }
                }else if(gamecommand.command.equals("update-ter")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof GameScreen screen){
                        screen.terhowmanyLabel.setText("Vote: "+gamecommand.playerName+" / "+AppClient.getGameData().getPlayersData().size());
                    }
                }else if(gamecommand.command.equals("dc-game")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof GameScreen screen){
                        screen.handledc(screen.getStage());
                    }
                }else if(gamecommand.command.equals("resume-game")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof GameScreen screen){
                        screen.setDcmode(false);
                        screen.dcLabel.setVisible(false);
                        screen.overlay.addAction(Actions.sequence(
                            Actions.fadeOut(0.5f),
                            Actions.run(() -> screen.overlay.remove())
                        ));

                        if (screen.overlay != null) {
                            screen.overlay.remove();
                            screen.overlay = null;
                        }
                    }
                }else if(gamecommand.command.equals("mention")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof GameScreen screen){
                        screen.updateTagMessage("you got mentioned!");
                        new Timer().schedule(new Timer.Task() {

                            @Override
                            public void run() {
                                screen.removeTagMessage();
                            }
                        }, 5);
                    }
                }else if(gamecommand.command.equals("npc-dialogue")){
                    String npcName = gamecommand.playerName;
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof VillageScreen screen){
                        System.out.println("received dialogue for "+npcName);
                        for (NPCSprite npcSprite : screen.npcSprites) {
                            if(npcSprite.getNPCData().getName().equals(npcName)){
                                System.out.println("set it here");
                                screen.dialogueAnimation = npcSprite.dialogue();
                                screen.dialogueStatetime = 0f;
                                screen.isDialogue = true;
                                break;
                            }
                        }
                    }
                }
                else if(gamecommand.command.equals("text-mention")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof GameScreen screen){
                        screen.updateTagMessage(gamecommand.playerName);
                        new Timer().schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                screen.removeTagMessage();
                            }
                        }, 5);
                    }
                }
                else if(gamecommand.command.equals("go-to-village")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof FarmScreen screen){
                        Gdx.app.postRunnable(()->{
                            PlayerActs.changeScreen(new VillageScreen(Objects.requireNonNull(Finder.getpd()).getPlayer(), screen));
                        });
                    }
                }else if(gamecommand.command.equals("go-to-farm")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof VillageScreen screen){
                        Gdx.app.postRunnable(()->{
                            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                                .setScreen(screen.farmScreen);
                        });
                    }
                }else if(gamecommand.command.equals("update-village")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof VillageScreen screen){
                        Gdx.app.postRunnable(()->{
                           screen.updatePlayers();
                        });
                    }

                }else if(gamecommand.command.equals("propose-mode")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof GameScreen screen){
                        Gdx.app.postRunnable(()->{
                           screen.handlePropose(screen.getStage(), gamecommand.playerName);
                        });
                    }

                }else if(gamecommand.command.equals("go-left")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof VillageScreen screen){
                        Gdx.app.postRunnable(()->{
                            screen.getHero().playerX.set(screen.getHero().playerX.get()-1);
                            screen.getHero().targetX.set(screen.getHero().targetX.get()-1);
                            screen.getHero().renderX = screen.getHero().playerX.get() * CELL_SIZE;
                            screen.getHero().currentDirection = 2;
                        });
                    }else{
                        System.out.println("not in village for marriage");
                    }

                }else if(gamecommand.command.equals("go-right")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof VillageScreen screen){
                        Gdx.app.postRunnable(()->{
//                            screen.getHero().playerX.set(screen.getHero().playerX.get()+1);
//                            screen.getHero().targetX.set(screen.getHero().targetX.get()+1);
//                            screen.getHero().renderX = screen.getHero().playerX.get() * CELL_SIZE;
                            screen.getHero().currentDirection = 4;
                        });
                    }else{
                        System.out.println("not in village for marriage");
                    }

                }else if(gamecommand.command.equals("propose")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof VillageScreen screen){
                        screen.getHero().isActing.set(true);
                        screen.getHero().stateTime = 0;
                        screen.getHero().currentAnimation = screen.getHero().ring();
                        screen.onRepeat = false;
                    }
                }
                else if(gamecommand.command.equals("other-propose")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof VillageScreen screen){
                        for (PlayerSprite playerSprite : screen.playerSprites) {
                            if(playerSprite.getPlayerData().getUsername().equals(gamecommand.playerName)){
                                playerSprite.isActing = true;
                                playerSprite.stateTime = 0;
                                playerSprite.currentAnimation = playerSprite.ring();
                            }
                        }
                    }
                }else if(gamecommand.command.equals("kiss")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof VillageScreen screen){
                        screen.getHero().isActing.set(true);
                        screen.getHero().stateTime = 0;
                        screen.getHero().currentAnimation = screen.getHero().hug();
                        screen.onRepeat = false;
                    }
                }
                else if(gamecommand.command.equals("other-kiss")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof VillageScreen screen){
                        for (PlayerSprite playerSprite : screen.playerSprites) {
                            if(playerSprite.getPlayerData().getUsername().equals(gamecommand.playerName)){
                                playerSprite.isActing = true;
                                playerSprite.stateTime = 0;
                                playerSprite.currentAnimation = playerSprite.hug();
                            }
                        }
                    }
                }
            }


        }
    }

    public static void handle(String type, String message , Gson gson, long timestamp){
        switch (type){
            case "game-command"-> {
                GameMessage<String> gameMessage = gson.fromJson(message, new TypeToken<GameMessage<String>>() {}.getType());
                String command = gameMessage.body;
                if(command.equals("walk home")){
                    CellData cd = Finder.getfd().getStartPoints().get(0);
                    System.out.println("starting point is "+cd.getX()+" "+cd.getY());
                    for (CellData transferCell : Finder.getfd().getTransferCells()) {
                        System.out.println("  transfer cells "+transferCell.getX()+" "+transferCell.getY());
                    }
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof GameScreen gs){
                        if(gs instanceof FarmScreen fs){
                            fs.setDest();
                        }else{
                            GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("at home", AppClient.getUserData().getUsername()));
                            AppClient.getClient().send(new Gson().toJson(msg));
                        }
                        gs.setHalt(true);
                    }
                }else if(command.equals("new day")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof GameScreen gs){
                        System.out.println("setting it to false");
                        gs.setHalt(false);
                        PlayerActs.alrSent = false;
                    }
                }else if(command.equals("terminate-vote")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof GameScreen screen){
                        screen.voteMode = false;
                        screen.playerVoteLabel.setVisible(false);
                        screen.noVoteButton.setVisible(false);
                        screen.yesVoteButton.setVisible(false);
                        screen.howManyVotedLabel.setVisible(false);
                        screen.overlay.addAction(Actions.sequence(
                            Actions.fadeOut(0.5f),
                            Actions.run(() -> screen.overlay.remove())
                        ));

                        if (screen.overlay != null) {
                            screen.overlay.remove();
                            screen.overlay = null;
                        }
                        PlayerActs.alrrSent = false;
                    }
                }else if(command.equals("exit-game")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof GameScreen screen){
                        PlayerActs.alrrSent = false;
                        AppClient.endGame(screen);
                    }
                }else if(command.equals("new-ter")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).getScreen() instanceof GameScreen screen){
                        screen.handleTerminate(screen.getStage());
                    }
                }else if(command.equals("terminate-ter")){
                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen() instanceof GameScreen screen){
                        screen.terMode = false;
                        screen.terLabel.setVisible(false);
                        screen.ternoButton.setVisible(false);
                        screen.teryesButton.setVisible(false);
                        screen.terhowmanyLabel.setVisible(false);
                        screen.overlay.addAction(Actions.sequence(
                            Actions.fadeOut(0.5f),
                            Actions.run(() -> screen.overlay.remove())
                        ));

                        if (screen.overlay != null) {
                            screen.overlay.remove();
                            screen.overlay = null;
                        }
                        PlayerActs.alrrSent = false;
                    }
                }
            }
        }
    }


    private static void updateTime(GameScreen gs){
        if(AppClient.getGameData() == null) return;
        gs.getTimeScreen().updatePointer(AppClient.getGameData().getTime().getHour(),
            AppClient.getGameData().getTime().convertDay()
            ,AppClient.getGameData().getTime().getYear(),
            Finder.getpd().getMoney()
        );
        gs.getTimeScreen().updateWeatherAndSeason(AppClient.getGameData().getTime(), AppClient.getGameData().getWeatherType());
    }
}
