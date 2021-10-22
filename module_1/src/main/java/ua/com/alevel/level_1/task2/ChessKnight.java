package ua.com.alevel.level_1.task2;

import java.util.Scanner;

public class ChessKnight {

    public static final Scanner scanner = new Scanner(System.in);

    public void knightMove(int file, String rank){
        int letteInt = 0, indexToBreak = 0;
        switch (rank){
            case "a" : letteInt = 1; break;
            case "b" : letteInt = 2; break;
            case "c" : letteInt = 3; break;
            case "d" : letteInt = 4; break;
            case "e" : letteInt = 5; break;
            case "f" : letteInt = 6; break;
            case "g" : letteInt = 7; break;
            case "h" : letteInt = 8; break;
            default :
                rank = scanner.nextLine();
                knightMove(file, rank);
                indexToBreak = 1;
                break;
        }
        String markup[][] = {{"1","2","3","4","5","6","7","8"},{"a","b","c","d","e","f","g","h"}};
        System.out.print("    ");
        for (int i = 0; i < 8; i++) {
            System.out.print(markup[1][i] + "   ");
        }
        if(indexToBreak!=1){
            int board[][] = new int[8][8];
            board[8 - file][letteInt-1] = 1;
            System.out.println();
            for (int i = 0; i < board.length; i++) {
                System.out.print(markup[0][i] + "   ");
                for (int j = 0; j < 8; j++) {
                    System.out.print(board[i][j] + "   ");
                }
                System.out.println();
            }
        }
    }
}
