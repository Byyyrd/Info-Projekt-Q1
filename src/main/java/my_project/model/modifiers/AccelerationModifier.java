package my_project.model.modifiers;

import my_project.control.PlayerController;
import my_project.model.Player;

public class AccelerationModifier extends PlayerModifier{
    public AccelerationModifier(double duration, double strength) {
        super(duration, strength);
    }

    @Override
    public void applyModifier(PlayerController playerController) {
        playerController.addAccelerationPercentage(strength);
        super.applyModifier(playerController);
    }

    @Override
    public void removeModifier(PlayerController playerController) {
        playerController.removeAccelerationPercentage(strength);
        super.removeModifier(playerController);
    }
}
