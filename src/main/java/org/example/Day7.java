package org.example;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day7 implements Runnable {
    @Override
    public void run() {
        List<String> inputLines = new ArrayList<>();
        try {
            inputLines = Files.readAllLines(Paths.get("C:\\Users\\Lenovo\\OneDrive\\Desktop\\Advent_of_code\\day_7\\Day_7_input.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<BigInteger> targets = new ArrayList<>();
        List<List<BigInteger>> sequences = new ArrayList<>();
        parseInput(inputLines, targets, sequences);

        BigInteger task1Result = processTask1(targets, sequences);
        System.out.println("Task 1: Sum of achievable targets using + and *: " + task1Result);

        BigInteger task2Result = processTask2(targets, sequences);
        System.out.println("Task 2: Sum of achievable targets using +, *, and ||: " + task2Result);

        System.out.println(task1Result.add(task2Result));
    }

    private static void parseInput(List<String> inputLines, List<BigInteger> targets, List<List<BigInteger>> sequences) {
        for (String line : inputLines) {
            String[] parts = line.split(":");
            BigInteger target = new BigInteger(parts[0].trim());
            List<BigInteger> sequence = new ArrayList<>();
            for (String num : parts[1].trim().split(" ")) {
                sequence.add(new BigInteger(num));
            }
            targets.add(target);
            sequences.add(sequence);
        }
    }

    private static BigInteger processTask1(List<BigInteger> targets, List<List<BigInteger>> sequences) {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < targets.size(); i++) {
            BigInteger target = targets.get(i);
            List<BigInteger> sequence = sequences.get(i);

            if (isTargetAchievableWithPlusAndMultiply(sequence, 1, sequence.get(0), target)) {
                sum = sum.add(target);
            }
        }
        return sum;
    }

    private static BigInteger processTask2(List<BigInteger> targets, List<List<BigInteger>> sequences) {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < targets.size(); i++) {
            BigInteger target = targets.get(i);
            List<BigInteger> sequence = sequences.get(i);

            if (!isTargetAchievableWithPlusAndMultiply(sequence, 1, sequence.get(0), target)) {
                if (isTargetAchievableWithAllOperators(sequence, 1, sequence.get(0), sequence.get(0), target)) {
                    sum = sum.add(target);
                }
            }
        }
        return sum;
    }

    private static boolean isTargetAchievableWithPlusAndMultiply(
            List<BigInteger> sequence, int index, BigInteger currentResult, BigInteger target) {
        if (index == sequence.size()) {
            return currentResult.equals(target);
        }

        BigInteger nextNumber = sequence.get(index);

        // addition
        if (isTargetAchievableWithPlusAndMultiply(sequence, index + 1, currentResult.add(nextNumber), target)) {
            return true;
        }

        // multiplication
        if (isTargetAchievableWithPlusAndMultiply(sequence, index + 1, currentResult.multiply(nextNumber), target)) {
            return true;
        }

        return false;
    }

    private static boolean isTargetAchievableWithAllOperators(
            List<BigInteger> sequence, int index, BigInteger currentResult, BigInteger lastNumber, BigInteger target) {
        if (index == sequence.size()) {
            return currentResult.equals(target);
        }

        BigInteger nextNumber = sequence.get(index);

        // addition
        if (isTargetAchievableWithAllOperators(sequence, index + 1,
                currentResult.add(nextNumber),
                nextNumber,
                target)) {
            return true;
        }

        // multiplication
        if (isTargetAchievableWithAllOperators(sequence, index + 1,
                currentResult.subtract(lastNumber).add(lastNumber.multiply(nextNumber)),
                lastNumber.multiply(nextNumber),
                target)) {
            return true;
        }

        // concatenation (||)
        BigInteger concatenatedNumber = concatenateNumbers(lastNumber, nextNumber);
        if (isTargetAchievableWithAllOperators(sequence, index + 1,
                currentResult.subtract(lastNumber).add(concatenatedNumber),
                concatenatedNumber,
                target)) {
            return true;
        }

        return false;
    }

    private static BigInteger concatenateNumbers(BigInteger left, BigInteger right) {
        // Concatenate two numbers as BigInteger
        String concatenated = left.toString() + right.toString();
        return new BigInteger(concatenated);
    }
}
