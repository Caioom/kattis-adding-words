package com.companies.tests;

import java.util.HashMap;
import java.util.Scanner;

public class Application {

    private static HashMap<String, Integer> wordToNumber = new HashMap<>();

    private static HashMap<Integer, String> numberToWord = new HashMap<>();

    private static Integer previousNumber;

    private static String operation = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            solution(data);
        }
    }

    public static void solution(String line) {
        String lineWithoutSpaces = line.trim();
        String[] command = lineWithoutSpaces.split(" ");
        String wordResult = "unknown";
        Integer numberResult = 0;
        for (int i = 0; i < command.length; i++) {
            if (i == 0) {
                operation = command[i];
            }

            if (i == 0 && operation.equalsIgnoreCase("clear")) {
                wordToNumber.clear();
                numberToWord.clear();
            }

            if (i == 1 && operation.equalsIgnoreCase("def")) {
                String word = command[i];
                Integer number = Integer.valueOf(command[++i]);
                if (wordToNumber.get(word) != null) {
                    numberToWord.remove(wordToNumber.get(word));
                }
                numberToWord.put(number, word);
                wordToNumber.put(word, number);
            }

            if (i > 0 && operation.equalsIgnoreCase("calc")) {
                String variable = command[i];
                if (previousNumber == null && !variable.equals("+") && !variable.equals("-") && !variable.equals("=")) {
                    if (wordToNumber.get(variable) == null) break;

                    previousNumber = wordToNumber.get(variable);
                    if (command.length == 3) {
                        wordResult = variable;
                        break;
                    }
                }

                if (variable.equals("+")) {
                    String nextWord = command[++i];
                    if (wordToNumber.get(nextWord) == null) break;

                    if (previousNumber == null) {
                        numberResult += wordToNumber.get(nextWord);
                    } else {
                        numberResult += previousNumber + wordToNumber.get(nextWord);
                        previousNumber = null;
                    }
                }

                if (variable.equals("-")) {
                    String nextWord = command[++i];
                    if (wordToNumber.get(nextWord) == null) break;

                    if (previousNumber == null) {
                        if (wordToNumber.get(nextWord) < 0) {
                            numberResult += wordToNumber.get(nextWord);
                        } else {
                            numberResult -= wordToNumber.get(nextWord);
                        }
                    } else {
                        if (wordToNumber.get(nextWord) < 0) {
                            numberResult += previousNumber + wordToNumber.get(nextWord);
                        } else {
                            numberResult += previousNumber - wordToNumber.get(nextWord);
                        }
                        previousNumber = null;
                    }
                }
                wordResult = numberToWord.get(numberResult) == null ? "unknown" : numberToWord.get(numberResult);
            }
        }

        if (operation.equals("calc")) {
            System.out.println(lineWithoutSpaces.substring(5) + " " + wordResult);
        }
    }
}
