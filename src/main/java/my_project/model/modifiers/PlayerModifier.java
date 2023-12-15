package my_project.model.modifiers;

import my_project.control.PlayerController;

/**
 * The PlayerModifier is an abstract class that serves as a base Template for all modifiers.
 * It can be applied,removed and updated.
 */
public abstract class PlayerModifier {
    protected double duration;
    protected double strength;
    protected double timer;
    private boolean applied;

    /**
     * Base Constructor for all inheriting classes to set up essential parameters duration and strength for later use.
     * @param duration the duration of the modifier after it has been applied in seconds
     * @param strength the strength to use when applying and removing modifiers in corresponding methods
     */
    public PlayerModifier(double duration, double strength) {
        this.duration = duration;
        this.strength = strength;
    }

    public void update(double dt) {
        if (applied) {
            timer -= dt;
        }
    }

    /**
     * Base Method for applying the modifier of a modifier.
     * After call the timer is at max duration and the modifier is applied.
     * Should apply the effect with corresponding strength to the playerController when called.
     * @param playerController the playerController to which the effect should be applied
     */
    public void applyModifier(PlayerController playerController) {
        timer = duration;
        applied = true;
    }
    /**
     * Base Method for removing applied modifier of a modifier.
     * After call the timer is at expired duration and the modifier is not applied.
     * Should remove the effect with corresponding strength from the playerController when called.
     * @param playerController the playerController to which the effect should be applied
     */
    public void removeModifier(PlayerController playerController){
        timer = -1;
        applied = false;
    }

    public boolean isApplied() {
        return applied;
    }

    /**
     * Checks if timer is less than zero and therefore the effect is expired.
     * @return whether effect has expired and should be removed
     */
    public boolean hasExpired(){
        return timer < 0;
    }
}
