package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.Rock;
import com.CEliconValley.models.foragings.Nature.RockType;
import com.CEliconValley.models.foragings.Nature.Tree;
import com.CEliconValley.models.foragings.Nature.TreeType;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleToIntFunction;

import static com.CEliconValley.models.locations.FarmScreen.CELL_SIZE;
import static com.CEliconValley.models.locations.FarmScreen.grassTexture;

public class RockSpawner {
    private final Farm farm;
    private final WaterSpawner waterSpawner;
    private final GroundSpawner groundSpawner;
    private final Texture rockTexture = new Texture("game/general/tiles/rocks.png");
    private final Texture bigRockTexture= new Texture("game/general/tiles/bigRocks.png");





    public void splitRockTexture() {

    }
    public RockSpawner(Farm farm) {
        splitRockTexture();
        waterSpawner=new WaterSpawner(farm);
        groundSpawner=new GroundSpawner(farm);
        this.farm = farm;

    }
    public boolean renderRocks(SpriteBatch batch,Cell cell,float passiveState) {
        float x = cell.getX()*CELL_SIZE;
        float y = cell.getY()*CELL_SIZE;
        if (cell.getObjectMap() instanceof Rock) {
            Rock rock = (Rock) cell.getObjectMap();

            if (rock.getRockType() == RockType.BigRock) {
                if(!waterSpawner.renderWater(batch,cell,passiveState)&&!groundSpawner.renderGround(batch,cell,passiveState)){
                    batch.draw(grassTexture, x, y, CELL_SIZE, CELL_SIZE);
                }

                if (cell.getX() == rock.getAnchorX() && cell.getY() == rock.getAnchorY()) {
                    int variant = rock.getVariant();
                    int frameCount = 5;
                    int frameWidth = bigRockTexture.getWidth() / frameCount;
                    int frameHeight = bigRockTexture.getHeight();

                    TextureRegion rockFrame = new TextureRegion(
                        bigRockTexture,
                        variant * frameWidth, 0,
                        frameWidth, frameHeight
                    );

                    batch.draw(rockFrame, x-CELL_SIZE*1, y, CELL_SIZE * 2, CELL_SIZE * 2);
                    return true;
                }

            } else {

                int variant = rock.getVariant();
                int frameCount = 12;
                int frameWidth = rockTexture.getWidth() / frameCount;
                int frameHeight = rockTexture.getHeight();

                TextureRegion rockFrame = new TextureRegion(
                    rockTexture,
                    variant * frameWidth, 0,
                    frameWidth, frameHeight
                );

                batch.draw(rockFrame, x, y, CELL_SIZE, CELL_SIZE);
                return true;
            }
        }
        return false;

    }



}
