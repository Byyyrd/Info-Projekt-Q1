package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.control.CollisionController;
import my_project.model.effects.DustParticleEffect;
import my_project.model.Player;
import my_project.model.projectiles.Projectile;

import java.awt.*;

public class ListEnemy extends Enemy {
    private List<ListEnemyNode> list = new List<>();
    private double rotation;
    private double distanceFromCenter;
    private double currentChangeTimer;
    private final double currentChangeSpeed = 5;
    public ListEnemy(double x, double y, double speed, int amountOfNodes, Player player, CollisionController collisionController) {
        super(x, y, speed, player, collisionController);
        images = Util.getAllImagesFromFolder("listEnemy");
        radius = 16;
        distanceFromCenter = 32;
        if (amountOfNodes > 10) amountOfNodes = 10;
        for (int i = 0; i < amountOfNodes; i++) {
            list.append(new ListEnemyNode(x,y,radius,i));
        }
        list.toFirst();
    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        if(list.hasAccess()){
            if(collidesWithNode(projectile,list.getContent())){
                projectile.setX(list.getContent().getX());
                projectile.setY(list.getContent().getY());
                collisionController.addEffect(new DustParticleEffect(list.getContent().getX(),list.getContent().getY(),50,60,30,new Color(0, 0, 0)));
                list.remove();
                if(!list.hasAccess()) list.toFirst();
                if(list.isEmpty()) destroyed = true;
                return true;
            } else {
                ListEnemyNode current = list.getContent();
                list.toFirst();
                while (list.hasAccess()){
                    if(collidesWithNode(projectile,list.getContent())) {
                        projectile.setX(list.getContent().getX());
                        projectile.setY(list.getContent().getY());
                        Util.listSetCurrent(list,current);
                        return true;
                    }
                    list.next();
                }
                Util.listSetCurrent(list,current);
            }
        }
        return false;
    }

    @Override
    public boolean checkCollision(Player player) {
        ListEnemyNode current = list.getContent();
        list.toFirst();
        while (list.hasAccess()){
            if(Util.circleToCircleCollision(x,y,list.getContent().getDegrees(),player.getX(),player.getY(),8,0)) {
                Util.listSetCurrent(list,current);
                return true;
            }
            list.next();
        }
        Util.listSetCurrent(list,current);
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
            drawTool.setCurrentColor(new Color(0x403353));
            if(list.getContent() == current)
                drawTool.drawImage(images[current.index],list.getContent().getX()-16,list.getContent().getY()-16);
            else
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
        double radiant = Math.atan2(player.getY() - this.y,player.getX() - this.x);
        double degree = Math.toDegrees(radiant);
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
        rotation += dt/2;
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
        private int index;

        public ListEnemyNode(double x, double y, double radius, int index) {
            super(x, y, radius);
            this.index = index;
        }
    }
}