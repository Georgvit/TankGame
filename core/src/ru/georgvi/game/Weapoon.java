package ru.georgvi.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Weapoon {
    private TextureRegion texture;
    private float firePeriod;
    private  int damage;

    public Weapoon(TextureAtlas atlas) {
        this.texture = atlas.findRegion("simpleWeapon");
        this.firePeriod = 0.3f;
        this.damage = 1;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public float getFirePeriod() {
        return firePeriod;
    }

    public int getDamage() {
        return damage;
    }
}
