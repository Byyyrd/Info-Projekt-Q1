package my_project.control;

import my_project.Util;
import my_project.model.Player;
import my_project.model.Bow;
import my_project.model.modifiers.AccelerationModifier;
import my_project.model.modifiers.SlowingModifier;
import my_project.model.projectiles.Arrow;

public class PlayerController{
    private double currentSpeed = 200;
    private Player player;
    private Bow bow;
    private SpawnController spawnController;
    private ModifierController modifierController;
    //Dash
    private final double dashMultiplier = 3.5;
    private final double dashDuration = .2;
    private final double dashCooldown = .5;
    private double dashTimer = 0;
    private boolean canDash = true;
    //Modifiers
    private double slowPercentage = 0;
    private double accelerationPercentage = 0;

    public PlayerController(Player player, Bow bow, SpawnController spawnController, ModifierController modifierController){
        this.player = player;
        this.bow = bow;
        this.spawnController = spawnController;
        this.modifierController = modifierController;
    }

    /**
     * dashTimer counts down from 0
     *
     * If dashTimer is lower than 0
     * Sets a new Player-Image
     * Adds a slowingModifier
     */

    public void update(double dt){
        dashTimer -= dt;
        if(dashTimer < 0)
            player.setDrawFirstImage(true);
        modifierController.add(new SlowingModifier(dt,bow.getPower() * 0.3));
    }

    /**
     * Moves the player and bow by the displacement amount
     *
     * @param xDisplacement X coordinate displacement
     * @param yDisplacement Y coordinate displacement
     */

    public void updatePlayerPosition(double xDisplacement, double yDisplacement){
        if(slowPercentage < 0.01)
            slowPercentage = 0;
        xDisplacement = xDisplacement - xDisplacement * slowPercentage + xDisplacement * accelerationPercentage;
        yDisplacement = yDisplacement - yDisplacement * slowPercentage + yDisplacement * accelerationPercentage;
        player.movePlayer(xDisplacement, yDisplacement);
        bow.updateDesiredPosition(player.getX(),player.getY());
    }

    /**
     * Sets mouse Position on mouseX and mouseY
     *
     * @param mouseX X coordinate of the mouse
     * @param mouseY Y coordinate of the mouse
     */

    public void updateMousePosition(double mouseX, double mouseY){
        bow.setMousePosition(mouseX,mouseY);
    }

    /**
     * Sets left mouse button on isDown
     *
     * If the button is not down the bow shoots an Arrow
     *
     * @param isDown isDown stands for the state of a button (isDown or isUp)
     */

    public void updateLeftMouseState(boolean isDown){
        bow.setLeftMouseDown(isDown);
        if(!isDown) shootArrow();
    }

    /**
     * Creates a new Arrow-Projectile from the shootInfo-array (gets the information from the Bow)
     *
     * Responsible for shaking the Screen
     */

    private void shootArrow(){
        double[] shootInfo = bow.getShootInfo();
        if(shootInfo == null) return;
        Util.setCamShake(0.2,shootInfo[3]*50+20);
        Arrow arrow = new Arrow(shootInfo[0],shootInfo[1],shootInfo[2],shootInfo[3] * 5000);
        spawnController.addProjectile(arrow);
    }

    /**
     * Checks whether the player can dash dependent on isDown
     *
     * If is not down the player is able to dash
     *
     * Else dashTimer and canDash get updated
     * Adds a new AccelerationModifier
     * Sets new Player-Image
     *
     * @param isDown isDown stands for the state of a button (isDown or isUp)
     */

    public void updateRightMouseState(boolean isDown){
        if(!isDown)
            canDash = true;
        if(isDown && dashTimer < 0 && canDash){
            canDash = false;
            dashTimer = dashDuration + dashCooldown;
            modifierController.add(new AccelerationModifier(dashDuration,dashMultiplier));
            player.setDrawFirstImage(false);
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
