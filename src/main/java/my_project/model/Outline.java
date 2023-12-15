package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

/**
 * The Outline class is drawn in the foreground to make the arena visual
 */
public class Outline extends GraphicalObject {
    /**
     * Sets the image to be used for the outline
     */
    public Outline() {
        setNewImage("src/main/resources/graphic/outline.png");
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(),-10,-10); //The image is drawn slightly off the origin to account for camera shake
    }
}
