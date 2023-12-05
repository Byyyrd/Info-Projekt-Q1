package my_project.model.enemies;

import my_project.model.Player;
import my_project.model.Projectile;

public class ArrayEnemy extends Enemy {
    public ArrayEnemy(double x, double y, double speed, Player player) {
        super(x, y, speed, player);
    }

    @Override
    public boolean checkCollision(Projectile projectile) {
        return false;
    }
}
