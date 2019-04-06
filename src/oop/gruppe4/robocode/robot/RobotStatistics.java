package oop.gruppe4.robocode.robot;

import oop.gruppe4.robocode.transform.Transform;
import oop.gruppe4.robocode.transform.Vector2;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

/**
 * {@code RobotStatistics} keeps track of a robots previous {@code Transform}s.
 * @author Ask Hetland Berentsen.
 */
public class RobotStatistics {

    /**
     * The history of Transforms.
     */
    private final LinkedList<Transform> history = new LinkedList<>();

    /**
     * The amount of entries to log at once.
     */
    private final int capacity;

    /**
     * Class constructor.
     * @param capacity the amount of entries to log at once.
     */
    public RobotStatistics( int capacity ){
        this.capacity = capacity;
    }

    /**
     * Adds a {@code Transform} to the history.
     * If the amount of entries exceeds that of the capacity, the first
     * entry is removed.
     * @param stat a {@code Transform}.
     */
    public void add( @NotNull Transform stat ){

        /* If the size of history is larger than the capacity, remove the first entries */
        while( history.size() >= capacity ) history.removeFirst();

        history.add( stat );
    }

    /**
     * Gets the last entry in the history.
     * @return the last {@code Transform} in the history.
     * @throws NullPointerException if {@code history} is empty.
     * @see #history
     */
    public Transform getLast(){
        return history.getLast();
    }

    /**
     * Calculates the difference between the last and the next to last
     * {@code Transform} in the history.
     * @return the delta of two {@code Transform}s.
     * @throws NullPointerException if {@code history} is empty.
     * @see #history
     */
    public Transform getDelta(){
        /* If getDelta() is called before two entries exists, the delta is just the last index */
        if( history.size() == 1) return history.getLast();

        Transform last = history.getLast();
        Transform previous = history.get(history.size()-2);

        Vector2 positionDelta = last.getPosition().subtract(previous.getPosition());
        Vector2 trajectoryDelta = last.getTrajectory().subtract(previous.getTrajectory());
        double velocityDelta = last.getVelocity() - previous.getVelocity();

        return new Transform( positionDelta, trajectoryDelta, velocityDelta );
    }

    /**
     * Gets a specific entry in the history.
     * @param index the index to fetch from.
     * @return
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     * @see #history
     */
    public Transform get( int index ){
        return history.get( index );
    }
}
