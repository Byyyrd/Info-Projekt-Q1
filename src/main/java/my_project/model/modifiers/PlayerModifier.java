package my_project.model.modifiers;

import my_project.control.PlayerController;

public abstract class PlayerModifier {
    protected double duration;
    protected double strength;
    protected double timer;
    private boolean applied;

    public PlayerModifier(double duration, double strength) {
        this.duration = duration;
        this.strength = strength;
    }

    public void applyModifier(PlayerController playerController) {
        timer = duration;
        applied = true;
    }

    public void update(double dt) {
        if (applied) {
            timer -= dt;
        }
    }
    public void removeModifier(PlayerController playerController){
        timer = duration;
        applied = false;
    }

    public boolean isApplied() {
        return applied;
    }

    public boolean hasExpired(){
        return timer < 0;
    }
}
