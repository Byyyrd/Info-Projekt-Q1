package my_project.model;

import KAGO_framework.view.DrawTool;
import my_project.Util;

import java.awt.*;

/**
 * The Orb class is managed by the OrbManager and adds health to the player
 */
public class HealingOrb {
    private double x;
    private double y;
    private double initialSpeed;
    private double angle;
    private double timer = 0.5;
    private final double radius = 5;

    /**
     * Sets the initial parameters for the orb
     *
     * @param x X-Position of the orb
     * @param y Y-Position of the orb
     * @param initialSpeed Start speed of orb, which
     * simultaneously acts as a current speed of orb
     * @param angle The initial angle
     */
    public HealingOrb(double x, double y, double initialSpeed, double angle){
        this.x = x;
        this.y = y;
        this.initialSpeed = initialSpeed;
        this.angle = angle;
    }

    /**
     * Emulation of the update method from a graphical object
     *
     * @param dt Time between frames
     * @return Whether the orb should be destroyed
     */
    public boolean update(double dt, double chaseSpeed, Player player){
        boolean shouldRemove = false;
        if(Util.circleToCircleCollision(x, y, radius, player.getX(), player.getY(), 8, 4)){
            player.addXp(1);
            shouldRemove = true;
        }
        if(timer > 0) {
            timer -= dt;
            initialSpeed = Util.lerp(initialSpeed,0,dt);
        } else {
            double degrees = Math.atan2(player.getY()-y,player.getX()-x);
            angle = Util.lerpAngle(angle,degrees,dt * 10);
            initialSpeed = Util.lerp(initialSpeed,chaseSpeed,dt * 5);
        }
        x += Math.cos(angle) * initialSpeed * dt;
        y += Math.sin(angle) * initialSpeed * dt;

        return shouldRemove;
    }

    /**
     * Emulation of the draw method from a graphical object
     *
     * @param drawTool Current drawTool to draw with
     */
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(new Color(0x66B4202A, true));
        drawTool.drawFilledCircle(x,y,radius);
    }
}