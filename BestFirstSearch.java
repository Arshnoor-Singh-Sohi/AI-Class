// import java.util.*;

// class Node implements Comparable<Node> {
//     int x, y; // Coordinates of the node
//     int cost; // Cost from the start node to this node
//     int heuristic; // Heuristic value (estimated cost to reach the goal)
    
//     public Node(int x, int y, int cost, int heuristic) {
//         this.x = x;
//         this.y = y;
//         this.cost = cost;
//         this.heuristic = heuristic;
//     }

//     @Override
//     public int compareTo(Node other) {
//         // Compare nodes based on the sum of cost and heuristic
//         return Integer.compare(this.cost + this.heuristic, other.cost + other.heuristic);
//     }
// }

// public class BestFirstSearch {

//     static final int INF = Integer.MAX_VALUE;
    
//     public static int bestFirstSearch(int[][] grid, int[] start, int[] goal) {
//         int rows = grid.length;
//         int cols = grid[0].length;

//         // Define possible moves (up, down, left, right)
//         int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

//         PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
//         boolean[][] visited = new boolean[rows][cols];

//         priorityQueue.offer(new Node(start[0], start[1], 0, heuristic(start, goal)));

//         while (!priorityQueue.isEmpty()) {
//             Node current = priorityQueue.poll();

//             if (current.x == goal[0] && current.y == goal[1]) {
//                 return current.cost; // Goal reached
//             }

//             if (!visited[current.x][current.y]) {
//                 visited[current.x][current.y] = true;

//                 for (int[] dir : directions) {
//                     int newX = current.x + dir[0];
//                     int newY = current.y + dir[1];

//                     if (isValid(newX, newY, rows, cols) && !visited[newX][newY] && grid[newX][newY] != 0) {
//                         priorityQueue.offer(new Node(newX, newY, current.cost + 1, heuristic(new int[]{newX, newY}, goal)));
//                     }
//                 }
//             }
//         }

//         return INF; // Goal not reachable
//     }

//     private static int heuristic(int[] current, int[] goal) {
//         // Manhattan distance heuristic
//         return Math.abs(current[0] - goal[0]) + Math.abs(current[1] - goal[1]);
//     }

//     private static boolean isValid(int x, int y, int rows, int cols) {
//         return x >= 0 && x < rows && y >= 0 && y < cols;
//     }

//     public static void main(String[] args) {
//         int[][] grid = {
//             {1, 1, 1, 1, 1},
//             {1, 0, 1, 0, 1},
//             {1, 0, 1, 0, 1},
//             {1, 1, 1, 1, 1},
//         };

//         int[] start = {0, 0};
//         int[] goal = {3, 4};

//         int result = bestFirstSearch(grid, start, goal);

//         if (result != INF) {
//             System.out.println("Shortest path cost: " + result);
//         } else {
//             System.out.println("Goal not reachable.");
//         }
//     }
// }


import java.util.*;

class TreeNode implements Comparable<TreeNode> {
    int value;
    int heuristic;
    List<TreeNode> children;

    public TreeNode(int value, int heuristic) {
        this.value = value;
        this.heuristic = heuristic;
        this.children = new ArrayList<>();
    }

    @Override
    public int compareTo(TreeNode other) {
        // Compare nodes based on the sum of value and heuristic
        return Integer.compare(this.value + this.heuristic, other.value + other.heuristic);
    }
}

public class BestFirstSearch {

    static final int INF = Integer.MAX_VALUE;

    public static int bestFirstSearch(TreeNode root, int goalValue) {
        PriorityQueue<TreeNode> priorityQueue = new PriorityQueue<>();
        boolean[] visited = new boolean[1000]; // Assuming a maximum of 1000 nodes

        priorityQueue.offer(root);

        while (!priorityQueue.isEmpty()) {
            TreeNode current = priorityQueue.poll();

            if (current.value == goalValue) {
                return current.value; // Goal reached
            }

            if (!visited[current.value]) {
                visited[current.value] = true;

                for (TreeNode child : current.children) {
                    if (!visited[child.value]) {
                        priorityQueue.offer(child);
                    }
                }
            }
        }

        return INF; // Goal not reachable
    }

    public static void main(String[] args) {
        // Example Tree:
        //        5
        //       / \
        //      3   8
        //     / \   \
        //    1   4   10

        TreeNode root = new TreeNode(5, heuristic(5, 10));
        root.children.add(new TreeNode(3, heuristic(3, 10)));
        root.children.add(new TreeNode(8, heuristic(8, 10)));
        root.children.get(0).children.add(new TreeNode(1, heuristic(1, 10)));
        root.children.get(0).children.add(new TreeNode(4, heuristic(4, 10)));
        root.children.get(1).children.add(new TreeNode(10, heuristic(10, 10)));

        int goalValue = 10;
        int result = bestFirstSearch(root, goalValue);

        if (result != INF) {
            System.out.println("Goal reached with value: " + result);
        } else {
            System.out.println("Goal not reachable.");
        }
    }

    private static int heuristic(int currentValue, int goalValue) {
        // Simple heuristic: Absolute difference between current and goal values
        return Math.abs(currentValue - goalValue);
    }
}
