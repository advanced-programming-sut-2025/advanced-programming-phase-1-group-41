package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.client.view.screen.GreenHouseScreen;
import com.CEliconValley.common.CellData;
import com.CEliconValley.common.FarmData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.CropType;
import com.CEliconValley.models.foragings.ForagingCrop;
import com.CEliconValley.models.foragings.ForagingCropType;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class CropSpawner {
    private final Texture cropTexture = new Texture("game/Crops/crops.png");
    private final Texture giantCropTexture = new Texture("game/Crops/GiantCrops.png");

    private final Map<CropType, TextureRegion[]> cropFramesMap = new HashMap<>();
    private final Map<ForagingCropType, TextureRegion> foragingCropFramesMap = new HashMap<>();

    public CropSpawner() {
        splitCropTexture();
    }

    private void splitCropTexture() {
        int cropCount = CropType.values().length;
        int forageCount = ForagingCropType.values().length;
        int totalRows = 46;
        int cols = 5;

        int frameWidth = cropTexture.getWidth() / cols;
        int frameHeight = cropTexture.getHeight() / totalRows;

        TextureRegion[][] split = TextureRegion.split(cropTexture, frameWidth, frameHeight);


        for (int row = 0; row < cropCount; row++) {
            CropType cropType = CropType.values()[row];
            List<Integer> stageList = cropType.getStages();
            int stageCount = stageList.size();

            TextureRegion[] frames = new TextureRegion[stageCount];
            for (int col = 0; col < stageCount; col++) {
                frames[col] = split[row][col];
            }

            cropFramesMap.put(cropType, frames);
        }


        int startRow = cropCount;
        for (int i = 0; i < forageCount; i++) {
            ForagingCropType type = ForagingCropType.values()[i];
            int row = startRow + i / 5;
            int col = i % 5;
            foragingCropFramesMap.put(type, split[row][col]);
        }
    }


    public void renderCrops(SpriteBatch batch, CellData cellData, FarmData farmData) {
        // TODO Giant Crop
        Cell cell = cellData.extractData();
        if (cell.getObjectMap() instanceof Crop crop) {
            CropType type = crop.getCropType();
            int stageIndex = crop.getCurrentStage();

            TextureRegion[] frames = cropFramesMap.get(type);
            if (!crop.isGiantCrop()&&frames != null && stageIndex >= 0 && stageIndex < frames.length) {
                TextureRegion frame = frames[stageIndex];

                float drawX = cell.getX() * CELL_SIZE;
                float drawY = cell.getY() * CELL_SIZE;

                batch.draw(frame, drawX, drawY-CELL_SIZE, CELL_SIZE, CELL_SIZE*2);
            }else if(crop.isGiantCrop()&&Finder.getcdByFarmData(cell.getX(),cell.getY()+1,farmData).extractData().getObjectMap() instanceof Crop crop2&&crop2.isGiantCrop()
            &&Finder.getcdByFarmData(cell.getX()+1,cell.getY()+1,farmData).extractData().getObjectMap() instanceof Crop crop3&&crop3.isGiantCrop()
            &&Finder.getcdByFarmData(cell.getX()+1,cell.getY(),farmData).extractData().getObjectMap() instanceof Crop crop4&&crop4.isGiantCrop()) {
                if(crop.getCropType()==crop2.getCropType()&&crop2.getCropType()==crop3.getCropType()&&crop3.getCropType()==crop4.getCropType()) {
                    TextureRegion[][] Gframes =TextureRegion.split(giantCropTexture, giantCropTexture.getWidth()/4,giantCropTexture.getHeight());
                    float drawX = cell.getX() * CELL_SIZE;
                    float drawY = cell.getY() * CELL_SIZE;
                    switch (crop.getCropType()) {
                        case Melon -> {
                            batch.draw(Gframes[0][1], drawX, drawY-CELL_SIZE, CELL_SIZE*2, CELL_SIZE*4);
                            break;
                        }
                        case Powdermelon -> {
                            batch.draw(Gframes[0][3], drawX, drawY-CELL_SIZE, CELL_SIZE*2, CELL_SIZE*4);
                            break;
                        }
                        case Pumpkin -> {
                            batch.draw(Gframes[0][2], drawX, drawY-CELL_SIZE, CELL_SIZE*2, CELL_SIZE*4);
                            break;
                        }
                        case Cauliflower -> {
                            batch.draw(Gframes[0][0], drawX, drawY-CELL_SIZE, CELL_SIZE*2, CELL_SIZE*4);
                            break;
                        }
                    }

                }

            }
        } else if (cell.getObjectMap() instanceof ForagingCrop forage) {
            ForagingCropType type = forage.getForagingCropType();

            TextureRegion frame = foragingCropFramesMap.get(type);
            if (frame != null) {
                float drawX = cell.getX() * CELL_SIZE;
                float drawY = cell.getY() * CELL_SIZE;

                batch.draw(frame, drawX, drawY-CELL_SIZE, CELL_SIZE, CELL_SIZE*2);
            }
        }
    }
    public void renderCrops(SpriteBatch batch, CellData cellData, GreenHouseScreen greenHouseScreen) {
        Cell cell = cellData.extractData();
        if (cell.getObjectMap() instanceof Crop crop) {
            CropType type = crop.getCropType();
            int stageIndex = crop.getCurrentStage();

            TextureRegion[] frames = cropFramesMap.get(type);
            if (frames != null && stageIndex >= 0 && stageIndex < frames.length) {
                TextureRegion frame = frames[stageIndex];
                int cs = CELL_SIZE;
                float drawX = (cell.getX() - Finder.getfd().getGreenhouseX()) * cs + cs/2 * 1.2f ;drawX*= 1.1f;
                float drawY = (cell.getY() - Finder.getfd().getGreenhouseY()) * cs + cs;drawY *= 0.9f;

                batch.draw(frame, drawX, drawY, cs / 2 , cs / 2);
            }
        }
    }
}
