package my_project.model.enemies;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

/**
 * The EnemyNode class is used to generalize the individual components an enemy has
 */
public class EnemyNode extends GraphicalObject {
    private double degrees = 0;

    /**
     * Sets the proper values for the enemy node, as a circle
     *
     * @param x X coordinate of the node
     * @param y Y coordinate of the node
     * @param radius Radius coordinate of the node
     */
    public EnemyNode(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    /**
     * moves Node to coordinates gX and gY
     * @param dt time last frame was rendered
     * @param speed speed of node movement
     * @param gX X coordinate node moves to
     * @param gY Y coordinate node moves to
     */
    protected void moveByCoordinates(double dt,double speed,double gX,double gY){
        double degrees = Math.atan2(gY-y,gX-x);
        x += Math.cos(degrees) * speed * dt;
        y += Math.sin(degrees) * speed * dt;
    }
    /**
     * Similar to moveByCoordinates but instead of an x,y coordinate uses an angle
     *
     * @param dt Time last frame was rendered
     * @param speed Speed of node movement
     * @param degrees Angle node is moved to
     */
    protected void moveByAngle(double dt,double degrees, double speed){
        x += Math.cos(degrees) * speed * dt;
        y += Math.sin(degrees) * speed * dt;
    }

    @Override
    public void draw(DrawTool drawTool) {}

    @Override
    public void update(double dt) {}

    public void setDegrees(double degrees) {
        this.degrees = degrees;
    }

    public double getDegrees() {
        return degrees;
    }
}
