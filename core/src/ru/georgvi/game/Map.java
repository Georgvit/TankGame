package ru.georgvi.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Map {
    public static final int SIZE_X = 64;
    public static final int SIZE_Y = 36;
    public static final int CELL_SIZE = 20;
    private TextureRegion grassTexture;
    private TextureRegion wallTexture;
    private int obstacles[][];

    public Map(TextureAtlas atlas) {

        this.grassTexture = atlas.findRegion("grass40");
        this.wallTexture = atlas.findRegion("block");
        this.obstacles = new int[SIZE_X][SIZE_Y];
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < 3; j++) {
                this.obstacles[i][SIZE_Y - 1 - j] = 5;
            }
        }

    }

    public void checkWallAndBulletCollision(Bullet bullet) {
        int cx = (int) (bullet.getPosicion().x / CELL_SIZE);
        int cy = (int) (bullet.getPosicion().y / CELL_SIZE);
        if (cx >= 0 && cy >= 0 && cx <= SIZE_X && cy <= SIZE_Y) {
            if (obstacles[cx][cy] > 0) {
                obstacles[cx][cy] -= bullet.getDamage();
                bullet.deactivate();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < 1280 / 40; i++) {
            for (int j = 0; j < 720 / 40; j++) {
                batch.draw(grassTexture, i * 40, j * 40);
            }
        }
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (obstacles[i][j] > 0) {
                    batch.draw(wallTexture, i * CELL_SIZE, j * CELL_SIZE);
                }
            }
        }
    }
}
