package byog.Core;

import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

public class MapGenerator {
    public TERenderer ter;
    public static int width;
    public static int height;
    private TETile[][] finalWorld;
    public long seed;
    public Random random;
    public Set<Position> largestFloorSet;

    public MapGenerator(String s,  TERenderer t, int w, int h) {

        long temp = 0;
        /* read seed from string s */
        for (int i = 1; i < s.length() - 1; i += 1) {
            if (s.charAt(i) == 'S' || s.charAt(i) == 's') {
                break;
            }
            temp = Character.getNumericValue(s.charAt(i)) + temp * 10;
        }
        seed = temp;

        this.ter = t;
        width = w;
        height = h;
        ter.initialize(width, height);
        finalWorld = new TETile[width][height];
        random = new Random(seed);

    }

    public MapGenerator(TETile[][] world, TERenderer t) {
        this.finalWorld = world;
        this.ter = t;
        width = world.length;
        height = world[0].length;

        ter.initialize(width, height);

    }

    /**
     * initialize the world with nothing tile.
     */
    public void initWorld() {
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j+= 1) {
                finalWorld[i][j] = Tileset.NOTHING;
            }
        }
    }

    public TETile[][] getFinalWorld() {
        return finalWorld;
    }


    /** Create one rectangular room.
     *  Position p -> bottom left of the room;
     *  int w -> room width + 2(left and right walls);
     *  int h -> room height + 2(upper and lower walls). */
    public void newRoom(Position p, int w, int h) {
        int xCoord;
        int yCoord;
        for (int i = 0; i < w; i += 1) {
            for (int j = 0; j < h; j += 1) {
                xCoord = p.x + i;
                yCoord = p.y + j;
                if ( i==0 || i==w-1 || j==0 || j == h-1) {
                    if (!intersect(xCoord, yCoord)) {
                        finalWorld[xCoord][yCoord] = Tileset.WALL;
                    }
                } else {
                    finalWorld[xCoord][yCoord] = Tileset.FLOOR;
                }
            }
        }
    }

    public void newHallway(Position p, int len, char c) {
        if (c == 'v') {
            newRoom(p, 3, len);
        } else if (c == 'h') {
            newRoom(p, len, 3);
        } else {
            throw new NoSuchElementException();
        }
    }

    public boolean intersect(int x, int y) {
        return finalWorld[x][y] == Tileset.FLOOR;
    }

    public Position generateRandomPos(int spaceNeeded) {
        return new Position(random.nextInt(width - spaceNeeded), random.nextInt(height - spaceNeeded));
    }

    /**
     * type, r: room, h: hallway.
     * @param pos position
     * @param available available spaces
     * @param type type r:room h:hallway
     * @return random length
     */
    public int generateRandomLen(int pos, int available, char type) {
        if (type == 'r') {
            int len = random.nextInt(available - pos);
            while (len < 4 || len > available / 4) {
                len = random.nextInt(available - pos);
            }
            return len;
        } else if (type == 'h') {
            int len = random.nextInt(available - pos);
            while (len < 3 || len > available / 2) {
                len = random.nextInt(available - pos);
            }
            return len;
        } else {
            throw new IllegalArgumentException("type should be r(room) or h(hallway).");
        }
    }

    public void multipleRoom(int count) {
        for (int i = 0; i < count; i += 1) {
            Position p = generateRandomPos(4);
            newRoom(p, generateRandomLen(p.x, width, 'r'), generateRandomLen(p.y, height, 'r'));
        }
    }

    public void multipleHallways(int nVertical, int nHorizon) {
        for (int i = 0; i < nVertical; i += 1) {
            Position p = generateRandomPos(3);
            newHallway(p, generateRandomLen(p.y, height, 'h'), 'v');
        }
        for (int j = 0; j < nHorizon; j += 1) {
            Position p = generateRandomPos(3);
            newHallway(p, generateRandomLen(p.x, width, 'h'), 'h');
        }
    }

    public Set<Position> findRandomReachableSpace() {
        Position p = generateRandomPos(0);
        while (finalWorld[p.x][p.y] != Tileset.FLOOR) {
            p = generateRandomPos(0);
        }
        ReachableSpace reachS = new ReachableSpace(p, finalWorld);
        //reachS.addConnectedFloor();
        reachS.addAllFloor(0);
        return reachS.getFloorSet();
    }

    public int findLargestFloorSet() {
        Set<Position> randomFloorSet = findRandomReachableSpace();
        int r = randomFloorSet.size();

        while (r < width * height * 0.1) {
            randomFloorSet = findRandomReachableSpace();
            r = randomFloorSet.size();
        }
        for (int i = 0; i < 10; i += 1) {
            Set<Position> temp = findRandomReachableSpace();
            if (temp.size() > randomFloorSet.size()) {
                randomFloorSet = temp;
            }
        }
        largestFloorSet = randomFloorSet;
        r = largestFloorSet.size();
        return r;
    }

    public void deleteFragment() {
        Fragment fg = new Fragment(largestFloorSet, finalWorld);
        fg.findDelFloorFrag();
        fg.findDelWallFrag();
        fg.enclosure();
    }



    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        int w = 60;
        int h = 50;
        MapGenerator m = new MapGenerator(args[0], ter, w, h);
        m.initWorld();
        m.multipleRoom(10);
        m.multipleHallways(20,40);
        m.findLargestFloorSet();
        m.deleteFragment();

        m.ter.renderFrame(m.getFinalWorld());
    }

}
