package my_project.model.enemies;

import KAGO_framework.model.GraphicalObject;
import my_project.control.CollisionController;
import my_project.model.*;
import my_project.model.BulletType;

public abstract class Enemy extends GraphicalObject {
    protected CollisionController collisionController;
    protected Player player;

    protected void spawnBullet(double x, double y, double dirX, double dirY, double speed, BulletType bulletType){
        Projectile projectile = null;
        switch (bulletType){
            case Bullet -> projectile = new Bullet(x,y,dirX,dirY,speed);
            case Arrow -> projectile = new Arrow(x,y,dirX,dirY,speed);
        }
        collisionController.addProjectile(projectile);
    }
}
