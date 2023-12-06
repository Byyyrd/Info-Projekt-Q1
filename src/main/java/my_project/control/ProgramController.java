package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import my_project.Util;
import my_project.model.Background;
import my_project.model.Bow;
import my_project.model.Player;
import my_project.model.enemies.ListEnemy;
import my_project.model.enemies.QueueEnemy;
import my_project.model.enemies.StackEnemy;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute


    // Referenzen
    private CollisionController collisionController;
    private ViewController viewController;
    private Player player;
    private Background background;
    private double timer = 0;

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
        background = new Background();
        viewController.draw(background);
        player = new Player();
        viewController.draw(player);
        collisionController = new CollisionController(player, this);
        Bow bow = new Bow(player,collisionController);
        viewController.draw(bow);
        viewController.register(bow);
        //StackEnemy stackEnemy = new StackEnemy(100,100,150,player,5);
        //viewController.draw(stackEnemy);
        //collisionController.addEnemy(stackEnemy);
        ListEnemy testListEnemy = new ListEnemy(300,300,50,10, player, collisionController);
        viewController.draw(testListEnemy);
        collisionController.addEnemy(testListEnemy);
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){
        timer -= dt;
        if(timer < 1)
            background.setIntensity(50);
        else
            background.setIntensity(0);
        if(timer < 0){
            //QueueEnemy testQueueEnemy = new QueueEnemy(Math.random()*870+53,100,10,100,player,(int)(Math.random()*40+30));
            //viewController.draw(testQueueEnemy);
            //collisionController.addEnemy(testQueueEnemy);
            timer = 7;
        }
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
