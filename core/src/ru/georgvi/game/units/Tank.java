package ru.georgvi.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import ru.georgvi.game.GameScreen;
import ru.georgvi.game.utils.Direction;
import ru.georgvi.game.utils.TankOwner;
import ru.georgvi.game.utils.Utils;
import ru.georgvi.game.Weapoon;

import java.sql.Time;

public abstract class Tank {
    protected GameScreen gameScreen;
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

    public Tank(GameScreen gameScreen) {

        this.gameScreen = gameScreen;
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
//        float dist = this.position.dst(gameScreen.getPlayers().getPosition());

        if (gameScreen.getMap().isAreaClear(temp.x, temp.y, width / 2)) {
            angle = direction.getAngle();
            position.set(temp);
        }
    }

    public void rotateTurretToPoint(float pointX, float pointY, float dt) {
        float angleTo = Utils.getAngle(position.x, position.y, pointX, pointY);
        angleTurret = Utils.makeRotation(angleTurret, angleTo, 270.0f, dt);
        angleTurret = Utils.angleToFromNegPiToPosPi(angleTurret);
    }

    //    ???????????????? ?????????? ???? ????????????????


    public void fire() {
        if (fireTimer >= weapoon.getFirePeriod()) {
            long id = gameScreen.getSound().play(0.5f);
            gameScreen.getSound().setVolume(id, 0.08f);

            fireTimer = 0.0f;
            float angleRad = (float) Math.toRadians(angleTurret);
            float projectSpeed = 320.0f;
            gameScreen.getBulletEmitter().acvate(this, position.x, position.y, weapoon.getProjectileSpeed() * (float) Math.cos(angleRad),
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
