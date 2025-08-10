package com.CEliconValley.client.controller.spawners;

import com.CEliconValley.common.CellData;
import com.CEliconValley.common.FarmData;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.foragings.ForagingCrop;
import com.CEliconValley.models.foragings.ForagingCropType;
import com.CEliconValley.models.items.Item;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class ItemSpawner {
    public void renderItems(SpriteBatch batch, CellData cellData, FarmData farmData) {
        Item item = Finder.parseItem(cellData.getObjectName());
        if (item == null) {
            return;
        }
        TextureRegion itemTexture = ItemManager.getTexture(item.getID());
        float drawX = cellData.getX() * CELL_SIZE;
        float drawY = cellData.getY() * CELL_SIZE;
        float newWidth = itemTexture.getRegionWidth() * (CELL_SIZE / itemTexture.getRegionHeight());
        batch.draw(itemTexture, drawX + (CELL_SIZE-newWidth) / 2 , drawY,  newWidth , CELL_SIZE);
    }
}
