package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day7 implements Runnable {
    @Override
    public void run() {
        try {
            long totalResult = 0;
            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\Lenovo\\OneDrive\\Desktop\\Advent_of_code\\day_7\\day_7_input.txt"));
            for (String line : lines) {
                Long target = Long.parseLong(line.split(": ")[0]);
                List<Long> numbers = Arrays.stream(line.split(": ")[1].split("\\s+")).map(Long::parseLong).toList();

                if (hasMatchingCombination(numbers, target)) {
                    totalResult += target;
                }else {
                    if(isTargetAchievableWithAllOperators(numbers,1,numbers.get(0),numbers.get(0),target)){
                        totalResult += target;
                    }
                }
            }


            System.out.println(totalResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasMatchingCombination(List<Long> numbers, Long target) {
        return checkCombinations(numbers, 1, numbers.get(0), target);
    }

    private static boolean checkCombinations(List<Long> numbers, int index, long currentResult, Long target) {
        // Base case: if all numbers are processed, check if the result matches the target
        if (index == numbers.size()) {
            return currentResult == target;
        }
        return checkCombinations(numbers, index + 1, currentResult + numbers.get(index), target) ||
                checkCombinations(numbers, index + 1, currentResult * numbers.get(index), target);
    }
    // Check if the target can be achieved using +, *, and ||
    private static boolean isTargetAchievableWithAllOperators(List<Long> sequence, int index, long currentResult, long lastNumber, long target) {
        if (index == sequence.size()) {
            return currentResult == target;
        }

        long nextNumber = sequence.get(index);

        // Try addition
        if (isTargetAchievableWithAllOperators(sequence, index + 1, currentResult + nextNumber, nextNumber, target)) {
            return true;
        }

        // Try multiplication
        if (isTargetAchievableWithAllOperators(sequence, index + 1, currentResult - lastNumber + (lastNumber * nextNumber), lastNumber * nextNumber, target)) {
            return true;
        }

        // Try concatenation
        long concatenatedNumber = Long.parseLong(lastNumber + "" + nextNumber);
        if (isTargetAchievableWithAllOperators(sequence, index + 1, currentResult - lastNumber + concatenatedNumber, concatenatedNumber, target)) {
            return true;
        }

        return false;
    }
}
