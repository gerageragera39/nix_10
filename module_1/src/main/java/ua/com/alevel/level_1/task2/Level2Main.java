package ua.com.alevel.level_1.task2;

import ua.com.alevel.level_1.task1.NumberOfUniqueCharacters;

public class Level2Main {
    private final ChessKnight chessKnight = new ChessKnight();

    public void runlevel2() {
//        Knight knight = new Knight();
//        knight.setRank("u");
//        knight.setFile(8);
        int file = 1;
        String rank = "a";
        chessKnight.knightMove(file, rank);
    }
}
