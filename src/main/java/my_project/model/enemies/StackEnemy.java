package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.Stack;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.model.Player;
import my_project.model.Projectile;
import my_project.model.ProjectileType;

public class StackEnemy extends Enemy {
    private Stack<StackEntity> stack = new Stack<>();

    public StackEnemy(double x, double y, double speed, Player player) {
        super(x, y, speed, player);
    }

    public void draw(DrawTool drawTool){

    }

    public void update(double dt){

    }

    public void move(double dt){
        super.move(dt);
    }

    public void spawnBullets(){
        double degrees = Math.atan2(player.getY()-y,player.getX()-x);
        super.spawnBullet(x,y,degrees, stack.top().bulletSpeed,stack.top().type);
    }

    public void drawStackEnemy(DrawTool drawTool){
        drawTool.drawFilledRectangle(x,y, 20,20);
    }

    public void addStackEntities(int stackSize) {
        if (!stack.isEmpty()) {
            for (int i = 0; i < stackSize; i++) {
                StackEntity stackEntity = new StackEntity();
                stack.push(stackEntity);
            }
        }
    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        boolean collides = Util.rectToRectCollision(x, y, 20, 20, projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight());
        if(collides){
            spawnBullets();
            stack.pop();
        }
        return collides;
    }


    private class StackEntity{

        private final int speed;
        private final int amountOfBullets;
        private final ProjectileType type;
        private final double coolDown;
        private final int bulletSpeed;

        public StackEntity(){
            speed =(int)(Math.random()*40+20);
            amountOfBullets = (int)(Math.random()*10+10);
            coolDown = 1;
            type = ProjectileType.Arrow;
            bulletSpeed = 500;
        }
    }

}
