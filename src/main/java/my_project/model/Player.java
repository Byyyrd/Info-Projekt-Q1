package my_project.model;

import KAGO_framework.control.SoundController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawFrame;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Player class applies all keyboard related inputs and provides current actual position of the player
 */
public class Player extends GraphicalObject {
    private BufferedImage[] images = Util.getAllImagesFromFolder("player");
    private boolean drawFirstImage = true;
    private double healthPoints = 0;
    private double healthBarSize = 35;
    private double invincible = 0;

    /**
     * Sets standard x and y positions relative to the panel width and height
     */
    public Player(){
        x = Config.WINDOW_WIDTH / 2;
        y = Config.WINDOW_HEIGHT / 2 + 100;
    }

    @Override
    public void update(double dt) {
        if(invincible < 0)
            healthPoints -= dt;
        invincible -= dt;
        if(healthPoints < 0){
            healthPoints = 0;
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(drawFirstImage)
            drawTool.drawImage(images[0],x-8,y-8);
        else
            drawTool.drawImage(images[1],x-8,y-8);

        drawTool.setCurrentColor(new Color(152, 17, 17));
        drawTool.drawFilledRectangle(x - healthBarSize/2, y - 22, healthBarSize - healthPoints, 5);
        if(invincible < 0)
            drawTool.setCurrentColor(new Color(0, 0, 0));
        else
            drawTool.setCurrentColor(new Color((int)(invincible*50), (int)(invincible*50), (int)(invincible*50)));
        drawTool.drawRectangle(x - (healthBarSize/2 + 1), y - 23, healthBarSize + 2, 7);
    }

    public void setDrawFirstImage(boolean drawFirstImage) {
        this.drawFirstImage = drawFirstImage;
    }

    /**
     * Method that is called when the player gets hit
     *
     * @return Whether the player did take damage
     */
    public boolean takeDamage() {
        if(invincible > 0)
            return false;
        healthPoints += healthBarSize / 1.5;
        invincible = 3;
        Util.setCamShake(0.5,10);
        if(healthPoints >= healthBarSize) {
            SoundController.stopSound("mainTrack");
            DrawFrame.getActivePanel().setVisible(false);
        }
        return true;
    }

    /**
     * Moves the player towards an x and y coordinate
     *
     * @param xDisplacement x coordinate
     * @param yDisplacement y coordinate
     */

    public void movePlayer(double xDisplacement, double yDisplacement){
        x += xDisplacement;
        y += yDisplacement;
        checkBounds();
    }

    /**
     * If the player touches the outline, he gets stopped
     */
    private void checkBounds(){
        if((x+8) < Config.leftBound) x = Config.leftBound - 8;
        if((x+8) > Config.rightBound) x = Config.rightBound - 8;
        if((y+8) < Config.upBound) y = Config.upBound - 8;
        if((y+8) > Config.downBound) y = Config.downBound - 8;
    }
}
