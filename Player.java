// import the API.
// See xxx for the javadocs.
import bc.*;

import java.util.Random;

public class Player {
    public static PlanetMap EarthMap;
    public static Random random;
    public static GameController gc = new GameController();


    public static void main(String[] args) {
        random = new Random(6137);

        // MapLocation is a data structure you'll use a lot.
        MapLocation loc = new MapLocation(Planet.Earth, 10, 20);
        System.out.println("loc: " + loc + ", one step to the Northwest: " + loc.add(Direction.Northwest));
        System.out.println("loc x: " + loc.getX());

        // One slightly weird thing: some methods are currently static methods on a static class called bc.
        // This will eventually be fixed :/
        System.out.println("Opposite of " + Direction.North + ": " + bc.bcDirectionOpposite(Direction.North));

        // Connect to the manager, starting the game
        EarthMap = gc.startingMap(Planet.Earth);

        // Direction is a normal java enum.
        Direction[] directions = Direction.values();

        MapLocation goTo = randomLocation();

        while (true) {
            // System.out.println("Current round: " + gc.round());
            // VecUnit is a class that you can think of as similar to ArrayList<Unit>, but immutable.
            VecUnit units = gc.myUnits();
            for (int i = 0; i < units.size(); i++) {
                Unit unit = units.get(i);
                MapLocation unitLocation = unit.location().mapLocation();

                if (unitLocation.equals(goTo)) {
                    System.out.println("Round: " + gc.round());
                    System.out.println("Reached Goal!! at x: " + goTo.getX() + ", y: " + goTo.getY());
                    goTo = randomLocation();
                }

                Direction direction = BugAlgo.bug0(unit, goTo, EarthMap);
                if (gc.isMoveReady(unit.id()) && gc.canMove(unit.id(), direction)) {
                    gc.moveRobot(unit.id(), direction);
                }
                else {
                    for (int j = 0; j < 8; j++) {
                        if (gc.isMoveReady(unit.id()) && gc.canMove(unit.id(), directions[j])) {
                            System.out.println("RandomMove");
                            gc.moveRobot(unit.id(), directions[j]);
                            break;
                        }
                    }
                }
            }
            // Submit the actions we've done, and wait for our next turn.
            gc.nextTurn();
        }
    }

    public static MapLocation randomLocation() {
        MapLocation location;
        do {
            int x = random.nextInt((int) EarthMap.getWidth());
            int y = random.nextInt((int) EarthMap.getHeight());
            location = new MapLocation(Planet.Earth, x, y);
        } while (EarthMap.isPassableTerrainAt(location) == 0);

        System.out.println("RandomLocation: " + location.getX() + " " + location.getY());

        return location;
    }
}