package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.Util;

import java.awt.image.BufferedImage;

public class Background extends GraphicalObject {
    private BufferedImage[] backgroundEffects = new BufferedImage[4];
    private int index = 0;
    private double timer = 0.1;
    private final double maxTimer = 0.1;
    private double intensity = 10;
    public Background(){
        backgroundEffects = Util.getAllImagesFromFolder("backgroundEffect");
        setNewImage("src/main/resources/graphic/background.png");
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(20,16,19,255);
        drawTool.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        drawTool.setCurrentColor(255,255,255,255);
        for (int i = 0; i < 10; i++) {
            drawTool.drawImage(backgroundEffects[index],0,100 * i + Math.random() * intensity + y);
        }
        drawTool.drawImage(getMyImage(),0,0);
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
