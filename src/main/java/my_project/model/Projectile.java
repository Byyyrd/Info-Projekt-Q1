package my_project.model;

import KAGO_framework.model.GraphicalObject;
import my_project.Config;

import java.awt.image.BufferedImage;

public abstract class Projectile extends GraphicalObject {
    protected double degrees;
    protected double speed;
    protected double imageOffset;
    protected boolean isHarmful = true;
    protected boolean destroyed = false;
    protected BufferedImage[] images;

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

    public Effect onDestroyed(){
        return null;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isHarmful() {
        return isHarmful;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDegrees() {
        return degrees;
    }

    public boolean checkBounds(){
        boolean inWall = false;
        if((x+imageOffset) < Config.leftBound){
            x = Config.leftBound - imageOffset * 2;
            inWall = true;
        }
        if((x+imageOffset) > Config.rightBound){
            x = Config.rightBound - imageOffset * 2;
            inWall = true;
        }
        if((y+imageOffset) < Config.upBound){
            y = Config.upBound - imageOffset * 2;
            inWall = true;
        }
        if((y+imageOffset) > Config.downBound){
            y = Config.downBound - imageOffset * 2;
            inWall = true;
        }
        return inWall;
    }
}
