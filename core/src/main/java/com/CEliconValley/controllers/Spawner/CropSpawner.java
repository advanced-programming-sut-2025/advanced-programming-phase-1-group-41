package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.models.Cell;
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
    private final Farm farm;
    private final Texture cropTexture = new Texture("game/Crops/crops.png");

    private final Map<CropType, TextureRegion[]> cropFramesMap = new HashMap<>();
    private final Map<ForagingCropType, TextureRegion> foragingCropFramesMap = new HashMap<>();

    public CropSpawner(Farm farm) {
        this.farm = farm;
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

        // محصولات Foraging (از ردیف cropCount به بعد، هر ردیف 5 محصول)
        int startRow = cropCount;
        for (int i = 0; i < forageCount; i++) {
            ForagingCropType type = ForagingCropType.values()[i];
            int row = startRow + i / 5;
            int col = i % 5;
            foragingCropFramesMap.put(type, split[row][col]);
        }
    }


    public void renderCrops(SpriteBatch batch, Cell cell, float passiveState) {
//        if (cell.getObjectMap() instanceof Crop crop) {
//            CropType type = crop.getCropType();
//            int stageIndex = crop.getCurrentStage();
//
//            TextureRegion[] frames = cropFramesMap.get(type);
//            if (frames != null && stageIndex >= 0 && stageIndex < frames.length) {
//                TextureRegion frame = frames[stageIndex];
//
//                float drawX = cell.getX() * CELL_SIZE;
//                float drawY = cell.getY() * CELL_SIZE;
//
//                batch.draw(frame, drawX, drawY, CELL_SIZE, CELL_SIZE);
//            }
//        }
//        else
            if (cell.getObjectMap() instanceof ForagingCrop forage) {
            ForagingCropType type = forage.getForagingCropType();

            TextureRegion frame = foragingCropFramesMap.get(type);
            if (frame != null) {
                float drawX = cell.getX() * CELL_SIZE;
                float drawY = cell.getY() * CELL_SIZE;

                batch.draw(frame, drawX, drawY, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}
