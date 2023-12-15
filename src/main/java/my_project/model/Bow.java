package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.ModifierController;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Bow extends GraphicalObject {
    private boolean leftMouseDown;
    private double mouseX;
    private double mouseY;
    private double smoothOffsetX;
    private double smoothOffsetY;
    private double desiredX;
    private double desiredY;
    private double power = 0;
    private final double maxPower = 1;
    private double chargeSpeed = 100;

    private ModifierController modifierController;

    /**
     * Creates a new Bow object with default values
     */
    public Bow(){
        setNewImage("src/main/resources/graphic/bow.png");
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawChargeCircles(drawTool);

        double degrees = Math.atan2(mouseY - desiredY, mouseX - desiredX);

        Graphics2D g2d = drawTool.getGraphics2D();
        AffineTransform old = g2d.getTransform();

        g2d.rotate(degrees, desiredX, desiredY);
        drawTool.setCurrentColor(Color.white);
        drawTool.drawImage(getMyImage(),smoothOffsetX,-16+smoothOffsetY);

        g2d.setTransform(old);
    }

    /**
     * draws the circle indicating the charge status
     * @param drawTool from kago method draw
     */
    private void drawChargeCircles(DrawTool drawTool){
        for (int i = 0; i < 5; i++) {
            if(power == maxPower) {
                drawTool.setCurrentColor(255, 255, 255, (int) (power * 255) / (i + 1));
            } else {
                drawTool.setCurrentColor(139, 147, 175, (int) (power * 255) / (i + 1));
            }
            drawTool.drawCircle(desiredX, desiredY,16-i);
        }
    }

    @Override
    public void update(double dt) {
        charge(dt);
        smoothOffsetX = Util.lerp(smoothOffsetX,desiredX,1 - Math.pow(0.5,dt*50));
        smoothOffsetY = Util.lerp(smoothOffsetY,desiredY,1 - Math.pow(0.5,dt*50));
    }

    /**
     * Increases current bow power while under max and left mouse button is down
     * @param dt time between each frame
     */
    private void charge(double dt){
        if(leftMouseDown){
            power += dt * chargeSpeed;
            if (power > maxPower) {
                power = maxPower;
            }
            Util.setCamShake(0.1,power*20);
        } else {
            power = 0;
        }
    }

    /**
     * Gets all the necessary infos to shoot an arrow
     * <pre>
     * index 0 -> X Position
     * index 1 -> Y Position
     * index 2 -> Degrees
     * index 3 -> Speed
     * </pre>
     * @return a 2D array with all infos
     */
    public double[] getShootInfo(){
        if(power == 0) return null;
        double xPos = mouseX - (desiredX + 4);
        double yPos = mouseY - (desiredY + 4);
        double degrees = Math.atan2(yPos,xPos);
        return new double[]{desiredX + Math.cos(degrees) * 20,desiredY + Math.sin(degrees) * 20,degrees,power};
    }

    public double getPower() {
        return power;
    }

    public void setMousePosition(double mouseX, double mouseY){
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void setLeftMouseDown(boolean isDown){
        leftMouseDown = isDown;
    }

    public void setDesiredPosition(double desiredX, double desiredY){
        this.desiredX = desiredX;
        this.desiredY = desiredY;
    }
}
