package my_project.model;

import KAGO_framework.model.GraphicalObject;

public class Bow extends GraphicalObject {
    private double power = 0;
    private final double maxPower = 1;

    public Bow(int x, int y){
        this.x = x;
        this.y = y;
    }
}
