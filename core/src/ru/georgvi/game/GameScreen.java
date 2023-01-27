package ru.georgvi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.georgvi.game.units.BotTank;
import ru.georgvi.game.units.PlayerTank;
import ru.georgvi.game.units.Tank;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private BitmapFont font24;
    private Map map;
    private PlayerTank player;
    private BulletEmitter bulletEmitter;
    private BotEmitter botEmitter;
    private float gameTimer;
    private Stage stage;
    private boolean paused;
    public static final boolean FRIENDLY_FIRE = false;

    public PlayerTank getPlayer() {
        return player;
    }

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    public Map getMap() {
        return map;
    }

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }


    @Override
    public void show() {
        textureAtlas = new TextureAtlas("game.pack");
        font24 = new BitmapFont(Gdx.files.internal("font24.fnt"));
        map = new Map(textureAtlas);
        player = new PlayerTank(this, textureAtlas);
        bulletEmitter = new BulletEmitter(textureAtlas);
        botEmitter = new BotEmitter(this, textureAtlas);
//        float coordX, coordY;
//        do {
//            coordX = MathUtils.random(0, Gdx.graphics.getWidth());
//            coordY = MathUtils.random(0, Gdx.graphics.getHeight());
//        } while (!map.isAreaClear(coordX, coordY, 20));
//        botEmitter.acvate(coordX, coordY);
        gameTimer = 100.0f;
        stage = new Stage();
        Skin skin = new Skin();
        skin.add("simpleButton", new TextureRegion(textureAtlas.findRegion("SimpleButton")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("simpleButton");
        textButtonStyle.font = font24;

        Group group = new Group();
        final TextButton pauseButton = new TextButton("Пауза", textButtonStyle);
        final TextButton exitButton = new TextButton("Выход", textButtonStyle);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = !paused;
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        pauseButton.setPosition(0, 0);
        exitButton.setPosition(200, 0);
        group.addActor(pauseButton);
        group.addActor(exitButton);
        group.setPosition(900, 15);
        stage.addActor(group);
        Gdx.input.setInputProcessor(stage);
//        Gdx.input.setCursorCatched(true);

    }

    @Override
    public void render(float delta) {
        updete(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        ScreenManager.getInstance().getCamera().position.set(player.getPosition().x , player.getPosition().y,0);
//        ScreenManager.getInstance().getCamera().update();
        batch.setProjectionMatrix(ScreenManager.getInstance().getCamera().combined);
        batch.begin();
        map.render(batch);
        player.render(batch);
        botEmitter.render(batch);
        bulletEmitter.render(batch);
        player.renderHUD(batch, font24);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        ScreenManager.getInstance().resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void updete(float dt) {
        if (!paused) {
            gameTimer += dt;
            if (gameTimer > 5.0f) {
                gameTimer = 0.0f;

                float coordX, coordY;

                do {
                    coordX = MathUtils.random(0, Gdx.graphics.getWidth());
                    coordY = MathUtils.random(0, Gdx.graphics.getHeight());
                } while (!map.isAreaClear(coordX, coordY, 20));
                botEmitter.acvate(coordX, coordY);
            }
            player.update(dt);
            botEmitter.update(dt);
            bulletEmitter.update(dt);
            checkCollisions();
        }
        stage.act(dt);
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
}
