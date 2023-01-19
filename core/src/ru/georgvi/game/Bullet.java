package ru.georgvi.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
    private Texture texture;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float speed;
    private boolean active;

    public Bullet() {
        this.texture = new Texture("projectile.png");
        this.x = 0.0f;
        this.y = 0.0f;
        this.vx = 0.0f;
        this.vy = 0.0f;
        this.speed = 100.0f;
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x - 8, y - 8);
    }

    public void activate(float x, float y, float vx, float vy){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.active = true;
    }

    public void deactivate(){
        active = false;
    }

    public void update(float dt) {
        x += vx * dt;
        y += vy * dt;
        if(x < 0.0f || x > 1280.f || y < 0.0f || y > 720.f){
            deactivate();
        }
    }
}
