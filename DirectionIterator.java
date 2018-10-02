import bc.*;

import java.util.Iterator;


public class DirectionIterator {
    private static Direction[] directions = Direction.values();

    public static Direction nextDirection(Direction direction) {
        if (direction == Direction.Center) return Direction.Center;
        int dirNum = direction.swigValue();

        dirNum = Math.floorMod( dirNum+1, 8);

        return directions[dirNum];
    }

    public static Direction nextDirection(Direction direction, int  amount) {
        if (direction == Direction.Center) return Direction.Center;
        int dirNum = direction.swigValue();

        dirNum = Math.floorMod( dirNum+amount, 8);

        return directions[dirNum];
    }
}