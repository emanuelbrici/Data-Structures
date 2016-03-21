/**
 * Created by emanuel on 10/4/14.
 */

import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.PrintStream;
        //import java.util.Stack;
        import java.util.Vector;

public class SplaySymbolTable<K extends Comparable<K>,V> implements SymbolTable<K,V> {


    private Node root;
    public int comparisons = 0;
    public int rotations = 0;

    public SplaySymbolTable() {
        root = null;
    }
        //Rotates the tree right when called and
        //does so with parent pointer
    private Node rotateRight(Node tree) {
        assert tree.left != null;
        assert tree != null;
        Node tmp;
        tmp = tree.left;
        tree.left = tmp.right;
        if (tmp.right != null ) {
            tmp.right.parent = tree;
        }
        tmp.right = tree;
        tmp.parent = tree.parent;
        tree.parent = tmp;
        if(tmp.parent == null) {
            root = tmp;
        }
        else if (tmp.parent.left == tree) {
            tmp.parent.left = tmp;
        }
        else {
            tmp.parent.right = tmp;
        }
        rotations += 1;
        return tmp;


    }
        //Rotates the tree left when called and
        //does so with parent pointer
    private Node rotateLeft(Node tree) {
        assert tree.right != null;
        assert tree != null;
        Node tmp;
        tmp = tree.right;
        tree.right = tmp.left;
        if (tmp.left != null ) {
            tmp.left.parent = tree;
        }
        tmp.left = tree;
        tmp.parent = tree.parent;
        tree.parent = tmp;
        if(tmp.parent == null) {
            root = tmp;
        }
        else if (tmp.parent.right == tree) {
            tmp.parent.right = tmp;

        }
        else {
            tmp.parent.left = tmp;
        }
        rotations += 1;
        return tmp;

    }

    private void splay(Node tree) {
        assert tree != null;
        while (tree.parent != null && tree.parent.parent != null) { // checks to see of there is a grandparent
            if (tree.parent.parent.left == tree.parent) {
                if (tree.parent.left == tree) { // Zig-Zig
                    tree = rotateRight(rotateRight(tree.parent.parent));
                } else {  // Zig-Zag
                    tree.parent.parent.left = rotateLeft(tree.parent);
                    tree = rotateRight(tree.parent);

                }
            } else {
                if (tree.parent.left == tree) { // Zag-Zig
                    tree.parent.parent.right = rotateRight(tree.parent);
                    tree = rotateLeft(tree.parent);
                } else if(tree.parent.right == tree) { //Zag-Zag
                    tree = rotateLeft(rotateLeft(tree.parent.parent));

                }
            }
        }
        if(tree != root) { // only a parent no grandparent

            if (root.left == tree) { // Zig
                rotateRight(tree.parent);
            }
            if (root.right == tree) { //Zag
                rotateLeft(tree.parent);
            }
        }


    }

    @Override
    public void insert(K key, V val) {
       root = insertAux(root, key, val);

    }



    private Node insertAux(Node tree, K key, V val) {
        if (tree == null) { // nothing in the tree
            return new Node(key, val);
        }

        int cmp = key.compareTo((K)tree.key);       // re-enters payload
        if(cmp == 0 ) {  // Key is already in the tree
            comparisons += 1;
            tree.key = key;
            tree.val = val;
            splay(tree);
            return tree;
        }
        Node tmp;
        tmp = tree;
        while(true) {

            if(key.compareTo((K)tmp.key) == 0 ) {  // Key is already in the tree
                comparisons += 1;
                tmp.key = key;
                tmp.val = val;;
                splay(tmp);
                return tmp;
            }

            if (tmp.left == null && key.compareTo((K)tmp.key) < 0) { // key is less then tmp.key and tmp.left has no node
                comparisons += 1;
                tmp.left = new Node(key, val);
                tmp.left.parent = tmp; // set new node parent to point to tmp
                splay(tmp.left);

                return root;
            }

            if (tmp.right == null && key.compareTo((K)tmp.key) > 0) {// key is greater then tmp.key and tmp.right has no node
                comparisons += 1;
                tmp.right = new Node(key, val);
                tmp.right.parent = tmp;  // set new node parent to point to tmp
                splay(tmp.right);
                return root;
            }
           ;
            if(key.compareTo((K)tmp.key) < 0 ) { // key is less the tmp.key
                comparisons += 1;
                if(tmp.left == null) {
                    tmp.left = new Node (key, val); // new node
                    tmp.left.parent = tmp;    // set new node parent to point to tmp
                    splay(tmp.left);
                    return root;
                }
                else {
                    tmp = tmp.left;
                }
            }
            else {        // key is greater the tmp.key
                comparisons += 1;
                if(tmp.right == null) {
                    tmp.right = new Node(key, val); // new node
                    tmp.right.parent = tmp;   // set new node parent to point to tmp
                    splay(tmp.right);
                    return root;
                }
                else {
                    tmp = tmp.right;
                }
            }

        }

    }


