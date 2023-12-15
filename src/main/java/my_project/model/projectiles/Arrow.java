package my_project.model.projectiles;

import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Describes the behavior of an Arrow, the standard player weapon
 */
public class Arrow extends Projectile {
    double speedDecay = 50;

    /**
     * Sets all needed values on instantiation
     *
     * @param x X coordinate of the enemy
     * @param y Y coordinate of the enemy
     * @param degrees The angle at which the projectile is rotated in radians
     * @param speed Speed of the enemy movement
     */
    public Arrow(double x, double y, double degrees, double speed){
        super(x,y,degrees,speed);
        images = Util.getAllImagesFromFolder("arrow");
        isHarmful = false;
        imageOffset = 8;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(checkBounds()) speed = 0;
        speed = Util.lerp(speed,0,dt*4);
        if(speed < speedDecay){
            destroyed = true;
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        Graphics2D g2d = drawTool.getGraphics2D();
        AffineTransform old = g2d.getTransform();

        g2d.rotate(degrees, x, y);
        if(speed > speedDecay * 20){
            drawTool.drawImage(images[1],x-imageOffset,y-imageOffset);
        } else {
            drawTool.drawImage(images[0],x-imageOffset,y-imageOffset);
        }

        g2d.setTransform(old);
    }

    @Override
    public Effect onDestroyed() {
        if(isStrong())
            return new DustParticleEffect(x+imageOffset,y+imageOffset,15,30,10,Color.red);
        return new DustParticleEffect(x+imageOffset,y+imageOffset);
    }

    /**
     * @return Whether the arrow is fast enough to make more damage
     */
    public boolean isStrong(){
        return speed > speedDecay * 20;
    }
}
