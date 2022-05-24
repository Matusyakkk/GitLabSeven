import btree.BTree;

public class Main {
    public static void main(String[] args) {
        BTree tree = new BTree(3);
        tree.insert(8);
        tree.insert(9);
        tree.insert(10);
        tree.insert(11);
        tree.show();
        System.out.println("Hello Git");
    }
}
