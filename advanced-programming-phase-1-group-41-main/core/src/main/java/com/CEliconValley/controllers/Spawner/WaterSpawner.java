package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.foragings.Nature.Tree;
import com.CEliconValley.models.foragings.Nature.TreeType;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleToIntFunction;

import static com.CEliconValley.models.locations.FarmScreen.CELL_SIZE;

public class WaterSpawner {
    private final Farm farm;
    Texture waterTexture = new Texture("game/general/tiles/water.png");
    Texture coastTexture = new Texture("game/general/tiles/coast.png");
    Texture cornerTexture = new Texture("game/general/tiles/waterCorner.png");
    private Animation<TextureRegion> waterAnimation;
    Animation<TextureRegion> animCornerSE ;
    Animation<TextureRegion> animCornerNE ;
    Animation<TextureRegion> animCornerNW ;
    Animation<TextureRegion> animCornerSW ;
    private Animation<TextureRegion>[] coastAnimations;

    public WaterSpawner(Farm farm) {
        this.farm = farm;
        splitWaterTexture();
    }

    public void splitWaterTexture() {
        int FRAME_COLS_WATER = 4;
        int FRAME_ROWS_WATER = 1;

        TextureRegion[][] tmpWater = TextureRegion.split(waterTexture,
            waterTexture.getWidth() / FRAME_COLS_WATER,
            waterTexture.getHeight() / FRAME_ROWS_WATER);

        TextureRegion[] waterFrames = new TextureRegion[FRAME_COLS_WATER];
        for (int i = 0; i < FRAME_COLS_WATER; i++) {
            waterFrames[i] = tmpWater[0][i];
        }
        waterAnimation = new Animation<>(0.3f, waterFrames);
        TextureRegion[][] tmp = TextureRegion.split(cornerTexture,
            cornerTexture.getWidth() / 4,
            cornerTexture.getHeight());

        TextureRegion[] seFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            seFrames[i] = tmp[0][i];
        }
        animCornerSE = new Animation<>(0.15f,seFrames);
        animCornerNE = new Animation<>(0.15f, flipY(seFrames));
        animCornerNW = new Animation<>(0.15f, flipXY(seFrames));
        animCornerSW = new Animation<>(0.15f, flipX(seFrames));

