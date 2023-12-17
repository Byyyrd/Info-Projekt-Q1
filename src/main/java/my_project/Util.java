package my_project;

import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.datenstrukturen.Queue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The Util class is completely made up of static functions.
 * It is used to not have duplicate code fragments and generalize the functions every class could use.
 */
public class Util {
    private static double camShakeX;
    private static double camShakeY;
    private static double strength;
    private static double duration;

    /**
     * Lerp is short for linear interpolation.
     * It interpolates / smooths a number from one to another
     *
     * @param start Starting number
     * @param end Ending number
     * @param time Time it should take (0 is no movement, 1 is instantaneous movement)
     * @return A smoothed value between the start and end
     */
    public static double lerp(double start, double end, double time) {
        return start * (1 - time) + end * time;
    }

    /**
     * Lerp is short for linear interpolation.
     * It interpolates / smooths a number from one to another.
     * This version takes two radians values between -PI and PI and interpolates them
     *
     * @param start Starting angle
     * @param end Ending angle
     * @param time Time it should take (0 is no movement, 1 is instantaneous movement)
     * @return A smoothed angle between the start and end
     */
    public static double lerpAngle(double start, double end, double time) {
        double xPos = (1 - time) * Math.cos(start) + time * Math.cos(end);
        double yPos = (1 - time) * Math.sin(start) + time * Math.sin(end);
        return Math.atan2(yPos, xPos);
    }

    /**
     * Counts the amount of objects inside a list, while setting current to null
     *
     * @param list List to count in
     * @return The number of objects (0 if the list is empty)
     */
    public static <ContentType> int countList(List<ContentType> list) {
        int count = 0;
        list.toFirst();
        while (list.hasAccess()) {
            count++;
            list.next();
        }
        return count;
    }

    /**
     * Counts the amount of objects inside a queue, without changing it
     *
     * @param queue Queue to count in
     * @return The number of objects (0 if the queue is empty)
     */
    public static <ContentType> int countQueue(Queue<ContentType> queue) {
        int count = 0;
        Queue<ContentType> helpQueue = new Queue<>();
        while (!queue.isEmpty()) {
            helpQueue.enqueue(queue.front());
            queue.dequeue();
            count++;
        }
        while (!helpQueue.isEmpty()) {
            queue.enqueue(helpQueue.front());
            helpQueue.dequeue();
        }
        return count;
    }

    /**
     * Finds an object inside a given list and makes it the current
     *
     * @param list List to set current in
     * @param object Object to set current to
     */
    public static <ContentType> void listSetCurrent(List<ContentType> list, ContentType object){
        list.toFirst();
        while(list.hasAccess() && list.getContent() != object){
            list.next();
        }
    }

    /**
     * Gets the content of the tail inside a given queue
     *
     * @param queue Queue to be searched in
     * @return The content of the tail, not the node
     */
    public static <ContentType> ContentType getTailContent(Queue<ContentType> queue) {
        int count = countQueue(queue);
        ContentType tailContent = null;
        for (int i = 0; i < count; i++) {
            if (i == count - 1) {
                tailContent = queue.front();
            }
            queue.enqueue(queue.front());
            queue.dequeue();
        }
        return tailContent;
    }

    /**
     * Finds a given object inside a list and removes it.
     * Current of the list is then put on first
     *
     * @param list List to be removed from
     * @param content Object to be removed
     */
    public static <ContentType> void removeFromList(List<ContentType> list, ContentType content) {
        findFromList(list, content);
        list.remove();
        list.toFirst();
    }

    /**
     * Finds a given object inside a list and makes it the current object of the list
     *
     * @param list List that includes searched object
     * @param content Object to be searched
     */
    public static <ContentType> void findFromList(List<ContentType> list, ContentType content) {
        list.toFirst();
        while (list.hasAccess()) {
            if (list.getContent() == content) {
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
     * <p>
     * Any other kind of structure won't work
     *
     * @param path The name of the top folder
     * @return An Array of Buffered Images that can be drawn using drawTool.drawImage()
     */
    public static BufferedImage[] getAllImagesFromFolder(String path) {
        File directory = new File("src/main/resources/graphic/" + path);
        int count = directory.list().length;
        BufferedImage[] images = new BufferedImage[count];
        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(new File("src/main/resources/graphic/" + path + "/" + path + (i + 1) + ".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return images;
    }

    public static BufferedImage[] getAllImagesForCutscene(String path) {
        File directory = new File("src/main/resources/graphic/" + path);
        int count = directory.list().length;
        BufferedImage[] images = new BufferedImage[count];
        for (int i = 0; i < images.length; i++) {
            try {
                String numberString = Integer.toString(i);
                if(i < 10){
                    numberString = "00" + numberString;
                } else if (i < 100) {
                    numberString = "0" + numberString;
                }
                images[i] = ImageIO.read(new File("src/main/resources/graphic/" + path + "/" + path + numberString + ".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return images;
    }

    /**
     * Updates the values for the camera shake
     *
     * @param dt Time between the current and the last frame
     */
    public static void applyCamShake(double dt) {
        camShakeX = (duration * strength * Math.random()) * 2 - (duration * strength);
        camShakeY = (duration * strength * Math.random()) * 2 - (duration * strength);
        duration -= dt;
        if (duration < 0) duration = 0;
    }

    /**
     * Applies new values to the camera shake, if the new values are higher than the current
     *
     * @param duration Duration of the camera shake
     * @param strength Strength of the camera shake
     */
    public static void setCamShake(double duration, double strength) {
        if (Util.strength * Util.duration < strength * duration) {
            Util.duration = duration;
            Util.strength = strength;
        }
    }

    /**
     * Returns the values used for camera shake
     *
     * @return A double array with the 1st index being the x camera shake and the 2nd the y camera shake
     */
    public static double[] getCamShake() {
        return new double[]{camShakeX, camShakeY};
    }

    /**
     * Checks whether two given circles collide
     *
     * @param x1 X position of 1st circle
     * @param y1 Y position of 1st circle
     * @param r1 Radius of 1st circle
     * @param x2 Y position of 2nd circle
     * @param y2 X position of 2nd circle
     * @param r2 Radius of 2nd circle
     * @param depth Depth of the circle intersection that has to happen until it the circles are considered colliding
     * @return A boolean that describes whether the circles collide
     */
    public static boolean circleToCircleCollision(double x1, double y1, double r1, double x2, double y2, double r2, double depth) {
        double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        return r1 + r2 - depth > distance;
    }

    /**
     * This is useful for one specific snip of code in QueueEnemy
     *
     * @param number Number to check
     * @return 0 if number is 0 else 1
     */
    public static double isNumberNotZero(double number) {
        if (number == 0) {
            return 0;
        }
        return 1;
    }
}


