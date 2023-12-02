package my_project.model;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.view.DrawTool;

public class Player extends InteractiveGraphicalObject {
    private double currentSpeed = 200;
    public Player(){
        x = 488-8; //Mitte des Bildschirms
        y = 488; //Eine sinnvolle Zahl unter dem Zentrum
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.drawFilledCircle(x,y,8);
    }

    @Override
    public void update(double dt) {
        movePlayer(dt);
    }

    private void movePlayer(double dt){
        if (ViewController.isKeyDown(65)) {
            x -= currentSpeed * dt;
        } else if (ViewController.isKeyDown(68)) {
            x += currentSpeed * dt;
        }
        if (ViewController.isKeyDown(87)) {
            y -= currentSpeed * dt;
        } else if (ViewController.isKeyDown(83)) {
            y += currentSpeed * dt;
        }
        if (x < 35) x = 35;
        if (x > 925) x = 925;
        if (y < 35) y = 35;
        if (y > 505) y = 505;
    }
}
