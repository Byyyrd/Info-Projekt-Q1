package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.Util;
import my_project.model.Background;
import my_project.model.Bow;
import my_project.model.Player;
import my_project.model.enemies.ListEnemy;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute


    // Referenzen
    private CollisionController collisionController;
    private ViewController viewController;

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
        Background background = new Background();
        viewController.draw(background);
        Player player = new Player();
        viewController.draw(player);
        collisionController = new CollisionController(player, viewController);
        Bow bow = new Bow(player,collisionController);
        viewController.draw(bow);
        viewController.register(bow);
        viewController.draw(new ListEnemy());
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){
        collisionController.update();
        Util.applyCamShake(dt);
    }
}
