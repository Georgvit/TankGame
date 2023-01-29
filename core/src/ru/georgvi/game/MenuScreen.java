package ru.georgvi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.georgvi.game.utils.GameType;


public class MenuScreen extends AbstractScreen {
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private BitmapFont font24;
    private Stage stage;

    public MenuScreen(SpriteBatch batch) {
        this.batch = batch;
    }


    @Override
    public void show() {
        textureAtlas = new TextureAtlas("game.pack");
        font24 = new BitmapFont(Gdx.files.internal("font24.fnt"));
        stage = new Stage();
        Skin skin = new Skin();
        skin.add("simpleButton", new TextureRegion(textureAtlas.findRegion("SimpleButton")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("simpleButton");
        textButtonStyle.font = font24;

        Group group = new Group();
        final TextButton stsrtButtonOne = new TextButton("Старт 1 игрок", textButtonStyle);
        final TextButton stsrtButtonTwo = new TextButton("Старт 2 игрока", textButtonStyle);
        final TextButton exitButton = new TextButton("Выход", textButtonStyle);
        stsrtButtonOne.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME, GameType.ONE_PLAYER);
            }
        });
        stsrtButtonTwo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME, GameType.TWO_PLAYERS);
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stsrtButtonOne.setPosition(0, 80);
        stsrtButtonTwo.setPosition(0, 40);
        exitButton.setPosition(0, 0);
        group.addActor(stsrtButtonOne);
        group.addActor(stsrtButtonTwo);
        group.addActor(exitButton);
        group.setPosition(580, 350);
        stage.addActor(group);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        updete(delta);
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }


    public void updete(float dt) {
        stage.act(dt);
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
        font24.dispose();
        stage.dispose();
    }


}
