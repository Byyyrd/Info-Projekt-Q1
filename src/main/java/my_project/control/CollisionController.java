package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.Effect;
import my_project.model.Player;
import my_project.model.Projectile;
import my_project.model.enemies.Enemy;

public class CollisionController {
    private List<Enemy> enemyList = new List<>();
    private List<Projectile> projectileList = new List<>();
    private Player player;
    private ViewController viewController;
    private EffectController effectController;

    public CollisionController(Player player, ViewController viewController){
        this.player = player;
        this.viewController = viewController;
        effectController = new EffectController(viewController);
    }

    public void update(){
        checkEnemyCollision();
        checkPlayerCollision();
        checkBullets();
        effectController.update();
    }

    public void addEnemy(Enemy enemy){
        enemyList.append(enemy);
        viewController.draw(enemy);
    }

    public void addProjectile(Projectile projectile){
        projectileList.append(projectile);
        viewController.draw(projectile);
    }

    private void checkEnemyCollision(){

    }

    private void checkPlayerCollision(){
        List<Projectile> list = getWantedProjectiles(true);
        list.toFirst();
        while(list.hasAccess()){
            if(player.collidesWith(list.getContent())) {
                player.takeDamage();
                list.getContent().setDestroyed(true);
            }
            list.next();
        }
    }

    /**
     * removes bullets from list that are supposed to be removed
     */
    private void checkBullets(){
        projectileList.toFirst();
        while(projectileList.hasAccess()) {
            if (projectileList.getContent().isDestroyed()) {
                Effect effect = projectileList.getContent().onDestroyed();
                if (effect != null) {
                    effectController.add(effect);
                    viewController.draw(effect);
                }
                viewController.removeDrawable(projectileList.getContent());
                projectileList.remove();
            } else {
                projectileList.next();
            }
        }
    }

    /**
     * Returns list of projectiles which have the property of harmfulCheck
     * @param harmfulCheck which Projectiles are wanted, harmful or not harmful
     * @return list
     */
    private List<Projectile> getWantedProjectiles(boolean harmfulCheck){
        List<Projectile> list = new List<>();
        while(projectileList.hasAccess()){
            projectileList.toFirst();
            if(projectileList.getContent().isHarmful() == harmfulCheck){
                list.append(projectileList.getContent());
            }
            projectileList.next();
        }
        return list;
    }
}
