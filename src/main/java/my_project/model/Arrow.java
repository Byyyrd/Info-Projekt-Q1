package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Util;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Arrow extends Projectile{
    double speedDecay = 50;
    public Arrow(double x, double y, double degrees, double speed){
        super(x,y,degrees,speed);
        setNewImage("src/main/resources/graphic/arrow.png");
        isHarmful = false;
        offsetX = 8;
        offsetY = 4;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(wasInWall()) speed = 0;
        speed = Util.lerp(speed,0,dt*4);
        if(speed < speedDecay){
            destroy = true;
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        Graphics2D g2d = drawTool.getGraphics2D();
        AffineTransform old = g2d.getTransform();

        g2d.rotate(degrees, x+offsetX, y+offsetY);
        drawTool.drawImage(getMyImage(),x-offsetX,y-offsetY);

        g2d.setTransform(old);
    }

    @Override
    public GraphicalObject onDestroy() {
        return new DustParticleEffect(x+offsetX,y+offsetX);
    }
}
