package com.CEliconValley.views.subGames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.OrthographicCamera;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class Thunder {

    private final Animation<TextureRegion> animation;
    private float stateTime = 0f;

    private final float renderX;
    private final float renderY;

    public boolean isFinished() {
        return animation.isAnimationFinished(stateTime);
    }

    public Thunder(int cellX, int cellY, float cameraHeight) {
        int frameCols = 12;
        Texture thunderTexture = new Texture("game/general/tiles/thunder.png");
        int frameWidth = thunderTexture.getWidth() / frameCols;
        int frameHeight = thunderTexture.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(thunderTexture, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[frameCols];
        for (int i = 0; i < frameCols; i++) {
            frames[i] = tmp[0][i];
        }

        animation = new Animation<>(0.03f, frames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);

        renderX = cellX * CELL_SIZE;
        renderY = (cellY * CELL_SIZE) ;
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (isFinished()) return;

        TextureRegion currentFrame = animation.getKeyFrame(stateTime);
        float prevH = currentFrame.getRegionHeight();
        currentFrame.setRegionHeight((int) camera.viewportHeight);
        currentFrame.setRegionWidth((int) (currentFrame.getRegionWidth() * (currentFrame.getRegionHeight() / prevH)));

        batch.draw(
            currentFrame,
            renderX,
            renderY,
            currentFrame.getRegionWidth(),
            currentFrame.getRegionHeight()
        );
    }

    public static Thunder strikeAt(int cellX, int cellY, OrthographicCamera camera) {
        return new Thunder(cellX, cellY, camera.viewportHeight);
    }
}
