package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        WorldState ws;
        int madeMoves;
        SearchNode prev;
        int priority;


        public SearchNode(WorldState ws, int madeMoves, SearchNode prev) {
            this.ws = ws;
            this.madeMoves = madeMoves;
            this.prev = prev;
            this.priority = madeMoves + ws.estimatedDistanceToGoal();
        }

        public int compareTo(SearchNode node) {
            return this.priority - node.priority;
        }
    }

    private MinPQ<SearchNode> pq;
    private SearchNode finalNode;

    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        SearchNode initNode = new SearchNode(initial, 0, null);
        pq.insert(initNode);

        while (!pq.isEmpty()) {
            SearchNode X = pq.delMin();
            if (X.ws.isGoal()) {
                finalNode = X;
                break;
            }
            for (WorldState w: X.ws.neighbors()) {
                if (X.prev == null || !X.prev.ws.equals(w)) {
                    SearchNode newNode = new SearchNode(w, X.madeMoves + 1, X);
                    pq.insert(newNode);
                }
            }
        }
    }


    public int moves() {
        return finalNode.madeMoves;
    }

    public Iterable<WorldState> solution() {
        List<WorldState> l = new ArrayList<>();
        l.add(finalNode.ws);
        SearchNode prevNode = finalNode.prev;
        while (prevNode != null) {
            l.add(0, prevNode.ws);
            prevNode = prevNode.prev;
        }
        return l;
    }


}