        int FRAME_COLS=9;
        int FRAME_ROWS=4;
        tmp = TextureRegion.split(coastTexture,coastTexture.getWidth()/FRAME_COLS,coastTexture.getHeight()/FRAME_ROWS);
        coastAnimations = new Animation[9];
        for(int i=0;i<FRAME_COLS;i++){
            TextureRegion[] frames = new TextureRegion[FRAME_ROWS];
            for(int j=0;j<FRAME_ROWS;j++){
                frames[j] = new TextureRegion(tmp[j][i]);
            }
            coastAnimations[i]=new Animation<>(0.15f,frames);
        }

    }

    public boolean renderWater(SpriteBatch batch,Cell cell,float passiveStateTime) {
        float x = cell.getX() * CELL_SIZE;
        float y = cell.getY() * CELL_SIZE;
        if (cell.getObjectMap() instanceof Lake) {
            TextureRegion waterFrame = waterAnimation.getKeyFrame(passiveStateTime, true);
            batch.draw(waterFrame, x, y, CELL_SIZE, CELL_SIZE);
            return true;

        } else if (isCoast(cell)) {
            int animIndex = getCoastAnimationIndex(cell.getX(), cell.getY());
            TextureRegion frame = coastAnimations[animIndex].getKeyFrame(passiveStateTime, true);
            batch.draw(frame, x, y, CELL_SIZE, CELL_SIZE);
            return true;
        } else if(getWaterCornerType(cell.getX(),cell.getY())!=CornerType.NONE) {
            CornerType corner = getWaterCornerType(cell.getX(), cell.getY());
            switch (corner) {
                case SE:
                    batch.draw(animCornerSE.getKeyFrame(passiveStateTime, true), x, y, CELL_SIZE, CELL_SIZE);
                    return true;

                case SW:
                    batch.draw(animCornerSW.getKeyFrame(passiveStateTime, true), x, y, CELL_SIZE, CELL_SIZE);
                    return true;

                case NE:
                    batch.draw(animCornerNE.getKeyFrame(passiveStateTime, true), x, y, CELL_SIZE, CELL_SIZE);
                    return true;

                case NW:
                    batch.draw(animCornerNW.getKeyFrame(passiveStateTime, true), x, y, CELL_SIZE, CELL_SIZE);
                    return true;
                default:
                    break;
            }
        }
        return false;

    }
    private TextureRegion[] flipY(TextureRegion[] original) {
        TextureRegion[] flipped = new TextureRegion[original.length];
        for (int i = 0; i < original.length; i++) {
            flipped[i] = new TextureRegion(original[i]);
            flipped[i].flip(false, true);
        }
        return flipped;
    }

    private TextureRegion[] flipXY(TextureRegion[] original) {
        TextureRegion[] flipped = new TextureRegion[original.length];
        for (int i = 0; i < original.length; i++) {
            flipped[i] = new TextureRegion(original[i]);
            flipped[i].flip(true, true);
        }
        return flipped;
    }
    private TextureRegion[] flipX(TextureRegion[] original) {
        TextureRegion[] flipped = new TextureRegion[original.length];
        for (int i = 0; i < original.length; i++) {
            flipped[i] = new TextureRegion(original[i]);
            flipped[i].flip(true, false);
        }
        return flipped;
    }
    private boolean isCoast(Cell cell) {

        int x = cell.getX();
        int y = cell.getY();
        return !isWater(x, y) && (isWater(x+1, y) || isWater(x-1, y) || isWater(x, y+1) || isWater(x, y-1));
    }
    public boolean isCorner(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        return !isWater(x,y)&&!isWater(x+1,y)&&!isWater(x,y+1)&&!isWater(x-1,y)&&!isWater(x,y-1)&&(isWater(x+1,y+1)||isWater(x+1,y-1)||isWater(x,y+1)||isWater(x-1,y+1)||isWater(x-1,y-1));
    }
    private int getCoastAnimationIndex(int x, int y) {
        boolean hasWaterRight = isWater(x+1, y);
        boolean hasWaterLeft = isWater(x-1, y);
        boolean hasWaterUp = isWater(x, y+1);
        boolean hasWaterDown = isWater(x, y-1);

        if (hasWaterRight && hasWaterDown) return 8;
        if (hasWaterUp && hasWaterLeft) return 0;
        if (hasWaterLeft && hasWaterDown) return 6;
        if (hasWaterUp && hasWaterRight) return 2;
        if (hasWaterDown) return 7;
        if (hasWaterRight) return 5;
        if (hasWaterLeft) return 3;
        if (hasWaterUp) return 1;

        if (!hasWaterRight && !hasWaterLeft && !hasWaterUp && !hasWaterDown) return 4;
        return 4;
    }
    private enum CornerType {
        NONE, SE, SW, NE, NW
    }

    private CornerType getWaterCornerType(int x, int y) {
        if (!(farm.getCell(x, y).getObjectMap() instanceof Grass)) return CornerType.NONE;

        boolean north = isWater(x, y + 1);
        boolean south = isWater(x, y - 1);
        boolean east = isWater(x + 1, y);
        boolean west = isWater(x - 1, y);

        boolean nw = isWater(x - 1, y + 1);
        boolean ne = isWater(x + 1, y + 1);
        boolean sw = isWater(x - 1, y - 1);
        boolean se = isWater(x + 1, y - 1);

        if (!east && !south && se) return CornerType.SE;
        if (!west && !south && sw) return CornerType.SW;
        if (!east && !north && ne) return CornerType.NE;
        if (!west && !north && nw) return CornerType.NW;

        return CornerType.NONE;
    }



    private boolean isWater(int x, int y) {
        for (Cell cell : farm.getCells()) {
            if (cell.getX() == x && cell.getY() == y) {

                return cell.getObjectMap() instanceof Lake;
            }
        }
        return false;
    }



}
