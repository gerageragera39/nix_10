package ua.com.alevel.level_2;

import ua.com.alevel.level_2.task1.CorrectOrder;
import ua.com.alevel.level_2.task2.Tree;

import java.util.Scanner;

public class Level2Main {

    public static final Scanner scan = new Scanner(System.in);

    public void runTask2() {
        Tree tree = new Tree();
        tree.treeCreating();
    }
    public void runTask1() {
        CorrectOrder brackets = new CorrectOrder();
        brackets.checkForCorrectOrder();
    }
}
