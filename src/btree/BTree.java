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

    private void split(Node parent, int childIdx, Node newChild) {
        int halfKey;
        Node xChild = new Node();
        xChild.isLeaf = newChild.isLeaf;

        if (M%2==0)
            halfKey = M/2 - 1;
        else
            halfKey = M/2;
        xChild.numKey = halfKey;

        for (int i = 0; i < halfKey; i++)
            xChild.keys[i] = newChild.keys[i + halfKey + 1];

        if (!newChild.isLeaf)
            for (int i = 0; i < halfKey + 1; i++)
                xChild.child[i] = newChild.child[i + halfKey + 1];
        newChild.numKey = halfKey;

        for (int i = parent.numKey; i >= childIdx + 1; i--)
            parent.child[i + 1] = parent.child[i];
        parent.child[childIdx + 1] = xChild;

        for (int i = parent.numKey - 1; i >= childIdx; i--)
            parent.keys[i + 1] = parent.keys[i];

        parent.keys[childIdx] = newChild.keys[halfKey];
        parent.numKey++;
    }
}
