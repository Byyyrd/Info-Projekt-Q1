package my_project.model;

import KAGO_framework.model.GraphicalObject;
import my_project.Config;

public abstract class Projectile extends GraphicalObject {
    protected double degrees;
    protected double speed;
    protected double offsetX;
    protected double offsetY;
    protected boolean isHarmful = true;
    protected boolean destroy = false;

    public Projectile(double x, double y, double degrees, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.degrees = degrees;
    }

    @Override
    public void update(double dt) {
        move(dt);
    }
    private void move(double dt) {
        double dirX = Math.cos(degrees);
        double dirY = Math.sin(degrees);
        x += dirX * speed * dt;
        y += dirY * speed * dt;
    }

    public GraphicalObject onDestroy(){
        return null;
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

    public boolean checkBounds(){
        boolean inWall = false;
        if((x+offsetX) < Config.leftBound){
            x = Config.leftBound - offsetX * 2;
            inWall = true;
        }
        if((x+offsetX) > Config.rightBound){
            x = Config.rightBound - offsetX * 2;
            inWall = true;
        }
        if((y+offsetY) < Config.upBound){
            y = Config.upBound - offsetY * 4;
            inWall = true;
        }
        if((y+offsetY) > Config.downBound){
            y = Config.downBound - offsetY * 4;
            inWall = true;
        }
        return inWall;
    }
}
