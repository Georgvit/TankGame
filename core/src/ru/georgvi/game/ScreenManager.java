package ru.georgvi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.georgvi.game.utils.FinalScreen;
import ru.georgvi.game.utils.GameType;


public class ScreenManager {
    public enum ScreenType {
        MENU, GAME, FINALGAME
    }

    private static ScreenManager ourInstance = new ScreenManager();

    public static ScreenManager getInstance() {
        return ourInstance;
    }

    private ScreenManager() {
    }

    private Game game;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private FinalScreen fihalScreen;
    private Viewport viewport;
    private Camera camera;

    public Viewport getViewport() {
        return viewport;
    }

    public Camera getCamera() {
        return camera;
    }

    public static final int WORLD_WIDTH = 1280;
    public static final int WORLD_HEIGHT = 720;

    public void init(Game game, SpriteBatch batch) {
        this.game = game;
        this.camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        this.camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        this.camera.update();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        this.menuScreen = new MenuScreen(batch);
        this.gameScreen = new GameScreen(batch);
        this.fihalScreen = new FinalScreen(batch);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }

    public void setScreen(ScreenType screenType, GameType... args) {
        Gdx.input.setCursorCatched(false);
        Screen currentScreen = game.getScreen();
        switch (screenType) {
            case MENU:
                game.setScreen(menuScreen);
                break;
            case GAME:
                gameScreen.setGameType((GameType) args[0]);
                game.setScreen(gameScreen);
                break;
            case FINALGAME:
                game.setScreen(fihalScreen);
                break;
        }
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }

    public void chekviktori(int variant){
        gameScreen.getMusic().stop();
        gameScreen.getSound().stop();
        fihalScreen.setTemp(variant);
    }
}
