package my_project.model.enemies;

import KAGO_framework.model.GraphicalObject;
import my_project.Config;
import my_project.Util;
import my_project.control.SpawnController;
import my_project.model.*;
import my_project.model.effects.Effect;
import my_project.model.projectiles.ProjectileType;
import my_project.model.projectiles.*;

import java.awt.image.BufferedImage;

/**
 * The Enemy class is a template class to make creating, updating and deleting enemies easier
 */
public abstract class Enemy extends GraphicalObject {
    protected SpawnController spawnController;
    protected Player player;
    protected double speed;
    protected BufferedImage[] images;
    protected boolean destroyed = false;

    public Enemy(double x, double y, double speed, Player player, SpawnController spawnController){
        this.x = x;
        this.y = y;
        this.player = player;
        this.speed = speed;
        this.spawnController = spawnController;
    }

    /**
     * spawns a Projectile. Depending on projectileType is either a Bullet,Arrow,Bounce,Bulltet or ChargeBullet
     * @param x x spawn coordinate
     * @param y y spawn coordinate
     * @param degrees angle projectile is shot at
     * @param speed speed of projectile
     * @param projectileType type of projectile, enum
     */

    protected void spawnBullet(double x, double y, double degrees, double speed, ProjectileType projectileType){
        Projectile projectile = null;
        switch (projectileType){
            case Bullet -> projectile = new Bullet(x,y,degrees,speed);
            case Arrow -> projectile = new Arrow(x,y,degrees,speed);
            case BounceBullet -> projectile = new BounceBullet(x,y,degrees,speed,player);
            case ChargeBullet -> projectile = new ChargeBullet(x,y,degrees,speed,player);
        }
        spawnController.addProjectile(projectile);
    }

    /**
     * sets the Enemy towards the direction of the Player by the speed of the Enemy and time from last frame
     * @param dt time from last frame
     */
    protected void move(double dt){
        double degrees = Math.atan2(player.getY()-y,player.getX()-x);
        this.x += Math.cos(degrees) * speed * dt;
        this.y += Math.sin(degrees) * speed * dt;
    }

    /**
     * Returns whether an enemy node is colliding with a Projectile.
     * At high speeds to proof collision projectile position is back tracked.
     * Because calculations are made only after a frame is rendered a projectile might skip over an enemy.
     *
     * @param projectile projectile to check collision with
     * @param node node to check collision with
     * @return whether projectile and node are colliding
     */
    protected boolean collidesWithNode(Projectile projectile, EnemyNode node){
        if (projectile.getSpeed() > 2000) {
            for (int i = (int)(projectile.getSpeed() * 0.01); i >= 0; i--) {
                double antiDegree = Math.atan2(-Math.sin(projectile.getDegrees()),-Math.cos(projectile.getDegrees()));
                double prevPointX = projectile.getX() + (Math.cos(antiDegree) * i);
                double prevPointY = projectile.getY() + (Math.sin(antiDegree) * i);
                if(Util.circleToCircleCollision(node.getX(),node.getY(),node.getRadius(),prevPointX,prevPointY,projectile.getImageOffset(),0))
                    return true;
            }
        } else {
            return Util.circleToCircleCollision(node.getX(),node.getY(),node.getRadius(),projectile.getX(),projectile.getY(),projectile.getImageOffset(),0);
        }

        return false;
    }

    /**
     * checks whether Enemy is on fully on screen or not
     *
     * @return boolean whether the before mentioned statement is true
     */
    protected boolean checkBounds(){
        if(x < Config.leftBound) return false;
        if(x > Config.rightBound) return false;
        if(y < Config.upBound) return false;
        if(y > Config.downBound) return false;
        return true;
    }

    /**
     * Is called when the enemy is destroyed
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

    /**
     * Abstract method to implement,
     * supposed to check whether collision is present with projectile
     *
     * @param projectile projectile to check if colliding
     * @return Return whether the hitting projectile should be destroyed or not
     */

    public abstract boolean checkCollision(Projectile projectile);

    /**
     * abstract method to implement,
     * supposed to check whether collision is present with player
     *
     * @param player projectile to check if colliding
     * @return Return true if colliding else returns false
     */
    public abstract boolean checkCollision(Player player);
}
