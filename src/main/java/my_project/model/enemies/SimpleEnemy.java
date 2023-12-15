package my_project.model.enemies;

import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.EffectController;
import my_project.control.SpawnController;
import my_project.model.Player;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;
import my_project.model.projectiles.Projectile;

import java.awt.*;

public class SimpleEnemy extends Enemy{
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
        return new DustParticleEffect(x,y);
    }
}
