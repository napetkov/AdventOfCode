package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 implements Runnable {
    @Override
    public void run() {

        try {
            String input = Files.readString(Paths.get("C:\\Users\\Lenovo\\OneDrive\\Desktop\\Advent_of_code\\day_5\\Day_5_input.txt"));
            String[] split = input.split("\\n\\s++");
            List<String> rulesInput = Arrays.stream(split[0].split("\\n")).map(String::trim).toList();
            List<String> updatesInput = Arrays.stream(split[1].split("\\n")).map(String::trim).toList();
            // Parse rules into a map
            List<int[]> rules = new ArrayList<>();
            for (String rule : rulesInput) {
                String[] parts = rule.split("\\|");
                rules.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
            }

            // Process updates and find the result
            int result = processUpdates(updatesInput, rules);
            System.out.println("Sum of middle page numbers after reordering: " + result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int processUpdates(List<String> updatesInput, List<int[]> rules) {
        int sum = 0;

        for (String update : updatesInput) {
            // Parse the update into a list of integers
            List<Integer> pages = new ArrayList<>();
            for (String page : update.split(",")) {
                pages.add(Integer.parseInt(page));
            }

            // Check if the update is valid
            if (!isValidOrder(pages, rules)) {
                // If not valid, reorder it
                pages = reorderPages(pages, rules);
            }

            // Find the middle page and add to the sum
            int middle = pages.get(pages.size() / 2);
            sum += middle;
        }

        return sum;
    }

    private static boolean isValidOrder(List<Integer> pages, List<int[]> rules) {
        // Check all relevant rules for this update
        Map<Integer, Integer> pageIndices = new HashMap<>();
        for (int i = 0; i < pages.size(); i++) {
            pageIndices.put(pages.get(i), i);
        }

        for (int[] rule : rules) {
            int x = rule[0];
            int y = rule[1];
            if (pageIndices.containsKey(x) && pageIndices.containsKey(y)) {
                if (pageIndices.get(x) > pageIndices.get(y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<Integer> reorderPages(List<Integer> pages, List<int[]> rules) {
        // Build a graph of dependencies for the current update
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int page : pages) {
            graph.put(page, new ArrayList<>());
            inDegree.put(page, 0);
        }

        // Add edges from the rules
        for (int[] rule : rules) {
            int x = rule[0];
            int y = rule[1];
            if (graph.containsKey(x) && graph.containsKey(y)) {
                graph.get(x).add(y);
                inDegree.put(y, inDegree.get(y) + 1);
            }
        }

        // Perform topological sort
        List<Integer> sortedPages = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();

        for (int page : inDegree.keySet()) {
            if (inDegree.get(page) == 0) {
                queue.add(page);
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            sortedPages.add(current);

            for (int neighbor : graph.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sortedPages;
    }

//            List<String> incorrectUpdates = new ArrayList<>();
//            int middlePageSumCorrect = 0;
//            int middlePageSumIncorrect = 0;
//
//            for (String update : updates) {
//                boolean isCorrectUpdate = false;
//
//                String[] currentUpdate = update.split(",");
//
//                List<String> pairs = pairsFromList(currentUpdate);
//
//                isCorrectUpdate = rules.containsAll(pairs);
//
//                if (isCorrectUpdate) {
//                    middlePageSumCorrect = getMiddlePageSum(currentUpdate, middlePageSumCorrect);
//                } else {
//                    incorrectUpdates.add(update);
//                }
//            }
//
//            Map<String, List<String>> rulesMap = new HashMap<>();
//
//            for (String rule : rules) {
//                String left = rule.split("\\|")[0];
//                String right = rule.split("\\|")[1];
//
//                if (rulesMap.containsKey(left)) {
//                    rulesMap.get(left).add(right);
//                } else {
//                    rulesMap.put(left, new ArrayList<>());
//                    rulesMap.get(left).add(right);
//                }
//            }
//            boolean isKey = false;
//
//            for (String incorrectUpdate : incorrectUpdates) {
//                List<String> forCorrection = Arrays.stream(incorrectUpdate.split(",")).toList().stream().collect(Collectors.toList());
//                String[] corrected = new String[forCorrection.size()];
//                for (int i = 0; i < forCorrection.size(); i++) {
//                    List<String> elements = new ArrayList<>(forCorrection);
//                    String page = elements.remove(i);
//                    isKey = rulesMap.containsKey(page);
//                    if (!isKey) {
//                        corrected[forCorrection.size() - 1] = page;
//                        forCorrection.add(forCorrection.remove(i));
//                        if (i != 0 && i != forCorrection.size() - 1) i--;
//                        continue;
//                    }
//
//                    List<String> ruleList = rulesMap.get(page);
//                    for (int j = 0; j < forCorrection.size(); j++) {
//                        List<String> subList = elements.subList(j, elements.size());
//
//                        if (ruleList.containsAll(subList)) {
//                            corrected[j] = page;
//                            break;
//                        }
//                    }
//                }
//                    middlePageSumIncorrect = getMiddlePageSum(corrected, middlePageSumIncorrect);
//            }
//
//
//            System.out.println(middlePageSumCorrect);
//            System.out.println(middlePageSumIncorrect);


    private static List<String> pairsFromList(String[] currentUpdate) {
        List<String> pairs = new ArrayList<>();
        for (int i = 0; i < currentUpdate.length - 1; i++) {
            String currentElement = currentUpdate[i];
            String nextElement = currentUpdate[i + 1];
            pairs.add(currentElement + "|" + nextElement);
        }
        return pairs;
    }

    private static int getMiddlePageSum(String[] elements, int middlePageSum) {
        List<Integer> numbers = Arrays.stream(elements).map(Integer::parseInt).toList();
        int midIndex = numbers.size() / 2;
        middlePageSum += numbers.get(midIndex);
        return middlePageSum;
    }
}
