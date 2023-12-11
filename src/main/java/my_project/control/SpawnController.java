package my_project.control;

import KAGO_framework.model.GraphicalObject;
import my_project.model.Player;
import my_project.model.effects.Effect;
import my_project.model.enemies.Enemy;
import my_project.model.projectiles.Projectile;

public class SpawnController {
    private ProgramController programController;
    private CollisionController collisionController;

    public SpawnController(ProgramController programController){
        this.programController = programController;
    }

    public void addEnemy(Enemy enemy){
        programController.addObject(enemy);
        collisionController.registerEnemy(enemy);
    }

    public void addProjectile(Projectile projectile){
        programController.addObject(projectile);
        collisionController.registerProjectile(projectile);
    }

    public void addEffect(Effect effect){
        programController.addObject(effect);
    }

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
