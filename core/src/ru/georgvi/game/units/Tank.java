package ru.georgvi.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import ru.georgvi.game.MyGdxGame;
import ru.georgvi.game.utils.Direction;
import ru.georgvi.game.utils.TankOwner;
import ru.georgvi.game.utils.Utils;
import ru.georgvi.game.Weapoon;

public abstract class Tank {
    protected MyGdxGame game;
    protected TankOwner ownerType;
    protected Weapoon weapoon;
    protected Vector2 position;
    protected Vector2 temp;
    protected TextureRegion texture;
    protected TextureRegion textureHp;
    protected Circle circle;


    protected float speed;
    protected float angle;
    protected float angleTurret;
    protected float fireTimer;

    protected int width;
    protected int height;

    protected int hp;
    protected int hpMax;


    public Tank(float x, float y, TextureRegion texture, float speed) {
        this.position = new Vector2(100.0f, 100.0f);
        this.texture = texture;
        this.speed = speed;
    }

    public Tank(MyGdxGame game) {

        this.game = game;
        this.temp = new Vector2(0.0f, 0.0f);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - width / 2, position.y - height / 2,
                width / 2, height / 2, width, height, 1, 1, angle);
        batch.draw(weapoon.getTexture(), position.x - width / 2, position.y - height / 2,
                width / 2, height / 2, width, height, 1, 1, angleTurret);

        if (hp < hpMax) {
            batch.setColor(1, 0, 0, 0.5f);
            batch.draw(textureHp, position.x - width / 2 - 2, position.y + height / 2 - 10, 44, 12);
            batch.setColor(0, 1, 0, 0.7f);
            batch.draw(textureHp, position.x - width / 2, position.y + height / 2 - 8, ((float) hp / hpMax) * 40, 8);
            batch.setColor(1, 1, 1, 1);
        }
    }

    public void update(float dt) {
        fireTimer += dt;
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x > Gdx.graphics.getWidth()) {
            position.x = Gdx.graphics.getWidth();
        }
        if (position.y < 0) {
            position.y = 0;
        }
        if (position.y > Gdx.graphics.getHeight()) {
            position.y = Gdx.graphics.getHeight();
        }
        circle.setPosition(position);
    }

    public void move(Direction direction, float dt) {
        temp.set(position);
        temp.add(speed * direction.getVx() * dt, speed * direction.getVy() * dt);
        float dist = this.position.dst(game.getPlayer().getPosition());

        if (game.getMap().isAreaClear(temp.x, temp.y, width / 2)) {
            angle = direction.getAngle();
            position.set(temp);
        }
    }

    public void rotateTurretToPoint(float pointX, float pointY, float dt) {
        float angleTo = Utils.getAngle(position.x, position.y, pointX, pointY);
        angleTurret = Utils.makeRotation(angleTurret, angleTo, 270.0f, dt);
        angleTurret = Utils.angleToFromNegPiToPosPi(angleTurret);
    }

    //    Движение танка по стрелкам


    public void fire() {
        if (fireTimer >= weapoon.getFirePeriod()) {
            fireTimer = 0.0f;
            float angleRad = (float) Math.toRadians(angleTurret);
            float projectSpeed = 320.0f;
            game.getBulletEmitter().acvate(this, position.x, position.y, weapoon.getProjectileSpeed() * (float) Math.cos(angleRad),
                    weapoon.getProjectileSpeed() * (float) Math.sin(angleRad), weapoon.getDamage(), weapoon.getProjectileLifetime());

        }

    }

    public Circle getCircle() {
        return circle;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            destroy();
        }
    }

    public TankOwner getOwnerType() {
        return ownerType;
    }

    public abstract void destroy();
}
