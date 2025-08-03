package com.CEliconValley.client.view.screen;

import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.items.Inventory;
import com.CEliconValley.models.items.Item;
import com.CEliconValley.models.items.Slot;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MenuBar {
    private final Texture menuTexture = new Texture("game/Buildings/Screen/Menu_Screen.png");
    private final TextureRegion selectedTile;
    private final int tileWidth;
    private final int tileHeight;

    public MenuBar() {
        tileWidth = menuTexture.getWidth() / 3;
        tileHeight = menuTexture.getHeight() / 3;
        TextureRegion[][] split = TextureRegion.split(menuTexture, tileWidth, tileHeight);
        selectedTile = split[0][1];

    }

    public void render(Batch batch, OrthographicCamera camera, Inventory inventory) {

        float startingX=camera.position.x+-camera.viewportWidth/2+(camera.viewportWidth-selectedTile.getRegionWidth()*2)/2;
        float startingY=camera.position.y- camera.viewportHeight/2+(camera.viewportHeight-selectedTile.getRegionHeight()*2)/2;

        float firstItemX=(2*tileWidth*49)/856f;
        float firstItemY=(2 * tileHeight * 486) /648f;
        int col = 0, row = 0,startPoint=0;
        float slotSize=tileWidth*56*2/856f;
        batch.draw(selectedTile, startingX,startingY, selectedTile.getRegionWidth()*2, selectedTile.getRegionHeight()*2);
        for (int i = startPoint; i < startPoint+12&&row<3; i++) {
            Slot slot = inventory.getSlots().get(i);
            Item item = slot.getItem();
            TextureRegion texture;
            if (item != null) {
                texture = ItemManager.getTexture(item);
                if (texture != null) {
                    float x = startingX+ firstItemX+col * (slotSize+16);
                    float y = startingY +firstItemY-row*(slotSize+32) ;
                    batch.draw(texture, x, y, slotSize,slotSize);
                }
            }
            col++;
            if(col==12){
                col=0;
                row++;
                if(row==2){startingY+=slotSize+8;}
            }

        }

    }

    public void dispose() {
        menuTexture.dispose();
    }
}
