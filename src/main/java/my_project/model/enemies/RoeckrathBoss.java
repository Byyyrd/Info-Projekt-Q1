package my_project.model.enemies;

import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.Util;
import my_project.control.SpawnController;
import my_project.model.Player;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;
import my_project.model.projectiles.Arrow;
import my_project.model.projectiles.MandelBrot;
import my_project.model.projectiles.Projectile;
import my_project.model.projectiles.ProjectileType;

import java.awt.*;

public class RoeckrathBoss extends Enemy{

    private int healthPoints;
    private int radius;
    private ProjectileType type = ProjectileType.MandelBrot;
    private double jumpTimer = 2;
    private double projectileTimer = 3;
    private int healthBarSize = 35;


    /**
     * Sets all needed values on instantiation and for future use
     *
     * @param x X coordinate of the enemy
     * @param y Y coordinate of the enemy
     * @param speed  Speed of the enemy movement
     * @param player Player to home towards
     * @param spawnController Spawn controller in use
     */
    public RoeckrathBoss(double x, double y, double speed, Player player, SpawnController spawnController) {
        super(x, y, speed, player, spawnController);
        radius = 7;
        healthPoints = 0;
    }

    public void draw(DrawTool drawTool){
        //Roeckrath
        drawTool.setCurrentColor(new Color(226,168,152));
        drawTool.drawFilledCircle(x,y,radius);
        drawTool.setCurrentColor(new Color(226,168,152, 190));
        drawTool.setLineWidth(4);
        drawTool.drawCircle(x,y,radius);
        //HealthBar
        drawTool.setCurrentColor(new Color(152, 17, 17));
        drawTool.drawFilledRectangle(x - 17.5, y - 22, healthBarSize, 5);
    }

    public void update(double dt){
        spawnBullet(dt);
        moveR(dt);
    }

    /**
     * Jumps around the map every 2 seconds
     */
    private void moveR(double dt){
        jumpTimer -= dt;
        if(jumpTimer <= 0){
            jump();
            jumpTimer = 2;
        }
    }

    /**
     * Spawns a new Bullet(MandelBrot) every 0.3 seconds
     */

    private void spawnBullet(double dt) {
        projectileTimer -= dt;
        if (projectileTimer <= 0) {
            int projectileSpeed = 150 + healthPoints * 170;
            double degrees = Math.atan2(player.getY() - y, player.getX() - x);
            super.spawnBullet(x - 16, y - 16, degrees, projectileSpeed, type);
            projectileTimer = 0.3;
        }
    }

    /**
     * Jumps(Telports) on a random coordinate on the map
     */

    private void jump(){
        x = Math.random()* 733 + 150;
        y = Math.random()* 313 + 150;
        spawnController.addEffect(new DustParticleEffect(x,y,30,30,10,new Color(26, 185, 27)));
    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        boolean collides = collidesWithNode(projectile, new EnemyNode(x,y,radius));
        if(collides){
            jump();
            jumpTimer = 2;
            healthPoints++;
            healthBarSize -= 5;
        }
        if(healthPoints == 7){
            destroyed = true;
        }
        return collides;
    }

    @Override
    public boolean checkCollision(Player player) {
        return  Util.circleToCircleCollision(x,y, radius,player.getX(),player.getY(),8,0);
    }

    @Override
    public Effect onDestroyed() {
        return new DustParticleEffect(x,y,50,60,10,new Color(152, 17, 17));
    }

}
