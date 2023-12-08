package my_project.model.projectiles;

import KAGO_framework.view.DrawTool;
import my_project.model.Player;

import java.awt.*;

public class ChargeBullet extends Projectile {
    private boolean isCharging = false;
    private double timer = 1;
    private Player player;

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
}
