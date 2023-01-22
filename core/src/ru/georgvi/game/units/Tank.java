package ru.georgvi.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ru.georgvi.game.MyGdxGame;
import ru.georgvi.game.utils.Utils;
import ru.georgvi.game.Weapoon;

public abstract class Tank {
    protected MyGdxGame game;
    protected Weapoon weapoon;
    protected Vector2 position;
    protected Texture texture;


    protected float speed;
    protected float angle;
    protected float angleTurret;
    protected float fireTimer;

    protected int width;
    protected int height;

    protected int hp;
    protected int hpMax;


    public Tank(float x, float y, Texture texture, float speed) {
        this.position = new Vector2(100.0f, 100.0f);
        this.texture = texture;
        this.speed = speed;
    }

    public Tank(MyGdxGame game) {
        this.game = game;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - width / 2, position.y - height / 2, width / 2, height / 2, width, height, 1, 1, angle,
                0, 0, width, height, false, false);
        batch.draw(weapoon.getTexture(), position.x - width / 2, position.y - height / 2, width / 2, height / 2, width, height, 1, 1, angleTurret,
                0, 0, width, height, false, false);
    }

    public abstract void update(float dt);

    public void rotateTurretToPoint(float pointX, float pointY, float dt) {
        float angleTo = Utils.getAngle(position.x, position.y, pointX, pointY);
        angleTurret = Utils.makeRotation(angleTurret, angleTo, 270.0f, dt);
        angleTurret = Utils.angleToFromNegPiToPosPi(angleTurret);
    }

    //    Движение танка по стрелкам


    public void fire(float dt) {
        fireTimer += dt;
        if (fireTimer >= weapoon.getFirePeriod()) {
            fireTimer = 0.0f;
            float angleRad = (float) Math.toRadians(angleTurret);
            game.getBulletEmitter().acvate(position.x, position.y, 320.0f * (float) Math.cos(angleRad),
                    320.0f * (float) Math.sin(angleRad), weapoon.getDamage());

        }

    }
}
