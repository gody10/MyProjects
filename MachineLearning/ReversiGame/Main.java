package com.company;
import java.util.*;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Reversi, You will be playing5" +
                " vs an AI bot\n" +
                "Please choose the max depth of minimax and the colour of your choice, just remember black goes first :)");
        System.out.print("Enter the depth: ");

        int depth = Integer.parseInt(scanner.nextLine());
        System.out.print("\nEnter the colour of your choice: B for Black or W for white: ");

        Reversi AI = new Reversi(depth);
        String choice = scanner.nextLine();
        int playerNo;
        int AIno;
        int nowPlaying;
        switch (choice) {
            case "B" -> {
                System.out.println("You chose to go first, select one of the possible moves");
                playerNo = 1;
                AIno = 2;
            }
            case "W" -> {
                System.out.println("Ai starting");
                playerNo = 2;
                AIno = 1;
            }
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        }
        Board b = new Board(playerNo);
        nowPlaying = 1 ;
        b.displayBoard();
        int scorePlayer = 2;
        int scoreAI = 2;
        while (!b.IsTerminal()){
            if (nowPlaying == playerNo) {
                System.out.println("Enter your move: (e.g. a6)");
                choice = scanner.nextLine();
                while (!b.placeMove(choice, playerNo)) {
                    System.out.println("Incorrect move please choose one of the valid moves shown");
                    choice = scanner.nextLine();
                }
            }
            else{
                AI.minimax(1,b,true,AIno,playerNo);
            }
            b.displayBoard();
            scorePlayer = b.CalculatePieces(1);
            scoreAI = b.CalculatePieces(2);
            System.out.println("Current score: " + scorePlayer + " "+ " "+scoreAI);
            nowPlaying = nowPlaying % 2 + 1;
        }

        if (scoreAI > scorePlayer ) {
            System.out.println("The winner is Ai with score: " + scorePlayer + " "+ " "+scoreAI);
        }
        else  if (scoreAI < scorePlayer ){
            System.out.println("The winner is You with score: " + scorePlayer + " "+ " "+scoreAI);
        }
        else{
            System.out.println("Tie with score: " + scorePlayer + " "+ " "+scoreAI);
        }
    }
}
