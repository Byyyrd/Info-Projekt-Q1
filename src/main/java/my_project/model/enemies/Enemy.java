package my_project.model.enemies;

import KAGO_framework.model.GraphicalObject;
import my_project.Util;
import my_project.control.CollisionController;
import my_project.model.*;
import my_project.model.effects.Effect;
import my_project.model.projectiles.ProjectileType;
import my_project.model.projectiles.*;

import java.awt.image.BufferedImage;

public abstract class Enemy extends GraphicalObject {
    protected CollisionController collisionController;
    protected Player player;
    protected double speed;
    protected BufferedImage[] images;
    protected boolean destroyed = false;

    public Enemy(double x, double y, double speed, Player player, CollisionController collisionController){
        this.x = x;
        this.y = y;
        this.player = player;
        this.speed = speed;
        this.collisionController = collisionController;
    }

    protected void spawnBullet(double x, double y, double degrees, double speed, ProjectileType projectileType){
        Projectile projectile = null;
        switch (projectileType){
            case Bullet -> projectile = new Bullet(x,y,degrees,speed);
            case Arrow -> projectile = new Arrow(x,y,degrees,speed);
            case BounceBullet -> projectile = new BounceBullet(x,y,degrees,speed,player);
            case ChargeBullet -> projectile = new ChargeBullet(x,y,degrees,speed,player);
        }
        collisionController.addProjectile(projectile);
    }

    protected void move(double dt){
        double degrees = Math.atan2(player.getY()-y,player.getX()-x);
        this.x += Math.cos(degrees) * speed * dt;
        this.y += Math.sin(degrees) * speed * dt;
    }

    protected boolean collidesWithNode(Projectile projectile, EnemyNode node){
        boolean collides;

        if (projectile.getSpeed() > 3000) {
            double antiDegree = projectile.getDegrees() + Math.PI;
            double prevPointX = projectile.getX() + Math.cos(antiDegree) * projectile.getSpeed() * 1 / 20 + projectile.getImageOffset();
            double prevPointY = projectile.getY() + Math.sin(antiDegree) * projectile.getSpeed() * 1 / 20;
            collides = Util.isLineAndCircleColliding(prevPointX,prevPointY,projectile.getX(),projectile.getY(),node.getX(),node.getY(),node.getRadius());
            if(collides) return true;
            prevPointX = projectile.getX() + Math.cos(antiDegree) * projectile.getSpeed() * 1 / 20 - projectile.getImageOffset();
            collides = Util.isLineAndCircleColliding(prevPointX,prevPointY,projectile.getX(),projectile.getY(),node.getX(),node.getY(),node.getRadius());
            if(collides) return true;
            prevPointX = projectile.getX() + Math.cos(antiDegree) * projectile.getSpeed() * 1 / 20;
            prevPointY = projectile.getX() + Math.cos(antiDegree) * projectile.getSpeed() * 1 / 20 + projectile.getImageOffset();
            collides = Util.isLineAndCircleColliding(prevPointX,prevPointY,projectile.getX(),projectile.getY(),node.getX(),node.getY(),node.getRadius());
            if(collides) return true;
            prevPointY = projectile.getY() + Math.sin(antiDegree) * projectile.getSpeed() * 1 / 20 + projectile.getImageOffset();
            collides = Util.isLineAndCircleColliding(prevPointX,prevPointY,projectile.getX(),projectile.getY(),node.getX(),node.getY(),node.getRadius());
            //TODO: BESSER MACHEN BITTE, WER HAT MAXIM KOCHEN LASSEN :crying_emoji:
        } else {
            collides = Util.circleToCircleCollision(node.getX(),node.getY(),node.getRadius(),projectile.getX(),projectile.getY(),projectile.getHeight()/2,0);
        }

        return collides;
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
