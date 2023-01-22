package ru.georgvi.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.georgvi.game.units.PlayerTank;


public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Map map;
    private PlayerTank player;
    private BulletEmitter bulletEmitter;
    private BotEmitter botEmitter;
    private float gameTimer;

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        map = new Map();
        player = new PlayerTank(this);
        bulletEmitter = new BulletEmitter();
        botEmitter = new BotEmitter(this);
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
        if (gameTimer > 10.0f) {
            gameTimer = 0.0f;
            botEmitter.acvate(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, Gdx.graphics.getHeight()));
        }
        player.update(dt);
        botEmitter.update(dt);
        bulletEmitter.update(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
