package my_project.control;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.Player;
import my_project.model.Projectile;
import my_project.model.enemies.Enemy;

public class CollisionController {
    private List<Enemy> enemyList = new List();
    private List<Projectile> projectileList = new List();
    private Player player;

    public CollisionController(Player player){
        this.player = player;
    }

    public void addEnemy(Enemy enemy){
        enemyList.append(enemy);
    }

    public void addProjectile(Projectile projectile){
        projectileList.append(projectile);
    }

    private void checkEnemyCollision(){

    }

    private void checkPlayerCollision(){

    }
}
