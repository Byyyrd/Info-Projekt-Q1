package my_project.control;

import my_project.model.Player;
import my_project.model.enemies.*;

public class EnemyWaveController {

    private SpawnController spawnController;
    private double timer = 0;
    private int enemyKind = 3;

    public EnemyWaveController(SpawnController spawnController){
        this.spawnController = spawnController;
    }

    public void update(double dt){
        spawnEnemies(dt);
    }

    private void spawnEnemies(double dt){
        timer -= dt;
        if(timer < 0){
            spawnTestEnemies();
            timer = 1;
        }
    }

    private void spawnTestEnemies(){
        Enemy newEnemy;
        if(enemyKind == 0){
            newEnemy = new ListEnemy(Math.random()*870+53,-100,Math.random()*30+30,(int)(Math.random()*9+2), spawnController.getPlayer(), spawnController);
        } else if (enemyKind == 1) {
            newEnemy = new QueueEnemy(Math.random()*870+53,-100,10,Math.random()*60+60,spawnController.getPlayer(),spawnController,(int)(Math.random()*40+30));
        } else if (enemyKind == 2){
            newEnemy = new StackEnemy(Math.random()*870+53,-100,spawnController.getPlayer(),spawnController,(int)(Math.random()*4+2));
        } else {
            newEnemy = new ArrayEnemy(Math.random()*870+53,-100,150,20,spawnController.getPlayer(),spawnController,5);
        }
        spawnController.addEnemy(newEnemy);
        enemyKind++;
        if(enemyKind > 3){
            enemyKind = 0;
        }
    }
}
