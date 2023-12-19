package my_project.model.modifiers;

import my_project.control.PlayerController;

/**
 * The AccelerationModifier makes the player invincible while active. Used getting being hit
 */
public class InvincibilityModifier extends PlayerModifier{

    public InvincibilityModifier(double duration, double strength) {
        super(duration, strength);
    }

    @Override
    public void applyModifier(PlayerController playerController) {
        playerController.setInvincibilityPercentage(duration);
        super.applyModifier(playerController);
    }

    @Override
    public void removeModifier(PlayerController playerController) {
        playerController.setInvincibilityPercentage(playerController.getInvincibilityPercentage()-duration);
        super.removeModifier(playerController);
    }

}
