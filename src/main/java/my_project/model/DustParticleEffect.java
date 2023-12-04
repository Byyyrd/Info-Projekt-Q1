package my_project.model;

import KAGO_framework.view.DrawTool;
import my_project.Util;

public class DustParticleEffect extends Effect {
    private Particle[] circles;
    private double alpha = 1;
    public DustParticleEffect(double x, double y){
        super(x,y);
        circles = new Particle[10];
        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Particle(x+(Math.random()-0.5)*10,y+(Math.random()-0.5)*10,Math.random()*5+5);
        }
    }

    private class Particle {
        private double x;
        private double y;
        private double radius;
        public Particle(double x, double y, double radius){
            this.x = x;
            this.y = y;
            this.radius = radius;
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        for (int i = 0; i < circles.length; i++) {
            drawTool.setCurrentColor(255,255,255,(int)(Math.abs(Math.sin(alpha*2))*255));
            drawTool.drawFilledCircle(circles[i].x,circles[i].y,circles[i].radius);
        }
    }

    @Override
    public void update(double dt) {
        alpha = Util.lerp(alpha,0,dt*2);
        if(alpha < 0.05){
            destroyed = true;
            alpha = 0;
        }
        for (int i = 0; i < circles.length; i++) {
            circles[i].radius = Math.sin(alpha*2)*10;
        }
    }
}
