package com.CEliconValley.views.subGames;

import com.CEliconValley.models.items.Backpack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class Rain {

    private final Texture rainTexture;
    private final Array<RainDrop> drops = new Array<>();
    private long lastSpawnTime = 0;
    private final float spawnInterval = 0.002f;

    private final TextureRegion[][] rainFrames;

    public Rain() {

        this.rainTexture = new Texture("game/general/tiles/rain.png");

        this.rainFrames = TextureRegion.split(rainTexture, 8, 16);
    }

    public void update(OrthographicCamera camera,float screenWidth, float screenHeight) {
        if (TimeUtils.nanoTime() - lastSpawnTime > spawnInterval * 1_000_000_000) {
            spawnDrop(camera,screenWidth, screenHeight);
            lastSpawnTime = TimeUtils.nanoTime();
        }

        for (int i = drops.size - 1; i >= 0; i--) {
            RainDrop drop = drops.get(i);
            drop.update(Gdx.graphics.getDeltaTime());
            if (drop.isFinished()) {
                drops.removeIndex(i);
            }
        }
    }

    public void render(SpriteBatch batch,OrthographicCamera camera) {
        update(camera,camera.position.x+camera.viewportWidth/2,camera.position.y+camera.viewportHeight/2);
        for (RainDrop drop : drops) {
            drop.render(batch);
        }
    }

    private void spawnDrop(OrthographicCamera camera,float screenWidth, float screenHeight) {
        int row = MathUtils.random(1);

        TextureRegion dropFrame = rainFrames[row][0];
        TextureRegion[] splashFrames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            splashFrames[i] = rainFrames[row][i + 1];
        }

        Animation<TextureRegion> splashAnim = new Animation<>(0.1f, splashFrames);
        splashAnim.setPlayMode(Animation.PlayMode.NORMAL);

        float x = MathUtils.random(camera.position.x-camera.viewportWidth/2,camera.position.x+camera.viewportWidth/2+160);
        float y =camera.position.y+camera.viewportHeight/2+1;

        drops.add(new RainDrop(x, y, dropFrame, splashAnim));
    }

    private class RainDrop {
        float x, y;
        float speedY = 1500f;
        float speedX = -750f;
        boolean hasHit = false;
        float stateTime = 0f;
        private float ground;

        final TextureRegion dropFrame;
        final Animation<TextureRegion> splashAnimation;

        RainDrop(float x, float y, TextureRegion dropFrame, Animation<TextureRegion> splashAnimation) {
            this.x = x;
            this.y = y;
            this.dropFrame = dropFrame;
            this.splashAnimation = splashAnimation;
            ground = MathUtils.random(0,75*CELL_SIZE);
        }

        void update(float delta) {
            if (!hasHit) {
                y -= speedY * delta;
                x += speedX * delta;
                if (y <= ground) {
                    y = ground;
                    hasHit = true;
                    stateTime = 0f;
                }
            } else {
                stateTime += delta;
            }
        }

        void render(SpriteBatch batch) {
            if (!hasHit) {
                batch.draw(dropFrame, x, y,dropFrame.getRegionWidth()*3, dropFrame.getRegionHeight()*3);
            } else {
                TextureRegion frame = splashAnimation.getKeyFrame(stateTime);
                batch.draw(frame, x, y,frame.getRegionWidth()*3, frame.getRegionHeight()*3);
            }
        }

        boolean isFinished() {
            return hasHit && splashAnimation.isAnimationFinished(stateTime);
        }
    }
}
