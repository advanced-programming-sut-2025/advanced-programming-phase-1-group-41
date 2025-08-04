package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.common.CellData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.Tree;
import com.CEliconValley.models.foragings.Nature.TreeType;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class TreeSpawner {
    private final Texture treeTexture=new Texture("game/Trees/trees.png");

    private final Map<String, TextureRegion[]> treeFramesMap = new HashMap<>();

    private final String[] treeNames = {
        "Apricot", "Banana", "Cherry", "Mango", "Orange", "Peach", "Apple",
        "Oak", "Pomegranate", "Pine", "Mahogany", "Mushroom", "Mystic", "Maple"
    };

    public void splitTreeTexture() {

        int cols = 10;
        int rows = 14;

        int frameWidth = treeTexture.getWidth() / cols;
        int frameHeight = treeTexture.getHeight() / rows;

        TextureRegion[][] splitRegions = TextureRegion.split(treeTexture, frameWidth, frameHeight);

        for (int row = 0; row < rows; row++) {
            TextureRegion[] frames = new TextureRegion[cols];
            for (int col = 0; col < cols; col++) {
                frames[col] = splitRegions[row][col];
            }
            treeFramesMap.put(treeNames[row], frames);
        }
    }
    public TreeSpawner() {
        splitTreeTexture();
    }
    public void renderTrees(SpriteBatch batch, CellData cellData, float passiveState){
            Cell cell = cellData.extractData();
            if (cell.getObjectMap() instanceof ForagingTree) {
                ForagingTree tree = (ForagingTree) cell.getObjectMap();
                TreeType type = tree.getTreeType();
                int stage = 7;

                TextureRegion[] frames = treeFramesMap.get(type.name());
                if (frames != null && stage >= 0 && stage < frames.length) {

                    TextureRegion frame = frames[stage];

                    float centerX = cell.getX() * CELL_SIZE+CELL_SIZE/2;
                    float centerY = cell.getY() * CELL_SIZE+CELL_SIZE/2;

                    float drawX = centerX - CELL_SIZE * 1.5f;
                    float drawY = centerY - CELL_SIZE*0.4f ;

                    batch.draw(frame, drawX, drawY,CELL_SIZE*3,CELL_SIZE*5);
                }

            }
            if (cell.getObjectMap() instanceof Tree) {
                Tree tree = (Tree) cell.getObjectMap();
                TreeType type = tree.getTreeType();
                int stage = 7;

                TextureRegion[] frames = treeFramesMap.get(type.name());
                if (frames != null && stage >= 0 && stage < frames.length) {

                    TextureRegion frame = frames[stage];

                    float centerX = cell.getX() * CELL_SIZE+CELL_SIZE/2;
                    float centerY = cell.getY() * CELL_SIZE+CELL_SIZE/2;

                    float drawX = centerX - CELL_SIZE * 1.5f;
                    float drawY = centerY - CELL_SIZE*0.4f ;

                    batch.draw(frame, drawX, drawY,CELL_SIZE*3,CELL_SIZE*5);
                }

            }



    }



}
