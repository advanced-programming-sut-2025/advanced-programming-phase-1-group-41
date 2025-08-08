package com.CEliconValley.client.view.screen;

import com.CEliconValley.Main;
import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.screen.maps.*;
import com.CEliconValley.common.CellData;
import com.CEliconValley.controllers.Spawner.InventoryRenderer;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Hero;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.foragings.Nature.Obstacle;
import com.CEliconValley.models.foragings.Nature.Rock;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Location;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Objects;

public abstract class GameScreen implements Screen {
    protected boolean onRepeat = true;
    protected boolean flip = false;
    protected boolean isMenuOpen = false;
    protected InventoryRenderer inventoryRenderer;
    protected boolean cheatMode = false;
    public boolean voteMode = false;
    public Image overlay;
    protected TextField cheatCodeField;

    public TextButton yesVoteButton, noVoteButton;
    public Label playerVoteLabel;
    public Label howManyVotedLabel;

    public TextButton teryesButton, ternoButton;
    public Label terLabel;
    public Label terhowmanyLabel;
    public boolean terMode = false;

    public boolean isGameFinished = false;

    protected Stage stage;
    public abstract void transfer();
    protected Hero hero;
    protected MenuBar menuBar=new MenuBar(this);
    protected Texture hudTexture = new Texture(Gdx.files.internal("game/Clock/Clock.png"));
    protected Image hudImage, energyBarImage, energyGreenImage, energyRedImage, energyYellowImage;
    protected TimeScreen timeScreen;
    protected Texture energyBarTexture = new Texture(Gdx.files.internal("game/EnergyBar/bar.png"));
    protected Texture energyGreenTexture = new Texture(Gdx.files.internal("game/EnergyBar/green.png"));
    protected Texture energyYellowTexture = new Texture(Gdx.files.internal("game/EnergyBar/yellow.png"));
    protected Texture energyRedTexture = new Texture(Gdx.files.internal("game/EnergyBar/red.png"));
    protected boolean halt = false;

    public void setTextForVoteLabel(String input){
        playerVoteLabel.setText(input);
        playerVoteLabel.setPosition(stage.getWidth() / 3 - playerVoteLabel.getWidth() / 2, stage.getHeight() * 5 / 6- playerVoteLabel.getHeight() / 2);
    }

    public void setTextForTerLabel(String input){
        terLabel.setText(input);
        terLabel.setPosition(Gdx.graphics.getWidth() / 3 - terLabel.getWidth(), stage.getHeight() * 5 / 6- terLabel.getHeight() / 2);
    }

    public void setupVoteUI(){
        playerVoteLabel = new Label("Vote", GameAssetManager.getGameAssetManager().getSkin());
        playerVoteLabel.setVisible(false);
        playerVoteLabel.setFontScale(2f);
        playerVoteLabel.setPosition(Gdx.graphics.getWidth() / 2 - playerVoteLabel.getWidth(), stage.getHeight() * 5 / 6- playerVoteLabel.getHeight() / 2);
        stage.addActor(playerVoteLabel);
        yesVoteButton = new TextButton("Yes", GameAssetManager.getGameAssetManager().getSkin());
        yesVoteButton.setColor(Color.GREEN);
        noVoteButton = new TextButton("No", GameAssetManager.getGameAssetManager().getSkin());
        noVoteButton.setColor(Color.RED);
        yesVoteButton.setPosition(stage.getWidth()* 3 / 4 - yesVoteButton.getWidth() / 2, stage.getHeight() / 2- yesVoteButton.getHeight() / 2);
        noVoteButton.setPosition(stage.getWidth()/4 - noVoteButton.getWidth() / 2, stage.getHeight() / 2 - noVoteButton.getHeight() / 2);
        yesVoteButton.setVisible(false);
        noVoteButton.setVisible(false);
        stage.addActor(yesVoteButton);
        stage.addActor(noVoteButton);
        howManyVotedLabel = new Label("Vote: 0 / "+AppClient.getGameData().getPlayersData().size(), GameAssetManager.getGameAssetManager().getSkin());
        howManyVotedLabel.setFontScale(1.5f);
        howManyVotedLabel.setPosition(Gdx.graphics.getWidth() / 2 - howManyVotedLabel.getWidth(), stage.getHeight() * 4 / 6- howManyVotedLabel.getHeight() / 2);
        howManyVotedLabel.setVisible(false);
        stage.addActor(howManyVotedLabel);

        ternoButton = new TextButton("No", GameAssetManager.getGameAssetManager().getSkin());
        ternoButton.setColor(Color.RED);
        ternoButton.setVisible(false);
        teryesButton = new TextButton("Yes", GameAssetManager.getGameAssetManager().getSkin());
        teryesButton.setColor(Color.GREEN);
        teryesButton.setVisible(false);
        teryesButton.setPosition(stage.getWidth()* 3 / 4 - teryesButton.getWidth() / 2, stage.getHeight() / 2- teryesButton.getHeight() / 2);
        ternoButton.setPosition(stage.getWidth()/4 - ternoButton.getWidth() / 2, stage.getHeight() / 2 - ternoButton.getHeight() / 2);
        stage.addActor(ternoButton);
        stage.addActor(teryesButton);
        terLabel = new Label("Terminate Game", GameAssetManager.getGameAssetManager().getSkin());
        terLabel.setVisible(false);
        terLabel.setFontScale(2f);
        terLabel.setPosition(Gdx.graphics.getWidth() / 2 - terLabel.getWidth(), stage.getHeight() * 5 / 6- terLabel.getHeight() / 2);
        stage.addActor(terLabel);
        terhowmanyLabel = new Label("Vote: 0 / "+AppClient.getGameData().getPlayersData().size(), GameAssetManager.getGameAssetManager().getSkin());
        terhowmanyLabel.setFontScale(1.5f);
        terhowmanyLabel.setPosition(Gdx.graphics.getWidth() / 2 - terhowmanyLabel.getWidth(), stage.getHeight() * 4 / 6- terhowmanyLabel.getHeight() / 2);
        terhowmanyLabel.setVisible(false);
        stage.addActor(terhowmanyLabel);

    }


