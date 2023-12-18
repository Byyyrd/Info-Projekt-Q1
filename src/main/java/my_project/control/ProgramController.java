package my_project.control;

import KAGO_framework.control.Drawable;
import KAGO_framework.control.SoundController;
import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.view.DrawFrame;
import my_project.Util;
import my_project.model.*;
import my_project.model.enemies.RoeckrathBoss;
import my_project.view.InputManager;

import javax.swing.*;
import java.awt.*;

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
    private List<Drawable> sceneTwo = new List<>();
    private boolean wasDead = false;

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

        startGame();
        //new BaseCutscene(DrawFrame.getActivePanel(),"src/main/resources/cutscenes/cutscene1.mp4",this);
    }

    public void startGame(){
        SoundController.playSound("mainTrack");
        viewController.showScene(0);
        viewController.setOutline(new Outline());
        enemyWaveController.setActive(true);
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){
        enemyWaveController.update(dt);
        collisionController.update();
        modifierController.update(dt);
        Util.applyCamShake(dt);
        if(collisionController.getPlayer().isDead() && !wasDead){
            wasDead = true;
            SoundController.stopSound("mainTrack");
            SoundController.stopSound("bossTheme");
            viewController.createScene();
            viewController.setOutline(null);
            viewController.showScene(2);
            SoundController.playSound("death");
        }
    }

    public void addObject(GraphicalObject objectToDraw){
        viewController.draw(objectToDraw,0);
    }

    public void removeObject(GraphicalObject objectToRemove){
        viewController.removeDrawable(objectToRemove,0);
    }
}
