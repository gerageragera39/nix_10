package ua.com.alevel.level_1.task2;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ChessKnight {

    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final PrintStream printWriter = new PrintStream(System.out, true);
    public static final char aa = '\u265E';
    public static final char bb = '□';

    public ArrayList<Integer> knightMove(int file, String rank, int numOfStarts, ArrayList<Integer> canToMove) throws Exception {
        int letteInt = 0;
        int board[][] = new int[8][8];
        ArrayList<Integer> newCanToMove = new ArrayList<Integer>();
        boolean can = true;

        if (numOfStarts != 0) {
            can = false;
        }

        switch (rank) {
            case "a":
                letteInt = 1;
                break;
            case "b":
                letteInt = 2;
                break;
            case "c":
                letteInt = 3;
                break;
            case "d":
                letteInt = 4;
                break;
            case "e":
                letteInt = 5;
                break;
            case "f":
                letteInt = 6;
                break;
            case "g":
                letteInt = 7;
                break;
            case "h":
                letteInt = 8;
                break;
            default:
                throw new Exception("Incorrect rank entry");
        }

        if (numOfStarts == 1) {
            for (int i = 0; i < canToMove.size(); i += 2) {
                if ((file == canToMove.get(i)) && (letteInt == canToMove.get(i + 1))) {
                    can = true;
                }
            }
        }

        if (can == false) {
            throw new Exception("You can't go there");
        }

        System.out.println();
        String possibleMoves[][] = new String[8][8];
        if (file + 2 < 9) {
            if (letteInt + 1 < 9) {
                newCanToMove.add(file + 2);
                newCanToMove.add(letteInt + 1);
                board[file + 2 - 1][letteInt + 1 - 1] = 2;
            }
            if (letteInt - 1 > 0) {
                newCanToMove.add(file + 2);
                newCanToMove.add(letteInt - 1);
                board[file + 2 - 1][letteInt - 1 - 1] = 2;
            }
        }
        if (file - 2 > 0) {
            if (letteInt + 1 < 9) {
                newCanToMove.add(file - 2);
                newCanToMove.add(letteInt + 1);
                board[file - 2 - 1][letteInt + 1 - 1] = 2;
            }
            if (letteInt - 1 > 0) {
                newCanToMove.add(file - 2);
                newCanToMove.add(letteInt - 1);
                board[file - 2 - 1][letteInt - 1 - 1] = 2;
            }
        }
        if (letteInt + 2 < 9) {
            if (file + 1 < 9) {
                newCanToMove.add(file + 1);
                newCanToMove.add(letteInt + 2);
                board[file + 1 - 1][letteInt + 2 - 1] = 2;
            }
            if (file - 1 > 0) {
                newCanToMove.add(file - 1);
                newCanToMove.add(letteInt + 2);
                board[file - 1 - 1][letteInt + 2 - 1] = 2;
            }
        }
        if (letteInt - 2 > 0) {
            if (file + 1 < 9) {
                newCanToMove.add(file + 1);
                newCanToMove.add(letteInt - 2);
                board[file + 1 - 1][letteInt - 2 - 1] = 2;
            }
            if (file - 1 > 0) {
                newCanToMove.add(file - 1);
                newCanToMove.add(letteInt - 2);
                board[file - 1 - 1][letteInt - 2 - 1] = 2;
            }
        }

        System.out.println();
        String markup[][] = {{"1", "2", "3", "4", "5", "6", "7", "8"}, {"a", "b", "c", "d", "e", "f", "g", "h"}};
        System.out.print("     ");
        for (int i = 0; i < 8; i++) {
            System.out.print(markup[1][i] + "   ");
        }

        System.out.println();
        board[file - 1][letteInt - 1] = 1;
        System.out.println();
        for (int i = board.length - 1; i >= 0; i--) {
            System.out.print(markup[0][i] + "    ");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 0) {
                    System.out.print("●" + "   ");
                } else if (board[i][j] == 1) {
                    printWriter.print(ANSI_YELLOW + "2" + ANSI_RESET + "\t ");
                } else {
                    System.out.print(ANSI_GREEN + "x" + ANSI_RESET + "   ");
                }
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Your horse is in place " + ANSI_YELLOW + "2" + ANSI_RESET);
        System.out.println();
        for (int i = 0; i < newCanToMove.size(); i += 2) {
            System.out.println("You can go to " + newCanToMove.get(i) + " - " + refact(newCanToMove.get(i + 1)));
        }
        return newCanToMove;
    }

    public String refact(int letteInt) {
        String rank = "0";
        switch (letteInt) {

            case 1:
                rank = "a";
                break;
            case 2:
                rank = "b";
                break;
            case 3:
                rank = "c";
                break;
            case 4:
                rank = "d";
                break;
            case 5:
                rank = "e";
                break;
            case 6:
                rank = "f";
                break;
            case 7:
                rank = "g";
                break;
            case 8:
                rank = "h";
                break;
        }
        return rank;
    }
}