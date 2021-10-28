package ua.com.alevel.level3.task;

import java.util.Scanner;

public class GameLife {

    public static final int M = 10, N = 10;
    public static final Scanner scan = new Scanner(System.in);

    public void runGame() {
        int position = 1;
        int[][] grid = new int[M][N];
        grid = randomGrid(grid);
        while (position != 0) {
            grid = nextGeneration(grid, M, N);
            printGrid(grid);
            System.out.print("If you want to see the next generation enter 1, if not, then 0 : ");
            position = scan.nextInt();
        }
    }

    static int[][] nextGeneration(int grid[][], int M, int N) {
        int[][] future = new int[M][N];
        for (int l = 0; l < M; l++) {
            for (int m = 0; m < N; m++) {
                int aliveNeighbours = 0;
                if ((l < M - 1) && (m < N - 1) && (l > 0) && (m > 0)) {
                    for (int i = -1; i <= 1; i++)
                        for (int j = -1; j <= 1; j++)
                            aliveNeighbours += grid[l + i][m + j];
                    aliveNeighbours -= grid[l][m];
                } else if ((l == 0) && (m == 0)) {
                    for (int i = 0; i <= 1; i++) {
                        for (int j = 0; j <= 1; j++) {
                            aliveNeighbours += grid[l + i][m + j];
                        }
                    }
                    aliveNeighbours -= grid[l][m];
                } else if ((l == M - 1) && (m == 0)) {
                    for (int i = 0; i >= -1; i--) {
                        for (int j = 0; j <= 1; j++) {
                            aliveNeighbours += grid[l + i][m + j];
                        }
                    }
                    aliveNeighbours -= grid[l][m];
                } else if ((l == M - 1) && (m == N - 1)) {
                    for (int i = 0; i >= -1; i--) {
                        for (int j = 0; j >= -1; j--) {
                            aliveNeighbours += grid[l + i][m + j];
                        }
                    }
                    aliveNeighbours -= grid[l][m];
                } else if ((l == 0) && (m == N - 1)) {
                    for (int i = 0; i <= 1; i++) {
                        for (int j = 0; j >= -1; j--) {
                            aliveNeighbours += grid[l + i][m + j];
                        }
                    }
                    aliveNeighbours -= grid[l][m];
                } else if (l == 0) {
                    for (int i = 0; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            aliveNeighbours += grid[l + i][m + j];
                        }
                    }
                    aliveNeighbours -= grid[l][m];
                } else if (m == 0) {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = 0; j <= 1; j++) {
                            aliveNeighbours += grid[l + i][m + j];
                        }
                    }
                    aliveNeighbours -= grid[l][m];
                } else if (l == M - 1) {
                    for (int i = -1; i <= 0; i++) {
                        for (int j = -1; j <= 1; j++) {
                            aliveNeighbours += grid[l + i][m + j];
                        }
                    }
                    aliveNeighbours -= grid[l][m];
                } else if (m == N - 1) {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 0; j++) {
                            aliveNeighbours += grid[l + i][m + j];
                        }
                    }
                    aliveNeighbours -= grid[l][m];
                }

                if ((grid[l][m] == 1) && (aliveNeighbours < 2)) {
                    future[l][m] = 0;
                } else if ((grid[l][m] == 1) && (aliveNeighbours > 3)) {
                    future[l][m] = 0;
                } else if ((grid[l][m] == 0) && ((aliveNeighbours == 3))) {
                    future[l][m] = 1;
                } else {
                    future[l][m] = grid[l][m];
                }
            }
        }
        return future;
    }

    public void printGrid(int[][] future) {
        System.out.println("Next Generation");
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (future[i][j] == 0)
                    System.out.print(".   ");
                else
                    System.out.print("●   ");
            }
            System.out.println();
        }
    }

    public int[][] randomGrid(int[][] grid) {
        int randomLive;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                randomLive = (int) (Math.random() * 2);
                grid[i][j] = randomLive;
            }
        }

        System.out.println("Original Generation");
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 0)
                    System.out.print(".   ");
                else
                    System.out.print("●   ");
            }
            System.out.println();
        }
        System.out.println();
        return grid;
    }
}