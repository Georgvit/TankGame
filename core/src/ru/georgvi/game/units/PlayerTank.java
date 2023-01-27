package ru.georgvi.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import ru.georgvi.game.GameScreen;
import ru.georgvi.game.ScreenManager;
import ru.georgvi.game.Weapoon;
import ru.georgvi.game.utils.Direction;
import ru.georgvi.game.utils.TankOwner;

public class PlayerTank extends Tank {

    int score;

    int lives;

    public PlayerTank(GameScreen game, TextureAtlas atlas) {
        super(game);
        this.gameScreen = game;
        this.ownerType = TankOwner.PLAYER;
        this.weapoon = new Weapoon(atlas);
        this.texture = atlas.findRegion("playerTankBase");
        this.textureHp = atlas.findRegion("bar");
        this.position = new Vector2(100.0f, 200.0f);
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
            move(Direction.LEFT, dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            move(Direction.RIGTH, dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            move(Direction.DOWN, dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            move(Direction.UP, dt);
        }
    }

    public void update(float dt) {
        checkMovement(dt);
//        float mx = Gdx.input.getX();
//        float my = Gdx.graphics.getHeight() - Gdx.input.getY();
        temp.set(Gdx.input.getX(), Gdx.input.getY());
        ScreenManager.getInstance().getViewport().unproject(temp);
        rotateTurretToPoint(temp.x, temp.y, dt);
        if (Gdx.input.isTouched()) {
            fire();
        }
        super.update(dt);
    }

    public void addScore(int amount) {
        score += amount;
    }

    public void renderHUD(SpriteBatch batch, BitmapFont font24) {
        font24.draw(batch, "Счет: " + score + "\nЖизни: " + lives, 50, 53);
    }

    @Override
    public void destroy() {
        lives--;
        hp = hpMax;
    }
}
