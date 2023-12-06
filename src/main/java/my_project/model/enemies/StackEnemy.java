package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.Stack;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.CollisionController;
import my_project.model.Player;
import my_project.model.Projectile;
import my_project.model.ProjectileType;

import java.awt.*;

public class StackEnemy extends Enemy {
    private Stack<StackEntity> stack = new Stack<>();
    private double currentCooldown = 0;
    private int stackSize;

    public StackEnemy(double x, double y, Player player, CollisionController collisionController, int stackSize) {
        super(x, y, 100, player, collisionController);
        addStackEntities(stackSize);
        this.stackSize = stackSize;
    }

    public void draw(DrawTool drawTool){
        if(!stack.isEmpty()){
            drawTool.setCurrentColor(new Color(151, 26, 150));
            drawTool.drawFilledRectangle(x-(stackSize*6+10)/2,y-(stackSize*6+10)/2, stackSize*6+10,stackSize*6+10);
        }
    }

    public void update(double dt){
        if(!stack.isEmpty()) {
            super.move(dt);
            speed = stack.top().speed;
            currentCooldown -= dt;
            if (currentCooldown < 0) {
                spawnBullets();
            }
        }
    }

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

    private void addStackEntities(int stackSize) {
        for (int i = 0; i < stackSize; i++) {
            StackEntity stackEntity = new StackEntity();
            stack.push(stackEntity);
        }
    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        if(!stack.isEmpty()) {
            EnemyNode enemyNode = new EnemyNode(x,y,stackSize*5);
            boolean collides = collidesWithNode(projectile,enemyNode);
            if (collides) {
                spawnBullets();
                stack.pop();
                stackSize -= 1;
            }
            return collides;
        }
        return false;
    }


    private class StackEntity{

        private final int speed;
        private final int amountOfBullets;
        private final ProjectileType type;
        private final double coolDown;
        private final int bulletSpeed;

        public StackEntity(){
            speed = (int)(Math.random()*40+20);
            amountOfBullets = (int)((Math.random()*4+4));
            coolDown = 1;
            type = ProjectileType.Bullet;
            bulletSpeed = 500;
        }
    }

}
