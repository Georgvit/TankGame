package ru.georgvi.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.georgvi.game.MyGdxGame;
import ru.georgvi.game.Weapoon;
import ru.georgvi.game.utils.Direction;
import ru.georgvi.game.utils.TankOwner;

public class BotTank extends Tank {
    Direction prefereDirection;
    float aiTimer;
    float aiTimerTo;
    float pursuitRadius;
    boolean active;

    public boolean isActive() {
        return active;
    }

    public BotTank(MyGdxGame game, TextureAtlas atlas) {
        super(game);
        this.game = game;
        this.ownerType = TankOwner.AI;
        this.weapoon = new Weapoon(atlas);
        this.texture = atlas.findRegion("botTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(500.0f, 500.0f);
        this.speed = 102.0f;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.pursuitRadius = 200.0f;
        this.hpMax = 5;
        this.hp = this.hpMax;
        this.aiTimerTo = 3.3f;
        this.prefereDirection = Direction.UP;
        this.circle = new Circle(position.x, position.y, (width + height) / 2);
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
        float dist = this.position.dst(game.getPlayer().getPosition());
        if (dist < pursuitRadius) {
            rotateTurretToPoint(game.getPlayer().getPosition().x, game.getPlayer().getPosition().y, dt);
            fire(dt);
        }
        super.update(dt);
    }

    public void activate(float x, float y) {
        hpMax = 5;
        hp = this.hpMax;
        position.set(x, y);
        prefereDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
        angle = prefereDirection.getAngle();
        aiTimer = 0.0f;
        active = true;
    }

    @Override
    public void destroy() {
        active = false;
    }
}
