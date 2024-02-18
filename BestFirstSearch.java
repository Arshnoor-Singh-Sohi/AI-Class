// Java program to implement Best First Search using priority
// queue
import java.util.ArrayList;
import java.util.PriorityQueue;

public class BestFirstSearch {
    static ArrayList<ArrayList<edge>> adj = new ArrayList<>();

    // Function for adding edges to graph
    static class edge implements Comparable<edge> {
        int v, cost;

        edge(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }

        @Override
        public int compareTo(edge o) {
            if (o.cost < cost) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public BestFirstSearch(int v) {
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }
    }

    // Function For Implementing Best First Search
    // Gives output path having the lowest cost
    static void best_first_search(int source, int target, int v) {

        // MIN HEAP priority queue
        PriorityQueue<edge> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[v];
        visited[source] = true;

        // sorting in pq gets done by the first value of the pair
        pq.add(new edge(source, -1));
        while (!pq.isEmpty()) {
            int x = pq.poll().v;

            // Displaying the path having the lowest cost
            System.out.print(x + " ");
            if (target == x) {
                break;
            }
            for (edge adjacentNodeEdge : adj.get(x)) {
                if (!visited[adjacentNodeEdge.v]) {
                    visited[adjacentNodeEdge.v] = true;
                    pq.add(adjacentNodeEdge);
                }
            }
        }
    }

    void addedge(int u, int v, int cost) {
        adj.get(u).add(new edge(v, cost));
        adj.get(v).add(new edge(u, cost));
    }

    // Driver code to test above methods
    public static void main(String args[]) {

        // No. of Nodes
        int v = 14;

        // The nodes shown in the above example (by alphabets) are
        // implemented using integers addedge(x,y,cost);
        BestFirstSearch graph = new BestFirstSearch(v);
        // graph.addedge(0, 1, 3);
        // graph.addedge(0, 2, 6);
        // graph.addedge(0, 3, 5);
        // graph.addedge(1, 4, 9);
        // graph.addedge(1, 5, 8);
        // graph.addedge(2, 6, 12);
        // graph.addedge(2, 7, 14);
        // graph.addedge(3, 8, 7);
        // graph.addedge(8, 9, 5);
        // graph.addedge(8, 10, 6);
        // graph.addedge(9, 11, 1);
        // graph.addedge(9, 12, 10);
        // graph.addedge(9, 13, 2);

        graph.addedge(0, 1, 12);
        graph.addedge(0, 2, 4);
        graph.addedge(1, 3, 7);
        graph.addedge(1, 4, 3);
        graph.addedge(2, 5, 8);
        graph.addedge(2, 6, 2);
        graph.addedge(5, 7, 4);
        graph.addedge(6, 8, 9);
        graph.addedge(6, 9, 0);

        int source = 0;
        int target = 9;

        // Function call
        best_first_search(source, target, v);
    }
}