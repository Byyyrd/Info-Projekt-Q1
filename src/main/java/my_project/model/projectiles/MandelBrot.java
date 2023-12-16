package my_project.model.projectiles;

import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.model.Player;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;

import java.awt.*;

public class MandelBrot extends Projectile{

    private Player player;

    /**
     * Sets all needed values on instantiation
     *
     * @param x X coordinate of the projectile
     * @param y Y coordinate of the projectile
     * @param degrees The angle at which the projectile is rotated in radians
     * @param speed Speed of the projectile movement
     */
    public MandelBrot(double x, double y, double degrees, double speed, Player player) {
        super(x, y, degrees, speed);
        this.player = player;
        setNewImage("src/main/resources/graphic/mandelBrot.png");
        imageOffset = 8;
    }

    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(),x,y);
    }

    public void update(double dt) {
        super.update(dt);

        if(checkBounds()){
            destroyed = true;
        }
    }

    @Override
    public Effect onDestroyed() {
        return new DustParticleEffect(x+imageOffset,y+imageOffset,15,30,10,Color.cyan);
    }

}
