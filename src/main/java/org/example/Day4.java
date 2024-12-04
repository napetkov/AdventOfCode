package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day4 implements Runnable {
    @Override
    public void run() {
        char[][] matrix;
        List<char[]> matricList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\Lenovo\\OneDrive\\Desktop\\Advent_of_code\\day_4\\Day_4_input.txt"));
            for (int i = 0; i < lines.size(); i++) {
                char[] lineToCharArray = lines.get(i).toCharArray();
                matricList.add(lineToCharArray);
            }
            matrix = matricList.toArray(new char[0][]);
//Puzzle - B Get count of "X-MAS"
            System.out.println(puzzleBgetCountOfXmas(matrix));

// Puzzle - A Get count of "XMAS"
//            System.out.println(puzzleAgetCountOfXmas(matrix));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int puzzleBgetCountOfXmas(char[][] matrix) {
        int countOfXmas = 0;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                char charX = matrix[row][col];
                char[] matcherArr;
                String matcher;
                if (charX == 'A') {
                    countOfXmas = countMatchXmasCurrentPositionPuzzleB(row, col, matrix) ? countOfXmas + 1 : countOfXmas;
                }
            }
        }
        return countOfXmas;
    }

    private static int puzzleAgetCountOfXmas(char[][] matrix) {
        int countOfXmas = 0;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                char charX = matrix[row][col];
                char[] matcherArr;
                String matcher;
                if (charX == 'X') {
                    countOfXmas += countMatchXmasCurrentPositionPuzzleA(row, col, matrix);
                }
            }
        }
        return countOfXmas;
    }

    private static int countMatchXmasCurrentPositionPuzzleA(int row, int col, char[][] matrix) {
        int count = 0;
        count = checkMatchRight(row, col, matrix) ? count + 1 : count;
        count = checkMatchLeft(row, col, matrix) ? count + 1 : count;
        count = checkMatchUp(row, col, matrix) ? count + 1 : count;
        count = checkMatchDown(row, col, matrix) ? count + 1 : count;
        count = checkMatchRightUp(row, col, matrix) ? count + 1 : count;
        count = checkMatchRightDown(row, col, matrix) ? count + 1 : count;
        count = checkMatchLeftUp(row, col, matrix) ? count + 1 : count;
        count = checkMatchLeftDown(row, col, matrix) ? count + 1 : count;
        return count;
    }

    private static boolean countMatchXmasCurrentPositionPuzzleB(int row, int col, char[][] matrix) {
        boolean isXmas = false;
        if (row < 1 || col < 1 || row >= matrix.length - 1 || col >= matrix[0].length - 1) return false;

        isXmas = (('M' == matrix[row + 1][col + 1] && 'S' == matrix[row - 1][col - 1]) || ('S' == matrix[row + 1][col + 1] && 'M' == matrix[row - 1][col - 1])) &&
                (('M' == matrix[row + 1][col - 1] && 'S' == matrix[row - 1][col + 1]) || ('S' == matrix[row + 1][col - 1] && 'M' == matrix[row - 1][col + 1]));

        return isXmas;
    }

    private static boolean checkMatchRight(int row, int col, char[][] matrix) {
        if (validateRight(col, matrix)) return false;
        char[] matcherArr = new char[]{matrix[row][col], matrix[row][col + 1], matrix[row][col + 2], matrix[row][col + 3]};
        String matcher = new String(matcherArr);
        return "XMAS".equals(matcher);
    }

    private static boolean checkMatchLeft(int row, int col, char[][] matrix) {
        if (validateLeft(col)) return false;
        char[] matcherArr = new char[]{matrix[row][col], matrix[row][col - 1], matrix[row][col - 2], matrix[row][col - 3]};
        return "XMAS".equals(new String(matcherArr));
    }

    private static boolean checkMatchDown(int row, int col, char[][] matrix) {
        if (validateDown(row, matrix)) return false;
        char[] matcherArr = new char[]{matrix[row][col], matrix[row + 1][col], matrix[row + 2][col], matrix[row + 3][col]};
        return "XMAS".equals(new String(matcherArr));
    }

    private static boolean checkMatchUp(int row, int col, char[][] matrix) {
        if (validateUp(row)) return false;
        char[] matcherArr = new char[]{matrix[row][col], matrix[row - 1][col], matrix[row - 2][col], matrix[row - 3][col]};
        return "XMAS".equals(new String(matcherArr));
    }

    private static boolean checkMatchRightDown(int row, int col, char[][] matrix) {
        if (validateRight(col, matrix) || validateDown(row, matrix)) return false;
        char[] matcherArr = new char[]{matrix[row][col], matrix[row + 1][col + 1], matrix[row + 2][col + 2], matrix[row + 3][col + 3]};
        return "XMAS".equals(new String(matcherArr));
    }

    private static boolean checkMatchRightUp(int row, int col, char[][] matrix) {
        if (validateRight(col, matrix) || validateUp(row)) return false;
        char[] matcherArr = new char[]{matrix[row][col], matrix[row - 1][col + 1], matrix[row - 2][col + 2], matrix[row - 3][col + 3]};
        return "XMAS".equals(new String(matcherArr));
    }

    private static boolean checkMatchLeftDown(int row, int col, char[][] matrix) {
        if (validateLeft(col) || validateDown(row, matrix)) return false;
        char[] matcherArr = new char[]{matrix[row][col], matrix[row + 1][col - 1], matrix[row + 2][col - 2], matrix[row + 3][col - 3]};
        return "XMAS".equals(new String(matcherArr));
    }

    private static boolean checkMatchLeftUp(int row, int col, char[][] matrix) {
        if (validateLeft(col) || validateUp(row)) return false;
        char[] matcherArr = new char[]{matrix[row][col], matrix[row - 1][col - 1], matrix[row - 2][col - 2], matrix[row - 3][col - 3]};
        return "XMAS".equals(new String(matcherArr));
    }

    private static boolean validateUp(int row) {
        return row < 3;
    }

    private static boolean validateDown(int row, char[][] matrix) {
        return row >= matrix.length - 3;
    }

    private static boolean validateLeft(int col) {
        return col < 3;
    }

    private static boolean validateRight(int col, char[][] matrix) {
        return col >= matrix[0].length - 3;
    }

    private static void printMatrix(char[][] matrix) {
        for (char[] row : matrix) {
            for (char ch : row) {
                System.out.print(ch);
            }
            System.out.println();
        }
    }
}
