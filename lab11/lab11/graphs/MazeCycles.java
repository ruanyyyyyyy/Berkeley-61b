package lab11.graphs;
import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Stack<Integer> st = new Stack<>();
    private Maze maze;
    private boolean findCycle = false;
    private int[] comeFrom;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        comeFrom = new int[maze.V()];
        comeFrom[0] = 0;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(0);

    }

    // Helper methods go here
    private void dfs(int v) {
        marked[v] = true;
        announce();

        if (findCycle) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                comeFrom[w] = v;
                announce();
                dfs(w);
                if (findCycle) {
                    return;
                }
            } else {
                if (w != comeFrom[v]) {
                    findCycle = true;
                    edgeTo[w] = v;
                    announce();
                    while (v != w) {
                        edgeTo[v] = comeFrom[v];
                        v = edgeTo[v];
                        announce();
                    }

                    return;
                }
            }
        }
    }



}

