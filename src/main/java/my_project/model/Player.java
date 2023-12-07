package my_project.model;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Config;
import my_project.Util;

import java.awt.image.BufferedImage;

public class Player extends InteractiveGraphicalObject {
    private BufferedImage[] images = Util.getAllImagesFromFolder("player");
    private double currentSpeed = 200;
    private int hp = 2;
    private double dashTimer = 0;
    private final double dashSpeed = 700;
    private final double dashDuration = 0.2;
    private final double dashCooldown = 0.5;
    public Player(){
        x = 488-8; //Mitte des Bildschirms
        y = 488; //Eine sinnvolle Zahl unter dem Zentrum
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(dashTimer > 0)
            drawTool.drawImage(images[1],x-8,y-8);
        else
            drawTool.drawImage(images[0],x-8,y-8);
    }

    @Override
    public void update(double dt) {
        dash(dt);
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

    private void dash(double dt){
        dashTimer -= dt;
        if(dashTimer > dashCooldown)
            currentSpeed = dashSpeed;
        else if (currentSpeed == dashSpeed) {
            currentSpeed = 200;
        }
    }

    private void checkBounds(){
        if((x+8) < Config.leftBound) x = Config.leftBound - 8;
        if((x+8) > Config.rightBound) x = Config.rightBound - 8;
        if((y+8) < Config.upBound) y = Config.upBound - 8;
        if((y+8) > Config.downBound) y = Config.downBound - 8;
    }

    public void takeDamage(){
        x = 0;
        y = 0;
    }

    public void setCurrentSpeed(double newSpeed){
        if(dashTimer < dashCooldown) currentSpeed = newSpeed;
    }

    public void setDash(){
        if(dashTimer < 0) dashTimer = dashCooldown + dashDuration;
    }
}
