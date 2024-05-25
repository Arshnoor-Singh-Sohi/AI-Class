import java.util.*;

public class EightPuzzle {

    static class State {
        int[][] board;

        State(int[][] board) {
            this.board = board;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof State && Arrays.deepEquals(board, ((State) obj).board);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(board);
        }
    }

    static final int[][] GOAL_STATE = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    static boolean isGoal(State state) {
        return Arrays.deepEquals(state.board, GOAL_STATE);
    }

    static List<State> getSuccessors(State currentState) {
        List<State> successors = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};

        int x = -1, y = -1;
        outer:
        for (x = 0; x < 3; x++)
            for (y = 0; y < 3; y++)
                if (currentState.board[x][y] == 0)
                    break outer;

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i], newY = y + dy[i];
            if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
                int[][] newBoard = Arrays.stream(currentState.board).map(int[]::clone).toArray(int[][]::new);
                newBoard[x][y] = newBoard[newX][newY];
                newBoard[newX][newY] = 0;
                successors.add(new State(newBoard));
            }
        }
        return successors;
    }

    static void bfs(State initialState) {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        Map<State, State> parent = new HashMap<>();

        queue.offer(initialState);
        visited.add(initialState);
        parent.put(initialState, null);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (isGoal(currentState)) {
                System.out.println("Goal state found!");
                printPath(parent, currentState);
                return;
            }

            for (State successor : getSuccessors(currentState)) {
                if (!visited.contains(successor)) {
                    queue.offer(successor);
                    visited.add(successor);
                    parent.put(successor, currentState);
                }
            }
        }
        System.out.println("Goal state could not be reached.");
    }

    static void printPath(Map<State, State> parent, State goalState) {
        List<State> path = new ArrayList<>();
        for (State currentState = goalState; currentState != null; currentState = parent.get(currentState))
            path.add(currentState);
        Collections.reverse(path);
        path.forEach(s -> printBoard(s.board));
    }

    static void printBoard(int[][] board) {
        Arrays.stream(board).forEach(row -> System.out.println(Arrays.toString(row).replaceAll("[\\[\\],]", "")));
        System.out.println();
    }

    public static void main(String[] args) {
        int[][] initialState = {{1, 2, 3}, {4, 5, 6}, {0, 7, 8}};
        bfs(new State(initialState));
    }
}
