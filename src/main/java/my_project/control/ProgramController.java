package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import my_project.Util;
import my_project.model.Background;
import my_project.model.Bow;
import my_project.model.Outline;
import my_project.model.Player;
import my_project.view.InputManager;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute


    // Referenzen
    private CollisionController collisionController;
    private PlayerController playerController;
    private ViewController viewController;
    private EnemyWaveController enemyWaveController;
    private Player player;
    private Background background;

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
        //Visual Model
        background = new Background();
        viewController.draw(background);
        viewController.setOutline(new Outline());
        //Active Model
        player = new Player();
        viewController.draw(player);
        Bow bow = new Bow();
        viewController.draw(bow);
        //Control
        SpawnController spawnController = new SpawnController(this);
        playerController = new PlayerController(player,bow,spawnController);
        collisionController = new CollisionController(playerController,spawnController);
        enemyWaveController = new EnemyWaveController(spawnController);
        spawnController.setCollisionController(collisionController);
        //View
        InputManager inputManager = new InputManager(playerController);
        viewController.draw(inputManager);
        viewController.register(inputManager);
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){
        enemyWaveController.update(dt);
        collisionController.update();
        Util.applyCamShake(dt);
    }

    public void addObject(GraphicalObject objectToDraw){
        viewController.draw(objectToDraw);
    }

    public void removeObject(GraphicalObject objectToRemove){
        viewController.removeDrawable(objectToRemove);
    }
}
