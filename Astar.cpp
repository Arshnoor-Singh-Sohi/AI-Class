#include <iostream>
#include <vector>
#include <unordered_map>
#include <queue>

using namespace std;

// Data structure representing a state in the search
struct State {
    char name;
    int gScore;  // Cost from start to current state
    int hScore;  // Heuristic estimate of cost to reach goal
    int fScore;  // Total score = gScore + hScore
    State* parent;  // Pointer to parent state for backtracking

    State(char name, int gScore, int hScore) : name(name), gScore(gScore), hScore(hScore) {
        fScore = gScore + hScore;
        parent = nullptr;
    }

    // Comparison operator for priority queue
    bool operator<(const State& other) const {
        return fScore > other.fScore;  // Prioritize lower fScore
    }
};

// Heuristic function (estimated cost to reach goal)
int heuristic(char state) {
    switch (state) {
        case 'S': return 5;
        case 'A': return 3;
        case 'B': return 4;
        case 'C': return 2;
        case 'D': return 6;
        case 'G': return 0;
        default: return INT_MAX;  // Unknown state, assign very high cost
    }
}

// Function to implement A* search
State* aStarSearch(char start, char goal) {
    unordered_map<char, State*> states;  // Store states and their details
    priority_queue<State> pq;            // Priority queue for open set

    // Create initial state
    State* startState = new State(start, 0, heuristic(start));
    states[start] = startState;
    pq.push(*startState);

    while (!pq.empty()) {
        State* currentState = &pq.top();
        pq.pop();

        // Goal reached
        if (currentState->name == goal) {
            return currentState;
        }

        // Expand current state
        for (char nextState : {'A', 'B', 'C', 'D', 'G'}) {  // Explore all possible children
            // Skip invalid edges and already visited states
            if (nextState == start || states.count(nextState) > 0) {
                continue;
            }

            int tentativeGScore = currentState->gScore + 1;  // Assume edge cost is 1 for simplicity
            if (tentativeGScore < states[nextState]->gScore || states[nextState] == nullptr) {
                State* newState = new State(nextState, tentativeGScore, heuristic(nextState));
                newState->parent = currentState;
                states[nextState] = newState;
                pq.push(*newState);
            }
        }
    }

    return nullptr;  // No path found
}

int main() {
    char start = 'S';
    char goal = 'G';

    State* goalState = aStarSearch(start, goal);

    // Print the solution path
    if (goalState) {
        cout << "Path from " << start << " to " << goal << ":" << endl;
        vector<char> path;
        while (goalState) {
            path.push_back(goalState->name);
            goalState = goalState->parent;
        }
        reverse(path.begin(), path.end());  // Print path in reverse order (start to goal)
        for (char state : path) {
            cout << state << " ";
        }
        cout << endl;
    } else {
        cout << "No path found." << endl;
    }

    return 0;
}