import bc.*;

import java.util.HashMap;
import java.util.Map;

public class BugAlgo {
    public static Map<Integer, Direction> obstacleDirection = new HashMap<Integer, Direction>();

    public static Direction bug0(Unit unit, MapLocation goal, PlanetMap map) {
        MapLocation unitLocation = unit.location().mapLocation();
        Direction dirToGoal;

        // add unit to the HashMap
        if (!obstacleDirection.containsKey(unit.id())) {
            obstacleDirection.put(unit.id(), Direction.Center);
        }


        // Direction stored in HashMap
        Direction dirToObstacle = obstacleDirection.get(unit.id());

        if (dirToObstacle != Direction.Center) {
            // There is an obstacle
            dirToGoal = dirToObstacle;
            if (map.isPassableTerrainAt(unitLocation.add(unitLocation.directionTo(goal))) == 1) {
                //dirToGoal = unitLocation.directionTo(goal);
            }
        }
        else {
            // There is no obstacle registered
            dirToGoal = unitLocation.directionTo(goal);
        }

        if (map.isPassableTerrainAt(unitLocation.add(dirToGoal)) == 1 && Player.gc.isOccupiable(unitLocation.add(dirToGoal)) == 1) {
            obstacleDirection.put(unit.id(), Direction.Center);

            return dirToGoal;
        }

        if (map.isPassableTerrainAt(unitLocation.add(dirToGoal)) == 0) {
            obstacleDirection.put(unit.id(), dirToGoal);
        }
        for (int number : new int[]{-1, -2}) {
            Direction newDirection = DirectionIterator.nextDirection(dirToGoal, number);

            MapLocation candidateLoc = unitLocation.add(newDirection);
            if (map.isPassableTerrainAt(candidateLoc) == 1 && Player.gc.isOccupiable(candidateLoc) == 1) {
                //System.out.println("Direction: " + newDirection.swigValue());
                return newDirection;
            }

            if (map.isPassableTerrainAt(candidateLoc) == 0) {
                obstacleDirection.put(unit.id(), newDirection);
            }
        }
        System.out.println("no direction found!");
        return Direction.Center;
    }
}
