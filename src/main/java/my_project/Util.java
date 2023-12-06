package my_project;

import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.datenstrukturen.Queue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class Util {
    private static double camShakeX;
    private static double camShakeY;
    private static double strength;
    private static double duration;

    /**
     * Lerp is short for linear interpolation
     * It interpolates / smooths a number from one to another
     *
     * @param start starting number
     * @param end   ending number
     * @param time  time it should take (0 is no movement, 1 is instantaneous movement)
     * @return a smoothed value between start and end
     */
    public static double lerp(double start, double end, double time) {
        return start * (1 - time) + end * time;
    }

    public static double lerpAngle(double start, double end, double time) {
        double xPos = (1 - time) * Math.cos(start) + time * Math.cos(end);
        double yPos = (1 - time) * Math.sin(start) + time * Math.sin(end);
        return Math.atan2(yPos, xPos);
    }

    public static <ContentType> int countList(List<ContentType> list) {
        int count = 0;
        list.toFirst();
        while (list.hasAccess()) {
            count++;
            list.next();
        }
        return count;
    }

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
     * gibt einem content von tail
     *
     * @param queue         die queue von welcher man den Content der tail wissen will
     * @param <ContentType>
     * @return den content von tail
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
     * Finds Object in list and removes it. Current of list ends on first.
     *
     * @param list          list remove from
     * @param content       Object that  needs to be removed
     * @param <ContentType> Type of Objects in list and Object to find
     */
    public static <ContentType> void removeFromList(List<ContentType> list, ContentType content) {
        findFromList(list, content);
        list.remove();
        list.toFirst();
    }

    /**
     * Finds object in list and makes it current.
     *
     * @param list          list that includes searched Object
     * @param content       Object to be searched
     * @param <ContentType> Type of Objects in list and searched Object
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
     * @param path the name of the top folder
     * @return an Array of Buffered Images that can be drawn using drawTool.drawImage()
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

    public static void applyCamShake(double dt) {
        camShakeX = (duration * strength * Math.random()) * 2 - (duration * strength);
        camShakeY = (duration * strength * Math.random()) * 2 - (duration * strength);
        duration -= dt;
        if (duration < 0) duration = 0;
    }

    public static void setCamShake(double duration, double strength) {
        if (Util.strength * Util.duration < strength * duration) {
            Util.duration = duration;
            Util.strength = strength;
        }
    }

    public static double[] getCamShake() {
        return new double[]{camShakeX, camShakeY};
    }

    /**
     * Checks if two rectangle collide
     *
     * @param r1x x position of left edge from first rectangle
     * @param r1y y position of top edge from first rectangle
     * @param r1w width of first rectangle
     * @param r1h height of first rectangle
     * @param r2x x position of left edge from second rectangle
     * @param r2y y position of top edge from second rectangle
     * @param r2w width of second rectangle
     * @param r2h height of second rectangle
     * @return boolean that describes whether the rectangle collide
     */
    public static boolean rectToRectCollision(double r1x, double r1y, double r1w, double r1h, double r2x, double r2y, double r2w, double r2h) {
        return !(r1x > r2x + r2w) && !(r1x + r1w < r2x) && !(r1y > r2y + r2h) && !(r1y + r1h < r2y);
    }

    /**
     * @param x1    X position of 1st circle
     * @param y1    Y position of 1st circle
     * @param r1    radius of 1st circle
     * @param x2    X position of 2nd circle
     * @param y2    Y position of 2nd circle
     * @param r2    radius of 2nd circle
     * @param depth depth of circle intersection till they are considered colliding
     * @return
     */
    public static boolean circleToCircleCollision(double x1, double y1, double r1, double x2, double y2, double r2, double depth) {
        double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        return r1 + r2 - depth > distance;
    }

    /**
     * This is useful for one specific snip of code in QueueEnemy
     *
     * @param number we want to check if is zero
     * @return if number 0 we return 0 else we return 1
     */
    public static double isNumberNotZero(double number) {
        if (number == 0) {
            return 0;
        }
        return 1;
    }

    public static double DotProduct(double aX, double aY, double bX, double bY) {
        return aX * bX + aY * bY;
    }

    /**
     * calculates weather a circle and line are colliding or not
     * @param p1X X coordinate of point 1
     * @param p1Y Y coordinate of point 1
     * @param p2X X coordinate of point 2
     * @param p2Y Y coordinate of point 2
     * @param cX X coordinate of circle
     * @param cY Y coordinate of circle
     * @param r radius of circle
     * @return whether the circle and the line are intersecting
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
            // a are nonnegative.
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

            // here t1 didn't intersect so we are either started
            // inside the sphere or completely past it
            if (t2 >= 0 && t2 <= 1) {
                // ExitWound
                return true;
            }

            // no intn: FallShort, Past, CompletelyInside
            return false;
        }
    }

}


