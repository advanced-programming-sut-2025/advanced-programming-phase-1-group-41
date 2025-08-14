package com.CEliconValley.views;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.models.Hero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class Crow {
    enum State {
        FLYING_TO_HOUSE,
        IDLE,
        PECKING,
        FLYING_AWAY
    }

    private Animation<TextureRegion> flyAnim;
    private Animation<TextureRegion> idleAnim;
    private Animation<TextureRegion> peckAnim;
    Texture texture=new Texture("game/Hero/NPC/Crow.png");
    public int spawnDate=0;
    private float x, y;
    private float speed = 300f;
    private TextureRegion[][]frames;
    private State state;
    private float stateTime = 0f;
    private float idleTime = 1.5f;
    private float peckDuration = 3f;
    private float timer = 0f;
    private boolean despawned = false;

    public Crow(float startY) {
        spawnDate = AppClient.getGameData().getTime().getDay();
        frames=TextureRegion.split(texture, texture.getWidth()/2, texture.getHeight()/3);
        TextureRegion[] flyFrames = frames[2];
        flyAnim = new Animation<>(0.1f, flyFrames);
        flyAnim.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion idleFrames = frames[0][0];
        idleAnim = new Animation<>(0.5f, idleFrames);
        TextureRegion[] peckFrames = frames[1];
        peckAnim = new Animation<>(0.2f,peckFrames);
        this.x =CELL_SIZE*60;
        this.y = startY;
        state = State.FLYING_TO_HOUSE;
    }

    public void update(float delta) {
        if(!despawned) {

            stateTime += delta;

            switch (state) {
                case FLYING_TO_HOUSE:
                    x -= speed * delta;
                    if (x <= 300) {
                        state = State.IDLE;
                        stateTime = 0f;
                        timer = 0f;
                    }
                    break;

                case IDLE:
                    timer += delta;
                    if (timer >= idleTime) {
                        state = State.PECKING;
                        stateTime = 0f;
                        timer = 0f;
                    }
                    break;

                case PECKING:
                    timer += delta;
                    if (timer >= peckDuration) {
                        state = State.FLYING_AWAY;
                        stateTime = 0f;
                    }
                    break;

                case FLYING_AWAY:
                    x -= speed * delta;
                    if (x <= -20) {
                        despawned = true;
                    }
                    break;
            }
        }
        System.out.println(x+" "+y);
    }

    public void render(SpriteBatch batch) {
        if(!despawned) {
            TextureRegion frame;
            switch (state) {
                case FLYING_TO_HOUSE:
                    frame = flyAnim.getKeyFrame(stateTime, true);
                    break;
                case IDLE:
                    frame = idleAnim.getKeyFrame(stateTime, true);
                    break;
                case PECKING:
                    frame = peckAnim.getKeyFrame(stateTime, true);
                    break;
                case FLYING_AWAY:
                    frame = flyAnim.getKeyFrame(stateTime, true);
                    break;
                default:
                    frame = idleAnim.getKeyFrame(stateTime, true);
            }
//            batch.draw(frame, x, y);
            batch.draw(frame, x, y,CELL_SIZE, CELL_SIZE);

        }
    }

    public boolean isOutOfScreen() {
        return x + frames[0][0].getRegionWidth() < 0;
    }
    public boolean isDespawned() {
        return despawned;
    }
}
