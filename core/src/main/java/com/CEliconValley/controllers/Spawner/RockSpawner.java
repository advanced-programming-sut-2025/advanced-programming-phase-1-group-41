package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.common.CellData;
import com.CEliconValley.common.FarmData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.foragings.Nature.*;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class RockSpawner {
    private final WaterSpawner waterSpawner;
    private final GroundBorderSpawner groundSpawner;
    private final Map<Cell, Float> breakingEffects = new HashMap<>();
    private final Map<Cell, Float> breakingBigRockEffects = new HashMap<>();
    private final Map<Cell, TextureRegion> rockRenderCache = new HashMap<>();

    private TextureRegion[][] breakRockFrames;
    private TextureRegion[][] breakBigRockFrames;
    private Animation<TextureRegion>[] breakRockAnimations;
    private Animation<TextureRegion>[] breakBigRockAnimations;

    private final Texture rockTexture = new Texture("game/general/tiles/rocks.png");
    private final Texture bigRockTexture= new Texture("game/general/tiles/bigRocks.png");
    private final Texture rockBreak=new  Texture("game/general/tiles/rock_Break.png");
    private final Texture bigRockBreak=new  Texture("game/general/tiles/bigRock_Break.png");





    public void splitRockTexture() {
        int frameWidth = rockBreak.getWidth() / 7;
        int frameHeight = rockBreak.getHeight() / 12;

        breakRockFrames = TextureRegion.split(rockBreak, frameWidth, frameHeight);

        breakRockAnimations = new Animation[12];
        for (int i = 0; i < 12; i++) {
            breakRockAnimations[i] = new Animation<>(0.15f, breakRockFrames[i]);
        }
        int bigFrameWidth = bigRockBreak.getWidth() / 7;
        int bigFrameHeight = bigRockBreak.getHeight() / 5;
        breakBigRockFrames = TextureRegion.split(bigRockBreak, bigFrameWidth, bigFrameHeight);
        breakBigRockAnimations = new Animation[5];
        for (int i = 0; i < 5; i++) {
            breakBigRockAnimations[i] = new Animation<>(0.15f, breakBigRockFrames[i]);
        }


    }
    public RockSpawner() {
        splitRockTexture();
        waterSpawner=new WaterSpawner();
        groundSpawner=new GroundBorderSpawner();
    }
    public boolean renderRocks(SpriteBatch batch, CellData cellData, float passiveState) {
        Cell cell = cellData.extractData();
        if (breakingEffects.containsKey(cell) || breakingBigRockEffects.containsKey(cell)) return false;

        float x = cell.getX() * CELL_SIZE;
        float y = cell.getY() * CELL_SIZE;


//        if (rockRenderCache.containsKey(cell)) {
//            batch.draw(rockRenderCache.get(cell), x, y);
//            return true;
//        }

        if (cell.getObjectMap() instanceof Rock rock) {
            if (rock.getRockType() == RockType.BigRock) {


                if (cell.getX() == rock.getAnchorX() && cell.getY() == rock.getAnchorY()) {
                    int variant = rock.getVariant();
                    int frameCount = 5;
                    int frameWidth = bigRockTexture.getWidth() / frameCount;
                    int frameHeight = bigRockTexture.getHeight();

                    TextureRegion region = new TextureRegion(bigRockTexture, variant * frameWidth, 0, frameWidth, frameHeight);
                    TextureRegion scaled = new TextureRegion(region);

//                    rockRenderCache.put(cell, scaled);
                    batch.draw(scaled, x - CELL_SIZE, y, CELL_SIZE * 2, CELL_SIZE * 2);
                    return true;
                }
            } else {
                int variant = rock.getVariant();
                int frameCount = 12;
                int frameWidth = rockTexture.getWidth() / frameCount;
                int frameHeight = rockTexture.getHeight();

                TextureRegion region = new TextureRegion(rockTexture, variant * frameWidth, 0, frameWidth, frameHeight);
                TextureRegion scaled = new TextureRegion(region);

//                rockRenderCache.put(cell, scaled);
                batch.draw(scaled, x, y, CELL_SIZE, CELL_SIZE);
                return true;
            }
        }
        return false;
    }

    public boolean hitRock(CellData cellData, FarmData farmData) {
        Cell cell = cellData.extractData();
        if (cell.getObjectMap() instanceof Rock rock) {
            if (rock.getRockType() == RockType.BigRock) {

                 if (!breakingBigRockEffects.containsKey(cell)) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(450);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int anchorX = rock.getAnchorX();
                        int anchorY = rock.getAnchorY();
                        Cell c = Finder.getcdByFarmData(anchorX, anchorY, farmData).extractData();
                        breakingBigRockEffects.put(c, 0f);
                    }).start();
//                    rockRenderCache.remove(cell);
//                    rockRenderCache.remove(Finder.findCellByCoordinates(cell.getX()-1,cell.getY(),farm));
//                    rockRenderCache.remove(Finder.findCellByCoordinates(cell.getX()-1,cell.getY()+1,farm));
//                    rockRenderCache.remove(Finder.findCellByCoordinates(cell.getX(),cell.getY()+1,farm));

                }
            } else {
                if (!breakingEffects.containsKey(cell)) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(450);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        breakingEffects.put(cell, 0f);
                    }).start();
