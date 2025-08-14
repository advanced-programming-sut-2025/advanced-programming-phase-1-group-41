package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.model.AnimalSprite;
import com.CEliconValley.client.model.NPCSprite;
import com.CEliconValley.client.model.PlayerSprite;
import com.CEliconValley.common.CellData;
import com.CEliconValley.common.NPCData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.Position;
import com.CEliconValley.common.messages.TGPoint;
import com.CEliconValley.models.*;
import com.CEliconValley.models.animals.FishType;
import com.CEliconValley.models.animals.animalKinds.Cow;
import com.CEliconValley.models.animals.animalKinds.Goat;
import com.CEliconValley.models.animals.animalKinds.Sheep;
import com.CEliconValley.models.buildings.ShippingBin;
import com.CEliconValley.models.foragings.Fertilizer;
import com.CEliconValley.models.foragings.Seed;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.locations.Location;
import com.CEliconValley.models.tools.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;
import static com.CEliconValley.client.view.screen.FarmScreen.VIRTUAL_WIDTH;

public class PlayerActs {
    public static GameScreen screen;
    public static boolean alrSent = false;
    public static boolean alrrSent = false;
    public static void setScreen(GameScreen screen) {
        PlayerActs.screen = screen;
    }

    public static Result handleInput(Hero hero, Location location, Stage stage, float delta) {
        screen.updateEnergy();

        if(screen instanceof CottageScreen && ((CottageScreen) screen).isRefrigeratorOpen){
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                ((CottageScreen) screen).isRefrigeratorOpen = !((CottageScreen) screen).isRefrigeratorOpen;
            }
            return new Result(true, "refrigerator opened");
        }

