package my_project.control;

import my_project.Util;
import my_project.model.Player;
import my_project.model.Bow;
import my_project.model.modifiers.AccelerationModifier;
import my_project.model.modifiers.InvincibilityModifier;
import my_project.model.modifiers.SlowingModifier;
import my_project.model.projectiles.Arrow;

/**
 * The PlayerController class changes all player related variables according to input and various modifiers
 */
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
    private double invincibilityPercentage = 0;

    /**
     * Get a reference for all needed objects for later use
     *
     * @param player Current player in use
     * @param bow Current bow in use
     * @param spawnController Current spawn controller in use
     * @param modifierController Current modifier controller in use
     */
    public PlayerController(Player player, Bow bow, SpawnController spawnController, ModifierController modifierController){
        this.player = player;
        this.bow = bow;
        this.spawnController = spawnController;
        this.modifierController = modifierController;
    }

    /**
     * Updates all variables
     */
    public void update(double dt){
        dashTimer -= dt;
        invincibilityPercentage -= dt;

        if(dashTimer < 0)
            player.setDrawFirstImage(true);
        modifierController.add(new SlowingModifier(dt,bow.getPower() * 0.3));

        //Applies invincibility modifier
        player.setInvincibilityTimer(invincibilityPercentage);
        if(invincibilityPercentage < 0)
            player.setHealthPoints(player.getHealthPoints()-dt);
        if(player.getHealthPoints() < 0){
            player.setHealthPoints(0);
        }
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
        bow.setDesiredPosition(player.getX(),player.getY());
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
     * Sets left mouse button on isDown.
     * If the button is not down the bow shoots an Arrow.
     *
     * @param isDown isDown stands for the state of a button (isDown or isUp)
     */

    public void updateLeftMouseState(boolean isDown){
        bow.setLeftMouseDown(isDown);
        if(!isDown) shootArrow();
    }

    /**
     * Creates a new Arrow-Projectile from the shootInfo-array (gets the information from the Bow).
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
     * Applies the input for the right mouse button
     *
     * @param isDown Whether the button is being pressed
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

    /**
     * Checks whether the player is currently invincible or not and applies a modifier and changes hp accordingly
     *
     * @return Whether the player is currently invincible
     */
    public boolean playerTakeDamage(){
        if (invincibilityPercentage>=0)
            return false;
        player.takeDamage();
        modifierController.add(new InvincibilityModifier(3,1));
        return true;
    }

    public Player getPlayer(){
        return player;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public double getInvincibilityPercentage() {
        return invincibilityPercentage;
    }

    public void setInvincibilityPercentage(double invincibilityPercentage) {
        this.invincibilityPercentage = invincibilityPercentage;
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