    public GameScreen(InventoryRenderer inventoryRenderer) {
        stage = new Stage(new ScreenViewport(), Main.getBatch());
        Gdx.input.setInputProcessor(stage);
        this.inventoryRenderer = inventoryRenderer;
        cheatCodeField = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
        cheatCodeField.setMessageText("Enter cheat code");
        cheatCodeField.setVisible(false);
        cheatCodeField.setWidth(600);
        cheatCodeField.setPosition(stage.getWidth()/2  - cheatCodeField.getWidth() / 2, stage.getHeight() / 2 - cheatCodeField.getHeight() / 2);
        setupVoteUI();

        stage.addActor(cheatCodeField);
        hudImage = new Image(new TextureRegion(hudTexture));
        hudImage.setSize(hudImage.getWidth()*4, hudImage.getHeight()*4);
        energyBarImage = new Image(new TextureRegion(energyBarTexture));
        energyGreenImage = new Image(new TextureRegion(energyGreenTexture));
        energyYellowImage = new Image(new TextureRegion(energyYellowTexture));
        energyRedImage = new Image(new TextureRegion(energyRedTexture));
        float posX = stage.getWidth() - hudImage.getWidth() - 10;
        float posY = stage.getHeight() - hudImage.getHeight() - 10;
        stage.addActor(hudImage);
        timeScreen = new TimeScreen(GameAssetManager.getGameAssetManager().getSkin(), stage,
            posX, posY, hudImage);
        hudImage.setTouchable(Touchable.disabled);
        hudImage.setPosition(posX, posY);
        timeScreen.dateLabel.setPosition(posX + 120, posY + 180);
        timeScreen.timeLabel.setPosition(posX + 120, posY + 90);
        timeScreen.goldLabel.setPosition(posX + 66.5f, posY + 10);
        timeScreen.getHudTable().setPosition(0, -stage.getHeight() / 21f);
        energyBarImage.setPosition(stage.getWidth() - energyBarImage.getWidth() * 2, energyBarImage.getHeight() * 1.5f);
        timeScreen.goldLabel.setAlignment(Align.left);
        timeScreen.goldLabel.setFontScale(1.18f);
        timeScreen.dateLabel.setFontScale(0.8f);
        stage.addActor(timeScreen.dateLabel);
        stage.addActor(timeScreen.timeLabel);
        stage.addActor(timeScreen.goldLabel);
        stage.addActor(timeScreen.getHudTable());
        stage.addActor(energyBarImage);
        this.hero = new Hero();
    }


    public boolean canMoveTo(int x, int y, Location location) {
        if(location instanceof FarmMap farmMap){
            for (CellData cd : farmMap.farmData.getCells()) {
                if (cd.getX() == x && cd.getY() == y) {
                    Cell cell = cd.extractData();
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock ||cell.getObjectMap() instanceof Wall ||cell.getObjectMap() instanceof Obstacle) {
                        System.out.println(cd.getObjectName()+" "+cell.getX()+" "+cell.getY());
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }else if(location instanceof VillageMap villageMap){
            for (CellData cd : villageMap.villageData.getCellsData()) {
                if (cd.getX() == x && cd.getY() == y) {
                    Cell cell = cd.extractData();
                    if (cell.getObjectMap() instanceof Lake ||( cell.getObjectMap() instanceof Grass grass && !grass.isGround() )||cell.getObjectMap() instanceof Wall ||cell.getObjectMap() instanceof Obstacle) {
                        System.out.println(cd.getObjectName()+" "+cell.getX()+" "+cell.getY());
                        return false;
                    }
                    return true;
                }
            }
        }
        if(location instanceof CottageMap cottageMap){
            for (Cell cell : cottageMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock
                        || cell.getObjectMap() instanceof Wall || cell.getObjectMap() instanceof ForagingTree) {
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }
        if(location instanceof GreenhouseMap greenHouseMap){
            for (Cell cell : greenHouseMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock ||cell.getObjectMap() instanceof Wall ||cell.getObjectMap() instanceof ForagingTree) {
                        return false;
                    }
                    return true;
                }
            }
        }
        if(location instanceof CoopMap coopMap) {
            for (Cell cell : coopMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock || cell.getObjectMap() instanceof Wall) {
                        return false;
                    }
                    return true;
                }
            }
        }
        if(location instanceof BarnMap barnMap){
            for (Cell cell : barnMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock || cell.getObjectMap() instanceof Wall) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }


