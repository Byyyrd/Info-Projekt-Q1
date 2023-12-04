package my_project.model.enemies;

import KAGO_framework.model.abitur.datenstrukturen.Stack;
import my_project.model.Player;

public class StackEnemy extends Enemy {
    private Stack<EnemyNode> stack = new Stack<>();

    public StackEnemy(double x, double y, double speed, Player player) {
        super(x, y, speed, player);
    }
}
