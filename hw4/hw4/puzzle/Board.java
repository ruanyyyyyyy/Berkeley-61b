package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private int[][] board;
    private final int BLANK = 0;
    private final int size;


    public Board(int[][]tiles) {
        int N = tiles.length;
        board = new int[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                board[i][j] = tiles[i][j];
            }
        }
        size = board.length;

    }

    public int tileAt(int i, int j) {

        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return board[i][j];
    }

    public int size() {
        return size;
    }

    /**
     * return the neighbors of the current board
     * @source: http://joshh.ug/neighbors.html
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /**
     * hamming estimate: The number of tiles in the wrong position.
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if (tileAt(i, j) != (i * size + j + 1) && tileAt(i, j) != BLANK) {
                    count += 1;
                }
            }
        }
        return count;
    }

    /**
     * manhattan estimate : The sum of the Manhattan distances
     * (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions.
     */
    public int manhattan() {
        int count = 0;

        int rightI, rightJ, curV;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                curV = tileAt(i, j);
                if (curV == 0) {
                    continue;
                }
                rightI = (curV - 1) / size;
                rightJ = (curV - 1) % size;
                count += Math.abs(rightI - i) + Math.abs(rightJ - j);
            }
        }
        return count;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Returns true if this board's tile values are the same  position as y's
     */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }
        Board boardy = (Board) y;
        if (this.size() != boardy.size()) {
            return false;
        }
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if (tileAt(i, j) != boardy.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 1;
    }


    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
