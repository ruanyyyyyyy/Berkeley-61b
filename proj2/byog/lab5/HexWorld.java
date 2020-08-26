package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final long seed = 2873123;
    private static final Random RANDOM = new Random(seed);


    /**
     * calculate the width of hexagon, s is the size, i is the number of current row.
     */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return s + 2 * effectiveI;

    }

    /**
     * calculate the xOffset of each row. the start position is the left of the bottom line:0.
     */
    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /**
     * position contains x and y
     */
    public static class Position{
        public int x;
        public int y;
        public Position(int xp, int yp) {
            x = xp;
            y = yp;
        }
    }

    /**
     * add Row nees to know world, Position, width, tile
     * @param world
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        int thisRowY = p.y;
        for(int x = 0; x < width; x += 1) {
            int thisRowX = p.x + x;
            world[thisRowX][thisRowY] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
        /* xCord, yCoord*/
    }

    /**
     * addHexagon
     * @param world
     * @param p
     * @param s
     * @param t
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("s needs to be larger or equal to 2");
        }
        for(int y = 0; y < 2 * s; y += 1) {
            int thisRowY = p.y + y;

            int xRowStart = p.x + hexRowOffset(s, y);
            Position startPos = new Position(xRowStart, thisRowY);
            int width = hexRowWidth(s, y);
            addRow(world, startPos, width, t);
        }
    }

    /**
     * calculate the start position of the top right neighbor of the current hexagon.
     * @return topRightPos
     */
    public static Position topRightNeighbor(Position currenPos, int s) {
        int yCoord = currenPos.y + s;
        int xOffset = hexRowOffset(s, s);
        int rowWidth = hexRowWidth(s, s);
        int xCoord = currenPos.x + xOffset + rowWidth;
        return  new Position(xCoord, yCoord);
    }

    public static Position bottomRightNeighbor(Position currenPos, int s) {
        int yCoord = currenPos.y - s;
        int xOffset = hexRowOffset(s, s);
        int xCoord = currenPos.x + s - xOffset;
        return new Position(xCoord, yCoord);
    }

    public static Position bottomNeighbor(Position currenPos, int s) {
        int yCoord = currenPos.y - 2 * s;
        Position bottomPos = new Position(currenPos.x, yCoord);
        return bottomPos;
    }

    public static void addBottomHexagon(TETile[][]world, Position bottemP, int s) {
        TETile t = randomTile();
        addHexagon(world, bottemP, s, t);
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            default: return Tileset.GRASS;
        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        int WIDTH = 50;
        int HEIGHT = 50;
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        int size = 3;
        TETile t = Tileset.FLOWER;
        Position p = new Position(10,30);
        addHexagon(world, p, size, t);

        Position currentP = p;
        for (int i = 0; i < 2; i += 1) {
            Position bottemP = bottomNeighbor(currentP, size);
            addBottomHexagon(world, bottemP, size);
            currentP = bottemP;
        }

        currentP = topRightNeighbor(p, size);
        p = currentP;
        addHexagon(world, p, size, t);
        for (int i = 0; i < 3; i += 1) {
            Position bottemP = bottomNeighbor(currentP, size);
            addBottomHexagon(world, bottemP, size);
            currentP = bottemP;
        }

        currentP = topRightNeighbor(p, size);
        p = currentP;
        addHexagon(world, p, size, t);
        for (int i = 0; i < 4; i += 1) {
            Position bottemP = bottomNeighbor(currentP, size);
            addBottomHexagon(world, bottemP, size);
            currentP = bottemP;
        }

        currentP = bottomRightNeighbor(p, size);
        p = currentP;
        addHexagon(world, p, size, t);
        for (int i = 0; i < 3; i += 1) {
            Position bottemP = bottomNeighbor(currentP, size);
            addBottomHexagon(world, bottemP, size);
            currentP = bottemP;
        }

        currentP = bottomRightNeighbor(p, size);
        p = currentP;
        addHexagon(world, p, size, t);
        for (int i = 0; i < 2; i += 1) {
            Position bottemP = bottomNeighbor(currentP, size);
            addBottomHexagon(world, bottemP, size);
            currentP = bottemP;
        }



        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (world[x][y] == null) {
                    world[x][y] = Tileset.NOTHING;
                }
            }
        }

        // draws the world to the screen
        ter.renderFrame(world);
    }

    @Test
    public void testHexRowWidth() {
        assertEquals(3, hexRowWidth(3,5));
        assertEquals(7, hexRowWidth(3,3));
        assertEquals(7, hexRowWidth(3,2));
        assertEquals(4, hexRowWidth(2,1));
        assertEquals(4, hexRowWidth(2,2));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3,0));
        assertEquals(-1, hexRowOffset(3,1));
        assertEquals(-2, hexRowOffset(3,2));
        assertEquals(-2, hexRowOffset(3,3));
        assertEquals(-1, hexRowOffset(3,4));
        assertEquals(0, hexRowOffset(3,5));
    }



}
