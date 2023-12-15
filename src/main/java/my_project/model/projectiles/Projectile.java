package my_project.model.projectiles;

import KAGO_framework.model.GraphicalObject;
import my_project.Config;
import my_project.Util;
import my_project.model.effects.Effect;
import my_project.model.Player;

import java.awt.image.BufferedImage;

/**
 * The Projectile class is a template class to make creating, updating and deleting projectiles easier
 */
public abstract class Projectile extends GraphicalObject {
    protected double degrees;
    protected double speed;
    protected double imageOffset;
    protected boolean isHarmful = true;
    protected boolean destroyed = false;
    protected BufferedImage[] images;

    /**
     * Sets all needed values on instantiation
     *
     * @param x X coordinate of the projectile
     * @param y Y coordinate of the projectile
     * @param degrees The angle at which the projectile is rotated in radians
     * @param speed Speed of the projectile movement
     */
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

    /**
     * Moves the projectile according to the angle and speed of it
     *
     * @param dt Time between the current frame and the last frame
     */
    private void move(double dt) {
        double dirX = Math.cos(degrees);
        double dirY = Math.sin(degrees);
        x += dirX * speed * dt;
        y += dirY * speed * dt;
    }

    /**
     * Is called when the projectile is destroyed
     *
     * @return An effect that should be created on destruction
     */
    public Effect onDestroyed(){
        return null;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean checkCollision(Player player){
        return Util.circleToCircleCollision(x,y,radius,player.getX(),player.getY(),8,4);
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

    public double getImageOffset() {
        return imageOffset;
    }

    /**
     * Checks whether the projectile is outside the boundaries of the arena.
     * If yes, it pushes the projectile back inside
     *
     * @return Whether the projectile was outside the boundaries of the arena or not
     */
    public boolean checkBounds(){
        boolean inWall = false;
        if((x+imageOffset) < Config.leftBound){
            x = Config.leftBound - imageOffset;
            inWall = true;
        }
        if((x+imageOffset) > Config.rightBound){
            x = Config.rightBound - imageOffset * 3;
            inWall = true;
        }
        if((y+imageOffset) < Config.upBound){
            y = Config.upBound - imageOffset;
            inWall = true;
        }
        if((y+imageOffset) > Config.downBound){
            y = Config.downBound - imageOffset * 3;
            inWall = true;
        }
        return inWall;
    }
}
