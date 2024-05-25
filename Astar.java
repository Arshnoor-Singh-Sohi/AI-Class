// import java.util.Comparator;
// import java.util.HashSet;
// import java.util.PriorityQueue;

// class Node {
//     int x, y;
//     int g; // Cost from start node to current node
//     int h; // Heuristic value (estimated cost from current node to goal node)
//     int f; // f = g + h
//     Node parent; // Parent node

//     public Node(int x, int y, int g, int h) {
//         this.x = x;
//         this.y = y;
//         this.g = g;
//         this.h = h;
//         this.f = g + h;
//     }
// }

// public class Astar {
// // 
//     // private static final int[][] grid = {
//     //         {0, 0, 0, 0, 0},
//     //         {0, 1, 1, 1, 0},
//     //         {0, 1, 0, 1, 0},
//     //         {0, 1, 1, 1, 0},
//     //         {0, 0, 0, 0, 0}
//     // };

//     private static final int[][] grid = {
//                 {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
//                 {1, 1, 1, 0, 1, 1, 1, 0, 1, 1},
//                 {1, 1, 1, 0, 1, 1, 0, 1, 0, 1},
//                 {0, 0, 1, 0, 1, 0, 0, 0, 0, 1},
//                 {1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
//                 {1, 0, 1, 1, 1, 1, 0, 1, 0, 0},
//                 {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
//                 {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
//                 {1, 1, 1, 0, 0, 0, 1, 0, 0, 1}
//         };

//     // private static final int startX = 0;
//     // private static final int startY = 0;
//     // private static final int goalX = 4;
//     // private static final int goalY = 4;

//     private static final int startX = 8;
//     private static final int startY = 0;
//     private static final int goalX = 0;
//     private static final int goalY = 0;

//     private static final int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

//     public static void main(String[] args) {
//         Node result = aStarSearch();
//         if (result != null) {
//             System.out.println("Path found!");
//             printPath(result);
//         } else {
//             System.out.println("Path not found.");
//         }
//     }

//     private static Node aStarSearch() {
//         PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
//         HashSet<Node> closedSet = new HashSet<>();

//         Node startNode = new Node(startX, startY, 0, heuristic(startX, startY));
//         openSet.add(startNode);

//         while (!openSet.isEmpty()) {
//             Node current = openSet.poll();

//             if (current.x == goalX && current.y == goalY) {
//                 return current; // Goal reached
//             }

//             closedSet.add(current);

//             for (int[] direction : directions) {
//                 int newX = current.x + direction[0];
//                 int newY = current.y + direction[1];

//                 if (isValid(newX, newY) && grid[newX][newY] != 1) {
//                     int newG = current.g + 1;
//                     int newH = heuristic(newX, newY);
//                     Node neighbor = new Node(newX, newY, newG, newH);
//                     neighbor.parent = current; // Set the parent reference

//                     if (!closedSet.contains(neighbor) && !openSet.contains(neighbor)) {
//                         openSet.add(neighbor);
//                     }
//                 }
//             }
//         }

//         return null; // No path found
//     }

//     private static int heuristic(int x, int y) {
//         // Manhattan distance heuristic
//         return Math.abs(x - goalX) + Math.abs(y - goalY);
//     }

//     private static boolean isValid(int x, int y) {
//         return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
//     }

//     private static void printPath(Node node) {
//         while (node != null) {
//             System.out.println("(" + node.x + ", " + node.y + ")");
//             node = node.parent;
//         }
//     }
// }

import java.util.*;

// Data structure representing a state in the search
class State implements Comparable<State> {
    char name;
    int gScore;  // Cost from start to current state
    int hScore;  // Heuristic estimate of cost to reach goal
    int fScore;  // Total score = gScore + hScore
    State parent;  // Pointer to parent state for backtracking

    public State(char name, int gScore, int hScore) {
        this.name = name;
        this.gScore = gScore;
        this.hScore = hScore;
        this.fScore = gScore + hScore;
        this.parent = null;
    }

    // Comparison method for priority queue
    @Override
    public int compareTo(State other) {
        return Integer.compare(this.fScore, other.fScore);  // Prioritize lower fScore
    }
}

public class Astar {

    // Heuristic function (estimated cost to reach goal)
    private static int heuristic(char state) {
        switch (state) {
            case 'S': return 5;
            case 'A': return 3;
            case 'B': return 4;
            case 'C': return 2;
            case 'D': return 6;
            case 'G': return 0;
            default: return Integer.MAX_VALUE;  // Unknown state, assign very high cost
        }
    }

    // Function to implement A* search
    private static State aStarSearch(char start, char goal) {
        Map<Character, State> states = new HashMap<>();  // Store states and their details
        PriorityQueue<State> pq = new PriorityQueue<>();  // Priority queue for open set

        // Create initial state
        State startState = new State(start, 0, heuristic(start));
        states.put(start, startState);
        pq.add(startState);

        while (!pq.isEmpty()) {
            State currentState = pq.poll();

            // Goal reached
            if (currentState.name == goal) {
                return currentState;
            }

            // Expand current state
            for (char nextState : new char[]{'A', 'B', 'C', 'D', 'G'}) {  // Explore all possible children
                // Skip invalid edges and already visited states
                if (nextState == start || states.containsKey(nextState)) {
                    continue;
                }

                int tentativeGScore = currentState.gScore + 1;  // Assume edge cost is 1 for simplicity
                if (states.get(nextState) == null || tentativeGScore < states.get(nextState).gScore) {
                    State newState = new State(nextState, tentativeGScore, heuristic(nextState));
                    newState.parent = currentState;
                    states.put(nextState, newState);
                    pq.add(newState);
                }
            }
        }

        return null;  // No path found
    }

    public static void main(String[] args) {
        char start = 'S';
        char goal = 'G';

        State goalState = aStarSearch(start, goal);

        // Print the solution path
        if (goalState != null) {
            System.out.println("Path from " + start + " to " + goal + ":");
            List<Character> path = new ArrayList<>();
            while (goalState != null) {
                path.add(goalState.name);
                goalState = goalState.parent;
            }
            Collections.reverse(path);  // Print path in reverse order (start to goal)
            for (char state : path) {
                System.out.print(state + " ");
            }
            System.out.println();
        } else {
            System.out.println("No path found.");
        }
    }
}