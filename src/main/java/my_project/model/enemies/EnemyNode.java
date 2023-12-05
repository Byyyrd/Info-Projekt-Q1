package my_project.model.enemies;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

public class EnemyNode extends GraphicalObject {
    private double degrees = 0;
    public EnemyNode( double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }


    protected void moveByCoordinates(double dt,double speed,double gX,double gY){
        double degrees = Math.atan2(gY-y,gX-x);
        x += Math.cos(degrees) * speed * dt;
        y += Math.sin(degrees) * speed * dt;
    }
    protected void moveByAngle(double dt,double degrees, double speed){
        x += Math.cos(degrees) * speed * dt;
        y += Math.sin(degrees) * speed * dt;
    }

    @Override
    public void draw(DrawTool drawTool){

    }
    @Override
    public void update(double dt) {

    }

    public void setDegrees(double degrees) {
        this.degrees = degrees;
    }

    public double getDegrees() {
        return degrees;
    }
}
