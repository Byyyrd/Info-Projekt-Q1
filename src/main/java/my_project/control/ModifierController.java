package my_project.control;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.modifiers.PlayerModifier;

public class ModifierController {
    private PlayerController playerController;
    private final List<PlayerModifier> modifiers = new List<>();

    public void update(double dt) {
        modifiers.toFirst();
        while (modifiers.hasAccess()) {
            if (!modifiers.getContent().isApplied()) {
                modifiers.getContent().applyModifier(playerController);
                modifiers.next();
                continue;
            }
            if (modifiers.getContent().hasExpired()) {
                modifiers.getContent().removeModifier(playerController);
                modifiers.remove();
                continue;
            }
            if(modifiers.hasAccess())
                modifiers.getContent().update(dt);
            modifiers.next();
        }
    }

    public void add(PlayerModifier modifier){
        modifiers.append(modifier);
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }
}