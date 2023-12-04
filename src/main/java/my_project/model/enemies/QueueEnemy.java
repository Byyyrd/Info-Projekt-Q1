package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.Queue;
import my_project.model.Player;

public class QueueEnemy extends Enemy {
    private Queue<EnemyNode> queue = new Queue<>();

    public QueueEnemy(double x, double y, double speed, Player player) {
        super(x, y, speed, player);
    }


}
