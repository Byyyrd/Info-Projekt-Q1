package my_project;

/**
 * The Config class is used to manage global settings.
 */
public class Config {

    // Title of the programm
    public final static String WINDOW_TITLE = "LDS Slayer - Revenge of Info LK";

    // Options for the window
    public final static boolean SHOW_DEFAULT_WINDOW = true;
    public final static int WINDOW_WIDTH = 960+16;   // Actually 960, but 16 pixels extra due to the discrepancy from JPanel
    public final static int WINDOW_HEIGHT = 540+10+29;   // Actually 960, but 10 pixels extra due titelbar and 29 pixels extra due to the discrepancy from JPanel

    // More options for the programm
    public final static boolean useSound = true;
    public final static int leftBound = 43;
    public final static int rightBound = 933;
    public final static int upBound = 43;
    public final static int downBound = 513;

    // Options to speed up testing
    public final static boolean skipEnemies = false;
    public final static boolean skipCutscene = true;
}
