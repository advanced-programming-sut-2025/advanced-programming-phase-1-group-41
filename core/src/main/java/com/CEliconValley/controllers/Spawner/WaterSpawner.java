package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

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
        animCornerSE = new Animation<>(0.15f, seFrames);
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

    public boolean renderWater(SpriteBatch batch, Cell cell, float passiveStateTime) {
        float x = cell.getX() * CELL_SIZE;
        float y = cell.getY() * CELL_SIZE;

        if (cell.getObjectMap() instanceof Lake lake) {
            TextureRegion waterFrame = waterAnimation.getKeyFrame(passiveStateTime, true);
            batch.draw(waterFrame, x, y, CELL_SIZE, CELL_SIZE);
            if(lake.getInitialize()>-1) {
                switch (lake.getInitialize()) {
                    case 0, 2, 6, 8, 1, 3, 5, 7 -> {
                        TextureRegion coastFrame = coastAnimations[lake.getInitialize()].getKeyFrame(passiveStateTime, true);
                        batch.draw(coastFrame, x, y, CELL_SIZE, CELL_SIZE);
                    }
                    case 9 -> {
                        batch.draw(animCornerSW.getKeyFrame(passiveStateTime, true), x, y, CELL_SIZE, CELL_SIZE);
                    }
                    case 10 -> {
                        batch.draw(animCornerSE.getKeyFrame(passiveStateTime, true), x, y, CELL_SIZE, CELL_SIZE);
                    }
                    case 11 -> {
                        batch.draw(animCornerNW.getKeyFrame(passiveStateTime, true), x, y, CELL_SIZE, CELL_SIZE);
                    }
                    case 12 -> {
                        batch.draw(animCornerNE.getKeyFrame(passiveStateTime, true), x, y, CELL_SIZE, CELL_SIZE);
                    }
                }
               return true;
            }



            if (isCoast(cell)) {
                int animIndex = getCoastAnimationIndex(lake,cell.getX(), cell.getY());
                lake.setInitialize(animIndex);
                System.out.println(animIndex);
                TextureRegion frame = coastAnimations[animIndex].getKeyFrame(passiveStateTime, true);
                batch.draw(frame, x, y, CELL_SIZE, CELL_SIZE);
            }


            CornerType corner = getWaterCornerType(lake,cell.getX(), cell.getY());
            if (corner != CornerType.NONE) {
                Animation<TextureRegion> cornerAnim = switch (corner) {
                    case SE -> animCornerSE;
                    case SW -> animCornerSW;
                    case NE -> animCornerNE;
                    case NW -> animCornerNW;
                    default -> null;
                };
                if (cornerAnim != null) {
                    batch.draw(cornerAnim.getKeyFrame(passiveStateTime, true), x, y, CELL_SIZE, CELL_SIZE);
                }
            }

            return true;
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


        if (!(cell.getObjectMap() instanceof Lake)) return false;


        return isLand(x + 1, y) || isLand(x - 1, y) || isLand(x, y + 1) || isLand(x, y - 1);
    }

    private boolean isLand(int x, int y) {
        for (Cell cell : farm.getCells()) {
            if (cell.getX() == x && cell.getY() == y) {
                return !(cell.getObjectMap() instanceof Lake);
            }
        }
        return false;
    }


    public boolean isCorner(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        return !isWater(x,y)&&!isWater(x+1,y)&&!isWater(x,y+1)&&!isWater(x-1,y)&&!isWater(x,y-1)&&(isWater(x+1,y+1)||isWater(x+1,y-1)||isWater(x,y+1)||isWater(x-1,y+1)||isWater(x-1,y-1));
    }
    private int getCoastAnimationIndex(Lake lake,int x, int y) {
        boolean hasLandRight = isLand(x+1, y);
        boolean hasLandLeft = isLand(x-1, y);
        boolean hasLandUp = isLand(x, y+1);
        boolean hasLandDown = isLand(x, y-1);

        if (hasLandRight && hasLandDown) return 0;
        if (hasLandUp && hasLandLeft)return 8;
        if (hasLandLeft && hasLandDown)return 2;
        if (hasLandUp && hasLandRight)return 6;
        if (hasLandDown)return 1;
        if (hasLandRight)return 3;
        if (hasLandLeft)return 5;
        if (hasLandUp)return 7;

        return 4;
    }
    private enum CornerType {
        NONE, SE, SW, NE, NW
    }

    private CornerType getWaterCornerType(Lake lake,int x, int y) {
        Cell center = farm.getCell(x, y);
        if (center == null || !(center.getObjectMap() instanceof Lake)) return CornerType.NONE;

        boolean n = isWater(x, y + 1);
        boolean e = isWater(x + 1, y);
        boolean ne = isLand(x + 1, y + 1);

        boolean s = isWater(x, y - 1);
        boolean w = isWater(x - 1, y);
        boolean sw = isLand(x - 1, y - 1);

        if (n && e && ne) {lake.setInitialize(9);return CornerType.SW;}
        if (n && w && isLand(x - 1, y + 1)) {lake.setInitialize(10);return CornerType.SE;}
        if (s && e && isLand(x + 1, y - 1)) {lake.setInitialize(11);return CornerType.NW;}
        if (s && w && sw) {lake.setInitialize(12);return CornerType.NE;}

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
