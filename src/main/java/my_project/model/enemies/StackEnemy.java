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

    public StackEnemy(double x, double y, double speed, Player player, CollisionController collisionController, int stackSize) {
        super(x, y, speed, player, collisionController);
        addStackEntities(stackSize);
    }

    public void draw(DrawTool drawTool){
        if(!stack.isEmpty()){
            drawTool.setCurrentColor(new Color(151, 26, 150));
            drawTool.drawFilledRectangle(x,y, 20,20);
        }
    }

    public void update(double dt){
        currentCooldown -= dt;
        if(currentCooldown < 0 && !stack.isEmpty()) {
            spawnBullets();
        }
        move(dt);
    }

    public void move(double dt){
        super.move(dt);
    }

    private void spawnBullets() {
        if (!stack.isEmpty()) {
            for (int i = 0; i < stack.top().amountOfBullets; i++) {
                double degrees = Math.atan2(player.getY() - y, player.getX() - x);
                super.spawnBullet(x + 20,y + 20, degrees, stack.top().bulletSpeed, stack.top().type);
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
            boolean collides = Util.rectToRectCollision(x, y, 20, 20, projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight());
            if (collides) {
                spawnBullets();
                stack.pop();
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
            speed =(int)(Math.random()*40+20);
            amountOfBullets = (int)(Math.random()*5+5);
            coolDown = 1;
            type = ProjectileType.Arrow;
            bulletSpeed = 2000;
        }
    }

}
