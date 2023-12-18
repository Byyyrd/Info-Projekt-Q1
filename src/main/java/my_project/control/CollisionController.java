package my_project.control;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.Util;
import my_project.model.Background;
import my_project.model.effects.Effect;
import my_project.model.Player;
import my_project.model.projectiles.Projectile;
import my_project.model.enemies.Enemy;

/**
 * The CollisionController class checks for collision by passing information to enemies and projectiles and letting them calculate collision.
 * It also checks if enemies and projectiles have to be deleted and if yes, does it.
 */
public class CollisionController {
    private List<Enemy> enemyList = new List<>();
    private List<Projectile> projectileList = new List<>();
    private PlayerController playerController;
    private SpawnController spawnController;
    private EffectController effectController;

    /**
     * Registers all needed objects for future collision checks
     *
     * @param playerController Player controller that is currently in use
     * @param spawnController Spawn controller for registering effects and removing objects
     * @param background Current background to give it to the effect controller
     */
    public CollisionController(PlayerController playerController, SpawnController spawnController, Background background){
        this.playerController = playerController;
        this.spawnController = spawnController;
        effectController = new EffectController(spawnController,background);
    }

    /**
     * Updates all checks for collision
     */
    public void update(){
        checkEnemyCollision();
        checkPlayerCollision();
        checkProjectiles();
        effectController.update();
    }

    /**
     * Registers a given enemy, so collision can be detected in the future.
     * It also makes the EffectController change the background
     *
     * @param enemy The to be registered enemy
     */
    public void registerEnemy(Enemy enemy){
        enemyList.append(enemy);
        effectController.setBackgroundIntensity(Util.countList(enemyList));
    }

    /**
     * Registers a given projectile, so collision can be detected in the future
     *
     * @param projectile The to be registered projectile
     */
    public void registerProjectile(Projectile projectile){
        projectileList.append(projectile);
    }

    /**
     * Passes a given effect onto the effect controller and draws it
     *
     * @param effect The to be registered effect
     */
    public void addEffect(Effect effect){
        effectController.add(effect);
    }

    public Player getPlayer(){
        return playerController.getPlayer();
    }

    /**
     * Checks for collisions between all non-harmful projectiles and enemies and removes all the enemies that are supposed to be removed from the enemy list
     */
    private void checkEnemyCollision(){
        List<Projectile> projectileList = getWantedProjectiles(false);
        projectileList.toFirst();
        while(projectileList.hasAccess()){
            enemyList.toFirst();
            while (enemyList.hasAccess()){
                if(enemyList.getContent().checkCollision(projectileList.getContent())) {
                    projectileList.getContent().setDestroyed(true);
                }
                if (enemyList.getContent().isDestroyed()) {
                    spawnController.addEffect(enemyList.getContent().onDestroyed());
                    spawnController.removeObject(enemyList.getContent());
                    enemyList.remove();
                } else {
                    enemyList.next();
                }
            }
            projectileList.next();
        }
    }

    /**
     * Checks for collisions between all harmful projectiles and the player and all the enemies and the player.
     * If collision accuses, the player takes damage
     */
    private void checkPlayerCollision(){
        List<Projectile> list = getWantedProjectiles(true);
        list.toFirst();
        while(list.hasAccess()){
            if(list.getContent().checkCollision(playerController.getPlayer())) {
                if(playerController.playerTakeDamage()){
                    if(list.getContent() != null)
                        list.getContent().setDestroyed(true);
                    list.remove();
                }
                return;
            }
            list.next();
        }
        enemyList.toFirst();
        while(enemyList.hasAccess()){
            if(enemyList.getContent().checkCollision(playerController.getPlayer())) {
                playerController.playerTakeDamage();
                return;
            }
            enemyList.next();
        }
    }

    /**
     * Removes all the projectiles that are supposed to be removed from the projectile list
     */
    private void checkProjectiles(){
        projectileList.toFirst();
        while(projectileList.hasAccess()) {
            if (projectileList.getContent().isDestroyed()) {
                spawnController.addEffect(projectileList.getContent().onDestroyed());
                spawnController.removeObject(projectileList.getContent());
                projectileList.remove();
            } else {
                projectileList.next();
            }
        }
    }

    /**
     * Returns a list of all projectiles that have the property of harmfulCheck
     *
     * @param harmfulCheck Which projectiles are wanted, harmful to player or not
     * @return List of all needed projectiles
     */
    private List<Projectile> getWantedProjectiles(boolean harmfulCheck){
        List<Projectile> list = new List<>();
        projectileList.toFirst();
        while(projectileList.hasAccess()){
            if(projectileList.getContent().isHarmful() == harmfulCheck){
                list.append(projectileList.getContent());
            }
            projectileList.next();
        }
        return list;
    }
}
