package ua.com.alevel.level3.task;

public class GameLife {

    private Dots[][] area = new Dots[10][10];

    public void runGame() {
        randomDots(area);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.println(area[i][j].getLive());
            }
            System.out.println();
        }
    }

    public Dots[][] randomDots(Dots[][] area){
        int randomLive;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                randomLive = (int) Math.random();
                area[i][j].setLive((randomLive));
            }
        }
        return area;
    }
}