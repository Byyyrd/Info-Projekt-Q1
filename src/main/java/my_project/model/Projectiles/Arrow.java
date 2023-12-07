package my_project.model.Projectiles;

import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.model.DustParticleEffect;
import my_project.model.Effect;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Arrow extends Projectile {
    double speedDecay = 50;
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

    public boolean isStrong(){
        return speed > speedDecay * 20;
    }
}
