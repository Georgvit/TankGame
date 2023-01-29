package ru.georgvi.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import ru.georgvi.game.GameScreen;
import ru.georgvi.game.Weapoon;
import ru.georgvi.game.utils.Direction;
import ru.georgvi.game.utils.KeysControl;
import ru.georgvi.game.utils.TankOwner;
import ru.georgvi.game.utils.Utils;

public class PlayerTank extends Tank {
    int index;

    int score;

    int lives;
    KeysControl keysControl;
    StringBuilder tmpString;

    public PlayerTank(int index, GameScreen game, KeysControl keysControl, TextureAtlas atlas) {
        super(game);
        this.index = index;
        this.gameScreen = game;
        this.ownerType = TankOwner.PLAYER;
        this.keysControl = keysControl;
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
        this.tmpString = new StringBuilder();
    }

    public void checkMovement(float dt) {
        if (Gdx.input.isKeyPressed(keysControl.getLeft())) {
            move(Direction.LEFT, dt);
        } else if (Gdx.input.isKeyPressed(keysControl.getRight())) {
            move(Direction.RIGTH, dt);
        } else if (Gdx.input.isKeyPressed(keysControl.getDown())) {
            move(Direction.DOWN, dt);
        } else if (Gdx.input.isKeyPressed(keysControl.getUp())) {
            move(Direction.UP, dt);
        }
    }

    public void update(float dt) {
        checkMovement(dt);
//        float mx = Gdx.input.getX();
//        float my = Gdx.graphics.getHeight() - Gdx.input.getY();
//        temp.set(Gdx.input.getX(), Gdx.input.getY());
//        ScreenManager.getInstance().getViewport().unproject(temp);
        if (keysControl.getTargeting() == KeysControl.Targeting.MOUSE) {
            rotateTurretToPoint(gameScreen.getMousePosition().x, gameScreen.getMousePosition().y, dt);
            if (Gdx.input.isTouched()) {
                fire();
            }
        } else {
            if (Gdx.input.isKeyPressed(keysControl.getRotateTurretLeft())) {
                angleTurret = Utils.makeRotation(angleTurret, angleTurret + 90.0f, 270.0f, dt);
                angleTurret = Utils.angleToFromNegPiToPosPi(angleTurret);
            }
            if (Gdx.input.isKeyPressed(keysControl.getRotateTurretRight())) {
                angleTurret = Utils.makeRotation(angleTurret, angleTurret - 90.0f, 270.0f, dt);
                angleTurret = Utils.angleToFromNegPiToPosPi(angleTurret);
            }
            if (Gdx.input.isKeyPressed(keysControl.getFire())) {
                fire();
            }
        }
        super.update(dt);
    }

    public void addScore(int amount) {
        score += amount;
    }

    public void renderHUD(SpriteBatch batch, BitmapFont font24) {
        tmpString.setLength(0);
        tmpString.append("Игрок ").append(index);
        tmpString.append("  Счет: ").append(score);
        tmpString.append("\nЖизни: ").append(lives);
        font24.draw(batch, tmpString, 10 + (index - 1) * 200, 53);
    }

    @Override
    public void destroy() {
        lives--;
        hp = hpMax;
    }
}
