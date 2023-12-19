package my_project.control;

import KAGO_framework.control.Drawable;
import KAGO_framework.control.SoundController;
import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.view.DrawFrame;
import my_project.Util;
import my_project.model.*;
import my_project.model.visuals.Background;
import my_project.model.visuals.Cutscene;
import my_project.model.visuals.Outline;
import my_project.model.visuals.Transition;
import my_project.view.InputManager;

/**
 * The ProgramController class is the core of the program. Here all controller-update calls happen that influence the game
 */
public class ProgramController {
    private CollisionController collisionController;
    private ViewController viewController;
    private EnemyWaveController enemyWaveController;
    private ModifierController modifierController;
    private List<Drawable> scene = new List<>();
    private boolean wasDead = false;
    private double endingTimer = 0;
    private boolean inEnd = false;

    /**
     * Sets the current view controller
     *
     * @param viewController Currently used view controller
     */
    public ProgramController(ViewController viewController){
        this.viewController = viewController;
    }

    /**
     * Loads all sounds and instantiates all controllers, then plays the intro cutscene
     */
    public void startProgram() {
        viewController.createScene();
        viewController.createScene();
        //Loading Sound
        viewController.getSoundController().loadSound("src/main/resources/sound/mainTrack.mp3","mainTrack",false);
        viewController.getSoundController().loadSound("src/main/resources/sound/death.mp3","death",false);
        viewController.getSoundController().loadSound("src/main/resources/sound/bossTheme.mp3","bossTheme",true);
        viewController.getSoundController().loadSound("src/main/resources/sound/cutscene1.mp3","cutscene1",false);
        viewController.getSoundController().loadSound("src/main/resources/sound/cutscene2.mp3","cutscene2",false);
        //Visual Model
        Background background = new Background();
        scene.append(background);
        //Active Model
        Bow bow = new Bow();
        scene.append(bow);
        Player player = new Player();
        scene.append(player);
        //Control
        SpawnController spawnController = new SpawnController(this);
        modifierController = new ModifierController();
        PlayerController playerController = new PlayerController(player, bow, spawnController, modifierController);
        collisionController = new CollisionController(playerController, spawnController, background);
        enemyWaveController = new EnemyWaveController(spawnController);
        spawnController.setCollisionController(collisionController);
        modifierController.setPlayerController(playerController);
        //View
        InputManager inputManager = new InputManager(playerController);
        scene.append(inputManager);
        viewController.register(inputManager,0);

        scene.toFirst();
        while (scene.hasAccess()){
            viewController.draw(scene.getContent(),0);
            scene.next();
        }

        viewController.showScene(1);

        playCutscene("intro",0);
    }

    /**
     * Creates a new cutscene object and changes the scene
     *
     * @param cutsceneName Name of the cutscene, without the extension (.mp4)
     * @param cutsceneIndex Index of the event that happens after the cutscene ends
     */
    public void playCutscene(String cutsceneName, int cutsceneIndex){
        viewController.showScene(cutsceneIndex+5);
        new Cutscene(DrawFrame.getActivePanel(),"src/main/resources/cutscenes/" + cutsceneName + ".mp4",this,cutsceneIndex);
    }

    /**
     * Activates the main scene, therefore starting the game
     */
    public void startGame(){
        SoundController.playSound("mainTrack");
        viewController.showScene(0);
        viewController.setOutline(new Outline());
        enemyWaveController.setActive(true);
    }

    /**
     * Initializes the ending cutscene
     *
     * @param timer How long it should take for the cutscene to play
     */
    public void endGame(double timer){
        inEnd = true;
        endingTimer = timer;
        viewController.setOutline(new Transition());
    }

    /**
     * Deactivates the main scene, and starts the ending cutscene
     */
    private void startEnding(){
        SoundController.stopSound("mainTrack");
        SoundController.stopSound("bossTheme");
        playCutscene("ending",1);
        viewController.setOutline(null);
    }

    /**
     * Updates all controllers and checks for the triggering of cutscenes
     *
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){
        //Controllers
        enemyWaveController.update(dt);
        collisionController.update();
        modifierController.update(dt);
        Util.applyCamShake(dt);
        //Cutscenes
        if(inEnd){
            endingTimer -= dt;
            SoundController.setVolume("bossTheme",(endingTimer > 0 && endingTimer < 1) ? endingTimer : 1);
            if(endingTimer < 0){
                startEnding();
                inEnd = false;
            }
        }
        if(collisionController.getPlayer().isDead() && !wasDead && !inEnd && endingTimer >= 0){
            wasDead = true;
            SoundController.stopSound("mainTrack");
            SoundController.stopSound("bossTheme");
            viewController.setOutline(null);
            playCutscene("death",1);
        }
    }

    public void addObject(Drawable objectToDraw){
        viewController.draw(objectToDraw);
    }

    public void removeObject(Drawable objectToRemove){
        viewController.removeDrawable(objectToRemove);
    }
}
