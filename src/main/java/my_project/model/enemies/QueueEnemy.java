package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.Queue;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.model.Player;

public class QueueEnemy extends Enemy {
    private Queue<EnemyNode> queue = new Queue<>();
    private double timer = 5;

    private double rotationSpeed = 0.03;

    public QueueEnemy(double x, double y,double radius, double speed, Player player, int startNodeAmount) {
        super(x, y, speed, player);
        this.radius = radius;
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
        timer -= dt;
        if(timer < 0){
            //addEnemyNode();
            timer = 5;
        }
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
        for (int i = 0; i < Util.countQueue(queue); i++) {
            drawTool.drawFilledCircle(queue.front().getX(),queue.front().getY(),queue.front().getRadius());
            queue.enqueue(queue.front());
            queue.dequeue();
        }
    }

    /**
     * moves compartments of the queue
     * @param dt
     */
    private void moveNodes(double dt){
        double desiredXPos = player.getX();
        double desiredYPos = player.getY();

        for (int i = 0; i < Util.countQueue(queue); i++) {
            if(i == 0){
                double degrees = Math.atan2(desiredYPos-queue.front().getY(),desiredXPos-queue.front().getX());
                degrees = Util.lerpAngle(queue.front().getDegrees(),degrees,rotationSpeed);
                queue.front().moveByAngle(dt,degrees,speed);
                queue.front().setDegrees(degrees);
            } else if(!Util.circleToCircleCollision(queue.front().getX(),queue.front().getY(),queue.front().getRadius(),desiredXPos,desiredYPos,queue.front().getRadius(),queue.front().getRadius())){
                double degrees = Math.atan2(desiredYPos-queue.front().getY(),desiredXPos-queue.front().getX());
                degrees = Util.lerpAngle(queue.front().getDegrees(),degrees,0.5);
                queue.front().moveByAngle(dt,degrees,speed);
                queue.front().setDegrees(degrees);
            }

            desiredXPos = queue.front().getX();
            desiredYPos = queue.front().getY();
            queue.enqueue(queue.front());
            queue.dequeue();
        }
    }

}