    @Override
    public V search(K key) {
            return searchAux(root, key);
    }

    private V searchAux(Node tree, K key) {
        if (tree == null) {
            return null;
        }

        Node tmp;
        tmp = tree;
        Node tmpValFound;
        while (true) {
            if (key.compareTo((K) tmp.key) == 0 || tmp.left == null && tmp.key == key || tmp.right == null && tmp.key == key) { // key = tmp.key
                if (key.compareTo((K) tmp.key) == 0) {
                    comparisons += 1;
                    tmpValFound = tmp;
                    splay(tmpValFound);
                    return (V) tmp.val;
                }
                if (tmp.left == null && tmp.key != key) {
                    tmpValFound = tmp;
                    splay(tmpValFound);
                }
                if (tmp.right == null && tmp.key != key) {
                    tmpValFound = tmp;
                    splay(tmpValFound);
                }
            } else if (key.compareTo((K) tmp.key) < 0) {  // key is less the tmp.key
                comparisons += 1;
                if (tmp.key == key) {
                    tmpValFound = tmp;
                    splay(tmpValFound);
                    return (V) tmp.val;
                }
                if (tmp.left == null) {
                    splay(tmp);
                    break;
                }
                tmp = tmp.left;
            } else if (key.compareTo((K) tmp.key) > 0) {  // key is greater the tmp.key
                comparisons += 1;
                if (tmp.key == key) {
                    tmpValFound = tmp;
                    splay(tmpValFound);
                    return (V) tmp.val;
                }
                if (tmp.right == null) {
                    splay(tmp);
                    break;
                }
                tmp = tmp.right;
            }

        }
    return null;
    }





    private void serializeAux(Node tree, Vector<String> vec) {
        if (tree == null)
            vec.addElement(null);
        else {
            vec.addElement(tree.key.toString() + ":black");
            serializeAux(tree.left, vec);
            serializeAux(tree.right, vec);
        }
    }

    public Vector<String> serialize() {
        Vector<String> vec = new Vector<String>();
        serializeAux(root, vec);
        return vec;
    }

    void printTree(String fname) {
        Vector<String> st = serialize();
        TreePrinter treePrinter = new TreePrinter(st);
        treePrinter.fontSize = 14;
        treePrinter.nodeRadius = 14;
        try {
            FileOutputStream out = new FileOutputStream(fname);
            PrintStream ps = new PrintStream(out);
            treePrinter.printSVG(ps);
        } catch (FileNotFoundException e) {}
    }

    public static void main(String[] args) {
        SplaySymbolTable<Integer,Integer> symtab = new SplaySymbolTable<Integer,Integer>();
        for (int i = 0; i < 10; i++)
            symtab.insert(i,i);
        symtab.printTree("splay-insert-10.svg");

        Integer I = symtab.search(0);
        System.out.println("searched/found " + I);
        symtab.printTree("splay-search-0.svg");
        /*
        I = symtab.remove(7);
        System.out.println("removed/found " + I);
        symtab.printTree("splay-remove-7.svg");

        I = symtab.remove(1);
        System.out.println("removed/found " + I);
        symtab.printTree("splay-remove-1.svg");
        */
    }

}

