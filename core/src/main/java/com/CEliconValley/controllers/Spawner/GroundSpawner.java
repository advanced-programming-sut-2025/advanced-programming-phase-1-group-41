package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.CEliconValley.models.locations.FarmScreen.CELL_SIZE;

public class GroundSpawner {

    private final Farm farm;
    private final Texture groundTexture;
    private final Texture grassBorderTexture;
    private final TextureRegion[] grassBorders;

    public GroundSpawner(Farm farm) {
        this.farm = farm;
        groundTexture = new Texture("game/general/tiles/ground.png");
        grassBorderTexture = new Texture("game/general/tiles/grassBorder.png");

        grassBorders = new TextureRegion[9];
        int tileWidth = grassBorderTexture.getWidth() / 9;
        int tileHeight = grassBorderTexture.getHeight();

        for (int i = 0; i < 9; i++) {
            grassBorders[i] = new TextureRegion(grassBorderTexture, i * tileWidth, 0, tileWidth, tileHeight);
        }

    }

    public boolean renderGround(SpriteBatch batch, Cell cell,float passiveStateTime) {
        int x = cell.getX() * CELL_SIZE;
        int y = cell.getY() * CELL_SIZE;

        if (cell.getObjectMap() instanceof Grass &&((Grass) cell.getObjectMap()).isGround()) {
            batch.draw(groundTexture, x, y, CELL_SIZE, CELL_SIZE);
            return true;
        } else if (isGrassBorder(cell)) {
            int index = getBorderIndex(cell.getX(), cell.getY());
            batch.draw(grassBorders[index], x, y, CELL_SIZE, CELL_SIZE);
            return true;
        }
        return false;
    }

    private boolean isGrassBorder(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        return !(cell.getObjectMap() instanceof Grass && ((Grass) cell.getObjectMap()).isGround()) &&
            (isGround(x + 1, y) || isGround(x - 1, y) || isGround(x, y + 1) || isGround(x, y - 1));
    }

    private boolean isGround(int x, int y) {
        for (Cell cell : farm.getCells()) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell.getObjectMap() instanceof Grass && ((Grass) cell.getObjectMap()).isGround();
            }
        }
        return false;
    }

    private int getBorderIndex(int x, int y) {
        boolean up = isGround(x, y + 1);
        boolean down = isGround(x, y - 1);
        boolean left = isGround(x - 1, y);
        boolean right = isGround(x + 1, y);

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

