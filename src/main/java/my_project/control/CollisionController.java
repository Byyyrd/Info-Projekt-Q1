package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.Effects;
import my_project.model.Player;
import my_project.model.Projectile;
import my_project.model.enemies.Enemy;

public class CollisionController {
    private List<Enemy> enemyList = new List<>();
    private List<Projectile> projectileList = new List<>();
    private List<Effects> effectsList = new List<>();
    private Player player;
    private ViewController viewController;

    public CollisionController(Player player, ViewController viewController){
        this.player = player;
        this.viewController = viewController;
    }

    public void update(){
        checkEnemyCollision();
        checkPlayerCollision();
        checkBullets();
        checkEffects();
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
                Effects effect = projectileList.getContent().onDestroyed();
                if (effect != null) {
                    effectsList.append(effect);
                    viewController.draw(effect);
                }
                viewController.removeDrawable(projectileList.getContent());
                projectileList.remove();
            } else {
                projectileList.next();
            }
        }
    }

    private void checkEffects(){
        effectsList.toFirst();
        while(effectsList.hasAccess()) {
            if (effectsList.getContent().isDestroyed()) {
                viewController.removeDrawable(effectsList.getContent());
                effectsList.remove();
            } else {
                effectsList.next();
            }
        }
    }

    /**
     * Returns list of projectiles which have the property of harmfulCheck
     * @param harmfulCheck which Projectiles are wanted, harmful or !harmful
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
