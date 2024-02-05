Certainly! Let's break down the Java code implementing the Best-First Search (BFS) algorithm on a tree using the A* (A-star) algorithm. I'll provide a step-by-step explanation:

```java
import java.util.*;

// Node structure for the tree
class TreeNode implements Comparable<TreeNode> {
    int value;              // Value of the node
    int heuristic;          // Heuristic value for A* algorithm
    List<TreeNode> children; // List of children nodes

    // Constructor to initialize the node
    public TreeNode(int value, int heuristic) {
        this.value = value;
        this.heuristic = heuristic;
        this.children = new ArrayList<>();
    }

    // Comparable interface implementation to compare nodes based on the total cost
    @Override
    public int compareTo(TreeNode other) {
        return Integer.compare(this.value + this.heuristic, other.value + other.heuristic);
    }
}

// Main class for the Best-First Search on a tree
public class BestFirstSearchTree {

    // Constant representing infinity for unreachable goals
    static final int INF = Integer.MAX_VALUE;

    // Best-First Search algorithm method
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

    // Main method
    public static void main(String[] args) {
        // Example Tree:
        //        5
        //       / \
        //      3   8
        //     / \   \
        //    1   4   10

        // Creating the tree structure
        TreeNode root = new TreeNode(5, heuristic(5, 10));
        root.children.add(new TreeNode(3, heuristic(3, 10)));
        root.children.add(new TreeNode(8, heuristic(8, 10)));
        root.children.get(0).children.add(new TreeNode(1, heuristic(1, 10)));
        root.children.get(0).children.add(new TreeNode(4, heuristic(4, 10)));
        root.children.get(1).children.add(new TreeNode(10, heuristic(10, 10)));

        int goalValue = 10;

        // Performing Best-First Search
        int result = bestFirstSearch(root, goalValue);

        // Printing the result
        if (result != INF) {
            System.out.println("Goal reached with value: " + result);
        } else {
            System.out.println("Goal not reachable.");
        }
    }

    // Heuristic function: Simple absolute difference between current and goal values
    private static int heuristic(int currentValue, int goalValue) {
        return Math.abs(currentValue - goalValue);
    }
}
```

**Explanation:**

1. **TreeNode Class:**
   - Represents a node in the tree.
   - Each node has a `value` (data), a `heuristic` value (for A* algorithm), and a list of `children` nodes.

2. **BestFirstSearchTree Class:**
   - **`bestFirstSearch` Method:**
     - Performs Best-First Search starting from the `root` node.
     - Utilizes a `PriorityQueue` to prioritize nodes based on their total cost (actual value + heuristic).
     - Uses a boolean array `visited` to keep track of visited nodes.
     - Returns the value of the node if the goal is reached, or `INF` (infinity) if the goal is not reachable.

   - **`main` Method:**
     - Creates an example tree.
     - Calls `bestFirstSearch` with the root node and a goal value.
     - Prints the result indicating whether the goal is reached or not.

   - **`heuristic` Method:**
     - Simple heuristic function that calculates the absolute difference between the current and goal values.

3. **Example Tree:**
   - Represents a tree with nodes 5, 3, 8, 1, 4, and 10 arranged in a hierarchical structure.
   - Goal value is set to 10.

4. **Result Printing:**
   - Prints whether the goal is reached with a specific value or if it's not reachable.

Overall, this code demonstrates a simplified A* algorithm on a tree structure, where nodes are prioritized based on a combination of their actual value and a heuristic estimate. The example tree is traversed in a best-first manner to find the goal value.