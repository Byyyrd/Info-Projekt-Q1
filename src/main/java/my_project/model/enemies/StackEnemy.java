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
            drawTool.setCurrentColor(new Color(188, 74, 155,255-stackSize*40));
            drawTool.drawFilledRectangle(x-(stackSize*6+10)/2,y-(stackSize*6+10)/2, stackSize*6+10,stackSize*6+10);
        }
    }

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
            EnemyNode enemyNode = new EnemyNode(x,y,stackSize * 10+5);
            boolean collides = collidesWithNode(projectile,enemyNode);
            if (collides) {
                spawnBullets();
                stack.pop();
                stackSize -= 1;
            }
            if(stack.isEmpty()) destroyed = true;
            return collides;
        }
        return false;
    }

    @Override
    public boolean checkCollision(Player player) {
        if(!stack.isEmpty()) {
            return Util.circleToCircleCollision(x,y,(stackSize*6+10)/2,player.getX(),player.getY(),8,0);
        }
        return false;
    }

    @Override
    public Effect onDestroyed() {
        return new DustParticleEffect(x,y,50,60,10,new Color(188, 74, 155));
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
