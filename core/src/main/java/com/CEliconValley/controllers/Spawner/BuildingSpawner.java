//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.*;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Cottage;
import com.CEliconValley.models.buildings.Door;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.buildings.GreenHouse.WaterTank;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.BarnType;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.buildings.animalContainer.CoopType;
import com.CEliconValley.models.buildings.marketplaces.*;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.client.view.screen.FarmScreen;
import com.CEliconValley.models.npc.npchomes.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class BuildingSpawner {
    private final WaterSpawner waterSpawner;
    private final Texture cottageTexture = new Texture("game/cottage.png");
    private final Texture greenhouseTexture = new Texture("game/Buildings/GreenHouse.png");
    private final Texture waterTankTexture = new Texture("game/Buildings/WaterTank.png");
    private final Texture barnTexture = new Texture("game/Buildings/Barn.png");
    private final Texture bigBarnTexture = new Texture("game/Buildings/Big_Barn.png");
    private final Texture deluxeTexture = new Texture("game/Buildings/Deluxe_Barn.png");
    private final Texture coopTexture = new Texture("game/Buildings/Coop.png");
    private final Texture bigCoopTexture = new Texture("game/Buildings/Big_Coop.png");
    private final Texture deluxeCoopTexture = new Texture("game/Buildings/Deluxe_Coop.png");
    private final Texture fishShopTexture = new Texture("game/Buildings/FishShop.png");
    private final Texture generalStoreTexture = new Texture("game/Buildings/GeneralStore.png");
    private final Texture blacksmithTexture = new Texture("game/Buildings/Blacksmith.png");
    private final Texture jojaMartTexture = new Texture("game/Buildings/Jojamart.png");
    private final Texture marnieRanchTexture = new Texture("game/Buildings/MarnieRanch.png");
    private final Texture carpenterShopTexture = new Texture("game/Buildings/CarpenterShop.png");
    private final Texture saloonTexture = new Texture("game/Buildings/Saloon.png");
    private final Texture harveyHomeTexture = new Texture("game/Buildings/HarveyHome.png");
    private final Texture abigailHomeTexture = new Texture("game/Buildings/AbigailHome.png");
    private final Texture sebastienHomeTexture = new Texture("game/Buildings/SebastienHome.png");
    private final Texture robinHomeTexture = new Texture("game/Buildings/RobinHome.png");
    private final Texture liaHomeTexture = new Texture("game/Buildings/LiaHome.png");
    private final Texture CloseSignTexture = new Texture("game/Buildings/CloseSign.png");

    public BuildingSpawner() {
        this.waterSpawner = new WaterSpawner();
    }

    public boolean renderBuildings(SpriteBatch batch, CellData cellData, FarmData farmData) {
        Cell cell = cellData.extractData();
        float x = (float)(cell.getX() * CELL_SIZE);
        float y = (float)(cell.getY() * CELL_SIZE);
        if (cell.getObjectMap() instanceof Building) {
        }

//        Cell tmpCell = Finder.findCellByCoordinates(cell.getX() - 1, cell.getY() + 1, this.farm);
        CellData tempcd = Finder.getcdByFarmData(cell.getX() - 1, cell.getY() + 1, farmData);
        if(tempcd == null) return false;
        Cell tmpCell = tempcd.extractData();
        if(cellData.getX() == farmData.getCottageX() + Cottage.getCottageLength() && cellData.getY() == farmData.getCottageY()){
            int frameWidth = this.cottageTexture.getWidth();
            int frameHeight = this.cottageTexture.getHeight();
            TextureRegion cottageFrame = new TextureRegion(this.cottageTexture, 0, 0, frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE*5, y, CELL_SIZE*6, CELL_SIZE*6);
            return true;
        } else if(cellData.getX() == farmData.getGreenhouseX() + Greenhouse.getGreenhouseLength() && cellData.getY() == farmData.getGreenhouseY()) {
            int frameWidth = this.greenhouseTexture.getWidth();
            int frameHeight = this.greenhouseTexture.getHeight();
            TextureRegion greenHouseFrame = new TextureRegion(this.greenhouseTexture, 0, 0, frameWidth, frameHeight);
            batch.draw(greenHouseFrame, x - CELL_SIZE*6, y, CELL_SIZE*7, CELL_SIZE*8);
            return true;
//        }else if (tmpCell != null && tmpCell.getObjectMap() instanceof Cottage) {
//            Cottage cottage = (Cottage)tmpCell.getObjectMap();
//            if (cell.getX() - 1 == cottage.getAnchorX() && cell.getY() + 1 == cottage.getAnchorY()) {
//                int frameWidth = this.cottageTexture.getWidth();
//                int frameHeight = this.cottageTexture.getHeight();
//                TextureRegion cottageFrame = new TextureRegion(this.cottageTexture, 0, 0, frameWidth, frameHeight);
//                batch.draw(cottageFrame, x - CELL_SIZE*5, y, CELL_SIZE*6, CELL_SIZE*6);
//            }
//        } else if (tmpCell != null && tmpCell.getObjectMap() instanceof Greenhouse) {
//            Greenhouse greenhouse = (Greenhouse)tmpCell.getObjectMap();
//            if (cell.getX() - 1 == greenhouse.getAnchorX() && cell.getY() + 1 == greenhouse.getAnchorY()) {
//                int frameWidth = this.greenhouseTexture.getWidth();
//                int frameHeight = this.greenhouseTexture.getHeight();
//                TextureRegion greenHouseFrame = new TextureRegion(this.greenhouseTexture, 0, 0, frameWidth, frameHeight);
//                batch.draw(greenHouseFrame, x - CELL_SIZE*6, y, CELL_SIZE*7, CELL_SIZE*8);
//            }
        }else if (tmpCell != null && tmpCell.getObjectMap() instanceof WaterTank) {
            WaterTank waterTank = (WaterTank)tmpCell.getObjectMap();
            int frameWidth = this.waterTankTexture.getWidth();
            int frameHeight = this.waterTankTexture.getHeight();
            TextureRegion waterTankFrame = new TextureRegion(this.waterTankTexture, 0, 0, frameWidth, frameHeight);
            batch.draw(waterTankFrame, x, y, CELL_SIZE, CELL_SIZE);
            return true;
        }
        for(BarnData barnData : farmData.getBarnsData()){
            BarnType barnType = BarnType.values()[barnData.getBarnTypeInt()];
            if(cellData.getX() == barnData.getAnchorX() && cellData.getY() + 1 == barnData.getAnchorY()){
                int width = 7, height = 8;
                Texture texture = barnTexture;
                if(barnType.equals(BarnType.Big)){
                    texture = bigBarnTexture;
                    width++;
                    height++;
                } else if(barnType.equals(BarnType.Deluxe)){
                    texture = deluxeTexture;
                    width += 2;
                    height += 2;
                }
                int frameWidth = texture.getWidth();
                int frameHeight = texture.getHeight();
                TextureRegion barnFrame = new TextureRegion(texture, 0, 0, frameWidth, frameHeight);
                batch.draw(barnFrame, x - CELL_SIZE*6, y, CELL_SIZE*width, CELL_SIZE*height);
                return true;
            }
        }
        for(CoopData coopData : farmData.getCoopsData()){
            CoopType coopType = CoopType.values()[coopData.getCoopTypeInt()];
            if(cellData.getX() + 1 == coopData.getAnchorX() && cellData.getY() + 1 == coopData.getAnchorY()){
                Texture texture = coopTexture;
                int width = 7, height = 8;
                if(cell.equals(CoopType.Big)){
                    texture = bigCoopTexture;
                    width++;
                    height++;
                } else if(coopType.equals(CoopType.Deluxe)){
                    texture = deluxeCoopTexture;
                    width += 2;
                    height += 2;
                }
                int frameWidth = texture.getWidth();
                int frameHeight = texture.getHeight();
                TextureRegion barnFrame = new TextureRegion(texture, 0, 0, frameWidth, frameHeight);
                batch.draw(barnFrame, x - CELL_SIZE*6, y, CELL_SIZE*width, CELL_SIZE*height);
                return true;
            }
        }
//        else if(tmpCell != null && tmpCell.getObjectMap() instanceof Barn){
//            Barn barn = (Barn)tmpCell.getObjectMap();
//            if (cell.getX() - 1 == barn.getAnchorX() && cell.getY() + 1 == barn.getAnchorY()) {
//                int frameWidth = this.barnTexture.getWidth();
//                int frameHeight = this.barnTexture.getHeight();
//                TextureRegion barnFrame = new TextureRegion(this.barnTexture, 0, 0, frameWidth, frameHeight);
//                batch.draw(barnFrame, x - CELL_SIZE*6, y, CELL_SIZE*7, CELL_SIZE*8);
//            }
//        }
//        else if (tmpCell != null && tmpCell.getObjectMap() instanceof Coop) {
//            Coop coop = (Coop) tmpCell.getObjectMap();
//            if (cell.getX() - 1 == coop.getAnchorX() && cell.getY() + 1 == coop.getAnchorY()) {
//                int frameWidth = this.coopTexture.getWidth();
//                int frameHeight = this.coopTexture.getHeight();
//                TextureRegion coopFrame = new TextureRegion(this.coopTexture, 0, 0, frameWidth, frameHeight);
//                batch.draw(coopFrame, x - CELL_SIZE * 6, y, CELL_SIZE * 7, CELL_SIZE * 8);
//            }
//        }

        return false;
    }
    public boolean renderBuildings(SpriteBatch batch, CellData cellData, VillageData villageData) {
        Cell cell = cellData.extractData();
        float x = (float)(cell.getX() * CELL_SIZE);
        float y = (float)(cell.getY() * CELL_SIZE);
        if (cell.getObjectMap() instanceof Building) {
        }

//        Cell tmpCell = Finder.findCellByCoordinates(cell.getX() - 1, cell.getY() + 1, this.farm);
        CellData tempcd = Finder.getcdByVillageData(cell.getX() - 1, cell.getY() + 1, villageData);
        if(tempcd == null) return false;
        Cell tmpCell = tempcd.extractData();

        if(tmpCell.getObjectMap() instanceof FishShop fishShop && fishShop.getAnchorX()==tmpCell.getX() && fishShop.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.fishShopTexture.getWidth();
            int frameHeight = this.fishShopTexture.getHeight() / 4;
            TextureRegion cottageFrame = new TextureRegion(this.fishShopTexture, 0, (int) getSeasonalTexture(fishShopTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE * 5.5f, y, CELL_SIZE * 6, CELL_SIZE * 6);
            return true;
        }

        if(tmpCell.getObjectMap() instanceof GeneralStore generalStore && generalStore.getAnchorX()==tmpCell.getX() && generalStore.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.generalStoreTexture.getWidth();
            int frameHeight = this.generalStoreTexture.getHeight() / 4;

            TextureRegion cottageFrame = new TextureRegion(this.generalStoreTexture, 0,(int)getSeasonalTexture(generalStoreTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE * 14, y-CELL_SIZE*2, CELL_SIZE * 11, CELL_SIZE * 9);
            return true;

        }

        if(tmpCell.getObjectMap() instanceof Blacksmith  blackSmith && blackSmith.getAnchorX()==tmpCell.getX() && blackSmith.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.blacksmithTexture.getWidth();
            int frameHeight = this.blacksmithTexture.getHeight() / 4;

            TextureRegion cottageFrame = new TextureRegion(this.blacksmithTexture, 0,(int)getSeasonalTexture(blacksmithTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE * 5.5f, y, CELL_SIZE * 15, CELL_SIZE * 6);
            return true;

        }

        if(tmpCell.getObjectMap() instanceof Jojamart jojaMart && jojaMart.getAnchorX()==tmpCell.getX() && jojaMart.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.jojaMartTexture.getWidth();
            int frameHeight = this.jojaMartTexture.getHeight() / 4;

            TextureRegion cottageFrame = new TextureRegion(this.jojaMartTexture, 0,(int)getSeasonalTexture(jojaMartTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE * 7, y-CELL_SIZE*6, CELL_SIZE * 11, CELL_SIZE * 8);
            return true;

        }

        if(tmpCell.getObjectMap() instanceof MarnieRanch marnieRanch && marnieRanch.getAnchorX()==tmpCell.getX() && marnieRanch.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.marnieRanchTexture.getWidth();
            int frameHeight = this.marnieRanchTexture.getHeight() / 4;

            TextureRegion cottageFrame = new TextureRegion(this.marnieRanchTexture, 0,(int)getSeasonalTexture(marnieRanchTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE * 5, y, CELL_SIZE * 6, CELL_SIZE * 6);
            return true;

        }
        if(tmpCell.getObjectMap() instanceof Saloon saloon) {
            System.out.println(saloon.getAnchorX() + " "+tmpCell.getX()+" " + saloon.getAnchorY()+"  "+tmpCell.getY());


        if( saloon.getAnchorX()==tmpCell.getX() && saloon.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.saloonTexture.getWidth();
            int frameHeight = this.saloonTexture.getHeight()/4;
            batch.draw(waterTankTexture, tmpCell.getX()*CELL_SIZE, tmpCell.getY()*CELL_SIZE, CELL_SIZE , CELL_SIZE );

            TextureRegion cottageFrame = new TextureRegion(this.saloonTexture, 0,(int)getSeasonalTexture(saloonTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE*5, y-CELL_SIZE/3f, CELL_SIZE *6, CELL_SIZE * 8);
            return true;

        }
        }
        if(tmpCell.getObjectMap() instanceof CarpenterShop carpenterShop && carpenterShop.getAnchorX()==tmpCell.getX() && carpenterShop.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.carpenterShopTexture.getWidth();
            int frameHeight = this.carpenterShopTexture.getHeight() / 4;

            TextureRegion cottageFrame = new TextureRegion(this.carpenterShopTexture, 0,(int)getSeasonalTexture(carpenterShopTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE * 10, y, CELL_SIZE *10, CELL_SIZE * 10);
            return true;

        }
        if(tmpCell.getObjectMap() instanceof HarveyHome harveyHome && harveyHome.getAnchorX()==tmpCell.getX() && harveyHome.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.harveyHomeTexture.getWidth();
            int frameHeight = this.harveyHomeTexture.getHeight() / 4;

            TextureRegion cottageFrame = new TextureRegion(this.harveyHomeTexture, 0,(int)getSeasonalTexture(harveyHomeTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE * 6, y-CELL_SIZE, CELL_SIZE *7, CELL_SIZE * 5);
            return true;

        }
        if(tmpCell.getObjectMap() instanceof RobinHome robinHome && robinHome.getAnchorX()==tmpCell.getX() && robinHome.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.robinHomeTexture.getWidth();
            int frameHeight = this.robinHomeTexture.getHeight() / 4;

            TextureRegion cottageFrame = new TextureRegion(this.robinHomeTexture, 0,(int)getSeasonalTexture(robinHomeTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE * 5, y-CELL_SIZE/2f, CELL_SIZE *6, CELL_SIZE * 3);
            return true;

        }
        if(tmpCell.getObjectMap() instanceof SebastienHome sebastienHome && sebastienHome.getAnchorX()==tmpCell.getX() && sebastienHome.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.sebastienHomeTexture.getWidth();
            int frameHeight = this.sebastienHomeTexture.getHeight() / 4;

            TextureRegion cottageFrame = new TextureRegion(this.sebastienHomeTexture, 0,(int)getSeasonalTexture(sebastienHomeTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE * 5, y-CELL_SIZE/2f, CELL_SIZE *6, CELL_SIZE * 7);
            return true;

        }
        if(tmpCell.getObjectMap() instanceof LiaHome liaHome && liaHome.getAnchorX()==tmpCell.getX() && liaHome.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.liaHomeTexture.getWidth();
            int frameHeight = this.liaHomeTexture.getHeight() / 4;

            TextureRegion cottageFrame = new TextureRegion(this.liaHomeTexture, 0,(int)getSeasonalTexture(liaHomeTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE * 5.66f, y-CELL_SIZE/2f, CELL_SIZE *7, CELL_SIZE * 7);
            return true;

        }

        if(tmpCell.getObjectMap() instanceof AbigailHome abigailHome && abigailHome.getAnchorX()==tmpCell.getX() && abigailHome.getAnchorY()==tmpCell.getY()) {
            int frameWidth = this.abigailHomeTexture.getWidth();
            int frameHeight = this.abigailHomeTexture.getHeight() / 4;

            TextureRegion cottageFrame = new TextureRegion(this.abigailHomeTexture, 0,(int)getSeasonalTexture(abigailHomeTexture), frameWidth, frameHeight);
            batch.draw(cottageFrame, x - CELL_SIZE * 4.33f, y-CELL_SIZE/2f, CELL_SIZE *5, CELL_SIZE * 5);
            return true;

        }




        return false;
    }
    public boolean renderOnBuildings(SpriteBatch batch, CellData cellData, VillageData villageData) {
        Cell cell = cellData.extractData();
        float x = (float)(cell.getX() * CELL_SIZE);
        float y = (float)(cell.getY() * CELL_SIZE);
        if (cell.getObjectMap() instanceof Building) {
        }
        Cell tmpCell=cellData.extractData();
        if(tmpCell.getObjectMap() instanceof Door door) {
            float frameWidth;
            float frameHeight;
            if(door.getInitialize()==-1) {
                if (Finder.findCellByCoordinatesVillage(tmpCell.getX()+1, tmpCell.getY(), villageData).extractData().getObjectMap() instanceof Door door2) {
                    if (Finder.findCellByCoordinatesVillage(tmpCell.getX() + 2, tmpCell.getY(), villageData).extractData().getObjectMap() instanceof Door door3) {
                        door.setInitialize(3);
                        door2.setInitialize(0);
                        door3.setInitialize(0);
                    }else{
                        door.setInitialize(2);
                        door2.setInitialize(0);
                    }
                }
                door.setInitialize(1);
            }
//            door.setClosed(true);
            if(door.isClosed()&&door.getInitialize()!=0) {
                frameWidth = door.getInitialize()*0.4f;
                frameHeight = frameWidth;
                TextureRegion cottageFrame = new TextureRegion(this.CloseSignTexture, 0,0, this.CloseSignTexture.getWidth(), this.CloseSignTexture.getHeight());
                batch.draw(cottageFrame, x , y+CELL_SIZE/2f, frameWidth, frameHeight);
                System.out.println("done");
            }

            return true;

        }
        return false;
    }
    private float getSeasonalTexture(Texture texture) {
        switch (AppClient.getGameData().getTime().getSeason()){
            case Spring -> {
                return 0;
            }
            case Summer -> {
                return texture.getHeight()/4f;
            }
            case Autumn -> {
                return texture.getHeight()/4f*2;
            }
            case Winter -> {
                return texture.getHeight()/4f*3;
            }
        }
        return 0;
    }
}

