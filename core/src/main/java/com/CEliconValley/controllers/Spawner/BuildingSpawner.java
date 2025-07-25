package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.buildings.Cottage;
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

public class BuildingSpawner {
    private final Farm farm;
    private final WaterSpawner waterSpawner;
    private final Texture cottageTexture =new Texture("game/cottage.png");


    public BuildingSpawner(Farm farm) {
        waterSpawner=new WaterSpawner(farm);
        this.farm = farm;

    }
    public boolean renderBuildings(SpriteBatch batch,Cell cell,float passiveState) {
        float x = cell.getX()*CELL_SIZE;
        float y = cell.getY()*CELL_SIZE;
        if (cell.getObjectMap() instanceof Wall){
            if(Finder.findCellByCoordinates(cell.getX()-1,cell.getY()+1,this.farm).getObjectMap() instanceof Cottage) {
                Cottage cottage=(Cottage) Finder.findCellByCoordinates(cell.getX()-1,cell.getY()+1,this.farm).getObjectMap();
                batch.draw(grassTexture, x, y, CELL_SIZE, CELL_SIZE);
                if (cell.getX() - 1 == cottage.getAnchorX() && cell.getY() + 1 == cottage.getAnchorY()) {
                    int frameWidth = cottageTexture.getWidth();
                    int frameHeight = cottageTexture.getHeight();

                    TextureRegion cottageFrame = new TextureRegion(
                        cottageTexture,
                        0, 0,
                        frameWidth, frameHeight
                    );
                    batch.draw(grassTexture, x, y, CELL_SIZE, CELL_SIZE);

                    batch.draw(cottageFrame, x - CELL_SIZE * 5, y, CELL_SIZE * 6, CELL_SIZE * 6);
                }

            }else{
                batch.draw(grassTexture, x, y, CELL_SIZE, CELL_SIZE);
            }
        }
        return false;

    }



}
