package ru.georgvi.game.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ru.georgvi.game.GameScreen;
import ru.georgvi.game.Weapoon;
import ru.georgvi.game.utils.Direction;
import ru.georgvi.game.utils.TankOwner;

public class BotTank extends Tank {
    Direction prefereDirection;
    float aiTimer;
    float aiTimerTo;
    float pursuitRadius;
    boolean active;
    Vector3 lastPosition;

    public boolean isActive() {
        return active;
    }

    public BotTank(GameScreen game, TextureAtlas atlas) {
        super(game);
        this.gameScreen = game;
        this.ownerType = TankOwner.AI;
        this.weapoon = new Weapoon(atlas);
        this.texture = atlas.findRegion("botTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(500.0f, 500.0f);
        this.lastPosition = new Vector3(0.0f, 0.0f, 0.0f);
        this.speed = 102.0f;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.pursuitRadius = 250.0f;
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
            aiTimerTo = MathUtils.random(3.5f, 6.0f);
            prefereDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
            angle = prefereDirection.getAngle();
        }
        move(prefereDirection, dt);

        PlayerTank preferredTarget = null;

        if (gameScreen.getPlayers().size() == 1) {
            preferredTarget = gameScreen.getPlayers().get(0);
        } else {
            float minDist = Float.MAX_VALUE;
            for (int i = 0; i < gameScreen.getPlayers().size(); i++) {
                PlayerTank player = gameScreen.getPlayers().get(i);
                float dist = this.position.dst(player.getPosition());
                if (dist < minDist){
                    minDist = dist;
                    preferredTarget = player;
                }
            }
        }

        float dst = this.position.dst(preferredTarget.getPosition());
        if (dst < pursuitRadius) {
            rotateTurretToPoint(preferredTarget.getPosition().x, preferredTarget.getPosition().y, dt);
            fire();
        }
        if (Math.abs(position.x - lastPosition.x) < 0.5f && Math.abs(position.y - lastPosition.y) < 0.5f) {
            lastPosition.z += dt;
            if (lastPosition.z > 0.25f) {
                aiTimer += 10.0f;
            }
        } else {
            lastPosition.x = position.x;
            lastPosition.y = position.y;
            lastPosition.z = 0.0f;
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
