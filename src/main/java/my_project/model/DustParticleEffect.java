package my_project.model;

import KAGO_framework.view.DrawTool;
import my_project.Util;

import java.awt.*;

public class DustParticleEffect extends Effect {
    private final Particle[] circles;
    private double alpha = 1;
    private Color color = Color.white;
    public DustParticleEffect(double x, double y){
        super(x,y);
        circles = new Particle[10];
        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Particle(x+(Math.random()-0.5)*10,y+(Math.random()-0.5)*10,Math.random()*5+5);
        }
    }

    public DustParticleEffect(double x, double y, int amountOfObjects, double radiusOfEffect, double radiusOfParticles, Color color){
        super(x,y);
        circles = new Particle[amountOfObjects];
        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Particle(x+(Math.random()-0.5)*radiusOfEffect,y+(Math.random()-0.5)*radiusOfEffect,(Math.random()*radiusOfParticles/2)+radiusOfParticles/2);
        }
        this.color = color;
    }

    private class Particle {
        private final double x;
        private final double y;
        private double radius;
        public Particle(double x, double y, double radius){
            this.x = x;
            this.y = y;
            this.radius = radius;
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        for (Particle circle : circles) {
            drawTool.setCurrentColor(color.getRed(), color.getGreen(), color.getBlue(), (int) (Math.abs(Math.sin(alpha * 2)) * 255));
            drawTool.drawFilledCircle(circle.x, circle.y, circle.radius);
        }
    }

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
