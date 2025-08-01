package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;


public class InventoryRenderer {
    private final Inventory inventory;

    private final int slotSize = CELL_SIZE;
    private final int itemSize =slotSize*2/3;
    private int startPoint=0;

    public InventoryRenderer(Inventory inventory) {
        this.inventory = inventory;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        float bottomY = camera.position.y - camera.viewportHeight / 2;
        float leftX = camera.position.x - 4*slotSize;

        int col = 0, row = 0;
        for (int i = startPoint; i < startPoint+8; i++) {
            Slot slot = inventory.getSlots().get(i);
            Item item = slot.getItem();
            float x = leftX + 50 + col * (slotSize );
            float y = bottomY + 10 ;
            TextureRegion texture;
            if(i==startPoint){
            texture = ItemManager.getTexture(0);
            }
            else if(i==startPoint+7){
                texture = ItemManager.getTexture(2);
            }
            else{
                texture=ItemManager.getTexture(1);
            }
            batch.setColor(new Color(1f, 1f, 1f, 0.035f));
            batch.draw(texture, x, y, slotSize, slotSize);
            batch.setColor(new Color(1f, 1f, 1f, 1f));
            if (item != null) {
                 texture = ItemManager.getTexture(item);
                if (texture != null) {
                    x = leftX + 50 + col * (slotSize);
                    y = bottomY + 10 ;
                    batch.draw(texture, x+slotSize/6f, y+slotSize/6f, itemSize,itemSize);
                }
            }

            col++;

        }
    }
    public void shiftLeft(){
        if (inventory.getBackpack().getSize()>startPoint+8){
        startPoint ++;

        }
    }
    public void shiftRight(){
        if(startPoint>0) {
            startPoint--;
        }
    }

}
