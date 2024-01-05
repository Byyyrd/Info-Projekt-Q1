package my_project.control;

import KAGO_framework.model.GraphicalObject;
import my_project.model.Player;
import my_project.model.OrbManager;
import my_project.model.effects.Effect;
import my_project.model.enemies.Enemy;
import my_project.model.projectiles.Projectile;

/**
 * The SpawnController class communicates with the program controller and collision controller to properly create and register new entities
 */
public class SpawnController {
    private ProgramController programController;
    private CollisionController collisionController;
    private OrbManager orbManager;

    /**
     * Registers a program controller for future usage
     *
     * @param programController Program controller currently in use
     */
    public SpawnController(ProgramController programController, OrbManager orbManager){
        this.programController = programController;
        this.orbManager = orbManager;
    }

    /**
     * Registers a new enemy instance
     *
     * @param enemy Enemy to be registered
     */
    public void addEnemy(Enemy enemy){
        programController.addObject(enemy);
        collisionController.registerEnemy(enemy);
    }

    /**
     * Registers a new projectile instance
     *
     * @param projectile Projectile to be registered
     */
    public void addProjectile(Projectile projectile){
        programController.addObject(projectile);
        collisionController.registerProjectile(projectile);
    }

    /**
     * Registers a new effect instance
     *
     * @param effect Effect to be registered
     */
    public void addEffect(Effect effect){
        if(effect != null) {
            programController.addObject(effect);
            collisionController.addEffect(effect);
        }
    }

    /**
     * Spawns orbs on a coordinate with a certain speed
     *
     * @param x X spawn coordinate
     * @param y Y spawn coordinate
     * @param speed Initial speed of the orb
     */
    public void addNewOrb(double x, double y, double speed){
        orbManager.addNewOrb(x,y,speed);
    }

    /**
     * Removes any graphical object and prevents it from drawing
     *
     * @param graphicalObject Graphical object to be removed
     */
    public void removeObject(GraphicalObject graphicalObject){
        programController.removeObject(graphicalObject);
    }

    public void endGame(double timer){
        programController.endGame(timer);
    }

    public Player getPlayer(){
        return collisionController.getPlayer();
    }

    public void setCollisionController(CollisionController collisionController){
        this.collisionController = collisionController;
    }
}
