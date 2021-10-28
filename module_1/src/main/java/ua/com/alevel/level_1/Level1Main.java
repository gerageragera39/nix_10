package ua.com.alevel.level_1;

import ua.com.alevel.level_1.task1.NumberOfUniqueCharacters;
import ua.com.alevel.level_1.task2.ChessKnight;
import ua.com.alevel.level_1.task3.TriangleArea;

import java.util.ArrayList;
import java.util.Scanner;

public class Level1Main {

    private final ChessKnight chessKnight = new ChessKnight();
    private int numOfStarts = 0;
    private final Scanner scanInt = new Scanner(System.in);
    private final Scanner scanLine = new Scanner(System.in);
    public ArrayList<Integer> canToMove = new ArrayList<Integer>();
    private final NumberOfUniqueCharacters numberOfUniqueCharacters = new NumberOfUniqueCharacters();

    public void runtask1() {
        System.out.println(numberOfUniqueCharacters.findNumOfUniqueChar());
    }

    public void runtask2() throws Exception {
        int position = 1;
        int file;
        String rank;
        switch (position) {
            case 1:
                System.out.print("Enter file - ");
                file = scanInt.nextInt();
                System.out.print("Enter rank - ");
                rank = scanLine.nextLine();
                canToMove = chessKnight.knightMove(file, rank, numOfStarts, canToMove);
                numOfStarts = 1;
                System.out.println();
                System.out.print("If you want to go on press 1 (else 0) : ");
                position = scanInt.nextInt();
                if (position == 1) {
                    this.runtask2();
                }
                break;

            case 0:
                break;
        }
    }

    public void runtask3() {
        TriangleArea dots = new TriangleArea();
        dots.calcTriangleArea();
    }
}