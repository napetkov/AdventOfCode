package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day6 implements Runnable {
    private int guardRow = 0;
    private int guardCol = 0;
    private int visitedPosition = 0;
    private boolean endOfMap = true;

    @Override
    public void run() {
        char[][] map;
        List<char[]> matricList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\Lenovo\\OneDrive\\Desktop\\Advent_of_code\\day_6\\Day_6_input.txt"));


            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains("^")) {
                    guardCol = line.indexOf("^");
                    guardRow = i;
                }
                char[] lineToCharArray = line.toCharArray();
                matricList.add(lineToCharArray);
            }
            map = matricList.toArray(new char[0][]);



            while (endOfMap) {
                char currentPosition = map[guardRow][guardCol];
                switch (currentPosition) {
                    case '^':
                        if (obstructionUp(guardRow, guardCol, map)) {
                            if (obstructionRight(guardRow, guardCol, map)) {
                                endOfMap = moveDown(map);
                                break;
                            }
                            endOfMap = moveRight(map);
                        }else {
                            endOfMap = moveUp(map);
                        }
                        break;
                    case '>':
                        if (obstructionRight(guardRow, guardCol, map)) {
                            if (obstructionDown(guardRow, guardCol, map)) {
                                endOfMap = moveLeft(map);
                                break;
                            }
                            endOfMap = moveDown(map);
                        }else {
                            endOfMap = moveRight(map);
                        }
                        break;
                    case 'v':
                        if (obstructionDown(guardRow, guardCol, map)) {
                            if (obstructionLeft(guardRow, guardCol, map)) {
                                endOfMap = moveUp(map);
                                break;
                            }
                            endOfMap = moveLeft(map);
                        }else {
                            endOfMap = moveDown(map);
                        }
                        break;
                    case '<':
                        if (obstructionLeft(guardRow, guardCol, map)) {
                            if (obstructionUp(guardRow, guardCol, map)) {
                                endOfMap = moveRight(map);
                                break;
                            }
                            endOfMap = moveUp(map);
                        }else {
                            endOfMap = moveLeft(map);
                        }
                        break;
                }

                if (!endOfMap) {
                    visitedPosition++;
                }


            }


            printMatrix(map);
            System.out.println("Guard row: " + guardRow + "\nGuard col: " + guardCol + "\n");
            System.out.println("Visited position: " + visitedPosition);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean moveUp(char[][] map) {
        int nextRow = guardRow - 1;
        int nextCol = guardCol;
        if (isValidNextPosition(nextRow, nextCol, map)) {
            if (map[nextRow][nextCol] != 'X') {
                visitedPosition++;
            }
            map[guardRow][guardCol] = 'X';
            map[nextRow][nextCol] = '^';
            guardRow = nextRow;
            guardCol = nextCol;
            return true;
        }
        return false;
    }

    public boolean moveDown(char[][] map) {
        int nextRow = guardRow + 1;
        int nextCol = guardCol;
        if (isValidNextPosition(nextRow, nextCol, map)) {
            if (map[nextRow][nextCol] != 'X') {
                visitedPosition++;
            }
            map[guardRow][guardCol] = 'X';
            map[nextRow][nextCol] = 'v';
            guardRow = nextRow;
            guardCol = nextCol;
            return true;
        }
        return false;
    }

    public boolean moveRight(char[][] map) {
        int nextRow = guardRow;
        int nextCol = guardCol + 1;
        if (isValidNextPosition(nextRow, nextCol, map)) {
            if (map[nextRow][nextCol] != 'X') {
                visitedPosition++;
            }
            map[guardRow][guardCol] = 'X';
            map[nextRow][nextCol] = '>';
            guardRow = nextRow;
            guardCol = nextCol;
            return true;
        }
        return false;
    }

    public boolean moveLeft(char[][] map) {
        int nextRow = guardRow;
        int nextCol = guardCol - 1;
        if (isValidNextPosition(nextRow, nextCol, map)) {
            if (map[nextRow][nextCol] != 'X') {
                visitedPosition++;
            }
            map[guardRow][guardCol] = 'X';
            map[nextRow][nextCol] = '<';
            guardRow = nextRow;
            guardCol = nextCol;
            return true;
        }
        return false;
    }

    private static boolean isValidNextPosition(int guardRow, int guardCol, char[][] map) {
        return guardRow >= 0 && guardRow < map.length && guardCol >= 0 && guardCol < map[0].length;
    }

//    public static String checkObstructionPosition(int guardRow, int guardCol, char[][] map) {
////        forward row - 1
//        String up = obstructionUp(guardRow, guardCol, map);
//        if (up != null) return up;
////        down row + 1
//        String down = obstructionDown(guardRow, guardCol, map);
//        if (down != null) return down;
////        right col + 1
//        String right = obstructionRight(guardRow, guardCol, map);
//        if (right != null) return right;
////        left col -1
//        String left = obstructionLeft(guardRow, guardCol, map);
//        if (left != null) return left;
//
//        return "noObstruction";
//    }

    private static boolean obstructionLeft(int guardRow, int guardCol, char[][] map) {
        if (isValidNextPosition(guardRow, guardCol - 1, map)) {
            return map[guardRow][guardCol - 1] == '#';
        }
        return false;
    }

    private static boolean obstructionRight(int guardRow, int guardCol, char[][] map) {
        if (isValidNextPosition(guardRow, guardCol + 1, map)) {
            return map[guardRow][guardCol + 1] == '#';
        }
        return false;
    }

    private static boolean obstructionDown(int guardRow, int guardCol, char[][] map) {
        if (isValidNextPosition(guardRow + 1, guardCol, map)) {
            return map[guardRow + 1][guardCol] == '#';
        }
        return false;
    }

    private static boolean obstructionUp(int guardRow, int guardCol, char[][] map) {
        if (isValidNextPosition(guardRow - 1, guardCol, map)) {
            return map[guardRow - 1][guardCol] == '#';
        }
        return false;
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
