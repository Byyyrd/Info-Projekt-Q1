package my_project.model.enemies;

import KAGO_framework.model.GraphicalObject;
import my_project.control.CollisionController;
import my_project.model.*;
import my_project.model.BulletType;

public abstract class Enemy extends GraphicalObject {
    protected CollisionController collisionController;
    protected Player player;

    protected void spawnBullet(double x, double y, double degrees, double speed, BulletType bulletType){
        Projectile projectile = null;
        switch (bulletType){
            case Bullet -> projectile = new Bullet(x,y,degrees,speed);
            case Arrow -> projectile = new Arrow(x,y,degrees,speed);
        }
        collisionController.addProjectile(projectile);
    }

    protected void move(double dt, int speed){
        double degrees = Math.atan2(player.getY()-y,player.getX()-x);
        this.x += Math.cos(degrees) * speed * dt;
        this.y += Math.sin(degrees) * speed * dt;
    }

}