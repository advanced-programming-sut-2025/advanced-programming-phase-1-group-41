package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.CellData;
import com.CEliconValley.common.FarmData;
import com.CEliconValley.common.VillageData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class GroundBorderSpawner {

    private Texture groundTexture;
    private Texture coastTexture;
    private Texture cornerTexture;
    private String currentSeason = "";



    private  TextureRegion groundRegion;
    private  TextureRegion[] coastRegions = new TextureRegion[9];
    private  TextureRegion cornerSE, cornerNE, cornerNW, cornerSW;

    public GroundBorderSpawner() {
        switch (AppClient.getGameData().getTime().getSeason()){
            case Spring -> {
                groundTexture = new Texture("game/general/tiles/ground_Spring.png");
                coastTexture = new Texture("game/general/tiles/groundBorder_Spring.png");
                cornerTexture = new Texture("game/general/tiles/groundCorner_Spring.png");
            }
            case Summer -> {
                groundTexture = new Texture("game/general/tiles/ground_Summer.png");
                coastTexture = new Texture("game/general/tiles/groundBorder_Summer.png");
                cornerTexture = new Texture("game/general/tiles/groundCorner_Summer.png");
            }
            case Autumn ->  {
                groundTexture = new Texture("game/general/tiles/ground_Autumn.png");
                coastTexture = new Texture("game/general/tiles/groundBorder_Autumn.png");
                cornerTexture = new Texture("game/general/tiles/groundCorner_Autumn.png");
            }
            case Winter -> {
                groundTexture = new Texture("game/general/tiles/ground_Winter.png");
                coastTexture = new Texture("game/general/tiles/groundBorder_Winter.png");
                cornerTexture = new Texture("game/general/tiles/groundCorner_Winter.png");
            }
        }

        groundRegion = new TextureRegion(groundTexture);


        TextureRegion[][] coastTmp = TextureRegion.split(
            coastTexture,
            coastTexture.getWidth()  / 9,
            coastTexture.getHeight()
        );
        for (int i = 0; i < 9; i++) {
            coastRegions[i] = coastTmp[0][i];
        }


        TextureRegion[][] tmp = TextureRegion.split(cornerTexture, cornerTexture.getWidth(), cornerTexture.getHeight());
        TextureRegion[] base = new TextureRegion[4];
        base[0] = tmp[0][0];

        cornerSE = base[0];
        cornerNE = flipY(base[0]);
        cornerNW = flipXY(base[0]);
        cornerSW = flipX(base[0]);
    }

    public void renderGround(SpriteBatch batch, CellData cellData, FarmData farmData) {


        String season = AppClient.getGameData().getTime().getSeason().name();
        if (!season.equals(currentSeason)) {
            currentSeason = season;
            loadSeasonTextures(season);
        }

        int x = cellData.getX(), y = cellData.getY();
        float drawX = x * CELL_SIZE, drawY = y * CELL_SIZE;

        Cell cell = cellData.extractData();
        if (!(cell.getObjectMap() instanceof Grass grass) || !grass.isGround())
            return;

        batch.draw(groundRegion, drawX, drawY, CELL_SIZE, CELL_SIZE);

        if (grass.getInitialize() > -1) {
            switch (grass.getInitialize()) {
                case 0, 2, 6, 8, 1, 3, 5, 7 -> batch.draw(coastRegions[grass.getInitialize()], drawX, drawY, CELL_SIZE, CELL_SIZE);
                case 9  -> batch.draw(cornerSW, drawX, drawY, CELL_SIZE, CELL_SIZE);
                case 10 -> batch.draw(cornerSE, drawX, drawY, CELL_SIZE, CELL_SIZE);
                case 11 -> batch.draw(cornerNW, drawX, drawY, CELL_SIZE, CELL_SIZE);
                case 12 -> batch.draw(cornerNE, drawX, drawY, CELL_SIZE, CELL_SIZE);
            }
            return;
        }

        if (isCoast(cell, farmData)) {
            int idx = getCoastIndex(x, y, farmData);
            grass.setInitialize(idx);
            batch.draw(coastRegions[idx], drawX, drawY, CELL_SIZE, CELL_SIZE);
        } else {
            CornerType corner = getGroundCornerType(x, y, farmData);
            if (corner != CornerType.NONE) {
                int code = switch (corner) {
                    case SE -> 10;
                    case SW -> 9;
                    case NE -> 12;
                    case NW -> 11;
                    default -> -1;
                };
                grass.setInitialize(code);
                switch (code) {
                    case 9  -> batch.draw(cornerSW, drawX, drawY, CELL_SIZE, CELL_SIZE);
                    case 10 -> batch.draw(cornerSE, drawX, drawY, CELL_SIZE, CELL_SIZE);
                    case 11 -> batch.draw(cornerNW, drawX, drawY, CELL_SIZE, CELL_SIZE);
                    case 12 -> batch.draw(cornerNE, drawX, drawY, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }


    private boolean isCoast(Cell cell, FarmData farmData) {
        int x = cell.getX(), y = cell.getY();
        if (!(cell.getObjectMap() instanceof Grass g) || !g.isGround()) return false;
        return !isGround(x+1,y,farmData)
            || !isGround(x-1,y,farmData)
            || !isGround(x,y+1,farmData)
            || !isGround(x,y-1,farmData);
    }

    private boolean isSimpleGrass(int x, int y, FarmData farmData) {
        CellData cd = Finder.getcdByFarmData(x, y, farmData);
        if (cd == null) return false;
        Cell c = cd.extractData();
        return c.getObjectMap() instanceof Grass g && !g.isGround();
    }
    private boolean isGround(int x, int y, FarmData farmData) {
        CellData cd = Finder.getcdByFarmData(x, y, farmData);
        if (cd == null) return false;
        Cell c = cd.extractData();
        return c.getObjectMap() instanceof Grass g && g.isGround();
    }

    private int getCoastIndex(int x, int y, FarmData farmData) {
        boolean r = !isGround(x+1,y,farmData);
        boolean l = !isGround(x-1,y,farmData);
        boolean u = !isGround(x,y+1,farmData);
        boolean d = !isGround(x,y-1,farmData);

        if (r && d) return 0;
        if (u && l) return 8;
        if (l && d) return 2;
        if (u && r) return 6;
        if (d)      return 1;
        if (r)      return 3;
        if (l)      return 5;
        if (u)      return 7;
        return 4;
    }

    private CornerType getGroundCornerType(int x, int y, FarmData farmData) {
        boolean n  = !isGround(x,   y+1, farmData) == false;
        boolean s  = !isGround(x,   y-1, farmData) == false;
        boolean e  = !isGround(x+1, y,   farmData) == false;
        boolean w  = !isGround(x-1, y,   farmData) == false;
        boolean ne = !isGround(x+1, y+1, farmData);
        boolean nw = !isGround(x-1, y+1, farmData);
        boolean se = !isGround(x+1, y-1, farmData);
        boolean sw = !isGround(x-1, y-1, farmData);

        if (n && e && ne) return CornerType.SW;
        if (n && w && nw) return CornerType.SE;
        if (s && e && se) return CornerType.NW;
        if (s && w && sw) return CornerType.NE;
        return CornerType.NONE;
    }
    public void renderGround(SpriteBatch batch, CellData cellData, VillageData villageData) {


        String season = AppClient.getGameData().getTime().getSeason().name();
        if (!season.equals(currentSeason)) {
            currentSeason = season;
            loadSeasonTextures(season);
        }

        int x = cellData.getX(), y = cellData.getY();
        float drawX = x * CELL_SIZE, drawY = y * CELL_SIZE;

        Cell cell = cellData.extractData();
        if (!(cell.getObjectMap() instanceof Grass grass) || !grass.isGround())
            return;

        batch.draw(groundRegion, drawX, drawY, CELL_SIZE, CELL_SIZE);

        if (grass.getInitialize() > -1) {
            switch (grass.getInitialize()) {
                case 0, 2, 6, 8, 1, 3, 5, 7 -> batch.draw(coastRegions[grass.getInitialize()], drawX, drawY, CELL_SIZE, CELL_SIZE);
                case 9  -> batch.draw(cornerSW, drawX, drawY, CELL_SIZE, CELL_SIZE);
                case 10 -> batch.draw(cornerSE, drawX, drawY, CELL_SIZE, CELL_SIZE);
                case 11 -> batch.draw(cornerNW, drawX, drawY, CELL_SIZE, CELL_SIZE);
                case 12 -> batch.draw(cornerNE, drawX, drawY, CELL_SIZE, CELL_SIZE);
            }
            return;
        }

        if (isCoast(cell, villageData)) {
            int idx = getCoastIndex(x, y, villageData);
            grass.setInitialize(idx);
            batch.draw(coastRegions[idx], drawX, drawY, CELL_SIZE, CELL_SIZE);
        } else {
            CornerType corner = getGroundCornerType(x, y, villageData);
            if (corner != CornerType.NONE) {
                int code = switch (corner) {
                    case SE -> 10;
                    case SW -> 9;
                    case NE -> 12;
                    case NW -> 11;
                    default -> -1;
                };
                grass.setInitialize(code);
                switch (code) {
                    case 9  -> batch.draw(cornerSW, drawX, drawY, CELL_SIZE, CELL_SIZE);
                    case 10 -> batch.draw(cornerSE, drawX, drawY, CELL_SIZE, CELL_SIZE);
                    case 11 -> batch.draw(cornerNW, drawX, drawY, CELL_SIZE, CELL_SIZE);
                    case 12 -> batch.draw(cornerNE, drawX, drawY, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }


    private boolean isCoast(Cell cell, VillageData villageData) {
        int x = cell.getX(), y = cell.getY();
        if (!(cell.getObjectMap() instanceof Grass g) || !g.isGround()) return false;
        return !isGround(x+1,y,villageData)
            || !isGround(x-1,y,villageData)
            || !isGround(x,y+1,villageData)
            || !isGround(x,y-1,villageData);
    }

    private boolean isSimpleGrass(int x, int y, VillageData villageData) {
        CellData cd = Finder.getcdByVillageData(x, y, villageData);
        if (cd == null) return false;
        Cell c = cd.extractData();
        return c.getObjectMap() instanceof Grass g && !g.isGround();
    }
    private boolean isGround(int x, int y,VillageData villageData) {
        CellData cd = Finder.getcdByVillageData(x, y, villageData);
        if (cd == null) return false;
        Cell c = cd.extractData();
        return c.getObjectMap() instanceof Grass g && g.isGround();
    }

    private int getCoastIndex(int x, int y,VillageData villageData) {
        boolean r = !isGround(x+1,y,villageData);
        boolean l = !isGround(x-1,y,villageData);
        boolean u = !isGround(x,y+1,villageData);
        boolean d = !isGround(x,y-1,villageData);

        if (r && d) return 0;
        if (u && l) return 8;
        if (l && d) return 2;
        if (u && r) return 6;
        if (d)      return 1;
        if (r)      return 3;
        if (l)      return 5;
        if (u)      return 7;
        return 4;
    }

    private CornerType getGroundCornerType(int x, int y, VillageData villageData) {
        boolean n  = !isGround(x,   y+1, villageData) == false;
        boolean s  = !isGround(x,   y-1, villageData) == false;
        boolean e  = !isGround(x+1, y,   villageData) == false;
        boolean w  = !isGround(x-1, y,   villageData) == false;
        boolean ne = !isGround(x+1, y+1, villageData);
        boolean nw = !isGround(x-1, y+1, villageData);
        boolean se = !isGround(x+1, y-1, villageData);
        boolean sw = !isGround(x-1, y-1, villageData);

        if (n && e && ne) return CornerType.SW;
        if (n && w && nw) return CornerType.SE;
        if (s && e && se) return CornerType.NW;
        if (s && w && sw) return CornerType.NE;
        return CornerType.NONE;
    }

    private TextureRegion flipY(TextureRegion orig) {
        TextureRegion f = new TextureRegion(orig); f.flip(false, true);
        return f;
    }
    private TextureRegion flipX(TextureRegion orig) {
        TextureRegion f = new TextureRegion(orig); f.flip(true, false);
        return f;
    }
    private TextureRegion flipXY(TextureRegion orig) {
        TextureRegion f = new TextureRegion(orig); f.flip(true, true);
        return f;
    }
    private void loadSeasonTextures(String season) {
        disposeTextures();

        groundTexture = new Texture("game/general/tiles/ground_" + season + ".png");
        coastTexture = new Texture("game/general/tiles/groundBorder_" + season + ".png");
        cornerTexture = new Texture("game/general/tiles/groundCorner_" + season + ".png");

        groundRegion = new TextureRegion(groundTexture);

        TextureRegion[][] coastTmp = TextureRegion.split(
            coastTexture,
            coastTexture.getWidth() / 9,
            coastTexture.getHeight()
        );
        for (int i = 0; i < 9; i++) {
            coastRegions[i] = coastTmp[0][i];
        }

        TextureRegion[][] tmp = TextureRegion.split(cornerTexture, cornerTexture.getWidth(), cornerTexture.getHeight());
        TextureRegion base = tmp[0][0];

        cornerSE = base;
        cornerNE = flipY(base);
        cornerNW = flipXY(base);
        cornerSW = flipX(base);
    }

    private void disposeTextures() {
        if (groundTexture != null) groundTexture.dispose();
        if (coastTexture != null) coastTexture.dispose();
        if (cornerTexture != null) cornerTexture.dispose();

    }


    private enum CornerType { SE, SW, NE, NW, NONE }
}
