package my_project.view;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.InteractiveGraphicalObject;
import my_project.control.PlayerController;
import java.awt.event.MouseEvent;

/**
 * Realisiert ein Objekt, dass alle Eingaben empfängt und dann danach passende Methoden
 * im PlayerController aufruft
 */
public class InputManager extends InteractiveGraphicalObject {
    private PlayerController playerController;

    public InputManager(PlayerController playerController){
        this.playerController = playerController;
    }

    public void update(double dt) {
        double currentSpeed = playerController.getCurrentSpeed();
        double xDisplacement = 0;
        double yDisplacement = 0;
        if (ViewController.isKeyDown(65)) {
            xDisplacement = -currentSpeed * dt;
        } else if (ViewController.isKeyDown(68)) {
            xDisplacement = currentSpeed * dt;
        }
        if (ViewController.isKeyDown(87)) {
            yDisplacement = -currentSpeed * dt;
        } else if (ViewController.isKeyDown(83)) {
            yDisplacement = currentSpeed * dt;
        }
        playerController.updatePlayerPosition(xDisplacement,yDisplacement);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        playerController.updateMousePosition(e.getX(),e.getY());
        if(e.getButton() == 1)
            playerController.updateLeftMouseState(true);
        if(e.getButton() == 3)
            playerController.updateRightMouseState(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        playerController.updateMousePosition(e.getX(),e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        playerController.updateMousePosition(e.getX(),e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        playerController.updateMousePosition(e.getX(),e.getY());
        if(e.getButton() == 1)
            playerController.updateLeftMouseState(false);
        if(e.getButton() == 3)
            playerController.updateRightMouseState(false);
    }
}
