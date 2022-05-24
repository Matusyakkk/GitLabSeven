package btree;

import java.util.Arrays;

public class BTree {
    static int order;
    BNode root;
    private class BNode {
        static int t; // variable to determine order of tree
        int count;  // number of key in node
        int[] key;  // array of key value
        BNode[] child; // array of reference
        boolean leaf;  // is node a leaf or not
        BNode parent; // parent of curent node

        BNode(){}

        BNode(int t, BNode parent) {
            this.t = t; // assing size
            this.parent = parent;   // assign parent
            key = new int[2 * t - 1];   // array of proper size
            child = new BNode[2 * t];   // array of refs proper size
            leaf = true;    // everyone is leaf at first
            count = 0;  // until we add keys later
        }

        int getValue(int idx) {return key[idx];}
        BNode getChild(int idx) {return child[idx];}
    }

    public BTree(int order) {
        this.order = order;
        root = new BNode(order, null);
    }

    public BNode search(int key) {
        return search(root, key);
    }
    private BNode search(BNode root, int key) {
        int i = 0;
        while (i < root.count && key > root.key[i]) i++;

        if (i <= root.count && key == root.key[i])
            return root;
        if (root.leaf)
            return null;
        else
            return search(root.getChild(i), key);
    }

    private void split(BNode parent, int idx, BNode newChild) {
        BNode xChild = new BNode(order, null);
        xChild.leaf = newChild.leaf;
        xChild.count = order - 1;

        for (int i = 0; i < order - 1; i++)
            xChild.key[i] = newChild.key[i + order]; //copy end of newChild into front of xChild

        if (!newChild.leaf) //if not leaf we have to reassign child nodes.
            for (int i = 0; i < order; i++)
                xChild.child[i] = newChild.child[i + order];
        newChild.count = order - 1; // new size of newChild

        for (int i = parent.count; i > idx; i--) // if we push key into parent we have to rearrange child nodes
            parent.child[i + 1] = parent.child[i]; // shift child

        parent.child[idx + 1] = xChild; // reassing child of parent

        for (int i = parent.count; i > idx; i--)
            parent.key[i + 1] = parent.key[i]; // shift keys

        parent.key[idx] = newChild.key[order - 1]; // push value up into root;
        newChild.key[order - 1] = 0;

        for (int i = 0; i < order - 1; i++)
            newChild.key[i + order] = 0; // del old values

        parent.count++;
    }

    public void insert(int key) {
        BNode r = root; // start with root node
        if (r.count == 2 * order - 1) {// if node full
            BNode s = new BNode(order, null);
            //init node
            root = s;
            s.leaf = false;
            s.count = 0;
            s.child[0] = r;

            split(s, 0, r); // split root
            insert(s, key); // call insert method
        } else
            insert(r, key);
    }

    private void insert(BNode node, int key) {
        int i = node.count;
        if (node.leaf) {
            while (i >= 1 && key < node.key[i - 1]) { // findn stop to push
                node.key[i] = node.key[i - 1];
                i--;
            }
            node.key[i] = key; // assing value to node
            node.count++;
        } else {
            int j = 0;
            while (j < node.count && key > node.key[j]) j++;
            i++;
            insert(node.child[j], key);
            if (node.child[j].count == order * 2 - 1) {
                split(node, j, node.child[j]); // call split on node's ith child
                if (key > node.key[j])
                    j++;
            }

        }
    }

    public void delete(int key) {
        BNode temp = new BNode(order, null);
        temp = search(root, key);
        if(temp.leaf && temp.count > order - 1) {
            int i = 0;
            while (key > temp.getValue(i)) i++;
            for (int j = i; j < 2 * order - 2; j++)
                temp.key[j] = temp.getValue(j+1);
            temp.count--;
        } else
            System.out.println("This node is either not a leaf or has less than order - 1 keys.");
    }

    public void print(){ print(root); }
    private void print(BNode node) {
        for (int i = 0; i < node.count; i++)
            System.out.print(node.getValue(i) + " ");
        if (!node.leaf)
            for (int i = 0; i <= node.count; i++)
                if (node.getChild(i) != null) {
                    System.out.println();
                    print(node.getChild(i));
                }
    }
}