package ru.georgvi.game;

import com.badlogic.gdx.graphics.Texture;

public class Weapoon {
    private Texture texture;
    private float firePeriod;
    private  int damage;

    public Weapoon() {
        this.texture = new Texture("simple_weapon.png");
        this.firePeriod = 0.3f;
        this.damage = 1;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getFirePeriod() {
        return firePeriod;
    }

    public int getDamage() {
        return damage;
    }
}
