package my_project.control;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.Effect;
import my_project.model.Player;
import my_project.model.projectiles.Projectile;
import my_project.model.enemies.Enemy;

public class CollisionController {
    private List<Enemy> enemyList = new List<>();
    private List<Projectile> projectileList = new List<>();
    private Player player;
    private ProgramController programController;
    private EffectController effectController;

    public CollisionController(Player player, ProgramController programController){
        this.player = player;
        this.programController = programController;
        effectController = new EffectController(programController);
    }

    public void update(){
        checkEnemyCollision();
        checkPlayerCollision();
        checkBullets();
        effectController.update();
    }

    public void addEnemy(Enemy enemy){
        enemyList.append(enemy);
        programController.addObject(enemy);
    }

    public void addProjectile(Projectile projectile){
        projectileList.append(projectile);
        programController.addObject(projectile);
    }

    private void checkEnemyCollision(){
        List<Projectile> projectileList = getWantedProjectiles(false);
        projectileList.toFirst();
        while(projectileList.hasAccess()){
            enemyList.toFirst();
            while (enemyList.hasAccess()){
                if(enemyList.getContent().checkCollision(projectileList.getContent())) {
                    projectileList.getContent().setDestroyed(true);
                }
                enemyList.next();
            }
            projectileList.next();
        }
    }

    private void checkPlayerCollision(){
        List<Projectile> list = getWantedProjectiles(true);
        list.toFirst();
        while(list.hasAccess()){
            if(list.getContent().checkCollision(player)) {
                player.takeDamage();
                break;
            }
            list.next();
        }
        enemyList.toFirst();
        while(enemyList.hasAccess()){
            if(enemyList.getContent().checkCollision(player)) {
                player.takeDamage();
                break;
            }
            enemyList.next();
        }
    }

    /**
     * removes bullets from list that are supposed to be removed
     */
    private void checkBullets(){
        projectileList.toFirst();
        while(projectileList.hasAccess()) {
            if (projectileList.getContent().isDestroyed()) {
                addEffect(projectileList.getContent().onDestroyed());
                programController.removeObject(projectileList.getContent());
                projectileList.remove();
            } else {
                projectileList.next();
            }
        }
    }

    public void addEffect(Effect effect){
        if (effect != null) {
            effectController.add(effect);
            programController.addObject(effect);
        }
    }
    /**
     * Returns list of projectiles which have the property of harmfulCheck
     * @param harmfulCheck which Projectiles are wanted, harmful to player or not
     * @return list
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