    public void handleCheatCode(Stage stage) {
        cheatMode = true;
        cheatCodeField.setVisible(true);
        stage.setKeyboardFocus(cheatCodeField);
        cheatCodeField.setText("");

        overlay = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager
            .getGameAssetManager()
            .getBackgroundTexture("Field3.png"))));

//        overlay.setColor(0, 0, 0, 0.5f);
        overlay.setSize(stage.getWidth(), stage.getHeight());
        overlay.setPosition(0, 0);

        overlay.getColor().a = 0;
        overlay.addAction(Actions.fadeIn(0.5f));


        stage.addActor(overlay);
        overlay.toBack();
    }

    public void handleVote(Stage stage, String name) {
        Gdx.app.postRunnable(() -> {
            voteMode = true;
            playerVoteLabel.setVisible(true);
            noVoteButton.setVisible(true);
            yesVoteButton.setVisible(true);
            howManyVotedLabel.setVisible(true);
            setTextForVoteLabel("Vote for kicking out "+name);
            howManyVotedLabel.setText("Vote: 0 / "+AppClient.getGameData().getPlayersData().size());

            overlay = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager
                .getGameAssetManager()
                .getBackgroundTexture("Field1.png"))));

    //        overlay.setColor(0, 0, 0, 0.5f);
            overlay.setSize(stage.getWidth(), stage.getHeight());
            overlay.setPosition(0, 0);

            overlay.getColor().a = 0;
            overlay.addAction(Actions.fadeIn(0.5f));


            stage.addActor(overlay);
            overlay.toBack();

        });
    }

    public void handleTerminate(Stage stage) {
        Gdx.app.postRunnable(() -> {
            terMode = true;
            terLabel.setVisible(true);
            ternoButton.setVisible(true);
            teryesButton.setVisible(true);
            terhowmanyLabel.setVisible(true);
            terhowmanyLabel.setText("Vote: 0 / "+AppClient.getGameData().getPlayersData().size());
            setTextForTerLabel("Vote for terminating the game");


            overlay = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager
                .getGameAssetManager()
                .getBackgroundTexture("Field1.png"))));

            //        overlay.setColor(0, 0, 0, 0.5f);
            overlay.setSize(stage.getWidth(), stage.getHeight());
            overlay.setPosition(0, 0);

            overlay.getColor().a = 0;
            overlay.addAction(Actions.fadeIn(0.5f));


            stage.addActor(overlay);
            overlay.toBack();

        });
    }

    public Hero getHero() {
        return hero;
    }
    public MenuBar getMenuBar() {
        return menuBar;
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        hudImage.setPosition(stage.getWidth() - hudImage.getWidth(), stage.getHeight() - hudImage.getHeight());
    }

    public TimeScreen getTimeScreen() {
        return timeScreen;
    }

    public void updateEnergy() {
        double energy = Objects.requireNonNull(Finder.getpd()).getEnergy();
        float percentage = (float) (energy / 200.0);

        energyGreenImage.remove();
        energyYellowImage.remove();
        energyRedImage.remove();

        float barWidth = energyBarImage.getWidth();
        float barHeight = energyBarImage.getHeight() * percentage * 0.75f;

        Image currentImage;

        if (energy <= 50) {
            currentImage = energyRedImage;
        } else if (energy <= 100) {
            currentImage = energyYellowImage;
        } else {
            currentImage = energyGreenImage;
        }

        currentImage.setSize(barWidth / 2, barHeight);
        currentImage.setPosition(stage.getWidth() - energyBarImage.getWidth() * 1.73f, energyBarImage.getHeight() * 1.53f);

        stage.addActor(currentImage);
        currentImage.toFront();
    }

    public boolean isHalt() {
        return halt;
    }

    public void setHalt(boolean halt) {
        this.halt = halt;
    }

    public InventoryRenderer getInventoryRenderer() {
        return inventoryRenderer;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

