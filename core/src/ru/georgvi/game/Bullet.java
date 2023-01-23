package ru.georgvi.game;

import com.badlogic.gdx.math.Vector2;
import ru.georgvi.game.units.Tank;

public class Bullet {

    private Tank owner;
    private Vector2 posicion;
    private Vector2 velocity;
    private int damage;

    private boolean active;

    public Tank getOwner() {
        return owner;
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public Bullet() {
        this.posicion = new Vector2();
        this.velocity = new Vector2();
        this.active = false;
        this.damage = 0;
    }

    public boolean isActive() {
        return active;
    }

    public int getDamage() {
        return damage;
    }

    public void activate(Tank owner, float x, float y, float vx, float vy, int damage) {
        this.owner = owner;
        this.posicion.set(x, y);
        this.velocity.set(vx, vy);
        this.active = true;
        this.damage = damage;
    }

    public void deactivate() {
        active = false;
    }

    public void update(float dt) {
        posicion.mulAdd(velocity, dt);
        if (posicion.x < 0.0f || posicion.x > 1280.f || posicion.y < 0.0f || posicion.y > 720.f) {
            deactivate();
        }
    }
}
