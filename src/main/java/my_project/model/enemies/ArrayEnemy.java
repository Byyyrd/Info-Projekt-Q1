package my_project.model.enemies;

import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.CollisionController;
import my_project.model.Player;
import my_project.model.projectiles.Projectile;

public class ArrayEnemy extends Enemy {
    private EnemyNode[][] enemyNodesArray;
    private double emergenceDegrees;
    private double rotationSpeed = 0.03;
    private boolean hasInitialised = false;
    public ArrayEnemy(double x, double y, double speed, double radius, Player player, CollisionController collisionController, int ArrayLength) {
        super(x, y, speed, player, collisionController);
        this.radius = radius;
        enemyNodesArray = new EnemyNode[ArrayLength][ArrayLength];
        updateNodes();
        hasInitialised=true;

    }

    @Override
    public void draw(DrawTool drawTool) {
        drawNodes(drawTool);
    }

    @Override
    public void update(double dt) {
        moveNodes(dt);
    }

    private void updateNodes(){
        double xOrigin =  x + -(enemyNodesArray.length-1) * radius;
        double yOrigin =  y + -(enemyNodesArray.length-1) * radius;
        for (int i = 0; i < enemyNodesArray.length; i++) {
            for (int j = 0; j < enemyNodesArray[i].length ; j++) {
                if(hasInitialised&&enemyNodesArray[i][j]==null){
                    continue;
                }
                double nY = yOrigin + i * radius * 2;
                double nX = xOrigin + radius * 2 * j;
                enemyNodesArray[i][j] = new EnemyNode(nX, nY,radius);
            }
        }
    }
    private void drawNodes(DrawTool drawTool){
        for (int i = 0; i < enemyNodesArray.length; i++) {
            for (int j = 0; j < enemyNodesArray[i].length; j++) {
                updateNodes();
                if(enemyNodesArray[i][j]!=null)drawTool.drawFilledCircle(enemyNodesArray[i][j].getX(), enemyNodesArray[i][j].getY(), enemyNodesArray[i][j].getRadius());
            }
        }
    }
    private void moveNodes(double dt){
        double desiredXPos = player.getX();
        double desiredYPos = player.getY();

        boolean isIntersecting = Util.circleToCircleCollision(x,y,radius,desiredXPos,desiredYPos,radius,radius);
        double degrees = Math.atan2(desiredYPos - y, desiredXPos - x);

        if(!isIntersecting) {
            degrees = Util.lerpAngle(emergenceDegrees, degrees, rotationSpeed);//+(0.5-rotationSpeed)*Util.isNumberNotZero(i)

            x += Math.cos(degrees) * speed * dt;
            y += Math.sin(degrees) * speed * dt;

            emergenceDegrees = degrees;
        }
    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        for (int i = 0; i < enemyNodesArray.length; i++) {
            for (int j = 0; j < enemyNodesArray[i].length; j++) {
                if(enemyNodesArray[i][j] !=null && collidesWithNode(projectile,enemyNodesArray[i][j])){
                    enemyNodesArray[i][j] = null;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkCollision(Player player) {
        return false;
    }
}
