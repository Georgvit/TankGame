package ru.georgvi.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.georgvi.game.MyGdxGame;
import ru.georgvi.game.Weapoon;

public class PlayerTank extends Tank{

    public PlayerTank(MyGdxGame game) {
        super(game);
        this.game = game;
        this.weapoon = new Weapoon();
        this.texture = new Texture("player_tank_base.png");
        this.position = new Vector2(100.0f, 100.0f);
        this.speed = 102.0f;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.hpMax = 10;
        this.hp = this.hpMax;
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
    }
}
