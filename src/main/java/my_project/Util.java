package my_project;

import KAGO_framework.model.abitur.datenstrukturen.List;

public class Util {
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