        if(screen.sellmode){
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                screen.sellmode = false;
            }
            return new Result(true, "sellmode opened");
        }
        if(screen.trashmode){
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                screen.trashmode = false;
            }
            return new Result(true, "trash opened");
        }

        if (screen.cheatMode) {
            stage.act(delta);
            stage.draw();
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                String code = screen.cheatCodeField.getText();
                System.out.println("Cheat code entered: " + code);

                assert AppClient.getUserData() != null;
                GameMessage<GameCommand> cmnd = new GameMessage<>("game-command",
                    new GameCommand(code, AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(cmnd));

                screen.cheatCodeField.setText("");
                screen.cheatCodeField.setVisible(false);
                screen.cheatMode = false;

                screen.overlay.addAction(Actions.sequence(
                    Actions.fadeOut(0.5f),
                    Actions.run(() -> screen.overlay.remove())
                ));

                if (screen.overlay != null) {
                    screen.overlay.remove();
                    screen.overlay = null;
                }

            }
            return new Result(false, "cheat");
        }
        if(screen.chatMode){
            screen.chatStage.act(delta);
            screen.chatStage.draw();
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                String message = screen.getChatInput().getText();
                if (!message.trim().isEmpty()) {
                    assert AppClient.getUserData() != null;
                    GameMessage<PlayerMessage> msg = new GameMessage<>("player-message",
                        new PlayerMessage(AppClient.getUserData().getUsername(), message));
                    AppClient.getClient().send(new Gson().toJson(msg));
                    System.out.println("Chat message: " + message);
                }
                screen.getChatInput().setText("");
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                screen.chatMode = false;
                screen.getChatInput().setVisible(false);
                Gdx.input.setInputProcessor(screen.stage);
            }
            return new Result(false, "chat");
        }
        if(screen.isArtisanMenuOpen){
            if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                screen.isArtisanMenuOpen = false;
                screen.getChatInput().setVisible(false);
                Gdx.input.setInputProcessor(screen.stage);
            }
            return new Result(true, "artisan menu opened");
        }
        if(screen.scoreboardMode){
            screen.scoreboardStage.act(delta);
            screen.scoreboardStage.draw();
            if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)){
                screen.scoreboardMode = false;
                Gdx.input.setInputProcessor(screen.stage);
            }
            return new Result(false, "scoreboard");
        }
        if(screen.friendshipMode){
            if(screen.friendshipStage != null){
                screen.friendshipStage.act(delta);
                screen.friendshipStage.draw();
            }
            if(screen.friendshipStageHandler.isChatting && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                String message = screen.friendshipStageHandler.chatTextField.getText();
                if (!message.trim().isEmpty()) {
                    if (screen.friendshipStageHandler.isPlayer) {
                        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                            new GameCommand
                                ("talk -u " + screen.friendshipStageHandler.playerData.getUsername() +
                                    " -m " + message, AppClient.getUserData().getUsername()));
                        AppClient.getClient().send(new Gson().toJson(msg));
                    } else {
                        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                            new GameCommand
                                ("meet NPC " + screen.friendshipStageHandler.npcData.getName() +
                                    " " + message, AppClient.getUserData().getUsername()));
                        AppClient.getClient().send(new Gson().toJson(msg));
                    }
                }
                screen.friendshipStageHandler.chatTextField.setText("");
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                screen.friendshipMode = false;
                Gdx.input.setInputProcessor(screen.stage);
                screen.friendshipStageHandler.emptyFields();
            }
            return new Result(false, "friendship");
        }
        if (screen.voteMode) {
            stage.act(delta);
            stage.draw();
            screen.yesVoteButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!alrrSent) {
                        GameMessage<String> msg = new GameMessage<>("update-vote", ";)");
                        AppClient.getClient().send(new Gson().toJson(msg));
                    }
                    alrrSent = true;
                }
            });

            screen.noVoteButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!alrrSent){
                        GameMessage<String> msg = new GameMessage<>("terminate-vote", ";)");
                        AppClient.getClient().send(new Gson().toJson(msg));
                    }
                    alrrSent = true;
                }
            });

            return new Result(false, "cheat");
        }
        if(screen.terMode){
            stage.act(delta);
            stage.draw();
            screen.teryesButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!alrrSent) {
                        GameMessage<String> msg = new GameMessage<>("update-ter", ";)");
                        AppClient.getClient().send(new Gson().toJson(msg));
                    }
                    alrrSent = true;
                }
            });

            screen.ternoButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!alrrSent){
                        GameMessage<String> msg = new GameMessage<>("terminate-ter", ";)");
                        AppClient.getClient().send(new Gson().toJson(msg));
                    }
                    alrrSent = true;
                }
            });

            return new Result(false, "cheat");

        }
        if(screen.dcmode){
            stage.act(delta);
            stage.draw();
            return new Result(false, "cheat");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !screen.chatMode) {
            screen.handleCheatCode(stage);
            return new Result(true, "cheat");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T) && !screen.cheatMode){
            screen.handleChatMode(screen.chatStage);
            screen.getChatInput().setDisabled(false);
            screen.getChatInput().setFocusTraversal(true);
            screen.getChatInput().setCursorPosition(0);
            return new Result(true, "chat");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)){
            screen.handleScoreboard(screen.scoreboardStage);
            return new Result(true, "scoreboard");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && screen instanceof CottageScreen && !screen.isMenuOpen){
            ((CottageScreen) screen).isRefrigeratorOpen = !((CottageScreen) screen).isRefrigeratorOpen;
            return new Result(true, "refrigerator");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            screen.isMenuOpen = !screen.isMenuOpen;
            if(screen instanceof GreenHouseScreen greenHouseScreen){
                if(greenHouseScreen.isMenuOpen){
                    greenHouseScreen.camera.zoom *= 2;
                }else{
                    greenHouseScreen.camera.zoom /= 2;

                }
            }
            return new Result(true, "menu");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            if(screen instanceof FarmScreen farmScreen){
                GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                    new GameCommand("go-to-village",AppClient.getUserData().getUsername())
                    );
                AppClient.getClient().send(new Gson().toJson(msg));
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            if(screen instanceof VillageScreen villageScreen){
                villageScreen.isAnimalMenuOpen = !villageScreen.isAnimalMenuOpen;
            }
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            if(screen instanceof VillageScreen villageScreen){
                GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                    new GameCommand("go-to-farm",AppClient.getUserData().getUsername())
                );
                AppClient.getClient().send(new Gson().toJson(msg));
            }
        }
        else if(Gdx.input.isButtonJustPressed(0)){
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            if(screen instanceof FarmScreen farmScreen){
                farmScreen.camera.unproject(mousePos);
                putItemOnGround(mousePos.x, mousePos.y, farmScreen);
            }else if(screen instanceof GreenHouseScreen greenHouseScreen){
                greenHouseScreen.camera.unproject(mousePos);
                putInGreenhouse(mousePos.x, mousePos.y, greenHouseScreen);
            }else if(screen instanceof VillageScreen villageScreen){
                villageScreen.camera.unproject(mousePos);
                putInGreenhouse(mousePos.x, mousePos.y, villageScreen);
            }
        }else if(Gdx.input.isButtonJustPressed(1)){
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            if(screen instanceof VillageScreen villageScreen){
                villageScreen.camera.unproject(mousePos);
                handleClick(mousePos.x, mousePos.y, villageScreen);
            }

        }
        if (screen.isMenuOpen) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                screen.menuBar.goToPreviousTab();
                screen.menuBar.resetScroll();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                screen.menuBar.goToNextTab();
                screen.menuBar.resetScroll();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                screen.menuBar.scrollUp();

            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                screen.menuBar.scrollDown();

            }
        }else if((screen instanceof FarmScreen) || screen instanceof CottageScreen){ //TODO Just For Now The Condition!!!
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                screen.inventoryRenderer.shiftRight();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                screen.inventoryRenderer.shiftLeft();
            }
        } else if(screen instanceof BarnScreen || screen instanceof CoopScreen){
            if((screen instanceof BarnScreen && ((BarnScreen) screen).isBarnMenuOpen())
                || (screen instanceof CoopScreen && ((CoopScreen) screen).isCoopMenuOpen())){
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                    screen.barnOrCoopMenuBar.scrollUp();
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
                    screen.barnOrCoopMenuBar.scrollDown();

                }
            }
        }else if(screen instanceof VillageScreen villageScreen){
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                if(villageScreen.isMarketMenuOpen){
                    villageScreen.getMarketPlaceMenu().scrollUp();
                }
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
                if(villageScreen.isMarketMenuOpen){
                    villageScreen.getMarketPlaceMenu().scrollDown();
                }


            }
        }
        if (hero.isActing.get() || hero.isMoving.get()) {
//            System.out.println(hero.isActing+" "+hero.isMoving);
            return new Result(false, "act-move");
        }

        if(Objects.requireNonNull(Finder.getpd()).getEnergy() <= 0){
            hero.currentAnimation = hero.generalAct(getSleepAct());
            screen.onRepeat = true;
            return new Result(false,"energy");
        }

        if (Gdx.input.isKeyJustPressed((Input.Keys.X))) {
            assert AppClient.getUserData() != null;
            GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("at home", AppClient.getUserData().getUsername()));
            AppClient.getClient().send(new Gson().toJson(msg));
        }

        if(screen.isHalt()){
            if(screen instanceof FarmScreen fs){
                fs.nextMovement();
            }else if(screen instanceof VillageScreen vs){
                vs.nextMovement();
            }
            return new Result(false,"halt");
        }


        boolean moved = false;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            hero.currentDirection = 1;
            screen.onRepeat = true;
            if (screen.canMoveTo(hero.playerX.get(), hero.playerY.get() + 1, location)) {
                hero.targetX.set(hero.playerX.get());
                hero.targetY.set(hero.playerY.get() + 1);
                moved = true;
                assert AppClient.getUserData() != null;
                GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("walk up", AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            hero.currentDirection = 3;
            screen.onRepeat = true;
            if (screen.canMoveTo(hero.playerX.get(), hero.playerY.get() - 1, location)) {
                hero.targetX.set(hero.playerX.get());
                hero.targetY.set(hero.playerY.get() - 1);
                moved = true;
                assert AppClient.getUserData() != null;
                GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("walk down", AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            hero.currentDirection = 4;
            screen.onRepeat = true;
            if (screen.canMoveTo(hero.playerX.get() - 1, hero.playerY.get(), location)) {
                hero.targetX.set(hero.playerX.get() - 1);
                hero.targetY.set(hero.playerY.get());
                screen.flip = true;
                moved = true;
                assert AppClient.getUserData() != null;
                GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("walk left", AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
            }
        }else if(Gdx.input.isKeyPressed(Input.Keys.F)){
            if(screen instanceof FarmScreen fs){
                if(fs.isLakeAhead()) {
                    if (FishingRodLevel.parseFishingRodLevel(Finder.getpd().getCurrentToolName()) != null) {
                        screen.onRepeat = false;
                        hero.stateTime = 0;
                        int pre = getMainToolNumber();
                        ArrayList<TGPoint> tgp = getOtherToolNumber();
                        if (pre == -1) {
                            if(tgp == null){
                                if(Finder.getpd().getCurrentToolName() != null &&Finder.getpd().getCurrentToolName().equals(new Shear().getName())){
                                    AnimalSprite animalSprite = isAnimalHere();
                                    if(animalSprite != null && animalSprite.animalData.getAnimalType().equals(new Sheep(null, null).getAnimalType())){
                                        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                                new GameCommand("shear " + animalSprite.animalData.getName(), AppClient.getUserData().getUsername()));
                                        AppClient.getClient().send(new Gson().toJson(msg));
                                        hero.currentAnimation = hero.shear();
                                        if(screen instanceof BarnScreen barnScreen){
                                            barnScreen.setLastAnimal(animalSprite);
                                        }
                                    }else{
                                        hero.currentAnimation = hero.useTool(pre+1);
                                    }
                                }else if(Finder.getpd().getCurrentToolName() != null &&Finder.getpd().getCurrentToolName().equals(new MilkPale().getName())){
                                    AnimalSprite animalSprite = isAnimalHere();
                                    if(animalSprite != null &&(
                                            animalSprite.animalData.getAnimalType().equals(new Cow(null, null).getAnimalType())
                                                    ||
                                                    animalSprite.animalData.getAnimalType().equals(new Goat(null, null).getAnimalType()))
                                    ){
                                        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                                new GameCommand("milkpale " + animalSprite.animalData.getName(), AppClient.getUserData().getUsername()));
                                        AppClient.getClient().send(new Gson().toJson(msg));
                                        hero.currentAnimation = hero.milk();
                                        if(screen instanceof BarnScreen barnScreen){
                                            barnScreen.setLastAnimal(animalSprite);
                                        }
                                    }else{
                                        hero.currentAnimation = hero.useTool(pre+1);
                                    }
                                }
                                else{
                                    hero.currentAnimation = hero.useTool(pre+1);
                                }
                            }
                            else{
                                hero.currentAnimation = hero.useOtherTool(tgp);
                            }
                        }else{
                            hero.currentAnimation = hero.useTool(pre);
                        }
                        hero.isActing.set(true);
                        hero.stateTime = 0;
                        if (screen instanceof FarmScreen farmScreen) {
                            assert AppClient.getUserData() != null;
                            GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                    new GameCommand("tools use -d " + hero.currentDirection, AppClient.getUserData().getUsername()));
                            AppClient.getClient().send(new Gson().toJson(msg));
//                farmScreen.hit(hero.currentDirection, hero.playerX.get(), hero.playerY.get());
                        }
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                if (!fs.isFishing()) {
                                    fs.startFishing(FishType.Shad);
                                }
                            }
                        }, 0.5f);


                    }
                }
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            hero.currentDirection = 2;
            screen.flip = false;
            screen.onRepeat = true;
            if (screen.canMoveTo(hero.playerX.get() + 1, hero.playerY.get(), location)) {
                hero.targetX.set(hero.playerX.get() + 1);
                hero.targetY.set(hero.playerY.get());
                moved = true;
                assert AppClient.getUserData() != null;
                GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("walk right", AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
            }
        }else if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            if(screen instanceof VillageScreen villageScreen){
                villageScreen.tryOpeningMarketMenu();
            }
        }
        else if (Gdx.input.isKeyJustPressed((Input.Keys.R))) {
            if(screen instanceof FarmScreen fs){
                    fs.getThunder().strikeAt(hero.playerX.get(), hero.playerY.get());
            }
        }
        else if(Gdx.input.isKeyJustPressed((Input.Keys.TAB))) {
            if(screen instanceof BarnScreen){
                ((BarnScreen) screen).setBarnMenuOpen(!((BarnScreen) screen).isBarnMenuOpen());
            } else if(screen instanceof CoopScreen){
                ((CoopScreen) screen).setCoopMenuOpen(!((CoopScreen) screen).isCoopMenuOpen());
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            System.out.println("you're at "+hero.playerX+" "+hero.playerY);
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.K)){
            if(screen instanceof VillageScreen villageScreen){
                if(villageScreen.isMarketMenuOpen){
                    villageScreen.getMarketPlaceMenu().hideSoldOuts();
                }
            }
        }else if(Gdx.input.isKeyPressed(Input.Keys.P)){
            if(screen instanceof BarnScreen screen){
                petBarn(screen);
            } else if(screen instanceof CoopScreen screen){
                petCoop(screen);
            }
        }
        else if (Gdx.input.isKeyJustPressed((Input.Keys.E))) {
            screen.onRepeat = false;
            hero.stateTime = 0;
            int pre = getMainToolNumber();
            ArrayList<TGPoint> tgp = getOtherToolNumber();
            if (pre == -1) {
                if(tgp == null){
                    if(Finder.getpd().getCurrentToolName() != null &&Finder.getpd().getCurrentToolName().equals(new Shear().getName())){
                        AnimalSprite animalSprite = isAnimalHere();
                        if(animalSprite != null && animalSprite.animalData.getAnimalType().equals(new Sheep(null, null).getAnimalType())){
                            GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                new GameCommand("shear " + animalSprite.animalData.getName(), AppClient.getUserData().getUsername()));
                            AppClient.getClient().send(new Gson().toJson(msg));
                            hero.currentAnimation = hero.shear();
                            if(screen instanceof BarnScreen barnScreen){
                                barnScreen.setLastAnimal(animalSprite);
                            }
                        }else{
                            hero.currentAnimation = hero.useTool(pre+1);
                        }
                    }else if(Finder.getpd().getCurrentToolName() != null &&Finder.getpd().getCurrentToolName().equals(new MilkPale().getName())){
                        AnimalSprite animalSprite = isAnimalHere();
                        if(animalSprite != null &&(
                            animalSprite.animalData.getAnimalType().equals(new Cow(null, null).getAnimalType())
                            ||
                            animalSprite.animalData.getAnimalType().equals(new Goat(null, null).getAnimalType()))
                        ){
                            GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                new GameCommand("milkpale " + animalSprite.animalData.getName(), AppClient.getUserData().getUsername()));
                            AppClient.getClient().send(new Gson().toJson(msg));
                            hero.currentAnimation = hero.milk();
                            if(screen instanceof BarnScreen barnScreen){
                                barnScreen.setLastAnimal(animalSprite);
                            }
                        }else{
                            hero.currentAnimation = hero.useTool(pre+1);
                        }
                    }
                    else{
                        hero.currentAnimation = hero.useTool(pre+1);
                    }
                }
                else{
                    hero.currentAnimation = hero.useOtherTool(tgp);
                }
            }else{
                hero.currentAnimation = hero.useTool(pre);
            }
            hero.isActing.set(true);
            hero.stateTime = 0;
            if (screen instanceof FarmScreen farmScreen) {
                assert AppClient.getUserData() != null;
                GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                    new GameCommand("tools use -d " + hero.currentDirection, AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
//                farmScreen.hit(hero.currentDirection, hero.playerX.get(), hero.playerY.get());
            }else if(screen instanceof GreenHouseScreen greenHouseScreen){
                if(Finder.getpd().getCurrentToolName().equals(new WateringCan().getName())){
                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                        new GameCommand("greenhouse water", AppClient.getUserData().getUsername()));
                    AppClient.getClient().send(new Gson().toJson(msg));
                }else if(Finder.getpd().getCurrentToolName().equals(new Scythe().getName())){
                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                        new GameCommand("greenhouse harvest", AppClient.getUserData().getUsername()));
                    AppClient.getClient().send(new Gson().toJson(msg));
                }
            }
        } else if (
            (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT) ||
                Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) &&
            screen instanceof FarmScreen farmScreen) {
            farmScreen.transfer();
        } else if ((Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT) ||
            Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT))  && !(
            screen instanceof FarmScreen
        )) {
            System.out.println(screen);
            screen.transfer();
        }

        if (moved) {
            hero.isMoving.set(true);
            hero.currentAnimation = hero.walk(true, hero.currentDirection);
        } else if (!hero.isMoving.get() && !hero.isActing.get()) {
            hero.currentAnimation = hero.walk(false, hero.currentDirection);
        }
        return new Result(true, ";)");
    }

    public static void approach(Hero hero) {
        if(Objects.requireNonNull(Finder.getpd()).getEnergy() <= 0){
            hero.currentAnimation = hero.generalAct(getSleepAct());
            screen.onRepeat = true;
            if(screen.isHalt() && !alrSent){
                alrSent = true;
                new Timer().schedule(new Timer.Task() {
                    public void run() {
                        GameMessage<GameCommand> msg = new GameMessage<>("game-command", new GameCommand("at home", AppClient.getUserData().getUsername()));
                        AppClient.getClient().send(new Gson().toJson(msg));
                        this.cancel();
                    }
                }, 5, 1);
            }
            return;
        }
        if (hero.isMoving.get()) {
            float targetPixelX = hero.targetX.get() * CELL_SIZE;
            float targetPixelY = hero.targetY.get() * CELL_SIZE;

            float moveAmount = (float) CELL_SIZE / 4;

            if (screen instanceof GreenHouseScreen) {
                moveAmount /= 2;
            }else if(screen instanceof VillageScreen){
//                moveAmount *= 2;
            }

            hero.renderX = approach(hero.renderX, targetPixelX, moveAmount);
            hero.renderY = approach(hero.renderY, targetPixelY, moveAmount);

            if (hero.renderX == targetPixelX && hero.renderY == targetPixelY) {
                hero.playerX.set(hero.targetX.get());
                hero.playerY.set(hero.targetY.get());
                hero.isMoving.set(false);
            }
        }
        GameMessage<Position> msg = new GameMessage<>("position",
            new Position(hero.currentDirection, hero.isMoving.get(), AppClient.getUserData().getUsername(),
                hero.renderX, hero.renderY, hero.targetX.get(), hero.targetY.get(), hero.playerX.get(), hero.playerY.get()));
        AppClient.getClient().send(new Gson().toJson(msg));
    }

    private static float approach(float current, float target, float delta) {
//        if((hero.currentDirection == 1 || hero.currentDirection == 2) && target < current) {
//            return current;
//        } else if((hero.currentDirection == 3 || hero.currentDirection == 4) && target > current) {
//            return current;
//        }
        if (current < target) {
            return Math.min(current + delta, target);
        } else {
            return Math.max(current - delta, target);
        }
    }

    public static void changeScreen(GameScreen screen) {
        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(screen);
//        screen.show();
    }

    public static void animalApproach(ArrayList<AnimalSprite> animalSprites,
                                      float delta) {
        animalSprites.forEach(animalSprite -> {
            if (animalSprite.isMoving) {
                float moveAmount = 400 * delta;
                float targetPixelX = animalSprite.targetX * CELL_SIZE;
                float targetPixelY = animalSprite.targetY * CELL_SIZE;
                animalSprite.currentAnimation = animalSprite.walk(true, animalSprite.currentDirection);
                if (animalSprite.renderX < targetPixelX) {
                    animalSprite.renderX += moveAmount;
                    if (animalSprite.renderX > targetPixelX) animalSprite.renderX = targetPixelX;
                } else if (animalSprite.renderX > targetPixelX) {
                    animalSprite.renderX -= moveAmount;
                    if (animalSprite.renderX < targetPixelX) animalSprite.renderX = targetPixelX;
                }


                if (animalSprite.renderY < targetPixelY) {
                    animalSprite.renderY += moveAmount;
                    if (animalSprite.renderY > targetPixelY) animalSprite.renderY = targetPixelY;
                } else if (animalSprite.renderY > targetPixelY) {
                    animalSprite.renderY -= moveAmount;
                    if (animalSprite.renderY < targetPixelY) animalSprite.renderY = targetPixelY;
                }

                if (animalSprite.renderX == targetPixelX && animalSprite.renderY == targetPixelY) {
                    animalSprite.x = animalSprite.targetX;
                    animalSprite.y = animalSprite.targetY;
                    // TODO need to change the animaldata as well perhaps
                    animalSprite.isMoving = false;
                    animalSprite.currentAnimation = animalSprite.walk(false, animalSprite.currentDirection);
                }
            }
        });

    }


    public static int getMainToolNumber() {
        PlayerData pd = Finder.getpd();
        if (pd.getCurrentToolName() == null) return -1;
        if (BasicTool.parseBasicTool(pd.getCurrentToolName()) != null) {
            if (pd.getCurrentToolName().equals(new Axe().getName())) {
                switch (pd.getToolLevel()) {
                    case Default -> {
                        return 10;
                    }
                    case Copper -> {
                        return 11;
                    }
                    case Iron -> {
                        return 12;
                    }
                    case Gold -> {
                        return 13;
                    }
                    case Iridium -> {
                        return 14;
                    }
                }
            } else if (pd.getCurrentToolName().equals(new Hoe().getName())) {
                switch (pd.getToolLevel()) {
                    case Default -> {
                        return 0;
                    }
                    case Copper -> {
                        return 1;
                    }
                    case Iron -> {
                        return 2;
                    }
                    case Gold -> {
                        return 3;
                    }
                    case Iridium -> {
                        return 4;
                    }
                }
            } else if (pd.getCurrentToolName().equals(new Pickaxe().getName())) {
                switch (pd.getToolLevel()) {
                    case Default -> {
                        return 5;
                    }
                    case Copper -> {
                        return 6;
                    }
                    case Iron -> {
                        return 7;
                    }
                    case Gold -> {
                        return 8;
                    }
                    case Iridium -> {
                        return 9;
                    }
                }

            }
        }
        return -1;
    }

    public static ArrayList<TGPoint> getOtherToolNumber() {
        ArrayList<TGPoint> tgp = new ArrayList();
        PlayerData pd = Finder.getpd();
        if (pd.getCurrentToolName() == null) return null;
        if (BasicTool.parseBasicTool(pd.getCurrentToolName()) != null) {
            if (pd.getCurrentToolName().equals(new WateringCan().getName())) {
                switch (pd.getToolLevel()) {
                    case Default -> {
                        tgp.add(new TGPoint(0, 0));
                        tgp.add(new TGPoint(0, 1));
                        tgp.add(new TGPoint(0, 2));
                        return tgp;
                    }
                    case Copper -> {
                        tgp.add(new TGPoint(0, 3));
                        tgp.add(new TGPoint(0, 4));
                        tgp.add(new TGPoint(0, 5));
                        return tgp;
                    }
                    case Iron -> {
                        tgp.add(new TGPoint(1, 0));
                        tgp.add(new TGPoint(1, 1));
                        tgp.add(new TGPoint(1, 2));
                        return tgp;
                    }
                    case Gold -> {
                        tgp.add(new TGPoint(1, 3));
                        tgp.add(new TGPoint(1, 4));
                        tgp.add(new TGPoint(1, 5));
                        return tgp;
                    }
                    case Iridium -> {
                        tgp.add(new TGPoint(2, 0));
                        tgp.add(new TGPoint(2, 1));
                        tgp.add(new TGPoint(2, 2));
                        return tgp;
                    }
                }
            }
        }
        if(pd.getCurrentToolName().equals(new Scythe().getName())){
            for (int i = 0; i < 6; i++) {
                tgp.add(new TGPoint(3, i));
            }
            return tgp;
        }
        if(FishingRodLevel.parseFishingRodLevel(pd.getCurrentToolName()) != null){
            int row = 4;
            switch (FishingRodLevel.parseFishingRodLevel(pd.getCurrentToolName())){
                case Training -> {
                    row = 4;
                }
                case Bamboo -> {
                    row = 5;
                }
                case FiberGlass -> {
                    row = 6;
                }
                case Iridium -> {
                    row = 7;
                }
            }
            for (int i = 0; i < 5; i++) {
                tgp.add(new TGPoint(row, i));
            }
            return tgp;
        }
        return null;
    }

    public static ArrayList<TGPoint> getSleepAct(){
        ArrayList<TGPoint> tgp = new ArrayList();
        tgp.add(new TGPoint(9,4));
        tgp.add(new TGPoint(9,5));
        tgp.add(new TGPoint(9,6));
        tgp.add(new TGPoint(9,7));
        tgp.add(new TGPoint(9,7));
        tgp.add(new TGPoint(10,0));
        tgp.add(new TGPoint(10,0));
        return tgp;
    }

    public static AnimalSprite isAnimalHere(){
        if(screen instanceof BarnScreen barnScreen){
            for (AnimalSprite animalSprite : barnScreen.getAnimalSprites()) {
                Hero hero = barnScreen.getHero();
                if(animalSprite.x == hero.playerX.get() && animalSprite.y == hero.playerY.get()){
                    return animalSprite;
                }
            }
        }
        return null;
    }


    public static void putInGreenhouse(float mouseX, float mouseY, GreenHouseScreen screen){
        Hero hero = screen.getHero();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Cell cell = screen.greenHouseMap.getCell(hero.playerX.get()+j,
                    hero.playerY.get()+i);
                if(cell == null) continue;
                if(mouseX >= cell.getX()*CELL_SIZE && mouseX <= (cell.getX()+1)*CELL_SIZE &&
                    mouseY >= cell.getY()*CELL_SIZE && mouseY <= (cell.getY()+1)*CELL_SIZE){
                    int dir = getDir(i, j);
                    if(dir == -1){
                        System.out.println("invalid dir");
                        return;
                    }
                    if(hero.selectedItemName == null) return;
                    Item item = Finder.parseItem(hero.selectedItemName);
                    if(item == null) return ;
                    if(item instanceof Seed seed) {
                        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                            new GameCommand("greenhouse plant -s " + seed.getName() + " -d " + dir,
                                AppClient.getUserData().getUsername()));
                        AppClient.getClient().send(new Gson().toJson(msg));
                    }
                    else if(item instanceof Fertilizer fertilizer){
                        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                            new GameCommand("greenhouse fertilize -f " + fertilizer.getName() + " -d " + dir,
                                AppClient.getUserData().getUsername()));
                        AppClient.getClient().send(new Gson().toJson(msg));
                    }
                }
            }
        }
    }
    public static void putInGreenhouse(float mouseX, float mouseY, VillageScreen screen){
        Hero hero = screen.getHero();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                CellData cd = Finder.getcdByvd(hero.playerX.get()+j,
                    hero.playerY.get()+i);
                Cell cell = cd.extractData();
                if(mouseX >= cell.getX()*CELL_SIZE && mouseX <= (cell.getX()+1)*CELL_SIZE &&
                    mouseY >= cell.getY()*CELL_SIZE && mouseY <= (cell.getY()+1)*CELL_SIZE){
                    int dir = getDir(i, j);
                    if(dir == -1){
                        System.out.println("invalid dir");
                        return;
                    }
                    if(Finder.parseItem(cd.getObjectName()) instanceof ShippingBin shippingBin){
                        screen.sellmode = true;
                    }
                }
            }
        }
    }

    public static void putItemOnGround(float mouseX, float mouseY, FarmScreen fs){
        Hero hero = fs.getHero();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if(i ==0 && j == 0) continue;
                CellData cd = Finder.getcdByFarmData(hero.playerX.get()+j,
                    hero.playerY.get()+i, fs.getFarmMap().farmData);

                if(mouseX >= cd.getX()*CELL_SIZE && mouseX <= (cd.getX()+1)*CELL_SIZE &&
                mouseY >= cd.getY()*CELL_SIZE && mouseY <= (cd.getY()+1)*CELL_SIZE){

                    if(Finder.parseItem(cd.getObjectName()) instanceof CraftableMachine craftableMachine){
                        screen.isArtisanMenuOpen = !screen.isArtisanMenuOpen;
                        screen.cm = craftableMachine;
                        return;
                    }else if(Finder.parseItem(cd.getObjectName()) instanceof ShippingBin shippingBin){
                        System.out.println("im here for shippingbin s:)");
                        screen.sellmode = true;
                    }else if(Finder.parseItem(cd.getObjectName()) instanceof TrashCan){
                        System.out.println("im here for basic tool s:)");
                        screen.trashmode = true;
                    }

                    System.out.println("im around :)");
                    System.out.println("you clicked on "+cd.getX()+" "+cd.getY());
                    System.out.println("player pos "+hero.playerX.get()+" "+hero.playerY.get());
                    int dir = getDir(i, j);
                    if(dir == -1){
                        System.out.println("invalid dir");
                        return;
                    }
                    if(hero.selectedItemName == null) return;
                    Item item = Finder.parseItem(hero.selectedItemName);
                    if(item == null) return ;
                    if(item instanceof Seed seed) {
                        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                            new GameCommand("plant -s " + seed.getName() + " -d " + dir,
                                AppClient.getUserData().getUsername()));
                        AppClient.getClient().send(new Gson().toJson(msg));
                        screen.hero.isActing.set(true);
                        screen.onRepeat = false;
                        screen.hero.stateTime = 0;
                        screen.hero.currentAnimation = screen.hero.plant();
                    }
                    else if(item instanceof Fertilizer fertilizer){
                        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                            new GameCommand("fertilize -f " + fertilizer.getName() + " -d " + dir,
                                AppClient.getUserData().getUsername()));
                        AppClient.getClient().send(new Gson().toJson(msg));
                        screen.hero.isActing.set(true);
                        screen.onRepeat = false;
                        screen.hero.stateTime = 0;
                        screen.hero.currentAnimation = screen.hero.fertilize();
                    }
                    else{
                        GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                            new GameCommand("place item -n "+hero.selectedItemName +" -d "+dir,
                                AppClient.getUserData().getUsername()));
                        AppClient.getClient().send(new Gson().toJson(msg));
                        screen.hero.isActing.set(true);
                        screen.onRepeat = false;
                        screen.hero.stateTime = 0;
                        screen.hero.currentAnimation = screen.hero.plant();
                        return;
                    }
                }

            }

        }
    }
    private static int getDir(int i, int j){
        if(i == -1){
            return j+2;
        }else if(i == 0){
            if(j == 1) return 4;
            if(j == -1) return 8;
        }else if(i==1){
            return 6-j;
        }
        return -1;
    }


    public static void petBarn(BarnScreen barnScreen){
        for (AnimalSprite animalSprite : barnScreen.getAnimalSprites()) {
            float dx = animalSprite.renderX - barnScreen.hero.renderX;
            if (dx < 0) dx = -dx;
            float dy = animalSprite.renderY - barnScreen.hero.renderY;
            if (dy < 0) dy = -dy;
            if(dx+dy < CELL_SIZE * 2){
                GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                    new GameCommand("pet -w inside -n "+animalSprite.animalData.getName(), AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
                screen.hero.isActing.set(true);
                screen.hero.currentAnimation = screen.hero.pet();
                screen.hero.stateTime = 0;
                screen.onRepeat = false;
                break;
            }
        }
    }
    public static void petCoop(CoopScreen coopScreen){
        for (AnimalSprite animalSprite : coopScreen.getAnimalSprites()) {
            float dx = animalSprite.renderX - coopScreen.hero.renderX;
            if (dx < 0) dx = -dx;
            float dy = animalSprite.renderY - coopScreen.hero.renderY;
            if (dy < 0) dy = -dy;
            if(dx+dy < CELL_SIZE * 2){
                GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                    new GameCommand("pet -w inside -n "+animalSprite.animalData.getName(), AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
                screen.hero.isActing.set(true);
                screen.hero.currentAnimation = screen.hero.pet();
                screen.hero.stateTime = 0;
                screen.onRepeat = false;
            }
        }
    }


    public static void handleClick(float mouseX, float mouseY, VillageScreen vs){
        for (PlayerSprite playerSprite : vs.playerSprites) {
            float renderx = playerSprite.getPlayerData().renderx;
            float rendery = playerSprite.getPlayerData().rendery;
            float cs = CELL_SIZE * 2f;
            if(renderx - cs < mouseX && mouseX < renderx + cs
                &&
                rendery - cs < mouseY && mouseY < rendery + cs){
                System.out.println("found "+playerSprite.getPlayerData().getUsername());
                GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                    new GameCommand("hug -u "+playerSprite.getPlayerData().getUsername(), AppClient.getUserData().getUsername()));
                AppClient.getClient().send(new Gson().toJson(msg));
                return;
            }
        }
        for (NPCSprite npcSprite : vs.npcSprites) {
            float ratio = ( Gdx.graphics.getWidth() / VIRTUAL_WIDTH);
            float renderx = npcSprite.getNPCData().renderX / 160 *(ratio * 160);
            float rendery = npcSprite.getNPCData().renderY / 160 *(ratio * 160);
            float cs = CELL_SIZE * 2f;
            if(renderx - cs < mouseX && mouseX < renderx + cs
                &&
                rendery - cs < mouseY && mouseY < rendery + cs){
                Gdx.app.postRunnable(() -> {
                    System.out.println("found "+npcSprite.getNPCData().getName());
                    screen.friendshipMode = true;
                    screen.handleFriendship(screen.friendshipStage, null, npcSprite.getNPCData());
                });
            }
        }
    }
}
