import java.util.*;

public class WaterJug {

    static class State {
        int jug1;
        int jug2;
        State prevState;

        State(int jug2, int jug3, State prevState) {
            this.jug1 = jug2;
            this.jug2 = jug3;
            this.prevState = prevState;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            State state = (State) obj;
            return jug1 == state.jug1 && jug2 == state.jug2;
        }

        @Override
        public int hashCode() {
            int result = jug1;
            result = 31 * result + jug2;
            return result;
        }
    }

    static boolean isGoal(State state) {
        return state.jug1 == 1 && state.jug2 == 0;
    }

    static List<State> getSuccessors(State currentState, Set<State> visited) {
        List<State> successors = new ArrayList<>();

        // Fill jug2
        State fillJug2 = new State(2, currentState.jug2, currentState);
        if (!visited.contains(fillJug2)) {
            successors.add(fillJug2);
            visited.add(fillJug2);
        }

        // Fill jug3
        State fillJug3 = new State(currentState.jug1, 3, currentState);
        if (!visited.contains(fillJug3)) {
            successors.add(fillJug3);
            visited.add(fillJug3);
        }

        // Empty jug2
        State emptyJug2 = new State(0, currentState.jug2, currentState);
        if (!visited.contains(emptyJug2)) {
            successors.add(emptyJug2);
            visited.add(emptyJug2);
        }

        // Empty jug3
        State emptyJug3 = new State(currentState.jug1, 0, currentState);
        if (!visited.contains(emptyJug3)) {
            successors.add(emptyJug3);
            visited.add(emptyJug3);
        }

        // Pour from jug3 to jug2
        int pourAmount = Math.min(currentState.jug2, 2 - currentState.jug1);
        State pourJug3ToJug2 = new State(currentState.jug1 + pourAmount, currentState.jug2 - pourAmount, currentState);
        if (!visited.contains(pourJug3ToJug2)) {
            successors.add(pourJug3ToJug2);
            visited.add(pourJug3ToJug2);
        }

        // Pour from jug2 to jug3
        pourAmount = Math.min(currentState.jug1, 3 - currentState.jug2);
        State pourJug2ToJug3 = new State(currentState.jug1 - pourAmount, currentState.jug2 + pourAmount, currentState);
        if (!visited.contains(pourJug2ToJug3)) {
            successors.add(pourJug2ToJug3);
            visited.add(pourJug2ToJug3);
        }

        return successors;
    }

    static Stack<State> getPathToGoal(State goalState) {
        Stack<State> path = new Stack<>();
        State currentState = goalState;
        while (currentState != null) {
            path.push(currentState);
            currentState = currentState.prevState;
        }
        return path;
    }

    static void bfs() {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        queue.offer(new State(0, 0, null));

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (isGoal(currentState)) {
                Stack<State> pathToGoal = getPathToGoal(currentState);
                System.out.println("Path to Goal:");
                while (!pathToGoal.isEmpty()) {
                    State state = pathToGoal.pop();
                    System.out.println("Jug1: " + state.jug1 + " Jug2: " + state.jug2);
                }
                return;
            }

            List<State> successors = getSuccessors(currentState, visited);
            queue.addAll(successors);
        }

        System.out.println("No solution found.");
    }

    public static void main(String[] args) {
        bfs();
    }
}
