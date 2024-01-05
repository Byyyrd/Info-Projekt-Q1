package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.Queue;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.SpawnController;
import my_project.model.effects.DustParticleEffect;
import my_project.model.projectiles.Arrow;
import my_project.model.Player;
import my_project.model.projectiles.Projectile;

import java.awt.*;

/**
 * Describes the behavior of a QueueEnemy
 */
public class QueueEnemy extends Enemy {
    private Queue<EnemyNode> queue = new Queue<>();
    private final double rotationSpeed = 0.03;
    private int countQueue = 0;
    private int maxCount;
    private double regenerationTimer;


    /**
     * Sets initial values for the enemy
     *
     * @param x X coordinate of the front of the enemy
     * @param y Y coordinate of the front of the enemy
     * @param radius Radius of the individual nodes
     * @param speed Speed of the front of the enemy
     * @param player Player in use
     * @param spawnController Spawn controller in use
     * @param startNodeAmount Amount of nodes at the instantiation of the enemy
     */
    public QueueEnemy(double x, double y, double radius, double speed, Player player, SpawnController spawnController, int startNodeAmount) {
        super(x, y, speed, player, spawnController);
        this.radius = radius;
        maxCount = startNodeAmount;
        for (int i = 0; i < startNodeAmount; i++) {
            addEnemyNode();
        }
    }
    @Override
    public void draw(DrawTool drawTool) {
        drawNodes(drawTool);
    }
    @Override
    public void update(double dt) {
        moveNodes(dt);
        if(regenerate(dt) && countQueue!=maxCount) addEnemyNode();
    }

    /**
     * Adds a new node to the end of the queue
     * and so has to add to countQueue
     */
    private void addEnemyNode(){
        if(queue.isEmpty()){
            queue.enqueue(new EnemyNode(x,y,radius));
        } else {
            EnemyNode tail = Util.getTailContent(queue);
            queue.enqueue(new EnemyNode(tail.getX(), tail.getY(), radius));
        }
        countQueue++;
    }

    /**
     * Draws the whole queue
     *
     * @param drawTool Current draw tool in use
     */
    private void drawNodes(DrawTool drawTool){
        drawTool.setCurrentColor(new Color(0x14A02E));
        for (int i = 0; i < Util.countQueue(queue); i++) {
            drawTool.drawFilledCircle(queue.front().getX(),queue.front().getY(),queue.front().getRadius());
            queue.enqueue(queue.front());
            queue.dequeue();
        }
    }

    /**
     * We first define where our desired Position is. Then we go through every node of the queue and depending on whether the node is the first or not we lerp in an angle to the
     * @param dt deltaTime
     */
    private void moveNodes(double dt){
        double desiredXPos = player.getX();
        double desiredYPos = player.getY();

        for (int i = 0; i < countQueue; i++) {
            
            EnemyNode node = queue.front();

            boolean isIntersecting = Util.circleToCircleCollision(node.getX(),node.getY(),node.getRadius(),desiredXPos,desiredYPos,node.getRadius(),node.getRadius());
            double degrees = Math.atan2(desiredYPos-node.getY(),desiredXPos-node.getX());

            if(!isIntersecting){
                degrees = Util.lerpAngle(node.getDegrees(),degrees,rotationSpeed+(0.5-rotationSpeed)*Util.isNumberNotZero(i));
                node.moveByAngle(dt,degrees,speed);
                node.setDegrees(degrees);
            }

            desiredXPos = node.getX();
            desiredYPos = node.getY();
            queue.enqueue(node);
            queue.dequeue();
        }
    }


    @Override
    public boolean checkCollision(Projectile projectile) {
        boolean gotHit = false;
        for (int i = 0; i < Util.countQueue(queue); i++) {
            EnemyNode node = queue.front();

            if (collidesWithNode(projectile,node)) {
                gotHit = true;
            }

            queue.enqueue(node);
            queue.dequeue();
        }

        if(gotHit){
            if(projectile instanceof Arrow){
                Arrow arrow = (Arrow) projectile;
                getHit(arrow.isStrong());
            } else {
                getHit(false);
            }
            if(queue.isEmpty()) destroyed = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean checkCollision(Player player) {
        boolean gotHit = false;
        for (int i = 0; i < Util.countQueue(queue); i++) {
            EnemyNode node = queue.front();
            if (Util.circleToCircleCollision(node.getX(),node.getY(),node.getRadius(),player.getX(),player.getY(),8,0)) {
                gotHit = true;
            }
            queue.enqueue(node);
            queue.dequeue();
        }
        return gotHit;
    }

    /**
     * Dequeues nodes and so lowers queueCount, if a collision happens
     * additionally Puts regeneration on a cooldown
     *
     * @param hard Whether a lot of nodes or just one node should be dequeued
     */
    private void getHit(boolean hard){
        EnemyNode node = queue.front();
        if(node != null)
            spawnController.addEffect(new DustParticleEffect(node.getX(),node.getY(),15,30,10,Color.green));
        if(hard){
            for (int i = 0; i < 10; i++) {
                if(queue.isEmpty())
                    return;
                spawnOrbs(queue.front().getX(),queue.front().getY(),2);
                queue.dequeue();
                countQueue--;
            }
        } else {
            queue.dequeue();
            countQueue--;
        }
        regenerationTimer = 1;
    }

    /**
     *
     * Is responsible for lowering the regenerationTimer
     * as well as returns a boolean if regeneration should happen.
     * Time between regeneration is set at default at 0.1,
     * the time is increased if the QueueEnemy gets hit.
     *
     * @param dt time interval between two frames
     * @return whether Regen happens or not
     */
    private boolean regenerate(double dt){
        regenerationTimer-=1*dt;
        if(regenerationTimer<0){
            regenerationTimer = 0.1;
            return true;
        }
        return false;
    }

}
