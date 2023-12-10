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
     * Checks whether two given rectangles collide
     *
     * @param r1x X position of left edge from first rectangle
     * @param r1y Y position of top edge from first rectangle
     * @param r1w Width of first rectangle
     * @param r1h Height of first rectangle
     * @param r2x X position of left edge from second rectangle
     * @param r2y Y position of top edge from second rectangle
     * @param r2w Width of second rectangle
     * @param r2h Height of second rectangle
     * @return A boolean that describes whether the rectangles collide
     */
    public static boolean rectToRectCollision(double r1x, double r1y, double r1w, double r1h, double r2x, double r2y, double r2w, double r2h) {
        return !(r1x > r2x + r2w) && !(r1x + r1w < r2x) && !(r1y > r2y + r2h) && !(r1y + r1h < r2y);
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

    /**
     * Pretends that the given numbers are 2d-vectors and calculates the dot product
     *
     * @param aX The first number of the first vector
     * @param aY The second number of the first vector
     * @param bX The first number of the second vector
     * @param bY The second number of the second vector
     * @return The calculated value of the dot product
     */
    public static double DotProduct(double aX, double aY, double bX, double bY) {
        return aX * bX + aY * bY;
    }

    /**
     * Calculates whether a circle and line are intersecting
     *
     * @param p1X X coordinate of the first point of the line
     * @param p1Y Y coordinate of the first point of the line
     * @param p2X X coordinate of the second point of the line
     * @param p2Y Y coordinate of the second point of the line
     * @param cX X coordinate of circle
     * @param cY Y coordinate of circle
     * @param r Radius of circle
     * @return Whether the circle and the line are intersecting
     * source: <a href="https://stackoverflow.com/questions/1073336/circle-line-segment-collision-detection-algorithm">...</a>
     */
    public static boolean isLineAndCircleColliding(double p1X, double p1Y, double p2X, double p2Y, double cX, double cY, double r) {
        double dX = p2X-p1X;
        double dY = p2Y-p1Y;
        double fX = p1X-cX;
        double fY = p1Y-cY;

        double a = DotProduct(dX, dY, dX, dY);
        double b = 2 * DotProduct(fX, fY, dX, dY);
        double c = DotProduct(fX, fY, fX, fY - r * r);

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            // no intersection
            return false;
        } else {
            // ray didn't totally miss sphere,
            // so there is a solution to
            // the equation.

            discriminant = Math.sqrt(discriminant);

            // either solution may be on or off the ray so need to test both
            // t1 is always the smaller value, because BOTH discriminant and
            // variable "a" are non-negative.
            double t1 = (-b - discriminant) / (2 * a);
            double t2 = (-b + discriminant) / (2 * a);

            // 3x HIT cases:
            //          -o->             --|-->  |            |  --|->
            // Impale(t1 hit,t2 hit), Poke(t1 hit,t2>1), ExitWound(t1<0, t2 hit),

            // 3x MISS cases:
            //       ->  o                     o ->              | -> |
            // FallShort (t1>1,t2>1), Past (t1<0,t2<0), CompletelyInside(t1<0, t2>1)

            if (t1 >= 0 && t1 <= 1) {
                // t1 is the intersection, and it's closer than t2
                // (since t1 uses -b - discriminant)
                // Impale, Poke
                return true;
            }

            // here t1 didn't intersect, so we are either started
            // inside the sphere or completely past it
            if (t2 >= 0 && t2 <= 1) {
                // ExitWound
                return true;
            }

            // no intersection: FallShort, Past, CompletelyInside
            return false;
        }
    }
}


