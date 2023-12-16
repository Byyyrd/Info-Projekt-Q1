package my_project.model;

import KAGO_framework.control.SoundController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

public class Cutscene extends GraphicalObject {
    private double timer = 0;
    private double roeckrathPositionX = 950;
    @Override
    public void draw(DrawTool drawTool) {
        setNewImage("src/main/resources/graphic/Kaethe-Kollwitz-Gymnasium_Dortmund.png");
        drawTool.drawImage(getMyImage(),0,0);
        if(timer > 7){
            setNewImage("src/main/resources/graphic/Roeckrath.png");
            drawTool.drawImage(getMyImage(),roeckrathPositionX,400);
        }

    }

    @Override
    public void update(double dt) {
        timer += dt;
        if(timer > 5 && timer < 5.2){
            SoundController.stopSound("mainTrack");
            SoundController.playSound("Cutscene1");
        }
        if(timer > 7 && roeckrathPositionX > 200){
            roeckrathPositionX -= dt * 100;
        }


    }
}
