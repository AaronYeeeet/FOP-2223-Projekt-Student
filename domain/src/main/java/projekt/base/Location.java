package projekt.base;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A tuple for the x- and y-coordinates of a point.
 */
@SuppressWarnings("ClassCanBeRecord")
public final class Location implements Comparable<Location> {

    //test
    //test2
    //test3
    //420
    //test4

    private final static Comparator<Location> COMPARATOR =
        Comparator.comparing(Location::getX).thenComparing(Location::getY);

    private final int x;
    private final int y;

    /**
     * Instantiates a new {@link Location} object using {@code x} and {@code y} as coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of this location.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this location.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Adds the coordinates of this location and the other location and returns a new
     * {@link Location} object with the resulting coordinates.
     *
     * @param other the other {@link Location} object to get the second set of coordinates from
     * @return a new {@link Location} object with the sum of coordinates from both locations
     */
    public Location add(Location other) {
        return new Location(x + other.x, y + other.y);
    }

    /**
     * Subtracts the coordinates of this location from the other location and returns a new
     * {@link Location} object with the resulting coordinates.
     *
     * @param other the other {@link Location} object to get the second set of coordinates from
     * @return a new {@link Location} object with the difference of coordinates from both locations
     */
    public Location subtract(Location other) {
        return new Location(x - other.x, y - other.y);
    }

    /**
     * Compares the coordinates of two Locations.
     * @param o the Location to be compared.
     * @return 0 if the Locations are equal, a negative integer if the x coordinate is smaller than the one to be compared or if both x coordinates are equal and the y coordinate is smaller than the one compared to, a positive integer otherwise.
     */
    @Override
    public int compareTo(@NotNull Location o) {
        return COMPARATOR.compare(this,o);              //H1.1
    }

    /**
     * Creates a unique hash code for all Coordinates with x and y from -1024 to 1023.
     * @return the hash code.
     */
    @Override
    public int hashCode() {                             //H1.2
        int r = 0;
        if (x<=1023&&x>=-1024&&y<=1023&&y>=-1024) {
            int xHash = x + 1024;
            int yHash = y + 1024;
            r = xHash * 2048 + yHash;
        }
        return r;
    }

    @Override
    public boolean equals(Object o) {                   //H1.3
        if (o==null||!(o instanceof Location))
            return false;
        return this.hashCode()==o.hashCode();
    }

    @Override
    public String toString() {                          //H1.4
        return "("+x+","+y+")";
    }
}
