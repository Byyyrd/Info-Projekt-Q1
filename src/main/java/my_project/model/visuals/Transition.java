package my_project.model.visuals;

import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.Util;

import java.awt.*;

/**
 * The Transition class turns the entire screen white when created. Used to transition between the game and cutscenes
 */
public class Transition extends Outline {
    private double alpha = 0;

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(new Color(255,255,255,(int)(alpha*255)));
        drawTool.drawFilledRectangle(-50,-50, Config.WINDOW_WIDTH+100,Config.WINDOW_HEIGHT+100);
    }

    @Override
    public void update(double dt) {
        alpha = Util.lerp(alpha,1,dt);
    }
}
