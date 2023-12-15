package my_project.control;

import KAGO_framework.model.GraphicalObject;
import my_project.model.Player;
import my_project.model.effects.Effect;
import my_project.model.enemies.Enemy;
import my_project.model.projectiles.Projectile;

/**
 * The SpawnController class communicates with the program controller and collision controller to properly create and register new entities
 */
public class SpawnController {
    private ProgramController programController;
    private CollisionController collisionController;

    /**
     * Registers a program controller for future usage
     *
     * @param programController Program controller currently in use
     */
    public SpawnController(ProgramController programController){
        this.programController = programController;
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
        programController.addObject(effect);
    }

    /**
     * Removes any graphical object and prevents it from drawing
     *
     * @param graphicalObject Graphical object to be removed
     */
    public void removeObject(GraphicalObject graphicalObject){
        programController.removeObject(graphicalObject);
    }

    public Player getPlayer(){
        return collisionController.getPlayer();
    }

    public void setCollisionController(CollisionController collisionController){
        this.collisionController = collisionController;
    }
}
