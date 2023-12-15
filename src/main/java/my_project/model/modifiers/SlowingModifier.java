package my_project.model.modifiers;

import my_project.control.PlayerController;

/**
 * The SlowingModifier increases the slowPercentage of the player while active.
 */
public class SlowingModifier extends PlayerModifier{
    public SlowingModifier(double duration, double strength) {
        super(duration, strength);
    }
    @Override
    public void applyModifier(PlayerController playerController) {
        playerController.addSlowPercentage(strength);
        super.applyModifier(playerController);
    }

    @Override
    public void removeModifier(PlayerController playerController) {
        playerController.removeSlowPercentage(strength);
        super.removeModifier(playerController);
    }
}

