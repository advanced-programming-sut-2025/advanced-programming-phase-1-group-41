package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.CEliconValley.models.locations.FarmScreen.CELL_SIZE;

public class InventoryRenderer {
    private final Inventory inventory;

    private final int slotSize = CELL_SIZE*2/3;
    private final int startX = 50;
    private final int startY = 400;

    public InventoryRenderer(Inventory inventory) {
        this.inventory = inventory;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        float bottomY = camera.position.y - camera.viewportHeight / 2;
        float leftX = camera.position.x - 6*slotSize;

        int col = 0, row = 0;
        for (int i = 0; i < inventory.getSlots().size(); i++) {
            Slot slot = inventory.getSlots().get(i);
            Item item = slot.getItem();

            if (item != null) {
                TextureRegion texture = ItemManager.getTexture(item);
                if (texture != null) {
                    float x = leftX + 50 + col * (slotSize );
                    float y = bottomY + 10 + row * (slotSize );
                    batch.draw(texture, x, y, slotSize, slotSize);
                }
            }

            col++;
            if (col >= 6) {
                col = 0;
                row++;
            }
        }
    }

}
