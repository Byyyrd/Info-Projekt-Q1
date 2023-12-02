package my_project.model;

import KAGO_framework.model.GraphicalObject;

public abstract class Effects extends GraphicalObject {
    protected boolean isDestroyed;

    public Effects(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
