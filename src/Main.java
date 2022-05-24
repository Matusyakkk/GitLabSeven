import btree.BTree;

public class Main {
    public static void main(String[] args) {
        BTree tree = new BTree(3);
        for (int i = 1; i < 20; i++)
            tree.insert(i);
        tree.print();
    }
}
