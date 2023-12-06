package my_project.model.enemies;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.model.Player;
import my_project.model.Projectile;

import java.awt.*;

public class ListEnemy extends Enemy {
    private List<ListEnemyNode> list = new List<>();
    private double rotation;
    private double distanceFromCenter;
    private double currentChangeTimer;
    private final double currentChangeSpeed = 5;
    public ListEnemy(double x, double y, double speed, Player player) {
        super(x, y, speed, player);
        radius = 16;
        distanceFromCenter = 32;
        list.append(new ListEnemyNode(x,y,radius,Color.gray));
        list.append(new ListEnemyNode(x,y,radius,Color.green));
        list.append(new ListEnemyNode(x,y,radius,Color.black));
        list.append(new ListEnemyNode(x,y,radius,Color.blue));
        list.append(new ListEnemyNode(x,y,radius,Color.pink));
        list.append(new ListEnemyNode(x,y,radius,Color.yellow));
        list.append(new ListEnemyNode(x,y,radius,Color.red));
        list.append(new ListEnemyNode(x,y,radius,Color.orange));
        list.toFirst();

    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        if(list.hasAccess()){
            if(projectile.collidesWith(list.getContent())){
                list.toFirst();
                while (list.hasAccess()){
                    list.remove();
                }
                return true;
            }else{
                ListEnemyNode current = list.getContent();

                list.toFirst();
                while (list.hasAccess()){
                    if(projectile.collidesWith(list.getContent())){
                        return true;
                    }
                }

                Util.listSetCurrent(list,current);
            }
        }
        return false;
    }

    @Override
    public void draw(DrawTool drawTool) {


        drawAllNodes(drawTool);

    }

    /**
     * Draws all EnemyNodes stored in list
     * @param drawTool drawTool from KAGO draw method
     */
    private void drawAllNodes(DrawTool drawTool){
        //Save current to later set it to where it was after drawing
        ListEnemyNode current = list.getContent();

        list.toFirst();
        while (list.hasAccess()){
            if(list.getContent() == current) {
                drawTool.setCurrentColor(list.getContent().getColor());
            }else{
                drawTool.setCurrentColor(Color.white);
            }
            drawTool.drawFilledCircle(list.getContent().getX(),list.getContent().getY(),radius);
            list.next();
        }
        //reset current
        Util.listSetCurrent(list,current);

    }

    @Override
    public void update(double dt) {
        moveToPlayer(dt);
        moveNodes(dt);
        currentChangeTimer += dt;
        if (currentChangeTimer >= currentChangeSpeed){
            list.next();
            if(!list.hasAccess()){
                list.toFirst();
            }
            currentChangeTimer = 0;
        }
    }

    /**
     * Method to move the entire Object toward player with speed attribute specified in constructor
     * @param dt deltaTime should come from update
     */
    private void moveToPlayer(double dt){
        double radient = Math.atan2(player.getY() - this.y,player.getX() - this.x);
        double degree = Math.toDegrees(radient);
        this.moveInDirection(degree,speed,dt);
    }

    /**
     * Method to move nodes around center of object rotation speed currently 1/s (current stays the same)
     * @param dt deltaTime (should come from update)
     */
    private void moveNodes(double dt){

        //Save current to later set it to where it was after drawing
        ListEnemyNode current = list.getContent();
        //Move Nodes
        rotation += dt;
        double distance = (Math.PI * 2) / Util.countList(list);
        int i = 0;
        list.toFirst();
        while (list.hasAccess()){
            list.getContent().setX( x + Math.cos(rotation + i * distance) * distanceFromCenter);
            list.getContent().setY( y + Math.sin(rotation + i * distance) * distanceFromCenter);
            i++;
            list.next();
        }
        //put current to where it was
        Util.listSetCurrent(list,current);
    }


    private class ListEnemyNode extends EnemyNode{
        private Color color;

        public ListEnemyNode(double x, double y, double radius, Color color) {
            super(x, y, radius);
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }
}