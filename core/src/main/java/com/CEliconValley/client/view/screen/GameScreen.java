package com.CEliconValley.client.view.screen;

import com.CEliconValley.Main;
import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.model.AnimalSprite;
import com.CEliconValley.client.model.StrategyScoreboard;
import com.CEliconValley.client.view.screen.maps.*;
import com.CEliconValley.client.view.screen.menu.*;
import com.CEliconValley.common.CellData;
import com.CEliconValley.common.FarmData;
import com.CEliconValley.common.NPCData;
import com.CEliconValley.common.PlayerData;
import com.CEliconValley.common.messages.Emotion;
import com.CEliconValley.controllers.Spawner.InventoryRenderer;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Hero;
import com.CEliconValley.models.PlayerMessage;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.foragings.Nature.Obstacle;
import com.CEliconValley.models.foragings.Nature.Rock;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.locations.Location;
import com.CEliconValley.models.ui.CustomColors;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Objects;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public abstract class GameScreen implements Screen {
    protected final SpriteBatch batch;

    public boolean onRepeat = true;
    protected boolean flip = false;
    public boolean isMenuOpen = false;
    protected boolean isMarketMenuOpen = false;
    protected boolean isAnimalMenuOpen = false;
    protected boolean isArtisanMenuOpen = false;
    protected InventoryRenderer inventoryRenderer;
    protected boolean cheatMode = false;
    public boolean voteMode = false;
    public boolean chatMode = false;
    public boolean scoreboardMode = false;
    public boolean friendshipMode = false;
    public boolean reactionMode = false;
    public Image overlay;
    public TextField cheatCodeField;
    protected ShippingBinBar shippingBinBar;
    protected CraftInfo craftInfo;
    public TextButton yesVoteButton, noVoteButton;
    public Label playerVoteLabel;
    public Label howManyVotedLabel;


    protected AnimalSprite lastAnimal = null;

    public OrthographicCamera camera;

    public Label tagMessageLabel;
    public Label messageLabel;

    private TextField chatInput;
    private ScrollPane chatScrollPane;

    public TextButton teryesButton, ternoButton;
    public Label terLabel;
    public Label terhowmanyLabel;
    public boolean terMode = false;

    public boolean proposeMode = false;
    public TextButton proposeyesButton, proposenoButton;
    public Label proposeLabel;

    private Table scoreboardInfoTable;

    public boolean isGameFinished = false;

    protected Stage stage;
    protected Stage chatStage;
    protected Stage scoreboardStage;
    public Stage friendshipStage;
    public FriendshipStageHandler friendshipStageHandler;
    public CraftableMachine cm = null;
    public boolean sellmode = false;
    public boolean trashmode = false;

    public String lastProposer = null;
    public String lastCraftInfo = null;

    public abstract void transfer();
    public Hero hero;
    protected MenuBar menuBar = new MenuBar(this);
    protected ReactionBar reactionBar = new ReactionBar(this);
    protected MarketPlaceMenu marketPlaceMenu=new MarketPlaceMenu(this);
    protected AnimalMenu animalMenu=new AnimalMenu(this);
    protected ArtisanMenu artisanMenu = new ArtisanMenu(this);
    protected BarnOrCoopMenuBar barnOrCoopMenuBar = new BarnOrCoopMenuBar(this);
    protected Texture hudTexture = new Texture(Gdx.files.internal("game/Clock/Clock.png"));
    protected Image hudImage, energyBarImage, energyGreenImage, energyRedImage, energyYellowImage;
    protected TimeScreen timeScreen;
    protected Texture energyBarTexture = new Texture(Gdx.files.internal("game/EnergyBar/bar.png"));
    protected Texture energyGreenTexture = new Texture(Gdx.files.internal("game/EnergyBar/green.png"));
    protected Texture energyYellowTexture = new Texture(Gdx.files.internal("game/EnergyBar/yellow.png"));
    protected Texture energyRedTexture = new Texture(Gdx.files.internal("game/EnergyBar/red.png"));
    protected boolean halt = false;
    public boolean dcmode = false;
    public Label dcLabel;
    public Table chatTable;
    private Table sortButtonsTable;

    StrategyScoreboard ss = new StrategyScoreboard();

    public void setTextForVoteLabel(String input){
        playerVoteLabel.setText(input);
        playerVoteLabel.setPosition(stage.getWidth() / 3 - playerVoteLabel.getWidth() / 2, stage.getHeight() * 5 / 6- playerVoteLabel.getHeight() / 2);
    }

    public void setTextForTerLabel(String input){
        terLabel.setText(input);
        terLabel.setPosition(Gdx.graphics.getWidth() / 3f - terLabel.getWidth(), stage.getHeight() * 5 / 6- terLabel.getHeight() / 2);
    }
    public void setTextForProposeLabel(String input){
        proposeLabel.setText(input);
        proposeLabel.setPosition(Gdx.graphics.getWidth() / 3f - proposeLabel.getWidth(), stage.getHeight() * 5 / 6- proposeLabel.getHeight() / 2);
    }

    private void setupVoteUI(){
        playerVoteLabel = new Label("Vote", GameAssetManager.getGameAssetManager().getSkin());
        playerVoteLabel.setVisible(false);
        playerVoteLabel.setFontScale(2f);
        playerVoteLabel.setPosition(Gdx.graphics.getWidth() / 2f - playerVoteLabel.getWidth(), stage.getHeight() * 5 / 6- playerVoteLabel.getHeight() / 2);
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
        howManyVotedLabel.setPosition(Gdx.graphics.getWidth() / 2f - howManyVotedLabel.getWidth(), stage.getHeight() * 4 / 6- howManyVotedLabel.getHeight() / 2);
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
        terLabel.setPosition(Gdx.graphics.getWidth() / 2f - terLabel.getWidth(), stage.getHeight() * 5 / 6- terLabel.getHeight() / 2);
        stage.addActor(terLabel);
        terhowmanyLabel = new Label("Vote: 0 / "+AppClient.getGameData().getPlayersData().size(), GameAssetManager.getGameAssetManager().getSkin());
        terhowmanyLabel.setFontScale(1.5f);
        terhowmanyLabel.setPosition(Gdx.graphics.getWidth() / 2f - terhowmanyLabel.getWidth(), stage.getHeight() * 4 / 6- terhowmanyLabel.getHeight() / 2);
        terhowmanyLabel.setVisible(false);
        stage.addActor(terhowmanyLabel);

        proposenoButton = new TextButton("No", GameAssetManager.getGameAssetManager().getSkin());
        proposenoButton.setColor(Color.RED);
        proposenoButton.setVisible(false);
        proposeyesButton = new TextButton("Yes", GameAssetManager.getGameAssetManager().getSkin());
        proposeyesButton.setColor(Color.GREEN);
        proposeyesButton.setVisible(false);
        proposeyesButton.setPosition(stage.getWidth()* 3 / 4 - proposeyesButton.getWidth() / 2, stage.getHeight() / 2- proposeyesButton.getHeight() / 2);
        proposenoButton.setPosition(stage.getWidth()/4 - proposenoButton.getWidth() / 2, stage.getHeight() / 2 - proposenoButton.getHeight() / 2);
        stage.addActor(proposeyesButton);
        stage.addActor(proposenoButton);
        proposeLabel = new Label("Propose", GameAssetManager.getGameAssetManager().getSkin());
        proposeLabel.setVisible(false);
        proposeLabel.setFontScale(2f);
        proposeLabel.setPosition(Gdx.graphics.getWidth() / 2f - terLabel.getWidth(), stage.getHeight() * 5 / 6- terLabel.getHeight() / 2);
        stage.addActor(proposeLabel);






        dcLabel = new Label("oops someone got dced...", GameAssetManager.getGameAssetManager().getSkin());
        dcLabel.setFontScale(2f);
        dcLabel.setPosition(Gdx.graphics.getWidth() / 2f - dcLabel.getWidth(), stage.getHeight() * 5 / 6- dcLabel.getHeight() / 2);
        dcLabel.setVisible(false);
        stage.addActor(dcLabel);
    }

    public void updateChat(){
        chatTable.clear();
        chatTable = new Table();
        if(AppClient.getGameData() == null ) return;
        for(PlayerMessage message : AppClient.getGameData().getPlayerMessages()){
            assert AppClient.getUserData() != null;
            if(message.getSender().equals(AppClient.getUserData().getUsername())){
                Label label = new Label(message.getMessage() + "-", GameAssetManager.getGameAssetManager().getSkin());
                label.setWrap(true);
                label.setAlignment(Align.right);
                label.setColor(CustomColors.GAMEGREENCOLOR);
                chatTable.add(label).width(380).right().padBottom(5).row();
            } else{
                Label label = new Label(message.getSender() + ": " + message.getMessage(), GameAssetManager.getGameAssetManager().getSkin());
                label.setAlignment(Align.left);
                if(label.getText().toString().matches(".+@"+AppClient.getUserData().getUsername()+".+")){
                    label.setColor(Color.BLUE);
                }
                chatTable.add(label).width(380).left().padBottom(5).row();
            }
        }
        chatTable.row();
        chatTable.row().row();
        chatTable.padBottom(20);
        chatScrollPane.setActor(chatTable);
        chatScrollPane.setFadeScrollBars(false);
        chatScrollPane.setScrollingDisabled(true, false);
        Gdx.app.postRunnable(() -> {
            chatScrollPane.validate();
            chatScrollPane.setScrollPercentY(1f);
        });
        if(friendshipStageHandler != null){
            friendshipStageHandler.updateChat();
//            if(!friendshipStageHandler.isPlayer){
//                friendshipStageHandler.updateQuestsUI();
//            }
        }
    }

    private void setupChatUI(){
        chatTable = new Table();
        chatTable.setFillParent(true);
        for(PlayerMessage message : AppClient.getGameData().getPlayerMessages()){
            assert AppClient.getUserData() != null;
            if(message.getSender().equals(AppClient.getUserData().getUsername())){
                Label label = new Label(message.getMessage() + "-", GameAssetManager.getGameAssetManager().getSkin());
                label.setWrap(true);
                label.setAlignment(Align.right);
                label.setColor(CustomColors.GAMEGREENCOLOR);
                chatTable.add(label).width(380).right().padBottom(5).row();
            } else{
                Label label = new Label(message.getSender() + ": " + message.getMessage(), GameAssetManager.getGameAssetManager().getSkin());
                label.setWrap(true);
                label.setAlignment(Align.left);
                chatTable.add(label).width(380).left().padBottom(5).row();
            }
        }
        chatScrollPane = new ScrollPane(chatTable, GameAssetManager.getGameAssetManager().getSkin(), "hiddenScroll");
        chatScrollPane.setFadeScrollBars(false);
        chatScrollPane.setScrollingDisabled(true, false);

        chatInput = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
        chatInput.setMessageText("Type a message...");
        chatInput.setMaxLength(200);

        Table container = new Table();
        container.setFillParent(true);
        container.bottom().left().pad(10);
        container.add(chatScrollPane).width(400).height(400).row();
        container.add(chatInput).width(400).height(60).padTop(20);

        container.setPosition(stage.getWidth() / 2 - 200,
            stage.getHeight() / 2 - 200);
        chatStage.addActor(container);
    }

    public void updateScoreboard(){
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        if(scoreboardInfoTable == null){
            return;
        }
        scoreboardInfoTable.clear();
        scoreboardInfoTable.add(sortButtonsTable).colspan(4).padBottom(20).row();

        scoreboardInfoTable.add(new Label("Player", skin)).pad(10);
        scoreboardInfoTable.add(new Label("Money", skin)).pad(10);
        scoreboardInfoTable.add(new Label("Missions", skin)).pad(10);
        scoreboardInfoTable.add(new Label("Skills", skin)).pad(10);
        scoreboardInfoTable.row();

        ss.updateScoreboard(scoreboardInfoTable);


    }

    private void setupScoreboardUI(){

        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        scoreboardInfoTable = new Table();
        scoreboardInfoTable.setFillParent(true);

        scoreboardInfoTable.center().top().padTop(300);

        sortButtonsTable = new Table();
        TextButton sortMoneyBtn = new TextButton("Sort by Money", skin);
        sortMoneyBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ss.setStrategyScore(0);
                updateScoreboard();
            }
        });

        TextButton sortMissionsBtn = new TextButton("Sort by Missions", skin);
        sortMissionsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ss.setStrategyScore(1);
                updateScoreboard();
            }
        });

        TextButton sortSkillsBtn = new TextButton("Sort by Skills", skin);
        sortSkillsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ss.setStrategyScore(2);
                updateScoreboard();
            }
        });

        sortButtonsTable.add(sortMoneyBtn).padRight(10);
        sortButtonsTable.add(sortMissionsBtn).padRight(10);
        sortButtonsTable.add(sortSkillsBtn);



        updateScoreboard();
        scoreboardStage.addActor(scoreboardInfoTable);
    }


    public GameScreen(InventoryRenderer inventoryRenderer) {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        stage = new Stage(new ScreenViewport(), Main.getBatch());
        chatStage = new Stage(new ScreenViewport(), Main.getBatch());
        scoreboardStage = new Stage(new ScreenViewport(), Main.getBatch());
        friendshipStage = new Stage(new ScreenViewport(), Main.getBatch());
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
        timeScreen.goldLabel.setPosition(posX + 67f, posY + 10);
        timeScreen.getHudTable().setPosition(0, -stage.getHeight() / 21f);
        energyBarImage.setPosition(stage.getWidth() - energyBarImage.getWidth() * 2, energyBarImage.getHeight() * 1.5f);
        timeScreen.goldLabel.setAlignment(Align.left);
        timeScreen.goldLabel.setFontScale(1.18f);
        timeScreen.dateLabel.setFontScale(0.8f);

        Texture labelTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture("Info_Background1.png");
//        TextureRegionDrawable background = new TextureRegionDrawable(new TextureRegion(labelTexture));

        NinePatch ninePatch = new NinePatch(labelTexture, 40, 40, 0, 0);
        NinePatchDrawable background = new NinePatchDrawable(ninePatch);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont();
        style.font.getData().setScale(2f);
        style.background = background;

        tagMessageLabel = new Label("", style);
        // A space forces layout
        tagMessageLabel.pack();
        tagMessageLabel.setPosition(stage.getWidth() / 2 - tagMessageLabel.getWidth() / 2, stage.getHeight() / 1.2f);
        tagMessageLabel.setColor(CustomColors.SWAMP_COLOR);
        tagMessageLabel.setVisible(false);

        messageLabel = new Label("", style);
        messageLabel.pack();
        messageLabel.setPosition(stage.getWidth() / 2 - tagMessageLabel.getWidth() / 2, stage.getHeight() / 1.2f);
        messageLabel.setColor(CustomColors.GAMEGREENCOLOR);
        messageLabel.setVisible(false);

        stage.addActor(tagMessageLabel);
        stage.addActor(messageLabel);
        stage.addActor(timeScreen.dateLabel);
        stage.addActor(timeScreen.timeLabel);
        stage.addActor(timeScreen.goldLabel);
        stage.addActor(timeScreen.getHudTable());
        stage.addActor(energyBarImage);
        this.hero = new Hero();

        setupChatUI();
        setupScoreboardUI();
    }


    public boolean canMoveTo(int x, int y, Location location) {
        if(location instanceof FarmMap farmMap){
            for (CellData cd : farmMap.farmData.getCells()) {
                if (cd.getX() == x && cd.getY() == y) {
                    Cell cell = cd.extractData();
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock ||cell.getObjectMap() instanceof Wall ||cell.getObjectMap() instanceof Obstacle) {
                        return false;
                    }

                    if(cell.getObjectMap() instanceof Door){
                        for (int i = -2; i <= 2 ; i++) {
                            for (int j = -2; j <= 2 ; j++) {
                                int testx = cell.getX()+i;
                                int testy = cell.getY()+j;
                                FarmData fd= Finder.getfd();
                                if(fd.getGreenhouseX() < testx && testx < fd.getGreenhouseX()+Greenhouse.getGreenhouseLength()
                                && fd.getGreenhouseY() < testy && testy < fd.getGreenhouseY()+Greenhouse.getGreenhouseHeight()
                                ){
                                    if(!fd.isGreenHouseUnlocked()){
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                    return true;
                }
            }
            return false;
        }else if(location instanceof VillageMap villageMap){
//            System.out.println("checking villagemap");
            for (CellData cd : villageMap.villageData.getCellsData()) {
                if (cd.getX() == x && cd.getY() == y) {
                    Cell cell = cd.extractData();
                    if (cell.getObjectMap() instanceof Lake ||( cell.getObjectMap() instanceof Grass grass && !grass.isGround() )
                        ||cell.getObjectMap() instanceof Wall ||cell.getObjectMap() instanceof Obstacle) {
//                        System.out.println(cd.getObjectName()+" "+cell.getX()+" "+cell.getY());
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }
        if(location instanceof CottageMap cottageMap){
//            System.out.println("checking cottagemap");
            for (Cell cell : cottageMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    return !(cell.getObjectMap() instanceof Lake) && !(cell.getObjectMap() instanceof Rock)
                        && !(cell.getObjectMap() instanceof Wall) && !(cell.getObjectMap() instanceof ForagingTree);
                }
            }
            return false;
        }
        if(location instanceof GreenhouseMap greenHouseMap){
//            System.out.println("checking greenhousemap");
            for (Cell cell : greenHouseMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    return !(cell.getObjectMap() instanceof Lake) && !(cell.getObjectMap() instanceof Rock) && !(cell.getObjectMap() instanceof Wall) && !(cell.getObjectMap() instanceof ForagingTree);
                }
            }
            return false;
        }
        if(location instanceof CoopMap coopMap) {
//            System.out.println("checking coopmap");
            for (Cell cell : coopMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    return !(cell.getObjectMap() instanceof Lake) && !(cell.getObjectMap() instanceof Rock) && !(cell.getObjectMap() instanceof Wall);
                }
            }
            return false;
        }
        if(location instanceof BarnMap barnMap){
//            System.out.println("checking barnmap");
            for (Cell cell : barnMap.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
//                    System.out.println("reason "+cell.getObjectMap().getName());
//                    System.out.println(" "+!(cell.getObjectMap() instanceof Lake) );
//                    System.out.println(" "+!(cell.getObjectMap() instanceof Rock));
//                    System.out.println(" "+!(cell.getObjectMap() instanceof Wall));
//                    System.out.println(!(cell.getObjectMap() instanceof Lake) && !(cell.getObjectMap() instanceof Rock) && !(cell.getObjectMap() instanceof Wall));
                    return !(cell.getObjectMap() instanceof Lake) && !(cell.getObjectMap() instanceof Rock) && !(cell.getObjectMap() instanceof Wall);
                }
            }
            return false;
        }
        return false;
    }


    public void handleCheatCode(Stage stage) {
        cheatMode = true;
        cheatCodeField.setVisible(true);
        cheatCodeField.setMessageText("Enter Cheat Code");
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

    public void handleChatMode(Stage stage) {
        chatMode = true;
        chatInput.setVisible(true);
        chatScrollPane.setVisible(true);
        stage.setKeyboardFocus(chatInput);
        chatInput.setText("");

        Gdx.input.setInputProcessor(stage);

        overlay = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager
            .getGameAssetManager()
            .getBackgroundTexture("Chat_Background.png"))));

        overlay.setSize(stage.getWidth(), stage.getHeight());
        overlay.setPosition(0, 0);

        overlay.getColor().a = 0;
        overlay.addAction(Actions.fadeIn(0.5f));


        stage.addActor(overlay);
        overlay.toBack();
    }

    public void handleScoreboard(Stage stage) {
        scoreboardMode = true;
        Gdx.input.setInputProcessor(stage);

        overlay = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager
            .getGameAssetManager()
            .getBackgroundTexture("Scoreboard_Background.png"))));

        overlay.setSize(stage.getWidth(), stage.getHeight());
        overlay.setPosition(0, 0);
        overlay.getColor().a = 0;
        overlay.addAction(Actions.fadeIn(0.5f));
        stage.addActor(overlay);
        updateScoreboard();
        overlay.toBack();
    }

    public void handleFriendship(Stage stage, PlayerData playerData, NPCData npcData) {
        friendshipStageHandler = new FriendshipStageHandler(this, stage, playerData, npcData);
        friendshipMode = true;
        Gdx.input.setInputProcessor(stage);

        overlay = new Image(new TextureRegionDrawable(new TextureRegion(GameAssetManager
            .getGameAssetManager().getBackgroundTexture("Friendship_Background.png"))));

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
    public void handlePropose(Stage stage, String username) {
        Gdx.app.postRunnable(() -> {
            proposeMode = true;
            proposeLabel.setVisible(true);
            proposenoButton.setVisible(true);
            proposeyesButton.setVisible(true);
            setTextForProposeLabel(username+ "has proposed D:");
            lastProposer = username;


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


            PlayerActs.showProposalUI();
        });
    }

    public void handledc(Stage stage) {
        Gdx.app.postRunnable(() -> {
            dcmode = true;
            dcLabel.setVisible(true);

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
    public ArtisanMenu getArtisanMenu() {return artisanMenu;}
    public BarnOrCoopMenuBar getBarnOrCoopMenuBar() {
        return barnOrCoopMenuBar;
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

    public boolean isDcmode() {
        return dcmode;
    }

    public void setDcmode(boolean dcmode) {
        this.dcmode = dcmode;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getChatStage() {
        return chatStage;
    }

    public TextField getChatInput() {
        return chatInput;
    }

    public void updateTagMessage(String message) {
        tagMessageLabel.setText(message);
        tagMessageLabel.setVisible(true);
        tagMessageLabel.setPosition(stage.getWidth() / 2 - tagMessageLabel.getWidth() / 2, stage.getHeight() / 1.2f);
        tagMessageLabel.pack();
    }
    public void removeTagMessage(){
        tagMessageLabel.setText("");
        tagMessageLabel.setVisible(false);
    }

    public void updateMessage(String message, Color color) {
        messageLabel.setColor(color);
        messageLabel.setText(message);
        messageLabel.setVisible(true);
        messageLabel.setPosition(stage.getWidth() / 2 - messageLabel.getWidth() / 2, stage.getHeight() / 1.2f);
        messageLabel.pack();
    }
    public void removeMessage(){
        messageLabel.setText("");
        messageLabel.setVisible(false);
    }
    public MarketPlaceMenu getMarketPlaceMenu() {
        return marketPlaceMenu;
    }
    public AnimalMenu getAnimalMenu() {
        return animalMenu;
    }

    public void setLastAnimal(AnimalSprite lastAnimal) {
        this.lastAnimal = lastAnimal;
    }

    public void showTexture(float renderx, float rendery, Emotion emotion){
        TextureRegion emote = GameAssetManager.getGameAssetManager().getEmote(emotion.index);
        float ratio =(float) emote.getRegionWidth() / emote.getRegionHeight();
        batch.draw(emote, renderx, rendery + CELL_SIZE, ratio * CELL_SIZE , CELL_SIZE);
    }
}

