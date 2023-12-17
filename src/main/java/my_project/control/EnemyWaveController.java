package my_project.control;

import my_project.model.enemies.*;

/**
 * The EnemyWaveController controls when and what kind of enemies should be spawned
 */
public class EnemyWaveController {

    private SpawnController spawnController;
    private double timer = 1;
    private double simpleTimer = 1;
    private int enemyKind = 4;

    /**
     * Sets the spawn controller so enemies can actually spawn
     *
     * @param spawnController Current spawn controller
     */
    public EnemyWaveController(SpawnController spawnController){
        this.spawnController = spawnController;
    }

    public void update(double dt){
        spawnEnemies(dt);
    }

    private void spawnEnemies(double dt){
        timer -= dt;
        simpleTimer -= dt;
        if(timer < 0){
            //spawnTestEnemies();
            timer = 7;
        }
        if(simpleTimer < 0){
            double degrees = (Math.random() - 0.5) * 2 * Math.PI;
            double xPos = 1200 * Math.cos(degrees);
            double yPos = 1000 * Math.sin(degrees);
            //spawnController.addEnemy(new SimpleEnemy(xPos,yPos,Math.random()*50+100,spawnController.getPlayer(),spawnController));
            simpleTimer = 2;
        }
    }

    private void spawnTestEnemies(){
        Enemy newEnemy;
        double degrees = (Math.random() - 0.5) * 2 * Math.PI;
        double xPos = 1200 * Math.cos(degrees);
        double yPos = 1000 * Math.sin(degrees);
        if(enemyKind == 0) {
            newEnemy = new ListEnemy(xPos,yPos,Math.random()*30+100,(int)(Math.random()*9+2), spawnController.getPlayer(), spawnController);
        } else if (enemyKind == 1) {
            newEnemy = new QueueEnemy(xPos,yPos,10,Math.random()*60+200,spawnController.getPlayer(),spawnController,(int)(Math.random()*40+30));
        } else if (enemyKind == 2){
            newEnemy = new StackEnemy(xPos,yPos,spawnController.getPlayer(),spawnController,(int)(Math.random()*4+2));
        } else if (enemyKind == 3) {
            newEnemy = new ArrayEnemy(xPos,yPos,150,20,spawnController.getPlayer(),spawnController,(int)(Math.random()*5+2));
        } else {
            newEnemy = new Summoner(xPos, yPos, 100, spawnController.getPlayer(), spawnController, 10);
        }
        spawnController.addEnemy(newEnemy);
        enemyKind++;
        if(enemyKind > 4){
            enemyKind = 0;
        }
    }
}
