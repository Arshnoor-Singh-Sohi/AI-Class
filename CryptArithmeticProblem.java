public class CryptArithmeticProblem {

    public static void main(String[] args) {
        solveCryptarithmetic("SEND", "MORE", "MONEY");
    }

    public static void solveCryptarithmetic(String word1, String word2, String result) {
        String allLetters = word1 + word2 + result;
        int[] mapping = new int[26];
        boolean[] usedDigits = new boolean[10];
        boolean solutionFound = false;

        // Recursive function to search for a valid assignment
        solutionFound = assignDigits(word1, word2, result, allLetters, mapping, usedDigits, 0);

        // Display the result
        if (solutionFound) {
            System.out.println("Solution found:");
            displayMapping(mapping, allLetters);
        } else {
            System.out.println("No solution found.");
        }
    }

    private static boolean assignDigits(String word1, String word2, String result, String allLetters,
                                        int[] mapping, boolean[] usedDigits, int index) {
        // Base case: If all letters are assigned, check if the arithmetic expression holds
        if (index == allLetters.length()) {
            return isCryptarithmeticValid(word1, word2, result, mapping);
        }

        char currentLetter = allLetters.charAt(index);

        // If the current letter is already assigned, continue with the next letter
        if (mapping[currentLetter - 'A'] != 0) {
            return assignDigits(word1, word2, result, allLetters, mapping, usedDigits, index + 1);
        }

        // Try assigning digits from 0 to 9 to the current letter
        for (int digit = 0; digit <= 9; digit++) {
            if (!usedDigits[digit]) {
                // Assign the digit to the current letter
                mapping[currentLetter - 'A'] = digit;
                usedDigits[digit] = true;

                // Recursively check if the assignment leads to a valid solution
                if (assignDigits(word1, word2, result, allLetters, mapping, usedDigits, index + 1)) {
                    return true;
                }

                // Backtrack: Undo the assignment and try the next digit
                mapping[currentLetter - 'A'] = 0;
                usedDigits[digit] = false;
            }
        }

        // No valid assignment found for the current letter
        return false;
    }

    private static boolean isCryptarithmeticValid(String word1, String word2, String result, int[] mapping) {
        int value1 = getValue(word1, mapping);
        int value2 = getValue(word2, mapping);
        int resultValue = getValue(result, mapping);

        return (value1 + value2 == resultValue);
    }

    private static int getValue(String word, int[] mapping) {
        int value = 0;
        for (int i = 0; i < word.length(); i++) {
            value = value * 10 + mapping[word.charAt(i) - 'A'];
        }
        return value;
    }

    private static void displayMapping(int[] mapping, String allLetters) {
        for (int i = 0; i < allLetters.length(); i++) {
            char letter = allLetters.charAt(i);
            System.out.print(letter + ": " + mapping[letter - 'A'] + "  ");
        }
        System.out.println();
    }
}