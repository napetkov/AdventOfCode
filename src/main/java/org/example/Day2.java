package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day2 implements Runnable {

    @Override
    public void run() {
        int countOfSafeLevels = 0;

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("C:\\Users\\Lenovo\\OneDrive\\Desktop\\Advent_of_code\\Day_2_input.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<List<Integer>> levels = new ArrayList<>();
        for (String line : lines) {
            LinkedList<Integer> level = new LinkedList<>(Arrays.stream(line.split(" ")).map(Integer::parseInt).toList());

            if (isSafe(level)) {
                countOfSafeLevels++;
            } else {
                for (int i = 0; i < level.size(); i++) {
                    LinkedList<Integer> modifiedList = new LinkedList<>(level);
                    modifiedList.remove(i);
                    if (isSafe(modifiedList)) {
                        countOfSafeLevels++;
                        break;
                    }
                }
            }
        }
        System.out.println(countOfSafeLevels);
    }

    public static boolean isSafe(List<Integer> level) {

        if (level.get(0) == level.get(1)) {
            return false;
        }

        //First case -> increasing
        if (level.get(0) < level.get(1)) {
            for (int i = 0; i < level.size() - 1; i++) {
                int absValue = Math.abs(level.get(i) - level.get(i + 1));
                if (absValue < 1 || absValue > 3 || level.get(i) > level.get(i + 1)) {
                    return false;
                }
            }
        }

        //First case -> decreasing
        if (level.get(0) > level.get(1)) {
            for (int i = 0; i < level.size() - 1; i++) {
                int absValue = Math.abs(level.get(i) - level.get(i + 1));
                if (absValue < 1 || absValue > 3 || level.get(i) < level.get(i + 1)) {
                    return false;
                }
            }
        }
        return true;
    }
}

