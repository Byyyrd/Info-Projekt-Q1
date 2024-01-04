package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.SpawnController;

import java.awt.*;

public class XpOrb extends GraphicalObject {
    private Player player;
    private double chaseSpeed;
    private double initialSpeed;
    private double angle;
    private double timer;
    private SpawnController spawnController;
    public XpOrb(double x, double y, double angle ,double initialSpeed, Player player, SpawnController spawnController) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.initialSpeed = initialSpeed;
        this.angle = angle;
        this.spawnController = spawnController;
        chaseSpeed = 700;
        radius = 8;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(Color.green);
        drawTool.drawFilledCircle(x,y,radius);
    }

    @Override
    public void update(double dt) {
        if(Util.circleToCircleCollision(x, y, radius, player.getX(), player.getY(), 8, 4)){
            //TODO: DONT TOUCH PERFEcT MVC TRAVER -> VERY BAAAAS
            player.addXp(1);
            spawnController.removeObject(this);
        }
        if(timer < .1){
            timer += dt;
            moveInDirection(angle, initialSpeed,dt);
        }else{
            move(chaseSpeed,dt);
        }
    }

    protected void move(double speed,double dt){
        double degrees = Math.atan2(player.getY()-y,player.getX()-x);
        this.x += Math.cos(degrees) * speed * dt;
        this.y += Math.sin(degrees) * speed * dt;
    }
}
