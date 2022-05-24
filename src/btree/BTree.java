package btree;

public class BTree {
    private int M;

    int halfKey;

    private int halfKey() {
        if (M%2==0)
            halfKey = M/2 - 1;
        else
            halfKey = M/2;
        return halfKey;
    }
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
        Node xChild = new Node();
        xChild.isLeaf = newChild.isLeaf;
        xChild.numKey = halfKey();
        for (int i = 0; i < halfKey(); i++)
            xChild.keys[i] = newChild.keys[i + halfKey() + 1];

        if (!newChild.isLeaf)
            for (int i = 0; i < halfKey() + 1; i++)
                xChild.child[i] = newChild.child[i + halfKey() + 1];
        newChild.numKey = halfKey();

        for (int i = parent.numKey; i >= childIdx + 1; i--)
            parent.child[i + 1] = parent.child[i];
        parent.child[childIdx + 1] = xChild;

        for (int i = parent.numKey - 1; i >= childIdx; i--)
            parent.keys[i + 1] = parent.keys[i];

        parent.keys[childIdx] = newChild.keys[halfKey()];
        parent.numKey++;
    }

    public void insert(int key) {
        Node r = root;
        if (r.numKey == M - 1) {
            Node s = new Node();
            root = s;
            s.isLeaf = false;
            s.numKey = 0;
            s.child[0] = r;
            split(s, 0, r);
            insert(s, key);
        } else
            insert(r, key);
    }
    private void insert(Node node, int key) {
        int i;
        if (node.isLeaf) {
            for (i = node.numKey - 1; i >= 0 && key < node.keys[i]; i--)
                node.keys[i + 1] = node.keys[i];
            node.keys[i + 1] = key;
            node.numKey++;
        } else {
            for (i = node.numKey - 1; i >= 0 && key < node.keys[i]; i--) ;
            i++;

            Node temp = node.child[i];
            if (temp.numKey == M - 1) {
                split(node, i, temp);
                if (key > node.keys[i])
                    i++;
            }
            insert(node.child[i], key);
        }
    }
}
