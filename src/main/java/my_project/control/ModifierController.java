package my_project.control;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.modifiers.PlayerModifier;

/**
 * The ModifierController manages all active modifiers for the Player and applies them once.
 * All the modifiers updated and deleted when they expire.
 */
public class ModifierController {
    private PlayerController playerController;
    private final List<PlayerModifier> modifiers = new List<>();

    public void update(double dt) {
        //Iterate over all modifiers in modifier list
        modifiers.toFirst();
        while (modifiers.hasAccess()) {
            //apply newly added modifiers
            if (!modifiers.getContent().isApplied()) {
                modifiers.getContent().applyModifier(playerController);
                modifiers.next();
                continue;
            }
            //delete expired modifiers
            if (modifiers.getContent().hasExpired()) {
                modifiers.getContent().removeModifier(playerController);
                modifiers.remove();
                continue;
            }
            //update modifiers each frame to make them tik down
            modifiers.getContent().update(dt);
            modifiers.next();
        }
    }

    /**
     * Add one modifier to the modifier list.
     * It'll be applied,updated and deleted on expiration
     * @param modifier the modifier to add
     */
    public void add(PlayerModifier modifier){
        modifiers.append(modifier);
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }
}