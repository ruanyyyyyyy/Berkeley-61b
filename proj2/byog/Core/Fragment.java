package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Set;

public class Fragment {
    TETile[][] world;
    Set<Position> largestFloorSet;
    Set<Position> floorFrag = new HashSet<>();
    Set<Position> wallFrag = new HashSet<>();

    public Fragment(Set<Position> floorset, TETile[][] finalworld) {
        largestFloorSet = floorset;
        world = finalworld;
    }


    public void findDelFloorFrag() {
        for (int i = 0; i < world.length; i += 1) {
            for (int j = 0; j < world[0].length; j += 1) {
                Position p = new Position(i, j);
                if (world[i][j] == Tileset.FLOOR && (!largestFloorSet.contains(p))) {
                    floorFrag.add(p);
                    world[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    public void findDelWallFrag() {
        for (Position p : floorFrag) {
            Position[] ps = p.surrounding();
            for (int i = 0; i < ps.length; i += 1) {
                if (valid(ps[i]) && world[ps[i].x][ps[i].y] == Tileset.WALL) {
                    world[ps[i].x][ps[i].y] = Tileset.NOTHING;
                }
            }
        }
    }


    public void enclosure() {
        for (Position p : largestFloorSet) {
            Position[] ps = p.surrounding();
            for (int i = 0; i < ps.length; i += 1) {
                if (valid(ps[i]) && world[ps[i].x][ps[i].y] == Tileset.NOTHING) {
                    world[ps[i].x][ps[i].y] = Tileset.WALL;
                }
            }

        }

    }

    public boolean valid(Position p) {
        return p.x >= 0 && p.x < world.length && p.y >= 0 && p.y < world[0].length;
    }
}
