package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

public class Background extends GraphicalObject {
    public Background(){
        setNewImage("src/main/resources/graphic/background.png");
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(),0,0);
    }
}
