package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Queue<Integer> q = new ArrayDeque<>();
    private int s;
    private int t;
    private boolean targetFound= false;
    private Maze maze;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        // Add more variables here!
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;

    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        marked[v] = true;
        announce();

        if (v == t) {
            targetFound = true;
        }
        if (targetFound == true) {
            return;
        }
        q.add(v);

        while(!q.isEmpty()) {
            int me = q.poll();
            if (me == t) {
                targetFound = true;
                announce();
                break;
            }
            for (int w : maze.adj(me)) {
                if (!marked[w]) {
                    q.add(w);
                    edgeTo[w] = me;
                    announce();
                    distTo[w] = distTo[me] + 1;
                    marked[w] = true;
                }
            }

        }

    }


    @Override
    public void solve() {
        bfs(s);
    }
}