//                    rockRenderCache.remove(cell);
                }
            }
            return true;
        }
        return false;
    }

    public boolean renderBreakingEffectForCell(SpriteBatch batch, CellData cellData, float delta) {
        Cell cell = cellData.extractData();
        if (breakingEffects.containsKey(cell)) {
            float stateTime = breakingEffects.get(cell) + delta;
            Object obj = cell.getObjectMap();
            if (!(obj instanceof Rock rock)) {
                breakingEffects.remove(cell);
                return true;
            }

            int variant = rock.getVariant();
            if (variant < 0 || variant >= breakRockAnimations.length) {
                breakingEffects.remove(cell);
                return true;
            }

            Animation<TextureRegion> animation = breakRockAnimations[variant];
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);

            float x = cell.getX() * CELL_SIZE;
            float y = cell.getY() * CELL_SIZE;

            batch.draw(currentFrame, x, y, CELL_SIZE, CELL_SIZE);

            if (animation.isAnimationFinished(stateTime)) {
                cell.setObjectMap(new Grass());
                breakingEffects.remove(cell);
                return true;
            }

            breakingEffects.put(cell, stateTime);
            return false;
        }

        if (breakingBigRockEffects.containsKey(cell)) {
            float stateTime = breakingBigRockEffects.get(cell) + delta;
            Object obj = cell.getObjectMap();
            if (!(obj instanceof Rock rock)) {
                breakingBigRockEffects.remove(cell);
                return true;
            }

            int variant = rock.getVariant();
            if (variant < 0 || variant >= breakBigRockAnimations.length) {
                breakingBigRockEffects.remove(cell);
                return true;
            }

            Animation<TextureRegion> animation = breakBigRockAnimations[variant];
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);

            float x = cell.getX() * CELL_SIZE;
            float y = cell.getY() * CELL_SIZE;

            batch.draw(currentFrame, x - CELL_SIZE, y, CELL_SIZE * 2, CELL_SIZE * 2);

            if (animation.isAnimationFinished(stateTime)) {
                cell.setObjectMap(new Grass());
//                Finder.findCellByCoordinates(cell.getX()-1,cell.getY(),farm).setObjectMap(new Grass());
//                Finder.findCellByCoordinates(cell.getX()-1,cell.getY()+1,farm).setObjectMap(new Grass());
//                Finder.findCellByCoordinates(cell.getX(),cell.getY()+1,farm).setObjectMap(new Grass());


                breakingBigRockEffects.remove(cell);
                return true;
            }

            breakingBigRockEffects.put(cell, stateTime);
            return false;
        }

        return true;
    }




}
