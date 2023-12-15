package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawFrame;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.Util;

import java.awt.image.BufferedImage;

public class Player extends GraphicalObject {
    private BufferedImage[] images = Util.getAllImagesFromFolder("player");
    private boolean drawFirstImage = true;

    public Player(){
        x = 488-8; //Mitte des Bildschirms
        y = 488; //Eine sinnvolle Zahl unter dem Zentrum
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(drawFirstImage)
            drawTool.drawImage(images[0],x-8,y-8);
        else
            drawTool.drawImage(images[1],x-8,y-8);
    }

    public void setDrawFirstImage(boolean drawFirstImage) {
        this.drawFirstImage = drawFirstImage;
    }

    public void takeDamage(){
        DrawFrame.getActivePanel().setVisible(false);
    }

    public void movePlayer(double xDisplacement, double yDisplacement){
        x += xDisplacement;
        y += yDisplacement;
        checkBounds();
    }

    private void checkBounds(){
        if((x+8) < Config.leftBound) x = Config.leftBound - 8;
        if((x+8) > Config.rightBound) x = Config.rightBound - 8;
        if((y+8) < Config.upBound) y = Config.upBound - 8;
        if((y+8) > Config.downBound) y = Config.downBound - 8;
    }
}
