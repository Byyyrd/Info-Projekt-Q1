package my_project.model;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;

public class Player extends InteractiveGraphicalObject {
    private double currentSpeed = 200;
    private int hp = 2;
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
        checkBounds();
    }

    private void checkBounds(){
        if(x < Config.leftBound) x = Config.leftBound;
        if(x > Config.rightBound) x = Config.rightBound;
        if(y < Config.upBound) y = Config.upBound;
        if(y > Config.downBound) y = Config.downBound;
    }

    public void takeDamage(){

    }

}
