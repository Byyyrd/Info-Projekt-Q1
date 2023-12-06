package my_project.model.enemies;

import KAGO_framework.model.GraphicalObject;
import my_project.Util;
import my_project.control.CollisionController;
import my_project.model.*;
import my_project.model.ProjectileType;

import java.awt.image.BufferedImage;

public abstract class Enemy extends GraphicalObject {
    protected CollisionController collisionController;
    protected Player player;
    protected double speed;
    protected BufferedImage[] images;

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
            double prevPointX = projectile.getX() + Math.cos(antiDegree) * projectile.getSpeed() * 1 / 50;
            double prevPointY = projectile.getY() + Math.sin(antiDegree) * projectile.getSpeed() * 1 / 50;
            collides = Util.isLineAndCircleColliding(prevPointX,prevPointY,projectile.getX(),projectile.getY(),node.getX(),node.getY(),node.getRadius());
        } else {
            collides = Util.circleToCircleCollision(node.getX(),node.getY(),node.getRadius(),projectile.getX(),projectile.getY(),projectile.getHeight()/2,0);
        }

        return collides;
    }

    public abstract boolean checkCollision(Projectile projectile);
}
