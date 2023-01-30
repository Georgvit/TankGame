package ru.georgvi.game.utils;

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
import ru.georgvi.game.AbstractScreen;
import ru.georgvi.game.GameScreen;
import ru.georgvi.game.ScreenManager;


public class FinalScreen extends AbstractScreen {
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private BitmapFont font24;
    private Stage stage;
    private String viktorichek;
    private GameScreen gameScreen;
    public int temp;

    public FinalScreen(SpriteBatch batch) {

        this.batch = batch;
        this.temp = 1;
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
        final TextButton message = new TextButton(victory(), textButtonStyle);
        final TextButton buttonNewGame = new TextButton("Новая игра", textButtonStyle);
        final TextButton exitButton = new TextButton("Выход", textButtonStyle);
        buttonNewGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.MENU);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        message.setPosition(0, 80);
        buttonNewGame.setPosition(0, 40);
        exitButton.setPosition(0, 0);
        group.addActor(message);
        group.addActor(buttonNewGame);
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

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String victory(){
        switch (temp){
            case 1:
                return viktorichek = "Вы проиграли";

            case 2:
                return viktorichek = "Вы выиграли";
        }

        return null;
    }
}
