package com.CEliconValley.views.subGames;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.controllers.WeatherController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class Thunder {
    private final Animation<TextureRegion> animation;
    private final Array<Strike> activeStrikes = new Array<>();
    private Texture sheet;
    private float flashTimer = 0f;

    private final float FLASH_DURATION = 0.1f;

    public Thunder() {
        sheet = new Texture(Gdx.files.internal("game/general/tiles/thunder.png"));
        int frameCols = 12;
        int frameWidth = sheet.getWidth() / frameCols;
        int frameHeight = sheet.getHeight();


        TextureRegion[] frames = new TextureRegion[frameCols];
        TextureRegion[][] tmp = TextureRegion.split(sheet, frameWidth, frameHeight);
        for (int i = 0; i < frameCols; i++) {
            frames[i] = tmp[0][i];
        }

        animation = new Animation<>(0.03f, frames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public void strikeAt(int cellX, int cellY) {
        float x = cellX * CELL_SIZE;
        float y = cellY * CELL_SIZE;
        activeStrikes.add(new Strike(x, y));
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        float delta = Gdx.graphics.getDeltaTime();


        for (int i = activeStrikes.size - 1; i >= 0; i--) {
            Strike s = activeStrikes.get(i);
            s.stateTime += delta;
            if (animation.isAnimationFinished(s.stateTime)) {
                activeStrikes.removeIndex(i);
                flashTimer = FLASH_DURATION;
            }
        }


        float thunderWidth = sheet.getWidth() / 12f;
        float thunderHeight = sheet.getHeight();
        thunderWidth = thunderWidth * camera.viewportHeight / thunderHeight;
        thunderHeight = camera.viewportHeight;


        for (Strike s : activeStrikes) {
            TextureRegion frame = animation.getKeyFrame(s.stateTime);
            batch.draw(frame, s.x +CELL_SIZE- (thunderWidth / 2), s.y, thunderWidth, thunderHeight);
        }


        if (flashTimer > 0f) {
            flashTimer -= delta;
            batch.setColor(1f, 1f, 1f, 0.8f);
            batch.draw(getWhitePixel(),
                camera.position.x - camera.viewportWidth / 2f,
                camera.position.y - camera.viewportHeight / 2f,
                camera.viewportWidth, camera.viewportHeight);
            batch.setColor(1f, 1f, 1f, 1f);

        }
    }


    private Texture getWhitePixel() {
        if (whitePixel == null) {
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(1, 1, 1, 1);
            pixmap.fill();
            whitePixel = new Texture(pixmap);
            pixmap.dispose();
        }
        return whitePixel;
    }

    private static Texture whitePixel;

    private static class Strike {
        final float x, y;
        float stateTime = 0f;

        Strike(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
