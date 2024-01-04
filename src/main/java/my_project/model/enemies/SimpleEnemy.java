package my_project.model.enemies;

import KAGO_framework.control.ViewController;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.SpawnController;
import my_project.model.Player;
import my_project.model.XpOrb;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;
import my_project.model.projectiles.Projectile;

import java.awt.*;

/**
 * Describes the behavior of a SimpleEnemy, that just homes at the player
 */
public class SimpleEnemy extends Enemy{

    /**
     * Sets all needed values on instantiation and for future use
     *
     * @param x X coordinate of the enemy
     * @param y X coordinate of the enemy
     * @param speed Speed of the enemy movement
     * @param player Player to home towards
     * @param spawnController Spawn controller in use
     */
    public SimpleEnemy(double x, double y, double speed, Player player, SpawnController spawnController){
        super(x, y, speed, player, spawnController);
        radius = 10;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(new Color(255, 255, 255));
        drawTool.drawFilledCircle(x,y,radius);
    }

    @Override
    public void update(double dt) {
        double radiant = Math.atan2(player.getY() - this.y,player.getX() - this.x);
        double degree = Math.toDegrees(radiant);
        moveInDirection(degree,speed,dt);
    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        EnemyNode node = new EnemyNode(x,y,radius);
        if(collidesWithNode(projectile,node))
            destroyed = true;
        return false;
    }

    @Override
    public boolean checkCollision(Player player) {
        return Util.circleToCircleCollision(x, y, radius, player.getX(), player.getY(), 8, 4);
    }

    @Override
    public Effect onDestroyed() {
        spawnXpOrbs(x,y,4);
        return new DustParticleEffect(x,y, new Color(187, 18, 18));
    }
}
