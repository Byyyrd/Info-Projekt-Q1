package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.view.DrawTool;

public class ListEnemy extends Enemy {
    private List<EnemyNode> list = new List<>();

    @Override
    public void draw(DrawTool drawTool) {
        list.toFirst();
        int i = 0;
        while (list.hasAccess()){
            drawTool.drawCircle(x,y,radius);
            i++;
        }
    }


    @Override
    public void update(double dt) {

    }
}