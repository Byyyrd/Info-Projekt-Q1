package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.Queue;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.CollisionController;
import my_project.model.Arrow;
import my_project.model.Player;
import my_project.model.Projectile;

public class QueueEnemy extends Enemy {
    private Queue<EnemyNode> queue = new Queue<>();
    private final double rotationSpeed = 0.03;

    public QueueEnemy(double x, double y, double radius, double speed, Player player, CollisionController collisionController, int startNodeAmount) {
        super(x, y, speed, player, collisionController);
        this.radius = radius;
        setNewImage("src/main/resources/graphic/visor.png");
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
            projectile.setX(queue.front().getX());
            projectile.setY(queue.front().getY());
            if(projectile.getClass().isNestmateOf(Arrow.class)){
                Arrow arrow = (Arrow) projectile;
                getHit(arrow.isStrong());
            } else {
                getHit(false);
            }
            return true;
        }
        return false;
    }

    /**
     * adds a Node to the Queue
     */
    private void addEnemyNode(){
        if(queue.isEmpty()){
            queue.enqueue(new EnemyNode(x,y,radius));
        }
        EnemyNode tail = Util.getTailContent(queue);
        queue.enqueue(new EnemyNode(tail.getX(),tail.getY(),radius));
    }

    /**
     * Draws the whole queue
     * @param drawTool the Object that draws the Enemy
     */
    private void drawNodes(DrawTool drawTool){
        drawTool.setCurrentColor(255,255,255,255);
        for (int i = 0; i < Util.countQueue(queue); i++) {
            drawTool.drawImage(getMyImage(),queue.front().getX()-queue.front().getRadius(),queue.front().getY()-queue.front().getRadius());
            queue.enqueue(queue.front());
            queue.dequeue();
        }
    }

    /**
     * We first define where our desired Position is. Then we go throgh every node of the queue and depending on wheter the node is the first or not we lerp in an angle to the
     * @param dt deltaTime
     */
    private void moveNodes(double dt){
        double desiredXPos = player.getX();
        double desiredYPos = player.getY();

        for (int i = 0; i < Util.countQueue(queue); i++) {
            
            EnemyNode node = queue.front();

            boolean shouldShoot = Math.sqrt(Math.pow( (desiredXPos-node.getX()) ,2) + Math.pow( (desiredYPos-node.getY()) ,2) ) < 250;
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

    private void getHit(boolean hard){
        if(hard){
            for (int i = 0; i < 10; i++) {
                queue.dequeue();
            }
        } else {
            queue.dequeue();
        }
    }
}
