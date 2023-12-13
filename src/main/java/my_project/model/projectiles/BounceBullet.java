package my_project.model.projectiles;

import KAGO_framework.view.DrawTool;
import my_project.model.Player;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;

import java.awt.*;

public class BounceBullet extends Projectile {
    private boolean canBounce = true;
    private Player player;
    private double timer = 0;

    public BounceBullet(double x, double y, double degrees, double speed, Player player) {
        super(x, y, degrees, speed);
        imageOffset = 8;
        this.player = player;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        timer -= dt;
        if(checkBounds() && timer < 0){
            timer = 0.1;
            degrees = Math.atan2(player.getY()-y,player.getX()-x);
            if(!canBounce){
                destroyed = true;
            } else {
                speed /= 2;
                canBounce = false;
            }
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        //drawTool.drawImage(getMyImage(),x-imageOffset,y-imageOffset);
        if(canBounce)
            drawTool.setCurrentColor(new Color(0xdf3e23));
        else
            drawTool.setCurrentColor(new Color(0x73172d));
        drawTool.drawFilledCircle(x,y,8);
    }

    @Override
    public Effect onDestroyed() {
        if(canBounce)
            return new DustParticleEffect(x+imageOffset,y+imageOffset,15,30,10,new Color(0xdf3e23));
        return new DustParticleEffect(x+imageOffset,y+imageOffset,15,30,10,new Color(0x73172d));
    }
}
