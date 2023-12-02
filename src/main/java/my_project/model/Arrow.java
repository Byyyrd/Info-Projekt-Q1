package my_project.model;

import KAGO_framework.view.DrawTool;
import my_project.Util;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Arrow extends Projectile{
    double speedDecay = speed/2;
    public Arrow(double x, double y, double dirX, double dirY, double speed){
        super(x,y,dirX,dirY,speed);
        setNewImage("src/main/resources/graphic/arrow.png");
        isHarmful = false;
        offsetX = 8;
        offsetY = 4;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        speed = Util.lerp(speed,0,dt);
        if(speed < speedDecay){
            destroy = true;
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        double degrees = Math.atan2(offsetY+dirY,offsetX+dirX);

        Graphics2D g2d = drawTool.getGraphics2D();
        AffineTransform old = g2d.getTransform();

        g2d.rotate(degrees, x+offsetX, y+offsetY);
        drawTool.drawImage(getMyImage(),x,y);

        g2d.setTransform(old);
    }
}
