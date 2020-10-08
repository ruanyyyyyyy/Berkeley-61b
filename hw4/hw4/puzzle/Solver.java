package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {

    private class SearchNode {
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
    }

    MinPQ<SearchNode> pq;
    SearchNode finalNode;

    public Solver(WorldState initial) {
        pq = new MinPQ<>(new NodeComparator());
        SearchNode initNode = new SearchNode(initial, 0, null);
        pq.insert(initNode);

    }

    public int moves() {
        SearchNode X = pq.delMin();
        if (X.ws.estimatedDistanceToGoal() == 0) {
            finalNode = X;
            return X.madeMoves;
        }
        for(WorldState w: X.ws.neighbors()) {
            if (X.prev==null || !X.prev.ws.equals(w)) {
                SearchNode newNode = new SearchNode(w, X.madeMoves + 1, X);
                pq.insert(newNode);

            }

        }
        return moves();
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

    class NodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode s1, SearchNode s2) {
            return (s1.priority) - (s2.priority);
        }
    }
}
