package my_project.model.enemies;

import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.EffectController;
import my_project.control.SpawnController;
import my_project.model.Player;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;
import my_project.model.projectiles.Arrow;
import my_project.model.projectiles.Projectile;

import java.awt.*;

/**
 * Describes the behavior of a Summoner
 */
public class Summoner extends Enemy{

    private int healthPoints;
    private double currentCooldown = 1;

    /**
     * Sets all needed values on instantiation and for future use
     *
     * @param x X coordinate of the enemy
     * @param y Y coordinate of the enemy
     * @param speed Speed of the enemy movement
     * @param player Player to home towards
     * @param spawnController Spawn controller in use
     * @param healthPoints The amount of bullets it takes to destroy the enemy
     */
    public Summoner(double x, double y, double speed, Player player, SpawnController spawnController, int healthPoints) {
        super(x, y, speed, player, spawnController);
        this.healthPoints = healthPoints;
        radius = 20;
    }

    @Override
    public void update(double dt) {
        double speedMultiplier = 1;
        if(Math.sqrt(Math.pow(player.getX()-x,2)+Math.pow(player.getY()-y,2)) < 1000)
            speedMultiplier = Math.sqrt(Math.pow(player.getX()-x,2)+Math.pow(player.getY()-y,2))*0.01;
        super.move((dt*speedMultiplier));
        currentCooldown -= dt;
        if (currentCooldown < 0) {
            spawnSimpleEnemies();
            currentCooldown = 1;
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(new Color(0x793a80));
        drawTool.drawCircle(x,y, radius);
        drawTool.setCurrentColor(new Color(0x403353));
        drawTool.drawFilledCircle(x,y,radius);
    }

    /**
     * Spawns 3 SimpleEnemies around the Summoner with effects
     */
    private void spawnSimpleEnemies(){
        double spreadDegree = (Math.PI * 2) / 3;
        for (int i = 0; i < 3; i++) {
            double relativeDegree = i * spreadDegree;
            spawnController.addEffect(new DustParticleEffect(x + Math.cos(relativeDegree) * 30,y + Math.sin(relativeDegree) * 30, new Color(0xe86a73)));
            spawnController.addEnemy(new SimpleEnemy(x + Math.cos(relativeDegree) * 30,y + Math.sin(relativeDegree) * 30,100,player,spawnController));
        }
    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        boolean collides = collidesWithNode(projectile,new EnemyNode(x,y,radius));
        if(collides){
            healthPoints -= 1;
        }
        if(collides && projectile instanceof Arrow){
            Arrow arrow = (Arrow) projectile;
            if(arrow.isStrong())
                healthPoints -= 4;
        }
        if(healthPoints <= 0){
            destroyed = true;
        }
        return collides;
    }

    @Override
    public boolean checkCollision(Player player) {
        return Util.circleToCircleCollision(x,y, radius,player.getX(),player.getY(),8,0);
    }

    @Override
    public Effect onDestroyed() {
        return new DustParticleEffect(x,y,(int)radius*10,radius*2,10, new Color(0x793a80));
    }
}
