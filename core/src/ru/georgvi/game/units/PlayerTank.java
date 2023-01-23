package ru.georgvi.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import ru.georgvi.game.MyGdxGame;
import ru.georgvi.game.Weapoon;
import ru.georgvi.game.utils.TankOwner;

public class PlayerTank extends Tank {

    int lives;

    public PlayerTank(MyGdxGame game, TextureAtlas atlas) {
        super(game);
        this.game = game;
        this.ownerType = TankOwner.PLAYER;
        this.weapoon = new Weapoon(atlas);
        this.texture = atlas.findRegion("playerTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(100.0f, 100.0f);
        this.speed = 102.0f;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.hpMax = 10;
        this.hp = this.hpMax;
        this.circle = new Circle(position.x, position.y, (width + height) / 2);
        this.lives = 5;
    }

    public void checkMovement(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= speed * dt;
            angle = 180.0f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x += speed * dt;
            angle = 0.0f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y -= speed * dt;
            angle = 270.0f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y += speed * dt;
            angle = 90.0f;
        }
    }

    public void update(float dt) {
        checkMovement(dt);
        float mx = Gdx.input.getX();
        float my = Gdx.graphics.getHeight() - Gdx.input.getY();
        rotateTurretToPoint(mx, my, dt);
        if (Gdx.input.isTouched()) {
            fire(dt);
        }
        super.update(dt);
    }

    @Override
    public void destroy() {
        lives--;
        hp = hpMax;
    }
}
