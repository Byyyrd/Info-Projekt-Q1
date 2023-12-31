package my_project.model.enemies;

import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.SpawnController;
import my_project.model.Player;
import my_project.model.effects.DustParticleEffect;
import my_project.model.projectiles.Projectile;

import java.awt.*;

/**
 * Describes the behavior of an ArrayEnemy
 */
public class ArrayEnemy extends Enemy {
    private EnemyNode[][] enemyNodesArray;
    private double degreesForAllEnemies;
    private double rotationSpeed = 0.03;
    private boolean hasInitialised = false;

    /**
     * Sets all needed values on instantiation and for future use
     *
     * @param x X coordinate of the enemy
     * @param y Y coordinate of the enemy
     * @param speed Speed of the enemy movement
     * @param radius Radius of a single enemy node, represented by a square
     * @param player Player to home towards
     * @param spawnController Spawn controller in use
     * @param ArrayLength The width and height of the 2D-Array. The amount of nodes is the length squared
     */
    public ArrayEnemy(double x, double y, double speed, double radius, Player player, SpawnController spawnController, int ArrayLength) {
        super(x, y, speed, player, spawnController);
        this.radius = radius;
        enemyNodesArray = new EnemyNode[ArrayLength][ArrayLength];
        updateNodes();
        hasInitialised = true;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawNodes(drawTool);
    }

    @Override
    public void update(double dt) {
        moveNodes(dt);
    }

    /**
     * Updates the position of the EnemyNodes according to x and y.
     * This function is used by drawNodes() to draw the nodes properly.
     * If the enemy node is null, and it is not giving the nodes their values it is kept null
     */
    private void updateNodes(){
        double xOrigin =  x + -(enemyNodesArray.length-1) * radius;
        double yOrigin =  y + -(enemyNodesArray.length-1) * radius;
        for (int i = 0; i < enemyNodesArray.length; i++) {
            for (int j = 0; j < enemyNodesArray[i].length ; j++) {
                if(hasInitialised && enemyNodesArray[i][j]==null){
                    continue;
                }
                double nY = yOrigin + i * radius * 2;
                double nX = xOrigin + radius * 2 * j;
                enemyNodesArray[i][j] = new EnemyNode(nX, nY,radius);
            }
        }
    }

    /**
     * Draws the Nodes as rectangles amd updates their positions
     *
     * @param drawTool Current draw tool in use
     */
    private void drawNodes(DrawTool drawTool){
        boolean isDead = true;
        for (int i = 0; i < enemyNodesArray.length; i++) {
            for (int j = 0; j < enemyNodesArray[i].length; j++) {
                updateNodes();
                if(enemyNodesArray[i][j] != null){
                    isDead = false;
                    drawTool.setCurrentColor(new Color(255,255,255,255));
                    EnemyNode node = enemyNodesArray[i][j];
                    drawTool.setLineWidth(5);
                    drawTool.drawRectangle(node.getX()-node.getRadius(), node.getY()-node.getRadius(),node.getRadius(),node.getRadius());
                }
            }
        }
        if (isDead) destroyed = true;
    }

    /**
     * Moves the nodes towards the player by the time from the last frame
     *
     * @param dt Time from last frame
     */
    private void moveNodes(double dt){
        double desiredXPos = player.getX();
        double desiredYPos = player.getY();

        boolean isIntersecting = Util.circleToCircleCollision(x,y,radius,desiredXPos,desiredYPos,radius,radius);
        double degrees = Math.atan2(desiredYPos - y, desiredXPos - x);

        if(!isIntersecting) {
            degrees = Util.lerpAngle(degreesForAllEnemies, degrees, rotationSpeed);//+(0.5-rotationSpeed)*Util.isNumberNotZero(i)

            x += Math.cos(degrees) * speed * dt;
            y += Math.sin(degrees) * speed * dt;

            degreesForAllEnemies = degrees;
        }
    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        for (int i = 0; i < enemyNodesArray.length; i++) {
            for (int j = 0; j < enemyNodesArray[i].length; j++) {
                if(enemyNodesArray[i][j] !=null && collidesWithNode(projectile,enemyNodesArray[i][j])){
                    spawnOrbs(enemyNodesArray[i][j].getX(),enemyNodesArray[i][j].getY(),1);
                    spawnController.addEffect(new DustParticleEffect(enemyNodesArray[i][j].getX(),enemyNodesArray[i][j].getY()));
                    enemyNodesArray[i][j] = null;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkCollision(Player player) {
        for (int i = 0; i < enemyNodesArray.length; i++) {
            for (int j = 0; j < enemyNodesArray[i].length; j++) {
                EnemyNode node = enemyNodesArray[i][j];
                if(enemyNodesArray[i][j] != null && Util.circleToCircleCollision(node.getX(),node.getY(),node.getRadius()/2,player.getX(),player.getY(),8,4)){
                    return true;
                }
            }
        }
        return false;
    }
}
