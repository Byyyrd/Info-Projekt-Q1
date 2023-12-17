package my_project.model.enemies;

import KAGO_framework.control.SoundController;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.Util;
import my_project.control.SpawnController;
import my_project.model.Player;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;
import my_project.model.projectiles.*;

import java.awt.*;

public class RoeckrathBoss extends Enemy {

    private int healthPoints = 0;
    private double projectileTimer = 5;
    private int healthBarSize = 35;
    private double desiredX, desiredY;


    /**
     * Sets all needed values on instantiation and for future use
     *
     * @param x X coordinate of the enemy
     * @param y Y coordinate of the enemy
     * @param speed Speed of the enemy movement
     * @param player Player to home towards
     * @param spawnController Spawn controller in use
     */
    public RoeckrathBoss(double x, double y, double speed, Player player, SpawnController spawnController) {
        super(x, y, speed, player, spawnController);
        radius = 7;
        desiredX = 480;
        desiredY = 400;
        SoundController.playSound("cutscene2");
        SoundController.playSound("bossTheme");
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
        drawTool.drawFilledRectangle(x - 17.5, y - 22, healthBarSize - healthPoints, 5);
        drawTool.setCurrentColor(new Color(0, 0, 0));
        drawTool.drawRectangle(x - 18.5, y - 23, healthBarSize + 2, 7);
        //Teleport
        drawTool.setCurrentColor(new Color(255,0,0,(int)(255-(projectileTimer > 0 ? 25.5*projectileTimer : 0))));
        drawTool.drawFilledCircle(desiredX,desiredY,radius*3);
    }

    public void update(double dt){
        projectileTimer -= dt * (Math.sqrt(healthPoints/2) + 1);
        if(projectileTimer < 0){
            jump();
            spawnBullets();
        }
    }

    /**
     * Spawns a new Bullet(MandelBrot) every 0.3 seconds
     */
    private void spawnBullets() {
        int projectileSpeed = 150;
        double spreadDegree = (Math.PI * 2) / 4;
        for (int i = 0; i < 4; i++) {
            double relativeDegree = i * spreadDegree + Math.PI / 4;
            spawnController.addProjectile(new MandelBrot(x,y, relativeDegree, projectileSpeed,spawnController));
        }
        projectileTimer = 10;
    }

    /**
     * Jumps(Teleports) to a random coordinate on the map
     */
    private void jump(){
        x = desiredX;
        y = desiredY;
        spawnController.addEffect(new DustParticleEffect(x,y,30,30,10,new Color(26, 185, 27)));
        desiredX = Math.random() * 733 + 150;
        desiredY = Math.random() * 313 + 150;
    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        boolean collides = collidesWithNode(projectile, new EnemyNode(x,y,radius));
        if(collides){
            projectileTimer -= 1;
            healthPoints++;
        }
        if(healthPoints == healthBarSize){
            Util.setCamShake(5,10);
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
        return new DustParticleEffect(x,y,50,60,10,new Color(152, 17, 17));
    }

}
