package my_project.model.visuals;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Background class draws images in the background
 */
public class Background extends GraphicalObject {
    private final BufferedImage[] backgroundEffects;
    private int index = 0;
    private double timer = 0.1;
    private final double maxTimer = 0.1;
    private double intensity = 10;

    /**
     * Sets the images for later usage
     */
    public Background(){
        backgroundEffects = Util.getAllImagesFromFolder("backgroundEffect");
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(new Color(0x141013));
        drawTool.drawFilledRectangle(-50,-50, Config.WINDOW_WIDTH+100,Config.WINDOW_HEIGHT+100);
        drawTool.setCurrentColor(20,16,19,255);
        drawTool.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        drawTool.setCurrentColor(Color.white);
        for (int i = 0; i < 10; i++) {
            drawTool.drawImage(backgroundEffects[index],0,100 * i + Math.random() * intensity + y);
        }
    }

    @Override
    public void update(double dt) {
        timer -= dt;
        if (timer < 0) {
            if (index < backgroundEffects.length - 1) {
                index++;
            } else {
                index = 0;
            }
            timer = maxTimer;
        }
        y -= dt * 100;
        if (y < -500) {
            y += 500;
        }
    }

    public void setIntensity(double intensity){
        this.intensity = intensity;
    }
}
