package my_project.model;

import KAGO_framework.model.GraphicalObject;

public abstract class Projectile extends GraphicalObject {
    private double dirX;
    private double dirY;
    private double speed;
    private boolean isHarmful = false;

    public Projectile(double x, double y, double dirX, double dirY, double speed){
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
        this.speed = speed;
    }
}
