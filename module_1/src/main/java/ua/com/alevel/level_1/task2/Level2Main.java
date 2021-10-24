package ua.com.alevel.level_1.task2;

import java.util.ArrayList;
import java.util.Scanner;

public class Level2Main {
    private final ChessKnight chessKnight = new ChessKnight();
    private int numOfStarts = 0;
    private final Scanner scanInt = new Scanner(System.in);
    private final Scanner scanLine = new Scanner(System.in);
    ArrayList<Integer> canToMove = new ArrayList<Integer>();

    public void runlevel2() throws Exception {
//        Knight knight = new Knight();
//        knight.setRank("u");
//        knight.setFile(8);
        int position = 1;
        int file;
        String rank ;
        switch (position){
            case 1 :
                System.out.print("Enter file - ");
                file = scanInt.nextInt();
                System.out.print("Enter rank - ");
                rank = scanLine.nextLine();
                canToMove = chessKnight.knightMove(file, rank, numOfStarts, canToMove);
                numOfStarts = 1;
                System.out.println();
                System.out.print("If you want to go on press 1 (else 0) : ");
                position = scanInt.nextInt();
                if(position == 1){
                    this.runlevel2();
                }
                break;
            case 0:
                break;
        }
    }
}
