package ua.com.alevel.level_2.task2;

import java.util.Stack;

public class Tree {

    public TreeNode root = null;
    public int numOfKnot = 1;

    public TreeNode treeCreating() {

        root = makingTree();
        printTree(root);
        if (root != null) {
            System.out.println();
            System.out.println("Sum of all values = " + root.sum());
        } else {
            System.out.println("Size of tree = 0");
        }
        if (root != null) {
            System.out.println("Max depth = " + findMaxDepth(root));
        } else {
            System.out.println("Max depth = 0");
        }
        return root;
    }

    public TreeNode makingTree() {
        TreeNode tree = null;
        int random = randBranch(numOfKnot);
        numOfKnot = 0;
        if (random != 0) {
            if (random == 1) {
                if (leftOrRight() == 0) {
                    int value = randValue();
                    tree = new TreeNode(value,
                            makingTree(),
                            null);
                } else {
                    int value = randValue();
                    tree = new TreeNode(value,
                            null,
                            makingTree());
                }
            } else {
                int value = randValue();
                tree = new TreeNode(value,
                        makingTree(),
                        makingTree());

            }
        }

        return tree;
    }

    public static void printTree(TreeNode root) {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int gaps = 64;
        boolean isRowEmpty = false;
        String separator = "________________________________________________________________________________________________________________________________";
        System.out.println(separator);
        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (globalStack.isEmpty() == false) {
                TreeNode temp = (TreeNode) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.getValue());
                    localStack.push(temp.getLeft());
                    localStack.push(temp.getRight());
                    if (temp.getLeft() != null ||
                            temp.getRight() != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("__");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;
            if (gaps < 1) {
                System.out.println();
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tThe tree is too big to print further");
                break;
            }
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop());
        }
        System.out.println(separator);
    }

    public int findMaxDepth(TreeNode root) {

        if (root == null) return 0;

        int Left = findMaxDepth(root.getLeft());

        int Right = findMaxDepth(root.getRight());

        return Math.max(Left, Right) + 1;

    }

    public int randBranch(int numOfKnot) {
        int random;
        if (numOfKnot == 1) {
            random = (int) (Math.random() * 2) + 1;
        } else {
            random = (int) (Math.random() * 3);
        }
        return random;
    }

    public int randValue() {
        int value = (int) (Math.random() * 10) + 1;
        return value;
    }

    public int leftOrRight() {
        int orient = (int) (Math.random() * 2) + 1;
        return orient;
    }
}
