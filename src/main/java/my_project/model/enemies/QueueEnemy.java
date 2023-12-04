package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.Queue;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.model.Player;

public class QueueEnemy extends Enemy {
    private Queue<EnemyNode> queue = new Queue<>();

    //private double movementRange = Math.PI/4;
   // private double movementDegree;

    public QueueEnemy(double x, double y,double radius, double speed, Player player, int startNodeAmount) {
        super(x, y, speed, player);
        this.radius = radius;
        for (int i = 0; i < startNodeAmount; i++) {
            addEnemyNode();
        }
       // movementDegree = Math.atan2(player.getY()-queue.front().getY(),player.getX()-queue.front().getX());
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
     * adds an Node to the Queue
     */
    private void addEnemyNode(){
        if(queue.isEmpty()){
            queue.enqueue(new EnemyNode(x,y,radius));
        }
        EnemyNode tail = Util.getTail(queue);
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
        //TODO: Nodes moven nur in einem winkel von 45
        double desiredXPos = player.getX();//initialPlayerXPos + (player.getX() - initialPlayerXPos)/2;
        double desiredYPos = player.getY();//initialPlayerYPos + (player.getY() - initialPlayerYPos)/2;

        for (int i = 0; i < Util.countQueue(queue); i++) {

            //FÜR JETZT HABEN DIE KREISE DEN GLEICHEN RADIUS
            //System.out.println(Math.sqrt(Math.pow( (desiredXPos-queue.front().getX()) ,2) + Math.pow( (desiredYPos-queue.front().getY()) ,2) ));
            //System.out.println(queue.front().getRadius() > Math.sqrt(Math.pow( (desiredXPos-queue.front().getX()) ,2) + Math.pow( (desiredYPos-queue.front().getY()) ,2) ));
            if(i<1 || !Util.circleToCircleCollision(queue.front().getX(),queue.front().getY(),queue.front().getRadius(),desiredXPos,desiredYPos,queue.front().getRadius(),queue.front().getRadius())){

               // queue.front().move(dt,speed,desiredXPos,desiredYPos);
                double degrees = Math.atan2(desiredYPos-queue.front().getY(),desiredXPos-queue.front().getX());


                /*if((movementDegree-degrees-movementRange)/(Math.PI/2) == (int)((movementDegree-degrees-movementRange)/(Math.PI/2))){
                    movementDegree -=(movementDegree-degrees);
                }else{
                    movementDegree -= (Math.signum(movementDegree-degrees)*movementRange);
                }*/




                queue.front().moveByAngle(dt,degrees,speed);
            }

            desiredXPos = queue.front().getX();
            desiredYPos = queue.front().getY();
            queue.enqueue(queue.front());
            queue.dequeue();
        }
    }

}
