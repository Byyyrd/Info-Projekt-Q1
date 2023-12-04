package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.Effect;

public class EffectController {
    private List<Effect> effectsList = new List<>();
    private ViewController viewController;

    public EffectController(ViewController viewController){
        this.viewController = viewController;
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
                viewController.removeDrawable(effectsList.getContent());
                effectsList.remove();
            } else {
                effectsList.next();
            }
        }
    }
}
