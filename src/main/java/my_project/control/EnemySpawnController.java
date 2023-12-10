package my_project.control;

import my_project.model.Player;
import my_project.model.enemies.*;

public class EnemySpawnController {

    private CollisionController collisionController;
    private ProgramController programController;
    private Player player;
    private double timer = 0;
    private int enemyKind = 0;

    public EnemySpawnController(ProgramController programController, CollisionController collisionController, Player player){
        this.programController = programController;
        this.collisionController = collisionController;
        this.player = player;
    }

    public void update(double dt){
        spawnEnemies(dt);
    }
    private void spawnEnemies(double dt){
        timer -= dt;
        if(timer < 0){
            spawnTestEnemies();
            timer = 10;
        }
    }

    private void spawnTestEnemies(){
        Enemy newEnemy;
        if(enemyKind == 0){
            newEnemy = new ListEnemy(Math.random()*870+53,-100,Math.random()*30+30,(int)(Math.random()*9+2), player, collisionController);
        } else if (enemyKind == 1) {
            newEnemy = new QueueEnemy(Math.random()*870+53,-100,10,Math.random()*60+60,player,collisionController,(int)(Math.random()*40+30));
        } else {
            newEnemy = new StackEnemy(Math.random()*870+53,-100,player,collisionController,(int)(Math.random()*4+2));
        }
        programController.addObject(newEnemy);
        collisionController.addEnemy(newEnemy);
        enemyKind++;
        if(enemyKind > 2){
            enemyKind = 0;
        }
    }
}
