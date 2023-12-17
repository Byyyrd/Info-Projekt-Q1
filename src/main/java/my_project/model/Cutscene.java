package my_project.model;

import KAGO_framework.control.SoundController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.Util;
import my_project.control.ProgramController;

import java.awt.image.BufferedImage;

public class Cutscene extends GraphicalObject {
    private BufferedImage[] images;
    private int index = 0;
    private double timer = 2;
    private ProgramController programController;

    public Cutscene(ProgramController programController){
        this.programController = programController;
        images = Util.getAllImagesForCutscene("cutscene1");
        SoundController.playSound("cutscene1");
    }


    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(0,0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        try {
            drawTool.drawImage(images[index],0,0);
        } catch (ArrayIndexOutOfBoundsException e){
            programController.startGame();
            programController.removeObject(this);
        }
    }

    @Override
    public void update(double dt) {
        timer -= dt;
        if(timer < 0){
            timer = 0.1;
            index++;
        }
    }
}
