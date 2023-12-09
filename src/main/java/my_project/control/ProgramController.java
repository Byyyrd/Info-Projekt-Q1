package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import my_project.Util;
import my_project.model.Background;
import my_project.model.Bow;
import my_project.model.Outline;
import my_project.model.Player;
import my_project.model.enemies.ListEnemy;
import my_project.model.enemies.QueueEnemy;
import my_project.model.enemies.StackEnemy;
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
    private Player player;
    private Background background;
    private Outline outline;
    private double timer = 0;
    private int enemy = 2;

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
        collisionController = new CollisionController(player, this);
        playerController = new PlayerController(player,bow,collisionController);
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
        //This code can be deleted
        timer -= dt;
        if(timer < 1)
            background.setIntensity(50);
        else
            background.setIntensity(0);
        if(timer < 0){
            spawnTestEnemies();
            timer = 10;
        }

        collisionController.update();
        Util.applyCamShake(dt);
    }

    private void spawnTestEnemies(){
        /*if(enemy == 0){
            ListEnemy testListEnemy = new ListEnemy(Math.random()*870+53,-100,Math.random()*30+30,(int)(Math.random()*9+2), player, collisionController);
            viewController.draw(testListEnemy);
            collisionController.addEnemy(testListEnemy);
        } else if (enemy == 1) {
            QueueEnemy testQueueEnemy = new QueueEnemy(Math.random()*870+53,-100,10,Math.random()*60+60,player,collisionController,(int)(Math.random()*40+30));
            viewController.draw(testQueueEnemy);
            collisionController.addEnemy(testQueueEnemy);
        } else {*/
            StackEnemy stackEnemy = new StackEnemy(Math.random()*870+53,-100,player,collisionController,(int)(Math.random()*4+2));
            viewController.draw(stackEnemy);
            collisionController.addEnemy(stackEnemy);
        //}
        enemy++;
        if(enemy > 2){
            enemy = 0;
        }
    }

    public void addObject(GraphicalObject objectToDraw){
        viewController.draw(objectToDraw);
    }

    public void removeObject(GraphicalObject objectToRemove){
        viewController.removeDrawable(objectToRemove);
    }
}
