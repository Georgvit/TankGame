package ru.georgvi.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.georgvi.game.units.BotTank;
import ru.georgvi.game.units.PlayerTank;
import ru.georgvi.game.units.Tank;


public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Map map;
    private PlayerTank player;
    private BulletEmitter bulletEmitter;
    private BotEmitter botEmitter;
    private float gameTimer;

    public static final boolean FRIENDLY_FIRE = false;

    public PlayerTank getPlayer() {
        return player;
    }

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    @Override
    public void create() {
        TextureAtlas textureAtlas = new TextureAtlas("game.pack");
        batch = new SpriteBatch();
        map = new Map(textureAtlas);
        player = new PlayerTank(this, textureAtlas);
        bulletEmitter = new BulletEmitter(textureAtlas);
        botEmitter = new BotEmitter(this, textureAtlas);
        botEmitter.acvate(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, Gdx.graphics.getHeight()));

    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        updete(dt);
        ScreenUtils.clear(0, 0.4f, 0, 1);
        batch.begin();
        map.render(batch);
        player.render(batch);
        botEmitter.render(batch);
        bulletEmitter.render(batch);
        batch.end();
    }

    public void updete(float dt) {
        gameTimer += dt;
        if (gameTimer > 5.0f) {
            gameTimer = 0.0f;
            botEmitter.acvate(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, Gdx.graphics.getHeight()));
        }
        player.update(dt);
        botEmitter.update(dt);
        bulletEmitter.update(dt);
        checkCollisions();
    }

    public void checkCollisions() {
        for (int i = 0; i < bulletEmitter.getBullets().length; i++) {
            Bullet bullet = bulletEmitter.getBullets()[i];
            if (bullet.isActive()) {
                for (int j = 0; j < botEmitter.getBots().length; j++) {
                    BotTank bot = botEmitter.getBots()[j];
                    if (bot.isActive()) {
                        if (checkBulletAndTank(bot, bullet) && bot.getCircle().contains(bullet.getPosicion())) {
                            bullet.deactivate();
                            bot.takeDamage(bullet.getDamage());
                            break;
                        }
                    }
                }
                if (checkBulletAndTank(player, bullet) && player.getCircle().contains(bullet.getPosicion())) {
                    bullet.deactivate();
                    player.takeDamage(bullet.getDamage());
                }

                map.checkWallAndBulletCollision(bullet);

            }
        }
    }

    public boolean checkBulletAndTank(Tank tank, Bullet bullet) {
        if (!FRIENDLY_FIRE) {
            return tank.getOwnerType() != bullet.getOwner().getOwnerType();
        } else {
            return tank != bullet.getOwner();
        }
    }

    @Override
    public void dispose() {

        batch.dispose();
    }
}
