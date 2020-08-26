package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Set;

public class ReachableSpace {
    private TETile[][] world;
    private Position p;
    private Set<Position> floorSet = new HashSet<>();

    /**
     * construct function.
     * @param pi needs to be a FLOOR position.
     * @param worldi
     */
    public ReachableSpace(Position pi, TETile[][] worldi) {
        this.p = pi;
        this.world = worldi;
        floorSet.add(p);
    }

    public Set<Position> getFloorSet() {
        return floorSet;
    }

    public void addConnectedFloor() {
        // Set<Position> temp = new HashSet<>();
        Set<Position> visited = new HashSet<>();

        for (Position someP : floorSet) {
            if (visited.size() == floorSet.size()) {
                break;
            }

            if (!visited.contains(someP)) {
                Position[] surround = someP.surrounding();
                for (int i = 1; i < surround.length; i += 2) {
                    if (valid(surround[i])
                            && (world[surround[i].x][surround[i].y]
                            == Tileset.FLOOR)) {
                        floorSet.add(surround[i]);
                    }
                }
                visited.add(someP);
            }
        }

    }

    public void addAllFloor(int count) {
        if (count == 1000) {
            return;
        }

        Set<Position> temp = new HashSet<>();
        for (Position someP : floorSet) {
            Position[] surround  = someP.surrounding();
            for (int i = 1; i < surround.length; i += 2) {
                if (valid(surround[i])
                        && world[surround[i].x][surround[i].y]
                        == Tileset.FLOOR) {
                    temp.add(surround[i]);
                }
            }
        }
        floorSet.addAll(temp);
        count += 1;
        addAllFloor(count);
    }

    public boolean valid(Position someP) {
        return someP.x >= 0 && someP.x < world.length
                && someP.y >= 0 && someP.y <= world[0].length;
    }

}
