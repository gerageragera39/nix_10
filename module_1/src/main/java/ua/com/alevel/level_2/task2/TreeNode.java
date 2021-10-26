package ua.com.alevel.level_2.task2;


public class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    public int getValue() {
        return value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public TreeNode(int value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public TreeNode(int value) {
        this.value = value;
    }

    public int sum() {
        int summ = value;

        if (left != null) {
            summ += left.sum();
        }

        if (right != null) {
            summ += right.sum();
        }

        return summ;
    }
}


