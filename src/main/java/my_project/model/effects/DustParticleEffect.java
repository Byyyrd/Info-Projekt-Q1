package my_project.model.effects;

import KAGO_framework.view.DrawTool;
import my_project.Util;

import java.awt.*;

/**
 * The DustParticleEffect is a standard effect, usually used when something is destroyed
 */
public class DustParticleEffect extends Effect {
    private final Particle[] circles;
    private double alpha = 1;
    private Color color = Color.white;

    /**
     * Sets the position of the effect, standard version
     *
     * @param x X coordinate of the effect
     * @param y Y coordinate of the effect
     */
    public DustParticleEffect(double x, double y){
        super(x,y);
        circles = new Particle[10];
        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Particle(x+(Math.random()-0.5)*10,y+(Math.random()-0.5)*10,Math.random()*5+5);
        }
    }

    /**
     * Sets the used variables of the effect, extended version
     *
     * @param x X coordinate of the effect
     * @param y Y coordinate of the effect
     * @param amountOfObjects Amount of all particles in the effect
     * @param radiusOfEffect Range in which particles can spawn in away from the x and y coordinates
     * @param radiusOfParticles Average radius of the particles in the effect
     * @param color Used color of the effect
     */
    public DustParticleEffect(double x, double y, int amountOfObjects, double radiusOfEffect, double radiusOfParticles, Color color){
        super(x,y);
        circles = new Particle[amountOfObjects];
        for (int i = 0; i < circles.length; i++) {
            double degrees = (Math.random()-0.5) * 2 * Math.PI;
            circles[i] = new Particle(x+Math.cos(degrees)*radiusOfEffect*Math.random(),y+Math.sin(degrees)*radiusOfEffect*Math.random(),(Math.random()*(radiusOfParticles/2))+radiusOfParticles/2);
        }
        this.color = color;
    }

    /**
     * The particle class is for drawing the individual circles in the effect
     */
    private class Particle {
        private final double x;
        private final double y;
        private double radius;

        /**
         *  Sets the position and radius of an individual circle
         *
         * @param x X coordinate of the circle
         * @param y X coordinate of the circle
         * @param radius Radius of the circle
         */
        public Particle(double x, double y, double radius){
            this.x = x;
            this.y = y;
            this.radius = radius;
        }
    }

    /**
     * Draws all particles
     *
     * @param drawTool Draw tool in use
     */
    @Override
    public void draw(DrawTool drawTool) {
        for (Particle circle : circles) {
            drawTool.setCurrentColor(color.getRed(), color.getGreen(), color.getBlue(), (int) (Math.abs(Math.sin(alpha * 2)) * 255));
            drawTool.drawFilledCircle(circle.x, circle.y, circle.radius);
        }
    }

    /**
     * Fades the particles out
     *
     * @param dt Time between the current and the last frame
     */
    @Override
    public void update(double dt) {
        alpha = Util.lerp(alpha,0,dt*2);
        if(alpha < 0.05){
            destroyed = true;
            alpha = 0;
        }
        for (Particle circle : circles) {
            circle.radius = Math.sin(alpha * 2) * 10;
        }
    }
}
