package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.Stack;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.CollisionController;
import my_project.model.Player;
import my_project.model.effects.DustParticleEffect;
import my_project.model.effects.Effect;
import my_project.model.projectiles.Projectile;
import my_project.model.projectiles.ProjectileType;

import java.awt.*;



/**
 * Describes the behavior of a StackEnemy
 *
 * @author Maksym und Maxim
 * @version 1.3
 */
public class StackEnemy extends Enemy {
    private Stack<StackEntity> stack = new Stack<>();
    private double currentCooldown = 0;
    private int stackSize;

    /**
     * @param x postion of the StackEnemy (top left edge)
     * @param y postion of the StackEnemy (top left edge)
     * @param player eine Player Referenz, wird aus der Enemy-Klasse gererbt
     * @param collisionController eine collisionController Referenz, wird aus der Enemy-Klasse gererbt
     * @param stackSize number of added StackEntities in stack-Stack
     */
    public StackEnemy(double x, double y, Player player, CollisionController collisionController, int stackSize) {
        super(x, y, 100, player, collisionController);
        addStackEntities(stackSize);
        this.stackSize = stackSize;
    }

    /**
     * Draws a StackEnemy in shape of a pyramid
     */
    public void draw(DrawTool drawTool){
        if(!stack.isEmpty()){
            for (int i = 0; i < stackSize; i++) {
                drawTool.setCurrentColor(new Color(188, 74, 155,150));
                drawTool.drawFilledRectangle(x-(i*6+10)/2,y-(i*6+10)/2, i*6+10,i*6+10);
                drawTool.setCurrentColor(new Color(0, 0, 0,150));
                drawTool.drawRectangle((x-(i*6+10)/2)-1,(y-(i*6+10)/2)-1, i*6+12,i*6+12);
            }
        }
    }

    /**
     * Movement of the StackEnemy and creation of new Bullets
     *
     * The speed is depended on the distance between the Player and the stackEnemy
     *
     * Creats new Bullets depended on the coolDown (coolDown is depended on the BulletType)
     */

    public void update(double dt){
        if(!stack.isEmpty()) {
            double speedMultiplier = 1;
            if(Math.sqrt(Math.pow(player.getX()-x,2)+Math.pow(player.getY()-y,2)) < 1000)
                speedMultiplier = Math.sqrt(Math.pow(player.getX()-x,2)+Math.pow(player.getY()-y,2))*0.01;
            super.move((dt*speedMultiplier));
            speed = stack.top().speed;
            currentCooldown -= dt;
            if (currentCooldown < 0) {
                spawnBullets();
            }
        }
    }

    /**
     * Calculates the degree of each created Bullet around the StackEnemy
     *
     * Creates new Bullets
     *
     * Sets currentCooldown on the coolDown of the StackEntity at the top of the stack-Stack
     */

    private void spawnBullets() {
        if (!stack.isEmpty()) {
            double spreadDegree = (Math.PI + 2) / (stack.top().amountOfBullets - 1);
            for (int i = 0; i < stack.top().amountOfBullets; i++) {
                double degrees = Math.atan2(player.getY() - y, player.getX() - x);
                double relativeDegree = i * spreadDegree;
                super.spawnBullet(x, y, degrees + relativeDegree, stack.top().bulletSpeed, stack.top().type);
                currentCooldown = stack.top().coolDown;
            }
        }
    }

    /**
     * Adds a number of StackEntities in the stack-Stack
     *
     * @param stackSize number of added StackEntities in the stack-Stack
     */

    private void addStackEntities(int stackSize) {
        for (int i = 0; i < stackSize; i++) {
            StackEntity stackEntity = new StackEntity();
            stack.push(stackEntity);
        }
    }

    /**
     * Checks whether an EnemyNode and a Projectile collide
     * If true sets the projectile to the StackEnemy position, spawns new Bullets, removes one StackEntity from the stack-Stack and decreases the stackSize by 1
     *
     * If the stack-Stack is Empty set destroyed on true
     *
     * @return whether Projectile and EnemyNode collide
     */

    @Override
    public boolean checkCollision(Projectile projectile) {
        if(!stack.isEmpty()) {
            EnemyNode enemyNode = new EnemyNode(x,y,stackSize * 10+5);
            boolean collides = collidesWithNode(projectile,enemyNode);
            if (collides) {
                projectile.setX(x);
                projectile.setY(y);
                spawnBullets();
                stack.pop();
                stackSize -= 1;
            }
            if(stack.isEmpty()) destroyed = true;
            return collides;
        }
        return false;
    }

    /**
     * @return whether the StackEnemy and the player collide
     */

    @Override
    public boolean checkCollision(Player player) {
        if(!stack.isEmpty()) {
            return Util.circleToCircleCollision(x,y,(stackSize*6+10)/2,player.getX(),player.getY(),8,0);
        }
        return false;
    }

    /**
     * @return a new DustParticle on the x,y postion of the StackEnemy
     */
    @Override
    public Effect onDestroyed() {
        return new DustParticleEffect(x,y,50,60,10,new Color(188, 74, 155));
    }

    /**
     *  Private class of StackEnemy
     *
     *  Used for a task-related implementation of the StackEnemy
     */
    private class StackEntity{
        private final int speed;
        private final int amountOfBullets;
        private final ProjectileType type;
        private final double coolDown;
        private final int bulletSpeed;

        /**
         * Sets speed on a value between 20 and 59
         *
         * Sets numberOfBullets on a value between 4 and 7
         *
         * Sets Bullet-Type randomly
         * 50% for a normal Bullet
         * 25% for a charged Bullet
         * 25% for a bouncing Bullet
         *
         * Sets the coolDown based on the bulletType
         */

        public StackEntity(){
            speed = (int)(Math.random()*40+20);
            amountOfBullets = (int)((Math.random()*4+4));
            if(Math.random() > 0.5){
                type = ProjectileType.Bullet;
            } else if(Math.random() > 0.5) {
                type = ProjectileType.ChargeBullet;
            } else {
                type = ProjectileType.BounceBullet;
            }
            switch (type){
                case Bullet -> coolDown = 2;
                case BounceBullet -> coolDown = 4;
                case ChargeBullet -> coolDown = 5;
                default -> coolDown = 1;
            }
            bulletSpeed = 500;
        }
    }
}
