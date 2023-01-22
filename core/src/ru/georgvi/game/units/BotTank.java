package ru.georgvi.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.georgvi.game.MyGdxGame;
import ru.georgvi.game.Weapoon;
import ru.georgvi.game.utils.Direction;

public class BotTank extends Tank {
    Direction prefereDirection;
    float aiTimer;
    float aiTimerTo;
    boolean active;

    public boolean isActive() {
        return active;
    }

    public BotTank(MyGdxGame game) {
        super(game);
        this.game = game;
        this.weapoon = new Weapoon();
        this.texture = new Texture("bot_tank_base.png");
        this.position = new Vector2(500.0f, 500.0f);
        this.speed = 102.0f;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.hpMax = 5;
        this.hp = this.hpMax;
        this.aiTimerTo = 3.3f;
        this.prefereDirection = Direction.UP;
    }

    public void update(float dt) {
        aiTimer += dt;
        if (aiTimer >= aiTimerTo) {
            aiTimer = 0.0f;
            aiTimerTo = MathUtils.random(2.5f, 4.0f);
            prefereDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
            angle = prefereDirection.getAngle();
        }
        position.add(speed * prefereDirection.getVx() * dt, speed * prefereDirection.getVy() * dt);
    }

    public void activate(float x, float y){
        hpMax = 5;
        hp = this.hpMax;
        position.set(x,y);
        prefereDirection = Direction.values()[MathUtils.random(0, Direction.values().length)];
        angle = prefereDirection.getAngle();
        aiTimer = 0.0f;
        active = true;
    }
}
