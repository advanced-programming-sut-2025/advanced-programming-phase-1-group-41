package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.CellData;
import com.CEliconValley.common.FarmData;
import com.CEliconValley.common.VillageData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Bridge;
import com.CEliconValley.models.buildings.Well;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.foragings.Nature.WaterTile;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class WaterSpawner {
    Texture waterTexture = new Texture("game/general/tiles/water.png");
    Texture bridgeTexture = new Texture("game/general/tiles/bridge.png");
    Texture edgeBridgeTexture = new Texture("game/general/tiles/edgeBridge.png");
    Texture topBridgeTexture = new Texture("game/general/tiles/topBridge.png");
    Texture coastTexture = new Texture("game/general/tiles/coast_Spring.png");
    Texture cornerTexture = new Texture("game/general/tiles/waterCorner_Spring.png");
    private Animation<TextureRegion> waterAnimation;
    Animation<TextureRegion> animCornerSE ;
    Animation<TextureRegion> animCornerNE ;
    Animation<TextureRegion> animCornerNW ;
    Animation<TextureRegion> animCornerSW ;
    private Animation<TextureRegion>[] coastAnimations;
    private String currentSeason = "";


    public WaterSpawner() {
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

    public boolean renderWater(SpriteBatch batch, CellData cellData, float passiveStateTime, FarmData farmData) {
        String season = AppClient.getGameData().getTime().getSeason().name();
        if (!season.equals(currentSeason)) {
            currentSeason = season;
            loadSeasonTextures(season);
        }

        float x = cellData.getX() * CELL_SIZE;
        float y = cellData.getY() * CELL_SIZE;
        Cell cell = cellData.extractData();
        Cell checker = Finder.getcdByFarmData(cellData.getX(), cellData.getY() - 1, farmData).extractData();
        if(checker.getObjectMap() instanceof Well){
            return false;
        }
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



            if (isCoast(cell, farmData)) {
                int animIndex = getCoastAnimationIndex(cell.getX(), cell.getY(), farmData);
                lake.setInitialize(animIndex);
//                System.out.println(animIndex);
                TextureRegion frame = coastAnimations[animIndex].getKeyFrame(passiveStateTime, true);
                batch.draw(frame, x, y, CELL_SIZE, CELL_SIZE);
            }


            CornerType corner = getWaterCornerType(lake,cell.getX(), cell.getY(), farmData);
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
    private boolean isCoast(Cell cell, FarmData farmData) {
        int x = cell.getX();
        int y = cell.getY();


        if (!(cell.getObjectMap() instanceof Lake)) return false;


        return isLand(x + 1, y, farmData) || isLand(x - 1, y, farmData) ||
            isLand(x, y + 1, farmData) || isLand(x, y - 1, farmData);
    }


    private boolean isLand(int x, int y, FarmData farmData) {
        CellData cd = Finder.getcdByFarmData(x, y , farmData);
        if(cd == null) return false;
        return ! cd.getObjectName().equals(new Lake().getName());
    }


    public boolean isCorner(CellData cellData, FarmData farmData) {
        int x = cellData.getX();
        int y = cellData.getY();
        return !isWater(x,y, farmData)&&!isWater(x+1,y, farmData)&&!isWater(x,y+1, farmData)&&!isWater(x-1,y, farmData)&&!isWater(x,y-1, farmData)&&
            (isWater(x+1,y+1, farmData)||isWater(x+1,y-1, farmData)||isWater(x,y+1, farmData)||
                isWater(x-1,y+1, farmData)||isWater(x-1,y-1, farmData));
    }
    private int getCoastAnimationIndex(int x, int y, FarmData farmData) {
        boolean hasLandRight = isLand(x+1, y, farmData);
        boolean hasLandLeft = isLand(x-1, y, farmData);
        boolean hasLandUp = isLand(x, y+1, farmData);
        boolean hasLandDown = isLand(x, y-1, farmData);

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

    private CornerType getWaterCornerType(Lake lake,int x, int y, FarmData farmData) {
        CellData centerData = Finder.getcdByFarmData(x, y, farmData);
        if (centerData == null || !(centerData.getObjectName().equals(new Lake().getName()))) return CornerType.NONE;

        boolean n = isWater(x, y + 1, farmData);
        boolean e = isWater(x + 1, y, farmData);
        boolean ne = isLand(x + 1, y + 1, farmData);

        boolean s = isWater(x, y - 1, farmData);
        boolean w = isWater(x - 1, y, farmData);
        boolean sw = isLand(x - 1, y - 1, farmData);

        if (n && e && ne) {lake.setInitialize(9);return CornerType.SW;}
        if (n && w && isLand(x - 1, y + 1, farmData)) {lake.setInitialize(10);return CornerType.SE;}
        if (s && e && isLand(x + 1, y - 1, farmData)) {lake.setInitialize(11);return CornerType.NW;}
        if (s && w && sw) {lake.setInitialize(12);return CornerType.NE;}

        return CornerType.NONE;
    }





    private boolean isWater(int x, int y, FarmData farmData) {
        // maybe check for more than just lake in here!
        CellData cd = Finder.getcdByFarmData(x, y ,farmData);
        if(cd == null) return false;
        return cd.getObjectName().equals(new Lake().getName());
    }
    public boolean renderWater(SpriteBatch batch, CellData cellData, float passiveStateTime, VillageData villageData) {
        String season = AppClient.getGameData().getTime().getSeason().name();
        if (!season.equals(currentSeason)) {
            currentSeason = season;
            loadSeasonTextures(season);
        }

        float x = cellData.getX() * CELL_SIZE;
        float y = cellData.getY() * CELL_SIZE;
        Cell cell = cellData.extractData();
        if (cell.getObjectMap() instanceof WaterTile tile) {
            TextureRegion waterFrame = waterAnimation.getKeyFrame(passiveStateTime, true);
            batch.draw(waterFrame, x, y, CELL_SIZE, CELL_SIZE);
            if(tile.getInitialize()>-1) {
                switch (tile.getInitialize()) {
                    case 0, 2, 6, 8, 1, 3, 5, 7 -> {
                        TextureRegion coastFrame = coastAnimations[tile.getInitialize()].getKeyFrame(passiveStateTime, true);
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



            if (isCoast(cell, villageData)) {
                int animIndex = getCoastAnimationIndex(cell.getX(), cell.getY(), villageData);
                tile.setInitialize(animIndex);
//                System.out.println(animIndex);
                TextureRegion frame = coastAnimations[animIndex].getKeyFrame(passiveStateTime, true);
                batch.draw(frame, x, y, CELL_SIZE, CELL_SIZE);
            }


            CornerType corner = getWaterCornerType(tile,cell.getX(), cell.getY(), villageData);
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
            if(tile instanceof Bridge bridge) {
                if(bridge.getType()==-1){
                    CellData cd =Finder.findCellByCoordinatesVillage(cellData.getX(), cellData.getY()-1, villageData);
                    CellData cdUp=Finder.findCellByCoordinatesVillage(cellData.getX(), cellData.getY()+1, villageData);
                    if(cd!=null&&!cd.getObjectName().equals(new Bridge().getName())) {
                        bridge.setType(2);
                    }else if(cdUp==null||cdUp!=null&&!cdUp.getObjectName().equals(new Bridge().getName())) {
                        bridge.setType(3);
                    }
                        else{
                        bridge.setType(1);
                    }
                }
                if(bridge.getType()==1){
                    batch.draw(bridgeTexture, cellData.getX()*CELL_SIZE, cellData.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);

                }else if(bridge.getType()==2){

                    batch.draw(edgeBridgeTexture, cellData.getX()*CELL_SIZE, cellData.getY()*CELL_SIZE-12, CELL_SIZE, CELL_SIZE+12);
                }else if(bridge.getType()==3){
                    batch.draw(topBridgeTexture, cellData.getX()*CELL_SIZE, cellData.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }

            return true;
        }


        return false;
    }
    private int getCoastAnimationIndex(int x, int y, VillageData villageData) {
        boolean hasLandRight = isLand(x+1, y, villageData);
        boolean hasLandLeft = isLand(x-1, y, villageData);
        boolean hasLandUp = isLand(x, y+1, villageData);
        boolean hasLandDown = isLand(x, y-1, villageData);

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


    private CornerType getWaterCornerType(WaterTile tile,int x, int y, VillageData villageData) {
        CellData center = Finder.getcdByVillageData(x, y, villageData);
        if (center == null) return CornerType.NONE;
        String name = center.getObjectName();
        boolean isLake  = name.equals(new Lake().getName());
        boolean isBridge = name.equals(new Bridge().getName());
        if (!isLake && !isBridge) return CornerType.NONE;
        boolean n = isWater(x, y + 1, villageData);
        boolean e = isWater(x + 1, y, villageData);
        boolean ne = isLand(x + 1, y + 1, villageData);

        boolean s = isWater(x, y - 1, villageData);
        boolean w = isWater(x - 1, y, villageData);
        boolean sw = isLand(x - 1, y - 1, villageData);

        if (n && e && ne) {tile.setInitialize(9);return CornerType.SW;}
        if (n && w && isLand(x - 1, y + 1, villageData)) {tile.setInitialize(10);return CornerType.SE;}
        if (s && e && isLand(x + 1, y - 1, villageData)) {tile.setInitialize(11);return CornerType.NW;}
        if (s && w && sw) {tile.setInitialize(12);return CornerType.NE;}

        return CornerType.NONE;
    }




    private boolean isWater(int x, int y, VillageData villageData) {
        CellData cd = Finder.getcdByVillageData(x, y, villageData);
        if (cd == null) return false;

        String obj = cd.getObjectName();
        return obj.equals(new Lake().getName())
            || obj.equals(new Bridge().getName());
    }


    private boolean isLand(int x, int y, VillageData villageData) {
        CellData cd = Finder.getcdByVillageData(x, y, villageData);
        if (cd == null) return false;
        String obj = cd.getObjectName();

        return !obj.equals(new Lake().getName())
            && !obj.equals(new Bridge().getName());
    }
    private boolean isCoast(Cell cell, VillageData villageData) {
        int x = cell.getX();
        int y = cell.getY();


        if (!(cell.getObjectMap() instanceof WaterTile)) return false;


        return isLand(x + 1, y, villageData) || isLand(x - 1, y, villageData) ||
            isLand(x, y + 1, villageData) || isLand(x, y - 1, villageData);
    }




    public boolean isCorner(CellData cellData, VillageData villageData) {
        int x = cellData.getX();
        int y = cellData.getY();
        return !isWater(x,y, villageData)&&!isWater(x+1,y, villageData)&&!isWater(x,y+1, villageData)&&!isWater(x-1,y, villageData)&&!isWater(x,y-1, villageData)&&
            (isWater(x+1,y+1, villageData)||isWater(x+1,y-1, villageData)||isWater(x,y+1, villageData)||
                isWater(x-1,y+1, villageData)||isWater(x-1,y-1, villageData));
    }
    public void loadSeasonTextures(String season) {
        if (coastTexture != null) coastTexture.dispose();
        if (cornerTexture != null) cornerTexture.dispose();

        coastTexture = new Texture("game/general/tiles/coast_" + season + ".png");
        cornerTexture = new Texture("game/general/tiles/waterCorner_" + season + ".png");

        splitSeasonalTextures();
    }
    private void splitSeasonalTextures() {
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

        int FRAME_COLS = 9;
        int FRAME_ROWS = 4;
        tmp = TextureRegion.split(coastTexture, coastTexture.getWidth() / FRAME_COLS, coastTexture.getHeight() / FRAME_ROWS);

        coastAnimations = new Animation[FRAME_COLS];
        for (int i = 0; i < FRAME_COLS; i++) {
            TextureRegion[] frames = new TextureRegion[FRAME_ROWS];
            for (int j = 0; j < FRAME_ROWS; j++) {
                frames[j] = new TextureRegion(tmp[j][i]);
            }
            coastAnimations[i] = new Animation<>(0.15f, frames);
        }
    }





}
