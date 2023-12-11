package my_project.model.enemies;

import KAGO_framework.model.GraphicalObject;
import my_project.Util;
import my_project.control.CollisionController;
import my_project.control.SpawnController;
import my_project.model.*;
import my_project.model.effects.Effect;
import my_project.model.projectiles.ProjectileType;
import my_project.model.projectiles.*;

import java.awt.image.BufferedImage;

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

    protected void move(double dt){
        double degrees = Math.atan2(player.getY()-y,player.getX()-x);
        this.x += Math.cos(degrees) * speed * dt;
        this.y += Math.sin(degrees) * speed * dt;
    }

    protected boolean collidesWithNode(Projectile projectile, EnemyNode node){
        if (projectile.getSpeed() > 2000) {
            for (int i = 0; i < Math.floor(projectile.getSpeed() * 0.01); i++) {
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

    public Effect onDestroyed(){
        return null;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public abstract boolean checkCollision(Projectile projectile);

    public abstract boolean checkCollision(Player player);
}
