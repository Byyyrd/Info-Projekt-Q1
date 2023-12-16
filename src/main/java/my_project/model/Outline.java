package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.Util;

/**
 * The Outline class is drawn in the foreground to make the arena visual
 */
public class Outline extends GraphicalObject {
    private double timer = 2;
    private double alpha = 1;

    /**
     * Sets the image to be used for the outline
     */
    public Outline() {
        setNewImage("src/main/resources/graphic/outline.png");
    }

    @Override
    public void update(double dt) {
        if(timer > 0){
            timer -= dt;
        } else {
            timer = 0;
            alpha = Util.lerp(alpha,0,dt);
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(),-10,-10); //The image is drawn slightly off the origin to account for camera shake
        drawTool.setCurrentColor(255,255,255,(int)(255*alpha));
        drawTool.drawFilledRectangle(-10,-10, Config.WINDOW_WIDTH + 10, Config.WINDOW_HEIGHT + 10);
    }
}
