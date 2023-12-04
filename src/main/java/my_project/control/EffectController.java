package my_project.control;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.Effect;

public class EffectController {
    private List<Effect> effectsList = new List<>();
    private ProgramController programController;

    public EffectController(ProgramController programController){
        this.programController = programController;
    }

    public void update(){
        checkEffects();
    }

    public void add(Effect effect){
        effectsList.append(effect);
    }

    private void checkEffects(){
        effectsList.toFirst();
        while(effectsList.hasAccess()) {
            if (effectsList.getContent().isDestroyed()) {
                programController.removeObject(effectsList.getContent());
                effectsList.remove();
            } else {
                effectsList.next();
            }
        }
    }
}
