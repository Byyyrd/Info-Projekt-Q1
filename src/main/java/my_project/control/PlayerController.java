package my_project.control;

import my_project.Util;
import my_project.model.Player;
import my_project.model.Bow;
import my_project.model.modifiers.AccelerationModifier;
import my_project.model.projectiles.Arrow;

public class PlayerController{
    private double currentSpeed = 200;
    private Player player;
    private Bow bow;
    private SpawnController spawnController;
    private ModificationController modificationController;

    private final double dashMultiplier = 3.5;
    private final double dashDuration = .2;

    private double slowPercentage = 0;
    private double accelerationPercentage = 0;

    public PlayerController(Player player, Bow bow, SpawnController spawnController,ModificationController modificationController){
        this.player = player;
        this.bow = bow;
        this.spawnController = spawnController;
        this.modificationController = modificationController;
    }

    public void updatePlayerPosition(double xDisplacement, double yDisplacement){
        if(slowPercentage < 0.01)
            slowPercentage = 0;
        System.out.println(slowPercentage);
        xDisplacement = xDisplacement - xDisplacement * slowPercentage + xDisplacement * accelerationPercentage;
        yDisplacement = yDisplacement - yDisplacement * slowPercentage + yDisplacement * accelerationPercentage;
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
        //TODO DASH
        if(isDown){
            modificationController.add(new AccelerationModifier(dashDuration,dashMultiplier));
        }
    }

    public Player getPlayer(){
        return player;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }


    public void setSlowPercentage(double slowPercentage) {
        this.slowPercentage = slowPercentage;
    }

    public void setAccelerationPercentage(double accelerationPercentage) {
        this.accelerationPercentage = accelerationPercentage;
    }
    public void addSlowPercentage(double slowPercentage) {
        this.slowPercentage += slowPercentage;
    }

    public void addAccelerationPercentage(double accelerationPercentage) {
        this.accelerationPercentage += accelerationPercentage;
    }
    public void removeSlowPercentage(double slowPercentage) {
        this.slowPercentage -= slowPercentage;
    }

    public void removeAccelerationPercentage(double accelerationPercentage) {
        this.accelerationPercentage -= accelerationPercentage;
    }
}
