package my_project.control;

import KAGO_framework.control.Drawable;
import KAGO_framework.control.SoundController;
import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.Util;
import my_project.model.Background;
import my_project.model.Bow;
import my_project.model.Outline;
import my_project.model.Player;
import my_project.model.enemies.RoeckrathBoss;
import my_project.view.InputManager;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute


    // Referenzen
    private CollisionController collisionController;
    private ViewController viewController;
    private EnemyWaveController enemyWaveController;
    private ModifierController modifierController;

    private List<Drawable> scene = new List<>();

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param viewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController viewController){
        this.viewController = viewController;
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {
        //Testing
        viewController.getSoundController().loadSound("src/main/resources/sound/test.mp3","mainTrack",true);
        SoundController.playSound("mainTrack");
        //Visual Model
        Background background = new Background();
        scene.append(background);
        viewController.setOutline(new Outline());
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
        viewController.register(inputManager);

        scene.toFirst();
        while (scene.hasAccess()){
            viewController.draw(scene.getContent());
            scene.next();
        }
        RoeckrathBoss roeckrathBoss = new RoeckrathBoss(100,100,10,player,spawnController);
        viewController.draw(roeckrathBoss);
        collisionController.registerEnemy(roeckrathBoss);
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){
        //enemyWaveController.update(dt);
        collisionController.update();
        modifierController.update(dt);
        Util.applyCamShake(dt);
    }

    public void addObject(GraphicalObject objectToDraw){
        viewController.draw(objectToDraw);
    }

    public void removeObject(GraphicalObject objectToRemove){
        viewController.removeDrawable(objectToRemove);
    }
}
