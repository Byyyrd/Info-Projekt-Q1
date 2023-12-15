package my_project.model.effects;

import KAGO_framework.model.GraphicalObject;

/**
 * The Effect class is a template class to make deleting effects easier
 */
public abstract class Effect extends GraphicalObject {
    protected boolean destroyed;

    /**
     * Sets the position of the effect
     *
     * @param x X coordinate of the effect
     * @param y Y coordinate of the effect
     */
    public Effect(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
