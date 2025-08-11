package com.CEliconValley.client.view.screen;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.*;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.VoteMessage;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.*;
import com.CEliconValley.models.items.*;
import com.CEliconValley.models.tools.Tool;
import com.CEliconValley.models.ui.CustomColors;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class MenuBar {
    private final Texture menuTexture;
    private final Texture miniMapTexture;
    private final Texture characterTexture;
    private final Texture infoTexture;
    private final Texture heartTexture;
    private final Texture emptyHeartTexture;
    private final Texture giftTexture;
    private final TextureRegion[] tabTextures;
    private final int tileWidth;
    private final int tileHeight;
    private Player player;
    BitmapFont font = new BitmapFont();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private int startingRow = 0;
    private float startingX;
    private float startingY;
    private String currentTab;
    private OrthographicCamera camera;
    private boolean camSet = false;
    private final ArrayList<Texture> relationTextures = new ArrayList<>();
    private final Texture mohsenAvatarTexture;
    private final ArrayList<NPCData> NPCsData;
    private final ArrayList<Integer> relationsIndex = new ArrayList<>();

    private final int visibleRelationsCount = 2;
    private int selectedIndex = 0;

    private GameScreen screen;
    private final String[] tabOrder = {
        "Inventory", "Stats", "Relation",
        "Map", "Crafting", "Food",
        "Control", "Vote", null
    };

    public MenuBar(GameScreen screen) {
        this.screen = screen;
        menuTexture = GameAssetManager.getGameAssetManager().getScreenTexture("Menu_Screen.png");
        miniMapTexture = GameAssetManager.getGameAssetManager().getScreenTexture("map.jpg");
        characterTexture = GameAssetManager.getGameAssetManager().getScreenTexture("character.png");
        infoTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture("Info_Background1.png");
        heartTexture = GameAssetManager.getGameAssetManager().getInventoryTexture("relations/Heart.png");
        emptyHeartTexture = GameAssetManager.getGameAssetManager().getInventoryTexture("relations/Empty_Heart.png");
        giftTexture = GameAssetManager.getGameAssetManager().getInventoryTexture("relations/Gift.png");


        int i = 0;
        for(PlayerData playerData : AppClient.getGameData().getPlayersData()){
            assert AppClient.getUserData() != null;
            if(playerData.getUsername().equals(AppClient.getUserData().getUsername())){
                i++;
                continue;
            }
            relationsIndex.add(i);
            relationTextures.add(new Texture(playerData.getAvatarPath()));
            i++;
        }

        mohsenAvatarTexture = GameAssetManager.getGameAssetManager().getNPCTexture("Mohsen", "Mohsen.png");

        NPCsData = AppClient.getGameData().getVillageData().getNPCsData();


        tileWidth = menuTexture.getWidth() / 3;
        tileHeight = menuTexture.getHeight() / 3;


        TextureRegion[][] split = TextureRegion.split(menuTexture, tileWidth, tileHeight);


        tabTextures = new TextureRegion[9];

        tabTextures[0] = split[0][1];
        tabTextures[1] = split[0][2];
        tabTextures[2] = split[1][0];
        tabTextures[3] = split[1][1];
        tabTextures[4] = split[1][2];
        tabTextures[5] = split[2][0];
        tabTextures[6] = split[2][1];
        tabTextures[7] = split[2][2];

        currentTab = "Inventory";
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void render(Batch batch, OrthographicCamera camera) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        Inventory inventory = Finder.getpd().getInventoryData().getInventory();
        this.camera = camera;

        if(screen instanceof GreenHouseScreen && !camSet){
            camSet = true;
//            camera.zoom *= 2;
        }

        float menuWidth = screenWidth * 0.6f;
        float menuHeight = screenHeight * 0.7f;
        startingX = camera.position.x - menuWidth / 2f;
        startingY = camera.position.y - menuHeight / 2f;

        int tabIndex = getTabIndex(currentTab);
        if (tabIndex >= 0 && tabIndex < tabTextures.length && tabTextures[tabIndex] != null) {
            batch.draw(tabTextures[tabIndex], startingX, startingY, menuWidth, menuHeight);
        }

        switch (currentTab) {
            case "Crafting":
            case "Food":
            case "Control":
                renderInventoryBar(batch, camera, inventory);
                break;
            case "Inventory":
                renderInventoryBar(batch, camera, inventory);
                renderSelectedItem(batch);
                break;
            case "Vote":
                renderVote(batch);
                break;
            case "Stats":
                renderStats(batch);
                break;
            case "Relation":
                renderRelations(batch);
                break;
            case "Map":
                renderMap(batch);
                break;

        }
        switch (currentTab) {
            case "Crafting":
                renderCrafting(batch);
                break;
            case "Food":
                renderCooking(batch);
        }
    }


    private void renderInventoryBar(Batch batch, OrthographicCamera camera, Inventory inventory) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float firstItemX = screenWidth * 0.0370f;
        float firstItemY = screenHeight * 0.5225f;

        int row = 0, startPoint = 0;
        float slotSize = screenWidth * 0.035f;

        float spacingX = slotSize * 0.275f;
        float spacingY = slotSize * 0.525f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        for (int col = 0; row < 3; ) {
            int index = col + (startingRow + row) * 12;
            if (inventory.getBackpack().getSize() > index) {
                Slot slot = inventory.getSlots().get(index);
                Item item = slot.getItem();
                TextureRegion texture;

                if (item != null) {
                    texture = ItemManager.getTexture(item);
                    if (texture != null) {
                        float x = startingX + firstItemX + col * (slotSize + spacingX);
                        float y = startingY + firstItemY - row * (slotSize + spacingY) * 0.9f;
//                        batch.draw(texture, x, y, slotSize, slotSize);
                        float originalWidth = texture.getRegionWidth() * 0.9f;
                        float originalHeight = texture.getRegionHeight() * 0.9f;

                        float aspectRatio = originalWidth / originalHeight;

                        float drawWidth, drawHeight;

                        if (originalWidth > originalHeight) {
                            drawWidth = slotSize;
                            drawHeight = slotSize / aspectRatio;
                        } else {
                            drawHeight = slotSize;
                            drawWidth = slotSize * aspectRatio;
                        }

                        float drawX = x + (slotSize - drawWidth) / 2f;
                        float drawY = y + (slotSize - drawHeight) / 2f;

                        batch.draw(texture, drawX, drawY, drawWidth, drawHeight);


                        if (slot.getQuantity()>1) {
                            font.getData().setScale(2.5f);
                            String amountText = String.valueOf(slot.getQuantity());
                            GlyphLayout layout = new GlyphLayout(font, amountText);
                            float textX = drawX + slotSize / 2f;
                            float textY = drawY + layout.height / 3f;
                            font.draw(batch, layout, textX, textY);
                            font.getData().setScale(1f);
                        }


                        if (mousePos.x >= x && mousePos.x <= x + slotSize &&
                            mousePos.y >= y && mousePos.y <= y + slotSize) {
                            if (Gdx.input.isButtonJustPressed(0)) {
                                if (item instanceof Tool) {
                                    assert AppClient.getUserData() != null;
                                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                        new GameCommand("tools equip " + item.getName(),
                                            AppClient.getUserData().getUsername())
                                    );
                                    AppClient.getClient().send(new Gson().toJson(msg));
                                }else if(Food.parseFood(item.getName()) != null){
                                    assert AppClient.getUserData() != null;
                                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                                        new GameCommand("eat " + item.getName(),
                                            AppClient.getUserData().getUsername())
                                    );
                                    AppClient.getClient().send(new Gson().toJson(msg));
                                    screen.isMenuOpen = !screen.isMenuOpen;
                                    screen.onRepeat = false;
                                    screen.hero.currentAnimation = screen.hero.eat();
                                    screen.hero.isActing.set(true);
                                    screen.hero.stateTime = 0;
                                }
                            }
                            else if(Gdx.input.isButtonJustPressed(1)){
                                screen.hero.selectedItemName = item.getName();
                            }

                            String name = readableName(item.getName());
                            GlyphLayout layout = new GlyphLayout(font, name);
                            float tooltipWidth = layout.width + 20;
                            float tooltipHeight = layout.height + 10;

                            float tooltipX = x + slotSize / 2f - tooltipWidth / 2f;
                            float tooltipY = y + slotSize + 10;


                            batch.end();
                            shapeRenderer.setProjectionMatrix(camera.combined);
                            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                            shapeRenderer.setColor(0, 0, 0, 0.8f);
                            shapeRenderer.rect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                            shapeRenderer.end();
                            batch.begin();

                            font.draw(batch, layout, tooltipX + 10, tooltipY + tooltipHeight - 5);
                        }
                    }
                }
            }

            col++;
            if (col == 12) {
                col = 0;
                row++;
                if (row == 2) {
                    startingY += 22;
                }
            }
        }
    }

    private void renderSelectedItem(Batch batch) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        if(Finder.getpd().getCurrentToolName() != null){
            Tool tool = (Tool) Finder.parseItem(Finder.getpd().getCurrentToolName());
            TextureRegion texture1 = ItemManager.getTexture(tool.getID());
            if (texture1 != null){
                float drawX = startingX + screenWidth / 10f;
                float drawY = startingY + screenHeight * 0.075f;
                float width = screenWidth * 0.1f;
                float height = screenWidth * 0.1f;

                batch.draw(texture1, drawX, drawY - 30, width, height);
                batch.setColor(1, 1, 1, 1);

                String itemText = "Selected Tool: " + Finder.getpd().getCurrentToolName();

                font.getData().setScale(2f);
                font.draw(batch, itemText,
                    drawX + width / 2 - itemText.length() * 7.5f, drawY + height + 20);
                font.getData().setScale(1f);
            }
        }

        if(screen.hero.selectedItemName == null){
            return;
        }
        Item item = Finder.parseItem(screen.hero.selectedItemName);
        TextureRegion texture2 = ItemManager.getTexture(item.getID());
        if (texture2 == null) return;

        float drawX = startingX + screenWidth / 2.5f;
        float drawY = startingY + screenHeight * 0.075f;
        float width = screenWidth * 0.1f * texture2.getRegionWidth() / texture2.getRegionHeight();
        float height = screenWidth * 0.1f;

        batch.draw(texture2, drawX, drawY - 30, width, height);
        batch.setColor(1, 1, 1, 1);

        String itemText = "Selected Item: " + screen.hero.selectedItemName;

        font.getData().setScale(2f);
        font.draw(batch, itemText,
            drawX + width / 2 - itemText.length() * 7.5f, drawY + height + 20);
        font.getData().setScale(1f);

    }

    private void renderStats(@NotNull Batch batch) {
        Texture farmingIcon = GameAssetManager.getGameAssetManager().getSkillTexture("Farming_Skill_Icon.png");
        Texture fishingIcon = GameAssetManager.getGameAssetManager().getSkillTexture("Fishing_Skill_Icon.png");
        Texture foragingIcon = GameAssetManager.getGameAssetManager().getSkillTexture("Foraging_Skill_Icon.png");
        Texture miningIcon = GameAssetManager.getGameAssetManager().getSkillTexture("Mining_Skill_Icon.png");

        Texture emptyPoint = GameAssetManager.getGameAssetManager().getSkillTexture("Empty_Point.png");
        Texture grandEmptyPoint = GameAssetManager.getGameAssetManager().getSkillTexture("Grand_Empty_Point.png");
        Texture achievedPoint = GameAssetManager.getGameAssetManager().getSkillTexture("Achieved_Point.png");
        Texture grandAchievedPoint = GameAssetManager.getGameAssetManager().getSkillTexture("Grand_Achieved_Point.png");

        Texture avatarTexture = GameAssetManager.getGameAssetManager().getAvatarTexture();

        int farming = Finder.getpd().getFarmingSkill().getLevel();
        int fishing = Finder.getpd().getFishingSkill().getLevel();
        int foraging = Finder.getpd().getForagingSkill().getLevel();
        int mining = Finder.getpd().getMiningSkill().getLevel();

        String[] names = { "Farming", "Fishing", "Foraging", "Mining" };
        Texture[] icons = { farmingIcon, fishingIcon, foragingIcon, miningIcon };
        int[] levels = { farming, fishing, foraging, mining };
        String[] tooltips = {
            "Levels are gained by harvesting crops and caring for animals.\nEach level grants +1 hoe and watering can proficiency.",
            "Fishing is increased by catching fish or using crab pots.\nEach level grants +1 fishing rod proficiency.",
            "Foraging skill increases by collecting goods and chopping trees.\nEach level grants +1 axe proficiency.",
            "Mining skill is increased by breaking rocks.\nEach level grants +1 pickaxe proficiency."
        };

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float x = startingX + screenWidth * 0.08f;
        float y = startingY + screenHeight * 0.5f;

        float rowSpacing = screenHeight * 0.10f;
        float iconSize = screenHeight * 0.07f;
        float pointSize = iconSize * 0.45f;
        float spacingBetweenPoints = pointSize * 1.2f;

        batch.draw(avatarTexture, x, startingY + screenHeight * 0.2f, iconSize * 3, iconSize * 3);

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        for (int i = 0; i < names.length; i++) {
            float iconX = x + screenWidth * 0.17f;
            float iconY = y - iconSize + 5;

            font.getData().setScale(2f);
            font.draw(batch, names[i], iconX, iconY - 5);
            batch.draw(icons[i], iconX, iconY, iconSize, iconSize);

            boolean hovered = mousePos.x >= iconX && mousePos.x <= iconX + iconSize &&
                mousePos.y >= iconY && mousePos.y <= iconY + iconSize;

            for (int j = 0; j < 5; j++) {
                boolean isAchieved = j <= levels[i];
                boolean isGrand = j == 4;
                float px = iconX + iconSize + j * spacingBetweenPoints + 15;
                float py = iconY + iconSize / 2f - pointSize / 2f;

                Texture pointTexture = isGrand ?
                    (isAchieved ? grandAchievedPoint : grandEmptyPoint) :
                    (isAchieved ? achievedPoint : emptyPoint);

                if (j == 4) {
                    batch.draw(pointTexture, px, py, pointSize * 1.5f, pointSize);
                } else {
                    batch.draw(pointTexture, px, py, pointSize, pointSize);
                }
            }

            String levelText = String.valueOf(levels[i] + 1);
            GlyphLayout layout = new GlyphLayout(font, levelText);
            float tx = x + screenWidth * 0.9f - layout.width;
            float ty = y;
            font.draw(batch, layout, tx, ty);

            if (hovered) {
                String tooltipText = tooltips[i];
                GlyphLayout tooltipLayout = new GlyphLayout(font, tooltipText);

                float tooltipWidth = tooltipLayout.width + 40;
                float tooltipHeight = tooltipLayout.height + 30;

                float tooltipX = iconX;
                float tooltipY = iconY + iconSize + 20;

                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.85f);
                shapeRenderer.rect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                shapeRenderer.end();
                batch.begin();

                font.draw(batch, tooltipText, tooltipX + 20, tooltipY + tooltipHeight - 15);
            }

            y -= rowSpacing;
        }

        font.getData().setScale(1f);
    }

    private void renderRelations(Batch batch) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;
        this.camera = camera;

        float menuWidth = screenWidth * 0.6f;
        float menuHeight = screenHeight * 0.7f;
        startingX = camera.position.x - menuWidth / 2f;
        startingY = camera.position.y - menuHeight / 2f;

        float x = startingX + screenWidth * 0.025f;
        float y = startingY + screenHeight * 0.6f;

        float spacing = screenWidth * 0.2f;
        float characterSize = screenHeight * 0.2f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);



        int endIndex = Math.min(selectedIndex + visibleRelationsCount, 9);
        for (int i = selectedIndex; i < endIndex; i++) {
            int friendShipLevel = 0;
            float characterX = x;
            float characterY = y - characterSize + 5;
            if(i < relationsIndex.size()) {
                PlayerData playerData = AppClient.getGameData().getPlayersData().get(relationsIndex.get(i));
                String username = playerData.getUsername();

                for(FriendshipData friendshipData : Objects.requireNonNull(Finder.getpd()).getFriendshipsData()){
                    if(friendshipData.getPlayer1Name().equals(username) || friendshipData.getPlayer2Name().equals(username)) {
                        friendShipLevel = friendshipData.getLevel();
                    }
                }

                batch.draw(relationTextures.get(i), characterX, characterY, characterSize, characterSize);

                boolean hovered = mousePos.x >= characterX && mousePos.x <= characterX + characterSize &&
                    mousePos.y >= characterY && mousePos.y <= characterY + characterSize;

                boolean clicked = false;

                if (hovered) {
                    if (Gdx.input.isButtonJustPressed(0)) {
                        clicked = true;
                    }

                    GlyphLayout tooltipLayout = new GlyphLayout(font, username);

                    float playerNameWidth = tooltipLayout.width + 40;
                    float playerNameHeight = tooltipLayout.height + 30;

                    float playerNameX = characterX + characterSize / 2f - playerNameWidth / 2f;
                    float playerNameY = characterY + characterSize;

                    batch.end();
                    shapeRenderer.setProjectionMatrix(camera.combined);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(0, 0, 0, 0.85f);
                    shapeRenderer.rect(playerNameX, playerNameY, playerNameWidth, playerNameHeight);
                    shapeRenderer.end();
                    batch.begin();

                    font.setColor(CustomColors.SWAMP_COLOR);

                    font.draw(batch, username, playerNameX + 20, playerNameY + playerNameHeight - 15);

                    font.setColor(Color.WHITE);
                }
                if (clicked) {
                    screen.friendshipMode = true;
                    screen.handleFriendship(screen.friendshipStage, playerData, null);
                }
            } else{
                NPCData npcData = NPCsData.get(i - relationsIndex.size());
                String NPCname = npcData.getName();

                friendShipLevel = npcData.getFriendShipData().get(Objects.requireNonNull(Finder.getpd()).getUsername());

                if(npcData.getName().equals("Mohsen")) {
                    batch.draw(mohsenAvatarTexture, characterX, characterY, characterSize, characterSize);
                }else{
                    batch.draw(GameAssetManager.getGameAssetManager().getNPCImage(npcData.getName(), friendShipLevel),
                        characterX, characterY, characterSize, characterSize);
                }

                boolean hovered = mousePos.x >= characterX && mousePos.x <= characterX + characterSize &&
                    mousePos.y >= characterY && mousePos.y <= characterY + characterSize;

                boolean clicked = false;

                if (hovered) {
                    if (Gdx.input.isButtonJustPressed(0)) {
                        clicked = true;
                    }

                    GlyphLayout tooltipLayout = new GlyphLayout(font, NPCname);

                    float NPCNameWidth = tooltipLayout.width + 40;
                    float NPCNameHeight = tooltipLayout.height + 30;

                    float NPCNameX = characterX + characterSize / 2f - NPCNameWidth / 2f;
                    float NPCNameY = characterY + characterSize;

                    batch.end();
                    shapeRenderer.setProjectionMatrix(camera.combined);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(0, 0, 0, 0.85f);
                    shapeRenderer.rect(NPCNameX, NPCNameY, NPCNameWidth, NPCNameHeight);
                    shapeRenderer.end();
                    batch.begin();

                    font.setColor(CustomColors.SWAMP_COLOR);

                    font.draw(batch, NPCname, NPCNameX + 20, NPCNameY + NPCNameHeight - 15);

                    font.setColor(Color.WHITE);
                }
                if (clicked) {
                    screen.friendshipMode = true;
                    screen.handleFriendship(screen.friendshipStage, null, npcData);
                }
            }

            float itemSize = characterSize / 4;

            characterX += screenWidth / 7.5f;
            characterY += itemSize / 4f;

            String friendShipState;

            for(int j = 0; j < friendShipLevel; j++){
                batch.draw(heartTexture, characterX, characterY, itemSize, itemSize);
                characterX += screenWidth / 20f;
            }
            if(i < relationsIndex.size()) {
                for(int j = 0; j < 4 - Math.max(friendShipLevel, 0); j++){
                    batch.draw(emptyHeartTexture, characterX, characterY, itemSize, itemSize);
                    characterX += screenWidth / 20f;
                }

                friendShipState = FriendshipLevel.values()[friendShipLevel].name();

            } else{
                for(int j = 0; j < 3 - friendShipLevel; j++){
                    batch.draw(emptyHeartTexture, characterX, characterY, itemSize, itemSize);
                    characterX += screenWidth / 20f;
                }

                if(friendShipLevel == 0) {
                    friendShipState = "Stranger";
                } else if(friendShipLevel == 1) {
                    friendShipState = "Friend";
                } else if(friendShipLevel == 2) {
                    friendShipState = "Close Friend";
                } else{
                    friendShipState = "Best Friend";
                }
            }

            characterX += screenWidth / 15f;

            GlyphLayout tooltipLayout = new GlyphLayout(font, friendShipState);

            float friendShipLevelWidth = tooltipLayout.width + 40;
            float playerNameHeight = tooltipLayout.height + 30;

            float friendShipLevelX = characterX + itemSize / 2f - friendShipLevelWidth / 2f;
            float friendShipLevelY = characterY;


            font.getData().setScale(2f);

            font.setColor(CustomColors.GAMEGREENCOLOR);

            font.draw(batch, friendShipState, friendShipLevelX + 20, friendShipLevelY + playerNameHeight - 15);

            font.setColor(Color.WHITE);

            font.getData().setScale(1f);

            y -= spacing;

        }
    }

    private void renderMap(Batch batch) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;
        batch.draw(miniMapTexture, startingX + screenWidth / 80f, startingY + screenHeight / 60f, menuTexture.getWidth() / 2.32f, menuTexture.getHeight() / 3f);
        int farmId = Finder.getpd().getFarmId();
        float characterX = startingX + Finder.getpd().getX() * screenWidth / 350f, characterY = startingY + Finder.getpd().getY() * screenHeight / 420f;
        if(Finder.getpd().isPlayerInVillage()){
            characterX += screenWidth / 4f;
            characterY += screenHeight / 4f;
            batch.draw(characterTexture, characterX, characterY, characterTexture.getWidth() / 2f, characterTexture.getHeight() / 2f);
        }
        if(farmId == 1){
            characterX += screenWidth / 2.5f;
            characterY += screenHeight / 2.5f;
        } else if(farmId == 0){
            characterY += screenHeight / 2.5f;
        } else if(farmId == 3){
            characterX += screenWidth / 2.5f;
        }
        //TODO If Village!
        batch.draw(characterTexture, characterX, characterY, characterTexture.getWidth() / 2f, characterTexture.getHeight() / 2f);
    }

    private void renderCrafting(Batch batch) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float minX = screenWidth / 25f * 1.3f;
        float maxX = screenWidth / 2.5f * 1.3f;
        float minY = screenHeight / 40f * 1.7f;
        float maxY = screenHeight / 10f * 1.7f;

        float currentX = minX;
        float currentY = maxY;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        if(!(screen instanceof CottageScreen)){
            font.getData().setScale(2f);
            font.setColor(Color.RED);

            GlyphLayout layout = new GlyphLayout(font, "You Should Go Home To Use This!");
            font.draw(batch, layout,  startingX + screenWidth / 3.2f - layout.width / 2, startingY + maxY + screenHeight / 9f);
            font.setColor(Color.WHITE);
            font.getData().setScale(1f);
        }

        for (CraftableMachine machine : CraftableMachine.values()) {
            TextureRegion texture = ItemManager.getTexture(machine);
            if (texture == null) continue;

            if (currentX > maxX) {
                float width = screenWidth * 0.02f;
                currentX = minX + width;
                currentY -= screenHeight / 15f * 1.7f;

                if (currentY < minY) break;
            }

            float drawX = startingX + currentX;
            float drawY = startingY + currentY;
            float width = screenWidth * 0.02f;
            float height = width * 2f;


            if (!Finder.getpd().getCraftingRecipes().contains(machine.getRecipe())) {
                batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
            }

            batch.draw(texture, drawX, drawY, width, height);
            batch.setColor(1, 1, 1, 1);

            boolean mouseOver = mousePos.x >= drawX && mousePos.x <= drawX + width &&
                mousePos.y >= drawY && mousePos.y <= drawY + height;

            if (mouseOver && Finder.getpd().getCraftingRecipes().contains(machine.getRecipe())) {
                drawToolTip(batch, machine, drawX, drawY);

                if (Gdx.input.justTouched()) {
                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                        new GameCommand("crafting craft "+machine, AppClient.getUserData().getUsername()));
                    AppClient.getClient().send(new Gson().toJson(msg));
//                    if (hasAllItems(machine.getRecipe())) {
//                        Map<Item, Integer> requiredItems = machine.getRecipe().neededItems;
//                        Inventory inventory = player.getInventory();
//                        for (Map.Entry<Item, Integer> entry : requiredItems.entrySet()) {
//                            Item item = entry.getKey();
//                            int Amount = entry.getValue();
//                            inventory.removeFromInventory(item, Amount);
//                        }
//                        inventory.addToInventory(machine, 1);
//
//                    }
                }
            }
            currentX += width * 2.3f;
        }
    }

    private void renderCooking(Batch batch) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float minX = screenWidth / 25f * 1.3f;
        float maxX = screenWidth / 2.5f * 1.3f;
        float minY = screenHeight / 40f * 1.7f;
        float maxY = screenHeight / 10f * 1.7f;

        float currentX = minX;
        float currentY = maxY;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        if(!(screen instanceof CottageScreen)){
            font.getData().setScale(2f);
            font.setColor(Color.RED);

            GlyphLayout layout = new GlyphLayout(font, "You Should Go Home To Use This!");
            font.draw(batch, layout,  startingX + screenWidth / 3.2f - layout.width / 2, startingY + maxY + screenHeight / 9f);
            font.setColor(Color.WHITE);
            font.getData().setScale(1f);
        }

        for (Food food : Food.values()) {
            TextureRegion texture = ItemManager.getTexture(food);
            if (texture == null) continue;

            if (currentX > maxX) {
                float width = screenWidth * 0.02f;
                currentX = minX + width;
                currentY -= screenHeight / 15f * 1.7f;

                if (currentY < minY) break;
            }

            float drawX = startingX + currentX;
            float drawY = startingY + currentY;
            float width = screenWidth * 0.03f;
            float height = width ;

            CookingRecipe recipe = food.getRecipe();
            boolean unlocked = recipe != null && Finder.getpd().getCookingRecipes().contains(recipe);

            if (!unlocked) {
                batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
            }

            batch.draw(texture, drawX, drawY, width, height);
            batch.setColor(1, 1, 1, 1);

            boolean mouseOver = mousePos.x >= drawX && mousePos.x <= drawX + width &&
                mousePos.y >= drawY && mousePos.y <= drawY + height;

            if (mouseOver && unlocked) {
                drawCookingTip(batch, food, drawX, drawY);

                if (Gdx.input.isButtonJustPressed(0)) {
                    GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                        new GameCommand("cooking prepare "+food, AppClient.getUserData().getUsername()));
                    AppClient.getClient().send(new Gson().toJson(msg));
//                    if (hasAllItems(recipe)) {
//                        Map<Item, Integer> requiredItems = recipe.neededItems;
//                        Inventory inventory = player.getInventory();
//
//                        for (Map.Entry<Item, Integer> entry : requiredItems.entrySet()) {
//                            inventory.removeFromInventory(entry.getKey(), entry.getValue());
//                        }
//
//                        inventory.addToInventory(food, 1);
//                    }
                }
            }

            currentX += width *1.33f;
        }
    }

    private void renderVote(@NotNull Batch batch) {
        Texture nameTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture("Player Name Background.png");
        Texture buttonTexture = GameAssetManager.getGameAssetManager().getBackgroundTexture("AvatarBackground2.png");

        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        float x = startingX + screenWidth * 0.025f;
        float y = startingY + screenHeight * 0.2f;

        float spacing = screenWidth * 0.14f;
        float playerSize = screenHeight * 0.08f;

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        String currentName = AppClient.getUserData().getUsername();
        List<PlayerData> players = AppClient.getGameData().getPlayersData();

        for (PlayerData playerData : players) {
            String name = playerData.getUsername();

            float playerX = x;
            float playerY = y - playerSize + 5;

            batch.draw(nameTexture, playerX, playerY, playerSize * 3, playerSize);

            font.getData().setScale(2f);
            font.draw(batch, name, playerX + playerSize * 1.5f - name.length() * font.getScaleX() * 7.5f / 2f, playerY + playerSize / 1.5f);

            boolean hovered = mousePos.x >= playerX && mousePos.x <= playerX + playerSize * 3f &&
                mousePos.y >= playerY && mousePos.y <= playerY + playerSize;

            boolean clicked = false;

            if (hovered) {
                if(Gdx.input.isButtonJustPressed(0)){
                    clicked = true;
                }
                String warnText = "Vote " + name;
                if (name.equals(currentName)) {
                    warnText = "Voting Yourself?!!";
                }
                GlyphLayout tooltipLayout = new GlyphLayout(font, warnText);

                float tooltipWidth = tooltipLayout.width + 40;
                float tooltipHeight = tooltipLayout.height + 30;

                float tooltipX = playerX + playerSize * 1.5f - tooltipWidth / 2f;
                float tooltipY = playerY + playerSize + 20;

                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.85f);
                shapeRenderer.rect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                shapeRenderer.end();
                batch.begin();

                font.setColor(Color.RED);

                font.draw(batch, warnText, tooltipX + 20, tooltipY + tooltipHeight - 15);

                font.setColor(Color.WHITE);
            }
            if(clicked && AppClient.getGameData().getPlayersData().size() > 1){
                GameMessage<VoteMessage> msg = new GameMessage<>("new-vote",
                    new VoteMessage(name));
                AppClient.getClient().send(new Gson().toJson(msg));
            }

            x += spacing;
        }
        x = startingX + screenWidth * 0.025f;
        for (int i = 0; i < 2; i++) {
            String text = "Force Terminate";
            if(i == 1) { // TODO If CurrentName Only Admin! if not, break;
//                if(Admin){
//                  break;
//                }
                text = "Save Game";
            }

            float playerX = x;
            float playerY = y - playerSize + 5 + screenHeight * 0.3f;

            batch.draw(buttonTexture, playerX, playerY, playerSize * 4, playerSize);

            font.getData().setScale(2f);
            font.draw(batch, text, playerX + playerSize * 2f - text.length() * font.getScaleX() * 7.5f / 2f, playerY + playerSize / 1.5f);

            boolean hovered = mousePos.x >= playerX && mousePos.x <= playerX + playerSize * 4f &&
                mousePos.y >= playerY && mousePos.y <= playerY + playerSize;

            boolean clicked = false;

            if(hovered && Gdx.input.isButtonJustPressed(0)){
                clicked = true;
            }
            if (hovered && i == 0) {

                String warnText = "Vote For Force Terminate";

                GlyphLayout tooltipLayout = new GlyphLayout(font, warnText);

                float tooltipWidth = tooltipLayout.width + 40;
                float tooltipHeight = tooltipLayout.height + 30;

                float tooltipX = playerX + playerSize * 2f - tooltipWidth / 2f;
                float tooltipY = playerY + playerSize + 20;

                batch.end();
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 0, 0, 0.85f);
                shapeRenderer.rect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                shapeRenderer.end();
                batch.begin();

                font.setColor(Color.RED);

                font.draw(batch, warnText, tooltipX + 20, tooltipY + tooltipHeight - 15);

                font.setColor(Color.WHITE);
            }
            if(clicked){
                if(i == 0){
                    GameMessage<String> msg = new GameMessage<>("new-ter", ";)");
                    AppClient.getClient().send(new Gson().toJson(msg));
                } else{
                    if(AppClient.getUserData().getUsername().equals(
                        AppClient.getGameData().getLobby().getAdmin()
                    )){
                        GameMessage<String> msg = new GameMessage<>("save-game", ";)");
                        AppClient.getClient().send(new Gson().toJson(msg));
                    }else{
                        System.out.println("you are not admin");
                        System.out.println("  you: "+AppClient.getUserData().getUsername());
                        System.out.println("  adming: "+AppClient.getGameData().getLobby().getAdmin());
                    }
                }
            }

            x += spacing * 2.5f;
        }

        font.getData().setScale(1f);
    }

    private boolean hasAllItems(CraftingRecipe recipe) {
        Map<Item, Integer> requiredItems = recipe.neededItems;
        Inventory inventory = player.getInventory();

        for (Map.Entry<Item, Integer> entry : requiredItems.entrySet()) {
            Item item = entry.getKey();
            int Amount = entry.getValue();
            if (!inventory.doHave(item, Amount)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasAllItems(CookingRecipe recipe) {
        Map<Item, Integer> requiredItems = recipe.neededItems;
        Inventory inventory = player.getInventory();

        for (Map.Entry<Item, Integer> entry : requiredItems.entrySet()) {
            Item item = entry.getKey();
            int amount = entry.getValue();
            if (!inventory.doHave(item, amount)) {
                return false;
            }
        }

        return true;
    }




    private String readableName(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z])", "$1 $2");
    }

    private void drawToolTip(Batch batch, CraftableMachine machine, float drawX, float drawY) {
        CraftingRecipe recipe = machine.getRecipe();
        if (recipe == null) return;

        float width = 150;
        float padding = 10;
        float lineHeight = 40;
        float iconSize = 32;

        int itemCount = recipe.getNeededItems().size();
        float height = padding * 2 + lineHeight + itemCount * lineHeight;

        float x = drawX - width - 10;
        float y = drawY + 192 - height;


        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.9f);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();
        String title = readableName(machine.name());
        font.getData().setScale(2f);
        GlyphLayout layout = new GlyphLayout(font, title);
        batch.draw(infoTexture,x-2*padding, y - 2 * padding,
            Math.max(infoTexture.getWidth(), layout.width + 40), infoTexture.getHeight() * recipe.getNeededItems().size() / 2f);

        font.draw(batch, layout, x  + Math.max(infoTexture.getWidth(), layout.width + 40) / 2 - layout.width / 2 - padding * 2, y + height - 2 * padding);
        font.getData().setScale(1f);


        int i = 0;
        for (Map.Entry<Item, Integer> entry : recipe.getNeededItems().entrySet()) {
            Item item = entry.getKey();
            int amount = entry.getValue();
            TextureRegion icon = ItemManager.getTexture(item);

            float itemY = y + height - padding - lineHeight * (i + 2);

            if (icon != null) {
                batch.draw(icon, x + padding, itemY, iconSize, iconSize);
            }
            if (hasInInventory(item, amount)) {
                font.setColor(0f, 0.5f, 1f, 1f);
            } else {
                font.setColor(1f, 0f, 0f, 1f);
            }
            font.draw(batch, "x" + amount, x + padding + iconSize + 10, itemY + iconSize / 2f + 5);
            i++;
            font.setColor(1f, 1f, 1f, 1f);
        }
    }

    private boolean hasInInventory(Item item, int quantity) {
        PlayerData pd = Finder.getpd();
        for(SlotData slot : pd.getInventoryData().getSlots()){
            if(slot.getItemName() == null || slot.getQuantity() == 0) continue;
            if(slot.getItemName().equals(item.getName())){
                return slot.getQuantity() >= quantity;
            }
        }
        return false;
    }

    private void drawCookingTip(Batch batch, Food food, float drawX, float drawY) {
        CookingRecipe recipe = food.getRecipe();
        if (recipe == null) return;

        float width = 150;
        float padding = 10;
        float lineHeight = 40;
        float iconSize = 32;

        int itemCount = recipe.neededItems.size();
        float height = padding * 2 + lineHeight + itemCount * lineHeight;

        float x = drawX - width - 10;
        float y = drawY + 192 - height;

        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.9f);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();

        if(recipe.neededItems.size() <= 1){
            batch.draw(infoTexture, x - 2 * padding, y - 2 * padding,
                infoTexture.getWidth(), infoTexture.getHeight());
        }else{
            batch.draw(infoTexture, x - 2 * padding, y - 2 * padding,
                infoTexture.getWidth(), infoTexture.getHeight() * recipe.neededItems.size() / 2f);
        }

        String title = readableName(food.getName());
        GlyphLayout layout = new GlyphLayout(font, title);
        font.draw(batch, layout, x + padding + width / 2 - layout.width / 2, y + height - 2 * padding);

        int i = 0;
        for (Map.Entry<Item, Integer> entry : recipe.neededItems.entrySet()) {
            Item item = entry.getKey();
            int amount = entry.getValue();
            TextureRegion icon = ItemManager.getTexture(item);

            float itemY = y + height - padding - lineHeight * (i + 2);

            if (icon != null) {
                batch.draw(icon, x + padding, itemY, iconSize, iconSize);
            }

            if (hasInInventory(item, amount)) {
                font.setColor(0f, 0.5f, 1f, 1f);
            } else {
                font.setColor(1f, 0f, 0f, 1f);
            }

            font.draw(batch, "x" + amount, x + padding + iconSize + 10, itemY + iconSize / 2f + 5);
            i++;
            font.setColor(1f, 1f, 1f, 1f);
        }
    }



    public void dispose() {
        menuTexture.dispose();
    }

    public void resetScroll(){
        startingRow = 0;
        selectedIndex = 0;
    }

    public void scrollDown() {
        startingRow++;

        if (selectedIndex < GameAssetManager.getGameAssetManager().npcTextures.size() + relationTextures.size() - visibleRelationsCount + 1)
            selectedIndex++;
    }

    public void scrollUp() {
        if (startingRow > 0) startingRow--;

        if (selectedIndex > 0) selectedIndex--;
    }

    public void goToNextTab() {
        int currentIndex = getTabIndex(currentTab);
        if (currentIndex >= 0) {
            int nextIndex = (currentIndex + 1) % tabOrder.length;
            if (tabOrder[nextIndex] != null) {
                currentTab = tabOrder[nextIndex];
            } else {

                nextIndex = (nextIndex + 1) % tabOrder.length;
                currentTab = tabOrder[nextIndex];
            }
        }
    }

    public void goToPreviousTab() {
        int currentIndex = getTabIndex(currentTab);
        if (currentIndex >= 0) {
            int prevIndex = (currentIndex - 1 + tabOrder.length) % tabOrder.length;
            if (tabOrder[prevIndex] != null) {
                currentTab = tabOrder[prevIndex];
            } else {

                prevIndex = (prevIndex - 1 + tabOrder.length) % tabOrder.length;
                currentTab = tabOrder[prevIndex];
            }
        }
    }

    private int getTabIndex(String tabName) {
        for (int i = 0; i < tabOrder.length; i++) {
            if (tabName.equals(tabOrder[i])) {
                return i;
            }
        }
        return -1;
    }

    public String getCurrentTab() {
        return currentTab;
    }
}
