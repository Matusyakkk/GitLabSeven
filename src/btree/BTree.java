package btree;

public class BTree {
    private int M;
    private Node root;

    class Node {
        int numKey;
        int[] keys = new int[M - 1]; // key array
        Node[] child = new Node[M];
        boolean isLeaf = true;
    }

    public BTree(int M) {
        this.M = M;
        root = new Node();
        root.numKey = 0;
        root.isLeaf = true;
    }

}
