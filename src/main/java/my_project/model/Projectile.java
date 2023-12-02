package my_project.model;

import KAGO_framework.model.GraphicalObject;

public abstract class Projectile extends GraphicalObject {
    protected double dirX;
    protected double dirY;
    protected double speed;
    protected double offsetX;
    protected double offsetY;
    protected boolean isHarmful = true;
    protected boolean destroy = false;

    public Projectile(double x, double y, double dirX, double dirY, double speed) {
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
        this.speed = speed;
    }

    @Override
    public void update(double dt) {
        move(dt);
    }
    private void move(double dt) {
        x += dirX * speed * dt;
        y += dirY * speed * dt;
    }

    public boolean isDestroyed() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public boolean isHarmful() {
        return isHarmful;
    }


    public boolean checkBounds() {
        return true;
    }
}
