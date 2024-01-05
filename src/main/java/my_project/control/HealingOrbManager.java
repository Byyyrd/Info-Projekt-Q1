package my_project.control;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.view.DrawTool;
import my_project.model.HealingOrb;
import my_project.model.Player;

/**
 * The OrbManager draws and updates the healing orbs, thus manging them.
 * It is the only controller that can draw things (to avoid flickering and lag caused by each object running draw and update separately)
 * and is thus not a Controller, but instead a Manager.
 */
public class HealingOrbManager extends GraphicalObject {
    private Player player;
    private List<HealingOrb> orbList = new List<>();
    private double chaseSpeed = 400;

    /**
     *
     * Registers player for use from Orb
     *
     * @param player concurrent player
     */
    public HealingOrbManager(Player player) {
        this.player = player;
    }

    /**
     * Adds a new Orb that moves in a random angle from its origin until it starts chasing th player
     *
     * @param x X-Position of the orb
     * @param y Y-Position of the orb
     * @param speed Initial speed for the orb before starting to chase the player
     */
    public void addNewOrb(double x, double y, double speed) {
        double angle = (Math.random() - 0.5) * Math.PI * 2;
        orbList.append(new HealingOrb(x,y,speed,angle));
    }

    @Override
    public void draw(DrawTool drawTool) {
        orbList.toFirst();
        while (orbList.hasAccess()){
            orbList.getContent().draw(drawTool);
            orbList.next();
        }
    }

    @Override
    public void update(double dt) {
        orbList.toFirst();
        while (orbList.hasAccess()){
            if(orbList.getContent().update(dt,chaseSpeed,player)){
                orbList.remove();
            } else {
                orbList.next();
            }
        }
    }
}
