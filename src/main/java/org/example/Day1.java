package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 implements Runnable {

    @Override
    public void run() {
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();
        Integer totalDistance = 0;
        Integer similarityScore = 0;


        try {
            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\Lenovo\\OneDrive\\Desktop\\Advent_of_code\\day_1\\Day_1_input.txt"));

            for (String line : lines) {
                String[] numbers = line.trim().split("\\s+");

                leftList.add(Integer.parseInt(numbers[0]));
                rightList.add(Integer.parseInt(numbers[1]));
            }
//First case
//            leftList.sort(Integer::compareTo);
//            rightList.sort(Integer::compareTo);
//
//            for (int i = 0; i < leftList.size(); i++) {
//                Integer leftNumber = leftList.get(i);
//                Integer rightNumber = rightList.get(i);
//
//                totalDistance += Math.abs(leftNumber - rightNumber);
//            }
//
//            System.out.println(totalDistance);

//Second case
            for (int i = 0; i < leftList.size(); i++) {
                Integer currentNumber = leftList.get(i);
                int frequency = Collections.frequency(rightList, currentNumber);
                similarityScore += frequency * currentNumber;
            }
            System.out.println(similarityScore);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }


    }
}
