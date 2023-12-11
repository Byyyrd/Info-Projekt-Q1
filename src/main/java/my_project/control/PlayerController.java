package my_project.control;

import my_project.Util;
import my_project.model.Player;
import my_project.model.Bow;
import my_project.model.projectiles.Arrow;

public class PlayerController{
    private double currentSpeed = 200;
    private Player player;
    private Bow bow;
    private SpawnController spawnController;

    public PlayerController(Player player, Bow bow, SpawnController spawnController){
        this.player = player;
        this.bow = bow;
        this.spawnController = spawnController;
    }

    public void updatePlayerPosition(double xDisplacement, double yDisplacement){
        player.movePlayer(xDisplacement, yDisplacement);
        bow.updateDesiredPosition(player.getX(),player.getY());
    }

    public void updateMousePosition(double mouseX, double mouseY){
        bow.setMousePosition(mouseX,mouseY);
    }

    public void updateLeftMouseState(boolean isDown){
        bow.setLeftMouseDown(isDown);
        if(!isDown) shootArrow();
    }

    private void shootArrow(){
        double[] shootInfo = bow.getShootInfo();
        if(shootInfo == null) return;
        Util.setCamShake(0.2,shootInfo[3]*50+20);
        Arrow arrow = new Arrow(shootInfo[0],shootInfo[1],shootInfo[2],shootInfo[3] * 5000);
        spawnController.addProjectile(arrow);
    }

    public void updateRightMouseState(boolean isDown){
        //TODO implement dash
    }

    public Player getPlayer(){
        return player;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }
}
