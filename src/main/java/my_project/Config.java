package my_project;

/**
 * In dieser Klasse werden globale, statische Einstellungen verwaltet.
 * Die Werte können nach eigenen Wünschen angepasst werden.
 */
public class Config {

    // Titel des Programms (steht oben in der Fenstertitelzeile)
    public final static String WINDOW_TITLE = "Leeres Vorlagenprojekt des KAGO-Frameworks";

    // Konfiguration des Standardfensters: Anzeige und Breite des Programmfensters (Width) und Höhe des Programmfensters (Height)
    public final static boolean SHOW_DEFAULT_WINDOW = true;
    public final static int WINDOW_WIDTH = 960+16;   // Eigentlich 960, aber 16 Pixel extra aufgrund von JPanel
    public final static int WINDOW_HEIGHT = 540+10+29;   // Effektive Höhe ist etwa 10 Pixel (JPanel Einrückung) und 29 Pixel geringer (Titelleiste wird mitgezählt)

    // Weitere Optionen für das Projekt
    public final static boolean useSound = true;
    public final static int leftBound = 35;
    public final static int rightBound = 925;
    public final static int upBound = 35;
    public final static int downBound = 505;

}
