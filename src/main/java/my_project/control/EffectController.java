package my_project.control;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.Background;
import my_project.model.effects.Effect;

/**
 * The EffectController class manages a list of all effects and checks if they have to be deleted.
 */
public class EffectController {
    private List<Effect> effectsList = new List<>();
    private SpawnController spawnController;
    private Background background;

    /**
     * Registers the program controller to delete effects in the future and registers the background to apply effects to it
     *
     * @param spawnController Currently used spawn controller
     * @param background Currently used background
     */
    public EffectController(SpawnController spawnController, Background background){
        this.spawnController = spawnController;
        this.background = background;
    }

    /**
     * Updates the checks for effect deletion
     */
    public void update(){
        checkEffects();
    }

    /**
     * Registers a given effect, so it can be deleted in the future
     *
     * @param effect The to be registered effect
     */
    public void add(Effect effect){
        effectsList.append(effect);
    }

    /**
     * Goes through all effects and deletes them if necessary
     */
    private void checkEffects(){
        effectsList.toFirst();
        while(effectsList.hasAccess()) {
            if (effectsList.getContent().isDestroyed()) {
                spawnController.removeObject(effectsList.getContent());
                effectsList.remove();
            } else {
                effectsList.next();
            }
        }
    }

    public void setBackgroundIntensity(int amountOfEnemies){
        if(background != null)
            background.setIntensity(amountOfEnemies * 5);
    }
}
