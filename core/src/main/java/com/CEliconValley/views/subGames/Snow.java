package com.CEliconValley.views.subGames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class Snow {

    private final Texture snowTexture;
    private final TextureRegion[] snowTypes;
    private final Array<SnowFlake> flakes = new Array<>();
    private long lastSpawnTime = 0;
    private final float spawnInterval = 0.005f;

    public Snow() {
        this.snowTexture = new Texture("game/general/tiles/snow.png");
        TextureRegion[][] split = TextureRegion.split(snowTexture, 8, 16);
        snowTypes = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            snowTypes[i] = split[i / split[0].length][i % split[0].length];
        }
    }

    public void update(OrthographicCamera camera) {
        if (TimeUtils.nanoTime() - lastSpawnTime > spawnInterval * 1_000_000_000L) {
            spawnFlake(camera);
            lastSpawnTime = TimeUtils.nanoTime();
        }

        for (int i = flakes.size - 1; i >= 0; i--) {
            SnowFlake flake = flakes.get(i);
            flake.update(Gdx.graphics.getDeltaTime());
            if (flake.isFinished()) {
                flakes.removeIndex(i);
            }
        }
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        update(camera);
        for (SnowFlake flake : flakes) {
            flake.render(batch);
        }
    }

    private void spawnFlake(OrthographicCamera camera) {
        TextureRegion flakeFrame = snowTypes[MathUtils.random(7)];
        float x = MathUtils.random(camera.position.x - camera.viewportWidth / 2,
            camera.position.x + camera.viewportWidth / 2 + 160);
        float y = camera.position.y + camera.viewportHeight / 2 + 10;


        float speedX;
        switch (MathUtils.random(2)) {
            case 0: speedX = -100f; break;
            case 1: speedX = 0f;    break;
            default: speedX = 100f; break;
        }

        flakes.add(new SnowFlake(x, y, flakeFrame, speedX));
    }

    private static class SnowFlake {
        float x, y;
        final float speedY = 150f;
        final float speedX;
        final TextureRegion frame;
        private final float ground;

        SnowFlake(float x, float y, TextureRegion frame, float speedX) {
            this.x = x;
            this.y = y;
            this.frame = frame;
            this.speedX = speedX;
            this.ground = MathUtils.random(0, 75 * CELL_SIZE);
        }

        void update(float delta) {
            x += speedX * delta;
            y -= speedY * delta;
        }

        void render(SpriteBatch batch) {
            batch.draw(frame, x, y, frame.getRegionWidth() * 3, frame.getRegionHeight() * 3);
        }

        boolean isFinished() {
            return y <= ground;
        }
    }
}
