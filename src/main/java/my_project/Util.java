package my_project;

import KAGO_framework.model.abitur.datenstrukturen.List;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Util {
    private static double camShakeX;
    private static double camShakeY;
    private static double strength;
    private static double duration;
    /**
     * Lerp is short for linear interpolation
     * It interpolates / smooths a number from one to another
     * @param start starting number
     * @param end ending number
     * @param time time it should take (0 is no movement, 1 is instantaneous movement)
     * @return a smoothed value between start and end
     */
    public static double lerp(double start, double end, double time){
        return start * (1 - time) + end * time;
    }

    /**
     * Finds Object in list and removes it. Current of list ends on first.
     * @param list list remove from
     * @param content Object that  needs to be removed
     * @param <ContentType> Type of Objects in list and Object to find
     */
    public static <ContentType> void removeFromList(List<ContentType> list, ContentType content){
        findFromList(list,content);
        list.remove();
        list.toFirst();
    }

    /**
     * Finds object in list and makes it current.
     * @param list list that includes searched Object
     * @param content Object to be searched
     * @param <ContentType> Type of Objects in list and searched Object
     */
    public static <ContentType> void findFromList(List<ContentType> list, ContentType content){
        list.toFirst();
        while(list.hasAccess()){
            if(list.getContent() == content){
                break;
            }
            list.next();
        }
    }

    /**
     * Gets and returns all images inside a folder in the graphic resources.
     * For this to work, the images inside the folder have to be the same name as the folder itself.
     * For Example this would work:
     * <pre>
     *      MyReallyCoolFolder:
     *      ---MyReallyCoolFolder1.png
     *      ---MyReallyCoolFolder2.png
     * </pre>
     *
     * Any other kind of structure won't work
     * @param path the name of the top folder
     * @return an Array of Buffered Images that can be drawn using drawTool.drawImage()
     */
    public static BufferedImage[] getAllImagesFromFolder(String path) {
        File directory = new File("src/main/resources/graphic/" + path);
        int count = directory.list().length;
        BufferedImage[] images = new BufferedImage[count];
        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(new File("src/main/resources/graphic/" + path + "/" + path + (i+1) + ".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return images;
    }

    public static void applyCamShake(double dt) {
        camShakeX = (duration * strength * Math.random()) * 2 - (duration * strength);
        camShakeY = (duration * strength * Math.random()) * 2 - (duration * strength);
        duration -= dt;
        if(duration < 0) duration = 0;
    }

    public static void setCamShake(double duration, double strength) {
        if(Util.strength * Util.duration < strength * duration){
            Util.duration = duration;
            Util.strength = strength;
        }
    }

    public static double[] getCamShake() {
        return new double[]{camShakeX, camShakeY};
    }

    /**
     * source "http://www.jeffreythompson.org/collision-detection/rect-rect.php"
     */

    public boolean rToRCollision(float r1x, float r1y, float r1w, float r1h, float r2x, float r2y, float r2w, float r2h) {
        //Ew, fuck you
        //are the sides of one rectangle touching the other?
        if (r1x + r1w >= r2x &&    // r1 right edge past r2 left
                r1x <= r2x + r2w &&    // r1 left edge past r2 right
                r1y + r1h >= r2y &&    // r1 top edge past r2 bottom
                r1y <= r2y + r2h) {    // r1 bottom edge past r2 top
            return true;
        }
        return false;
    }
}
