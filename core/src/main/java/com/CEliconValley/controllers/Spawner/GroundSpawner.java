package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.common.CellData;
import com.CEliconValley.common.FarmData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;
import static com.CEliconValley.client.view.screen.FarmScreen.farmSprite;

public class GroundSpawner {

    private final Texture groundTexture;
    private final Texture grassBorderTexture;
    private final TextureRegion[] grassBorders;

    public GroundSpawner() {
        groundTexture = new Texture("game/general/tiles/ground_Spring.png");
        grassBorderTexture = new Texture("game/general/tiles/grassBorder.png");

        grassBorders = new TextureRegion[9];
        int tileWidth = grassBorderTexture.getWidth() / 9;
        int tileHeight = grassBorderTexture.getHeight();

        for (int i = 0; i < 9; i++) {
            grassBorders[i] = new TextureRegion(grassBorderTexture, i * tileWidth, 0, tileWidth, tileHeight);
        }

    }
    // useless?
//    public boolean renderGround(SpriteBatch batch, CellData cellData, float passiveStateTime) {
//        int x = cellData.getX() * CELL_SIZE;
//        int y = cellData.getY() * CELL_SIZE;
//        Cell cell = cellData.extractData();
//        if (cell.getObjectMap() instanceof Grass &&((Grass) cell.getObjectMap()).isGround()) {
//            batch.draw(groundTexture, x, y, CELL_SIZE, CELL_SIZE);
//            return true;
//        } else if (isGrassBorder(cell)) {
//            int index = getBorderIndex(cell.getX(), cell.getY());
//            batch.draw(grassBorders[index], x, y, CELL_SIZE, CELL_SIZE);
//            return true;
//        }
//        return false;
//    }

    private boolean isGrassBorder(CellData cellData, FarmData farmData) {
        int x = cellData.getX();
        int y = cellData.getY();
        Cell cell = cellData.extractData();
        return !(cell.getObjectMap() instanceof Grass && ((Grass) cell.getObjectMap()).isGround()) &&
            (isNotGrass(x + 1, y, farmData) ||
                isNotGrass(x - 1, y, farmData) || isNotGrass(x, y + 1, farmData)
                || isNotGrass(x, y - 1, farmData));
    }

    private boolean isNotGrass(int x, int y, FarmData farmData) {
        CellData cd = Finder.getcdByFarmData(x, y, farmData);
        if(cd == null) return false;
        Cell cell = cd.extractData();
        if(cell.getObjectMap() instanceof Grass grass){
            if(grass.isGround()||grass.isFarmland()||grass.isBombed()||grass.isSand()||grass.isThundered()){
                return true;
            }
        }
        return false;
    }

    private int getBorderIndex(int x, int y, FarmData farmData) {
        boolean up = isNotGrass(x, y + 1, farmData);
        boolean down = isNotGrass(x, y - 1, farmData);
        boolean left = isNotGrass(x - 1, y, farmData);
        boolean right = isNotGrass(x + 1, y, farmData);

        if (up && left) return 8;
        if (up && right) return 6;
        if (down && left) return 2;
        if (down && right) return 0;
        if (up) return 7;
        if (down) return 1;
        if (left) return 5;
        if (right) return 3;

        return 4;
    }

}

