//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.common.BarnData;
import com.CEliconValley.common.CellData;
import com.CEliconValley.common.CoopData;
import com.CEliconValley.common.FarmData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.buildings.Building;
import com.CEliconValley.models.buildings.Cottage;
import com.CEliconValley.models.buildings.GreenHouse.Greenhouse;
import com.CEliconValley.models.buildings.GreenHouse.WaterTank;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.buildings.animalContainer.BarnType;
import com.CEliconValley.models.buildings.animalContainer.Coop;
import com.CEliconValley.models.buildings.animalContainer.CoopType;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.client.view.screen.FarmScreen;
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
            if(cellData.getX() - 1 == barnData.getAnchorX() && cellData.getY() + 1 == barnData.getAnchorY()){
                Texture texture = barnTexture;
                if(barnType.equals(BarnType.Big)){
                    texture = bigBarnTexture;
                } else if(barnType.equals(BarnType.Deluxe)){
                    texture = deluxeTexture;
                }
                int frameWidth = texture.getWidth();
                int frameHeight = texture.getHeight();
                TextureRegion barnFrame = new TextureRegion(texture, 0, 0, frameWidth, frameHeight);
                batch.draw(barnFrame, x - CELL_SIZE*6, y, CELL_SIZE*7, CELL_SIZE*8);
                return true;
            }
        }
        for(CoopData coopData : farmData.getCoopsData()){
            CoopType coopType = CoopType.values()[coopData.getCoopTypeInt()];
            if(cellData.getX() == coopData.getAnchorX() && cellData.getY() + 1 == coopData.getAnchorY()){
                Texture texture = coopTexture;
                if(cell.equals(CoopType.Big)){
                    texture = bigCoopTexture;
                } else if(coopType.equals(CoopType.Deluxe)){
                    texture = deluxeCoopTexture;
                }
                int frameWidth = texture.getWidth();
                int frameHeight = texture.getHeight();
                TextureRegion barnFrame = new TextureRegion(texture, 0, 0, frameWidth, frameHeight);
                batch.draw(barnFrame, x - CELL_SIZE*6, y, CELL_SIZE*7, CELL_SIZE*8);
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
}
