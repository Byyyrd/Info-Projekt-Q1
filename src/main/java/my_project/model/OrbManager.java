package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.view.DrawTool;
import my_project.Util;

import java.awt.*;

public class OrbManager extends GraphicalObject {
    private Player player;
    private List<Orb> orbList = new List<>();
    private double chaseSpeed = 400;

    public OrbManager(Player player) {
        this.player = player;
    }

    /**
     * Adds a new Orb that moves in a random angle from its origin until it starts chasing th player
     * @param x the X-Position of the Orb
     * @param y the Y-Position of the Orb
     * @param speed the initial speed for the orb before starting to chase the player
     */
    public void addNewOrb(double x, double y, double speed) {
        double angle = (Math.random() - 0.5) * Math.PI * 2;
        orbList.append(new Orb(x,y,speed,angle));
    }

    @Override
    public void draw(DrawTool drawTool) {
        orbList.toFirst();
        while (orbList.hasAccess()){
            orbList.getContent().draw(drawTool);
            orbList.next();
        }
    }

    @Override
    public void update(double dt) {
        orbList.toFirst();
        while (orbList.hasAccess()){
            if(orbList.getContent().update(dt)){
                orbList.remove();
            } else {
                orbList.next();
            }
        }
    }

    /**
     * private inner class that is managed by the OrbManager class
     */
    private class Orb {
        private double x;
        private double y;
        private double initialSpeed;
        private double angle;
        private double timer = 0.5;

        private Orb(double x, double y, double initialSpeed, double angle){
            this.x = x;
            this.y = y;
            this.initialSpeed = initialSpeed;
            this.angle = angle;
            radius = 5;
        }

        /**
         * Emulation of the update method from a GO
         * @param dt time between frames
         * @return Should the Orb be destroyed
         */
        private boolean update(double dt){
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
         * Emulation of the draw method from a GO
         * @param drawTool the drawtool to draw with
         */
        private void draw(DrawTool drawTool) {
            drawTool.setCurrentColor(new Color(0x66B4202A, true));
            drawTool.drawFilledCircle(x,y,radius);
        }
    }
}
