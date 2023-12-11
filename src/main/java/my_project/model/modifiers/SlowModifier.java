package my_project.model.modifiers;

import my_project.control.PlayerController;
import my_project.model.Player;

public class SlowModifier extends PlayerModifier{
    public SlowModifier(double duration, double strength) {
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

