package my_project.model.projectiles;

import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.SpawnController;
import my_project.model.Player;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;
import my_project.model.enemies.SimpleEnemy;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class MandelBrot extends Projectile{
    private SpawnController spawnController;

    /**
     * Sets all needed values on instantiation
     *
     * @param x X coordinate of the projectile
     * @param y Y coordinate of the projectile
     * @param degrees The angle at which the projectile is rotated in radians
     * @param speed Speed of the projectile movement
     */
    public MandelBrot(double x, double y, double degrees, double speed, SpawnController spawnController) {
        super(x, y, degrees, speed);
        this.spawnController = spawnController;

        setNewImage("src/main/resources/graphic/mandelBrot.png");
        imageOffset = 16;
        radius = imageOffset;
    }

    public void draw(DrawTool drawTool) {
        Graphics2D g2d = drawTool.getGraphics2D();
        AffineTransform old = g2d.getTransform();

        g2d.rotate(degrees, x + getMyImage().getWidth() * .5, y + getMyImage().getHeight() * .5);
        drawTool.drawImage(getMyImage(),x - imageOffset,y - imageOffset);
        g2d.setTransform(old);
    }

    public void update(double dt) {
        super.update(dt);

        if(checkBounds()){
            destroyed = true;
            double spreadDegree = (Math.PI * 2) / 4;
            for (int i = 0; i < 4; i++) {
                double relativeDegree = i * spreadDegree;
                if(Math.random() > 0.5)
                    spawnController.addProjectile(new Bullet(x + Math.cos(relativeDegree) * 30,y + Math.sin(relativeDegree) * 30,relativeDegree + Math.PI / 4, speed));
                else
                    spawnController.addProjectile(new BounceBullet(x + Math.cos(degrees) * -30,y + Math.sin(degrees) * -30,-degrees, speed * 2,spawnController.getPlayer()));
            }
        }
    }

    @Override
    public Effect onDestroyed() {
        return new DustParticleEffect(x+imageOffset,y+imageOffset,15,30,10,Color.cyan);
    }

}
