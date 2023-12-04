package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.view.DrawTool;
import my_project.Util;
import my_project.model.Player;

public class ListEnemy extends Enemy {
    private List<EnemyNode> list = new List<>();
    private double rotation;
    private double distanceFromCenter;
    public ListEnemy(double x, double y, double speed, Player player) {
        super(x, y, speed, player);
        radius = 8;
        list.append(new EnemyNode(x,y,radius));
    }

    @Override
    public void draw(DrawTool drawTool) {
        list.toFirst();
        while (list.hasAccess()){
            drawTool.drawFilledCircle(list.getContent().getX(),list.getContent().getY(),radius);
            list.next();
        }

    }


    @Override
    public void update(double dt) {
        double degree = Math.atan2(this.x - player.getX(),this.y - player.getY());
        this.moveInDirection(degree,speed,dt);
        rotation += dt;
        double distance = (Math.PI * 2) / Util.countList(list);
        int i = 0;
        list.toFirst();
        while (list.hasAccess()){
            list.getContent().setX( player.getX() + Math.cos(rotation + i * distance) * distanceFromCenter);
            list.getContent().setY( player.getY() + Math.sin(rotation + i * distance) * distanceFromCenter);
            i++;
            list.next();
        }
    }
}