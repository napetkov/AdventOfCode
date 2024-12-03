package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 implements Runnable{
    @Override
    public void run() {
        Integer result = 0;
        Integer resultToSubtract = 0;

        try {
            String input = Files.readString(Paths.get("C:\\Users\\Lenovo\\OneDrive\\Desktop\\Advent_of_code\\day_3\\Day_3_input.txt"));

            boolean enabled = true;

            String regex = "(mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don\\'t\\(\\))";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                String matchGroup = matcher.group();
                if("do()".equals(matchGroup)){
                    enabled = true;
                }else if("don't()".equals(matchGroup)){
                    enabled = false;
                }else {
                    if (enabled){
                        result += calculateResult(matchGroup);
                    }
                }
            }

            System.out.println(result);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Integer calculateResult(String input) {
        int result = 0;
        String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);


        while (matcher.find()) {
            int firstNumber = Integer.parseInt(matcher.group(1));
            int secondNumber = Integer.parseInt(matcher.group(2));

            result += firstNumber * secondNumber;
        }
        return result;
    }
}
