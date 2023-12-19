package my_project.model.visuals;

import KAGO_framework.view.DrawingPanel;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import my_project.control.ProgramController;

import java.awt.*;
import java.io.File;

/**
 * The Cutscene class plays a video on a newly created JavaFX panel that is deleted after the video ends
 */
public class Cutscene {
    /**
     * Creates a new JavaFX panel and plays a chosen video on it
     *
     * @param drawingPanel Currently used drawing panel
     * @param url Currently used program controller
     * @param programController Url / File directory of the video
     * @param cutsceneIndex Index to determine what should happen after the video ends
     */
    public Cutscene(DrawingPanel drawingPanel, String url, ProgramController programController, int cutsceneIndex) {
        JFXPanel panel = new JFXPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(960,540);
            }
        };

        Platform.runLater(() -> start(panel,url,drawingPanel,programController,cutsceneIndex));

        drawingPanel.add(panel);
    }

    /**
     * Starts the video playback
     *
     * @param jfxPanel Newly created JavaFX panel
     * @param url Url / File directory of the video
     * @param drawingPanel Currently used drawing panel
     * @param programController Currently used program controller
     * @param cutsceneIndex Index of the cutscene
     */
    private void start(JFXPanel jfxPanel,String url, DrawingPanel drawingPanel, ProgramController programController, int cutsceneIndex) {
        Media media = new Media(new File(url).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnError(()-> System.out.println("Media Error: " + mediaPlayer.getError().toString()));

        mediaPlayer.setOnEndOfMedia(() -> {
            drawingPanel.remove(jfxPanel);
            drawingPanel.add(new JFXPanel());
            end(programController,cutsceneIndex);
        });

        MediaView mediaView = new MediaView(mediaPlayer);

        Group root = new Group();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, 650,960);

        jfxPanel.setScene(scene);
    }

    /**
     * Determines what should happen, after the video has played
     *
     * @param programController Program controller in use
     * @param cutsceneIndex Cutscene index
     */
    private void end(ProgramController programController, int cutsceneIndex){
        switch (cutsceneIndex){
            case 0 -> programController.startGame();
            case 1 -> System.exit(0);
        }
    }
}
