package com.walge;

import java.util.Scanner;

public class Main {

    static String[] word  = {"Winter", "Spring", "Summer", "Autumn", "Freedom", "Victory", "Kangaroo", "Tiger",
                             "Coffee", "Wolverine", "Satan", "Brewery", "Nature", "Assembly", "Hardcore", "Grapefruit"};
    static int random = (int)Math.floor(Math.random() * word.length);
    static char c;
    static int activePlayer = 1;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите количество игроков:");
        int[] players = new int[input.nextInt()];
        input.nextLine();
        System.out.println("Угадай слово");
        int lengthWord = word[random].length();
        String maskWord = "\u0015".repeat(lengthWord);
        System.out.println(maskWord);

        while (maskWord.contains("\u0015")) {
            System.out.println("\nХод игрока " + activePlayer + ".\nВведите букву или слово целиком\n" + maskWord);
            String userWord = input.nextLine().toLowerCase();
            if (userWord.length() == 1) {
                c = userWord.charAt(0);
                if (word[random].toLowerCase().indexOf(c) >= 0) {
                    if (maskWord.indexOf(c) >= 0) {
                        System.out.println("Эта буква уже открыта.");
                    } else {
                        System.out.println("Удача");
                        for (char elem : word[random].toLowerCase().toCharArray()) {
                            if (elem == c) {
                                maskWord = replaceMaskLetter(c, maskWord);
                                players[activePlayer - 1] = players[activePlayer - 1] + 1;
                            }
                        }
                    }
                 } else {
                    System.out.println("Такой буквы нет.");
                    if (players.length > 1) nextTurn(players);
                }
            } else {
                if (userWord.equals(word[random].toLowerCase())) {
                    int countGuessedLetters = 0;
                    for (char element : maskWord.toCharArray()){
                        if (element == '\u0015') countGuessedLetters++;
                    }
                    players[activePlayer - 1] = players[activePlayer - 1] + countGuessedLetters;
                    maskWord = word[random];
                    System.out.println("Слово угадано!");
                    System.out.println(maskWord);
                } else {
                    System.out.println("Неверное слово, поробуй еще раз");
                    if (players.length > 1) nextTurn(players);
                }
            }
        }
        System.out.println("\nПобеда");
        int maxScore = 0;
        int winner = 0;
        for (int i = 1; i <= players.length; i++) {
            System.out.print("игрок " + i + " набрал " + players[i-1]);
            switch (players[i-1]) {
                case 1 -> System.out.println(" очко.");
                case 2, 3, 4 -> System.out.println(" очка.");
                default -> System.out.println(" очков.");
            }
            if (players[i - 1] > maxScore) {
                maxScore = players[i-1];
                winner = i;
            }
        }
        System.out.println("игрок " + winner + " победил!");
    }

    private static void nextTurn(int[] players) {
        System.out.print("игрок " + activePlayer + " набрал " + players[activePlayer - 1]);
        switch (players[activePlayer-1]) {
            case 1 -> System.out.println(" очко за раунд.");
            case 2, 3, 4 -> System.out.println(" очка за раунд.");
            default -> System.out.println(" очков за раунд.");
        }
        if (activePlayer < players.length) {
            activePlayer++;
        } else {
            activePlayer = 1;
        }
    }

    /*
    -a-a---- j
    ja-a----
     */
    public static String replaceMaskLetter(char c, String maskWord) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < word[random].length(); i++) {
            if (word[random].toLowerCase().charAt(i) == c) {
                stringBuilder.append(c);
            } else if (maskWord.charAt(i) != '\u0015') {
                stringBuilder.append(maskWord.charAt(i));
            } else {
                stringBuilder.append('\u0015');
            }
        }
        return stringBuilder.toString();
    }
}
