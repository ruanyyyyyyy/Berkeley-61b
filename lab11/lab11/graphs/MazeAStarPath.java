package lab11.graphs;
import edu.princeton.cs.algs4.IndexMinPQ;


/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int targetX, targetY;
    private IndexMinPQ<Integer> pq;
    private int[] keys;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        this.targetX = targetX;
        this.targetY = targetY;
        keys = new int[maze.V()];
        for (int i = 0; i < maze.V(); i += 1) {
            keys[i] = Integer.MAX_VALUE;
        }
        keys[s] = 0;
        pq = new IndexMinPQ<>(maze.V());
        pq.insert(s, keys[s]);
        marked[s] = true;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int coordX = maze.toX(v);
        int coordY = maze.toY(v);
        return Math.abs(coordX - targetX) + Math.abs(coordY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        while(!pq.isEmpty()) {
            int v = pq.delMin();
            if (v == t) {
                targetFound = true;
                return;
            }
            for (int w: maze.adj(v)) {
                if (keys[w] > distTo[v] + 1 + h(w)) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    keys[w] = distTo[v] + 1 + h(w);
                    if (pq.contains(w)) {
                        pq.decreaseKey(w, keys[w]);
                    } else {
                        pq.insert(w, keys[w]);
                    }
                    marked[w] = true;
                    announce();
                }


            }
        }


    }

    @Override
    public void solve() {
        astar(s);
    }

}

