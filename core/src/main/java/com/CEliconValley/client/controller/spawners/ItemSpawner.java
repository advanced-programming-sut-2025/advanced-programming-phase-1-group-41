package com.CEliconValley.client.controller.spawners;

import com.CEliconValley.client.view.screen.VillageScreen;
import com.CEliconValley.client.view.screen.menu.ShippingBinBar;
import com.CEliconValley.common.CellData;
import com.CEliconValley.common.FarmData;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Result;
import com.CEliconValley.models.buildings.ShippingBin;
import com.CEliconValley.models.buildings.Well;
import com.CEliconValley.models.foragings.ForagingCrop;
import com.CEliconValley.models.foragings.ForagingCropType;
import com.CEliconValley.models.foragings.Nature.Rock;
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
        if(item instanceof Rock){
            return;
        }
        TextureRegion itemTexture = ItemManager.getTexture(item);
        float drawX = cellData.getX() * CELL_SIZE;
        float drawY = cellData.getY() * CELL_SIZE;
        float newWidth = itemTexture.getTexture().getWidth() * ((float)(CELL_SIZE) / itemTexture.getTexture().getHeight());
        batch.draw(itemTexture, drawX + (CELL_SIZE-newWidth) / 2 , drawY,  newWidth , CELL_SIZE);
    }

    public void renderItems(SpriteBatch batch, CellData cellData, VillageScreen villageScreen){
        Item item = Finder.parseItem(cellData.getObjectName());
        if (item == null) {
            return;
        }
        if(item instanceof Rock){
            return;
        }
        TextureRegion itemTexture = ItemManager.getTexture(item);
        float drawX = cellData.getX() * CELL_SIZE;
        float drawY = cellData.getY() * CELL_SIZE;
        float newWidth = itemTexture.getTexture().getWidth() * ((float)(CELL_SIZE) / itemTexture.getTexture().getHeight());
        batch.draw(itemTexture, drawX + (CELL_SIZE-newWidth) / 2 , drawY,  newWidth , CELL_SIZE);
    }
}
