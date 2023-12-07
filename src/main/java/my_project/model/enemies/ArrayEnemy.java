package my_project.model.enemies;

import KAGO_framework.view.DrawTool;
import my_project.control.CollisionController;
import my_project.model.Player;
import my_project.model.Projectile;

public class ArrayEnemy extends Enemy {
    private EnemyNode[][] enemyNodesArray;
    public ArrayEnemy(double x, double y, double speed, double radius, Player player, CollisionController collisionController, int ArrayLength) {
        super(x, y, speed, player, collisionController);
        this.radius = radius;
        enemyNodesArray = new EnemyNode[ArrayLength][ArrayLength];
        initialiseNodes();
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawNodes(drawTool);
    }

    @Override
    public void update(double dt) {

    }

    private void initialiseNodes(){
        double xOrigin =  -enemyNodesArray.length * radius;
        double yOrigin =  -enemyNodesArray.length * radius;
        for (int i = 0; i < enemyNodesArray.length; i++) {
            for (int j = 0; j < enemyNodesArray[i].length ; j++) {
                double nY = yOrigin - i * radius * 2;
                double nX = xOrigin + radius * 2 * j;
                enemyNodesArray[i][j] = new EnemyNode(nX, nY,radius);
            }
        }
    }
    private void drawNodes(DrawTool drawTool){
        for (int i = 0; i < enemyNodesArray.length; i++) {
            for (int j = 0; j < enemyNodesArray[i].length; j++) {
                drawTool.drawFilledCircle(enemyNodesArray[i][j].getX(),enemyNodesArray[i][j].getY(),enemyNodesArray[i][j].getRadius());
            }
        }
    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        return false;
    }

    @Override
    public boolean checkCollision(Player player) {
        return false;
    }
}
