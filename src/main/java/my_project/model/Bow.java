package my_project.model;

import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.control.CollisionController;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class Bow extends InteractiveGraphicalObject {
    private double power = 0;
    private final double maxPower = 1;
    private double degrees;
    private double mouseX;
    private double mouseY;
    private boolean mouseDown;
    private Player player;
    private CollisionController collisionController;

    public Bow(Player player, CollisionController collisionController){
        this.player = player;
        this.collisionController = collisionController;
        setNewImage("src/main/resources/graphic/bow.png");
    }

    @Override
    public void draw(DrawTool drawTool) {
        double offset = 0;
        degrees = Math.atan2(offset+mouseY-player.getY(),offset+mouseX-player.getX());

        Graphics2D g2d = drawTool.getGraphics2D();
        AffineTransform old = g2d.getTransform();

        g2d.rotate(degrees, player.getX()+offset, player.getY()+offset);
        drawTool.drawImage(getMyImage(),player.getX()+offset,player.getY()+offset-16);

        g2d.setTransform(old);
    }

    @Override
    public void update(double dt) {
        charge(dt);
    }

    private void charge(double dt){
        if(mouseDown){
            power += dt;
            if (power > maxPower){
                power = maxPower;
            }
        } else {
            if (power > 0){
                shoot(power);
            }
            power = 0;
        }
    }

    private void shoot(double shootingPower){
        //TODO AAAAAAAAAAAAAAA
        Arrow arrow = new Arrow(player.getX()-4,player.getY()-4,mouseX-player.getX(),mouseY-player.getY(),shootingPower*8);
        collisionController.addProjectile(arrow);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        mouseDown = true;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }
}
