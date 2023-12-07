package my_project.model.Projectiles;

import KAGO_framework.view.DrawTool;

import java.awt.*;

public class Bullet extends Projectile {
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
}
