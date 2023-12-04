package my_project.model;

import KAGO_framework.model.InteractiveGraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.CollisionController;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bow extends InteractiveGraphicalObject {
    private double power = 0;
    private final double maxPower = 1;
    private double degrees;
    private double mouseX;
    private double mouseY;
    private double smoothOffsetX;
    private double smoothOffsetY;
    private boolean mouseDown;
    private boolean canDash = true;
    private Player player;
    private CollisionController collisionController;

    public Bow(Player player, CollisionController collisionController){
        this.player = player;
        this.collisionController = collisionController;
        setNewImage("src/main/resources/graphic/bow.png");
    }

    @Override
    public void draw(DrawTool drawTool) {
        double localOffset = 0;
        for (int i = 0; i < 5; i++) {
            if(power == maxPower) {
                drawTool.setCurrentColor(255, 255, 255, (int) (power * 255) / (i + 1));
            } else {
                drawTool.setCurrentColor(139, 147, 175, (int) (power * 255) / (i + 1));
            }
            drawTool.drawCircle(player.getX()+localOffset, player.getY()+localOffset,16-i);
        }

        degrees = Math.atan2(localOffset+mouseY-player.getY(),localOffset+mouseX-player.getX());

        Graphics2D g2d = drawTool.getGraphics2D();
        AffineTransform old = g2d.getTransform();

        g2d.rotate(degrees, player.getX()+localOffset, player.getY()+localOffset);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.drawImage(getMyImage(),localOffset+smoothOffsetX,localOffset-16+smoothOffsetY);

        g2d.setTransform(old);
    }

    @Override
    public void update(double dt) {
        charge(dt);
        smoothOffsetX = Util.lerp(smoothOffsetX,player.getX(),1 - Math.pow(0.5,dt*50));
        smoothOffsetY = Util.lerp(smoothOffsetY,player.getY(),1 - Math.pow(0.5,dt*50));
    }

    private void charge(double dt){
        if(mouseDown){
            power += dt;
            if (power > maxPower){
                power = maxPower;
            }
            player.setCurrentSpeed(200-power*150);
            Util.setCamShake(0.1,power*20);
        } else {
            if (power > 0){
                shoot(power);
                player.setCurrentSpeed(200);
                Util.setCamShake(0.2,power*50+20);
            }
            power = 0;
        }
    }

    private void shoot(double shootingPower) {
        double degrees = Math.atan2(mouseY - (player.getY() + 4),mouseX - (player.getX() + 4));
        Arrow arrow = new Arrow(player.getX() + 4 + Math.cos(degrees) * 20,player.getY() + 4 + Math.sin(degrees) * 20, degrees,shootingPower * 5000);
        collisionController.addProjectile(arrow);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if(e.getButton() == 1) {
            mouseDown = true;
        }
        if(e.getButton() == 3 && canDash) {
            canDash = false;
            player.setDash();
        }
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
        if(e.getButton() == 1) {
            mouseDown = false;
        }
        if(e.getButton() == 3) {
            canDash = true;
        }
    }
}
