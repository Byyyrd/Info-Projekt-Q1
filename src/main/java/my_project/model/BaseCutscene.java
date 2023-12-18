package my_project.model;

import KAGO_framework.view.DrawFrame;
import KAGO_framework.view.DrawingPanel;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import my_project.control.ProgramController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class BaseCutscene {
    private ProgramController programController;
    private JFrame frame;

    public BaseCutscene(DrawingPanel drawingPanel, String url, ProgramController programController) {
        JFXPanel panel = new JFXPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(960, 540);
            }
        };
        panel.setEnabled(true);
        panel.setBounds(0,0,900,500);
        Platform.runLater(() -> start(panel,url));

        frame = (JFrame) SwingUtilities.windowForComponent(drawingPanel);
        panel.setFocusable(false);
        frame.add(panel);

        this.programController = programController;
    }

    public void start(JFXPanel jfxPanel,String url) {
        Media media = new Media(new File(url).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnError(()-> System.out.println("Media Error: " + mediaPlayer.getError().toString()));

        mediaPlayer.setOnEndOfMedia(() -> {
            programController.startGame();
        });

        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setVisible(true);

        Group root = new Group();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, 650,960);

        jfxPanel.setFocusable(false);
        jfxPanel.setScene(scene);
    }
}
