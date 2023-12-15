package my_project.model.projectiles;

import KAGO_framework.view.DrawTool;
import my_project.model.Player;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;

import java.awt.*;

/**
 * Describes the behavior of a ChargeBullet
 */
public class ChargeBullet extends Projectile {
    private boolean isCharging = false;
    private double timer = 1;
    private Player player;

    /**
     * Sets all needed values on instantiation
     *
     * @param x X coordinate of the projectile
     * @param y Y coordinate of the projectile
     * @param degrees The angle at which the projectile is rotated in radians
     * @param speed Speed of the projectile movement
     * @param player Player to home towards
     */
    public ChargeBullet(double x, double y, double degrees, double speed, Player player) {
        super(x, y, degrees, speed);
        imageOffset = 8;
        this.player = player;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        timer -= dt;
        if(timer > 0){
            speed = timer * 500;
        } else {
            if(!isCharging){
                isCharging = true;
                degrees = Math.atan2(player.getY()-y,player.getX()-x);
            }
            speed = 500;
        }
        if(checkBounds()){
            destroyed = true;
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        //drawTool.drawImage(getMyImage(),x-imageOffset,y-imageOffset);
        if(isCharging)
            drawTool.setCurrentColor(new Color(0x20D6C7));
        else
            drawTool.setCurrentColor(new Color(0x143464));
        drawTool.drawFilledCircle(x,y,8);
    }

    @Override
    public Effect onDestroyed() {
        if(isCharging)
            return new DustParticleEffect(x+imageOffset,y+imageOffset,15,30,10,new Color(0x20D6C7));
        return new DustParticleEffect(x+imageOffset,y+imageOffset,15,30,10,new Color(0x143464));
    }
}
