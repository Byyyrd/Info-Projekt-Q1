package my_project.model.projectiles;

import KAGO_framework.view.DrawTool;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;

import java.awt.*;

/**
 * Describes the behavior of a normal Bullet
 */
public class Bullet extends Projectile {

    /**
     * Sets all needed values on instantiation
     *
     * @param x X coordinate of the projectile
     * @param y Y coordinate of the projectile
     * @param degrees The angle at which the projectile is rotated in radians
     * @param speed Speed of the projectile movement
     */
    public Bullet(double x, double y, double degrees, double speed){
        super(x,y,degrees,speed);
        imageOffset = 8;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(checkBounds()){
            destroyed = true;
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        //drawTool.drawImage(getMyImage(),x-imageOffset,y-imageOffset);
        drawTool.setCurrentColor(Color.white);
        drawTool.drawFilledCircle(x,y,8);
    }

    @Override
    public Effect onDestroyed() {
        return new DustParticleEffect(x+imageOffset,y+imageOffset,15,30,10,Color.white);
    }
}
