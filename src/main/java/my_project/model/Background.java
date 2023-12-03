package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;

import java.awt.image.BufferedImage;

public class Background extends GraphicalObject {
    private BufferedImage[] backgroundEffects = new BufferedImage[4];
    private int index = 0;
    private double timer = 0.1;
    public Background(){
        for (int i = 0; i < 4; i++) {
            setNewImage("src/main/resources/graphic/backgroundEffect/backgroundEffect" + (i+1) + ".png");
            backgroundEffects[i] = getMyImage();
        }
        setNewImage("src/main/resources/graphic/background.png");
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(20,16,19,255);
        drawTool.drawFilledRectangle(0,0, Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        drawTool.setCurrentColor(255,255,255,255);
        for (int i = 0; i < 10; i++) {
            //TODO Choose your character
            drawTool.drawImage(backgroundEffects[index],0,100 * i + Math.sin(y/10) * 30 + y);
            //drawTool.drawImage(backgroundEffects[index],0,100 * i + Math.sin(y) * 30 + y);
            //drawTool.drawImage(backgroundEffects[index],0,100 * i + Math.random() * 30 + y);
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
            timer = 0.1;
        }
        y -= dt * 100;
        if (y < -500) {
            y += 500;
        }
    }
}
