package my_project.model;

import KAGO_framework.model.GraphicalObject;

public abstract class Effect extends GraphicalObject {
    protected boolean destroyed;

    public Effect(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
