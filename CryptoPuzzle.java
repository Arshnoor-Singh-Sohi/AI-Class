import java.util.Arrays;

// Definition of a Node, representing a character and its corresponding value
class Node {
    char letter;
    int value;
}

// Main class for solving a cryptoarithmetic puzzle
public class CryptoPuzzle {
    // Static array to keep track of used digits (0-9)
    static int[] use = new int[10];

    // Function to check if the current assignment of values to letters is valid
    static int isValid(Node[] nodeList, int count, String s1, String s2, String s3) {
        int val1 = 0, val2 = 0, val3 = 0, m = 1, j, i;

        // Calculate the numerical value of s1 using the assigned values
        for (i = s1.length() - 1; i >= 0; i--) {
            char ch = s1.charAt(i);
            for (j = 0; j < count; j++)
                if (nodeList[j].letter == ch)
                    break;
            val1 += m * nodeList[j].value;
            m *= 10;
        }

        m = 1;
        // Repeat the process for s2
        for (i = s2.length() - 1; i >= 0; i--) {
            char ch = s2.charAt(i);
            for (j = 0; j < count; j++)
                if (nodeList[j].letter == ch)
                    break;
            val2 += m * nodeList[j].value;
            m *= 10;
        }

        m = 1;
        // Repeat the process for s3
        for (i = s3.length() - 1; i >= 0; i--) {
            char ch = s3.charAt(i);
            for (j = 0; j < count; j++)
                if (nodeList[j].letter == ch)
                    break;
            val3 += m * nodeList[j].value;
            m *= 10;
        }

        // Check if the sum of val1 and val2 equals val3
        return (val3 == (val1 + val2)) ? 1 : 0;
    }

    // Recursive function to generate permutations of values for letters
    static boolean permutation(int count, Node[] nodeList, int n, String s1, String s2, String s3) {
        if (n == count - 1) {
            // Base case: all letters have been assigned values
            for (int i = 0; i < 10; i++) {
                if (use[i] == 0) {
                    nodeList[n].value = i;
                    // Check if the current assignment is valid
                    if (isValid(nodeList, count, s1, s2, s3) == 1) {
                        System.out.print("Solution found:");
                        // Print the solution
                        for (int j = 0; j < count; j++)
                            System.out.print(" " + nodeList[j].letter + " = " + nodeList[j].value);
                        return true;
                    }
                }
            }
            return false;
        }

        // Recursive case: assign values to letters and continue with the next letter
        for (int i = 0; i < 10; i++) {
            if (use[i] == 0) {
                nodeList[n].value = i;
                use[i] = 1;
                // Recursively explore the next assignment
                if (permutation(count, nodeList, n + 1, s1, s2, s3))
                    return true;
                use[i] = 0; // Backtrack: undo the assignment for backtracking
            }
        }
        return false;
    }

    // Function to solve the cryptoarithmetic puzzle
    static boolean solvePuzzle(String s1, String s2, String s3) {
        int uniqueChar = 0;
        int len1 = s1.length();
        int len2 = s2.length();
        int len3 = s3.length();

        int[] freq = new int[26];

        // Count the frequency of each letter in the strings
        for (int i = 0; i < len1; i++)
            ++freq[s1.charAt(i) - 'A'];
        for (int i = 0; i < len2; i++)
            ++freq[s2.charAt(i) - 'A'];
        for (int i = 0; i < len3; i++)
            ++freq[s3.charAt(i) - 'A'];

        // Count the number of unique letters
        for (int i = 0; i < 26; i++)
            if (freq[i] > 0)
                uniqueChar++;

        // Check if there are more than 10 unique letters (invalid puzzle)
        if (uniqueChar > 10) {
            System.out.println("Invalid strings");
            return false;
        }

        // Create an array of Nodes to represent the unique letters
        Node[] nodeList = new Node[uniqueChar];
        for (int i = 0, j = 0; i < 26; i++) {
            if (freq[i] > 0) {
                nodeList[j] = new Node();
                nodeList[j].letter = (char) (i + 'A');
                j++;
            }
        }
        
        // Call the permutation function to find a solution
        return permutation(uniqueChar, nodeList, 0, s1, s2, s3);
    }

    // Main function
    public static void main(String[] args) {
        // Example puzzle strings
        String s1 = "SUN";
        String s2 = "RUN";
        String s3 = "FAST";

        // Solve the puzzle and print the result
        if (!solvePuzzle(s1, s2, s3))
            System.out.println("No solution");
    }
}
