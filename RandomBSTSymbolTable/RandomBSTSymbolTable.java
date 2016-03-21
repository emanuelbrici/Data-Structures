/**
 * Created by emanuel on 9/21/14.
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Vector;


public class RandomBSTSymbolTable<K extends Comparable<K>,V>
        implements SymbolTable<K,V>{
    Node root;
    //Constructor for this class
    public RandomBSTSymbolTable() {
        root = null;

    }
    // Inserts a Node with key and  val
    @Override
    public void insert(K key, V val) {
        root = insertRandom(root, key, val);

    }
    //Helper function of insert that passes in
    // the tree and recursively inserts
    // a new node as well with its designated
    // key and value
    private Node insertRootAux(Node tree, K key, V val) {
        if (tree == null)
            return new Node(key, val);
        int cmp = key.compareTo((K) tree.key);
        if (cmp == 0) { // key already in root
            tree.key = key; // replace payload
            tree.val = val;
        }
        else if (cmp < 0) { // key < tree.key
            tree.left = insertRootAux(tree.left, key, val);
            //update N
            tree = rotateRight(tree);
            tree.N = 1 + size(tree.left) + size(tree.right);

        }
        else { // key > tree.key
            tree.right = insertRootAux(tree.right, key, val);
            //update N
            tree = rotateLeft(tree);
            tree.N = 1 + size(tree.left) + size(tree.right) ;

        }
        return tree;
    }

    int size(Node tree) {
        return (tree == null) ? 0 : tree.N;
    }

    static Random rng = new Random(1234);
    //This method inserts the numbers in a random order.
    //this is useful because being random helps make a
    //more balanced tree then is some order
    private Node insertRandom(Node tree, K key, V val) {
        if (tree == null)
            return new Node(key, val);     // N = 1 (set in constructor)
        if (rng.nextDouble()*(tree.N+1) < 1.0)      // with probability 1/(N+1)
            return insertRootAux(tree, key, val);    // insert new node at root
        int cmp = key.compareTo((K)tree.key);     // else recursive down tree
        if (cmp == 0) {
            tree.key = key;
            tree.val = val;   // replace payload (N unchanged)
        }
        else if (cmp < 0) {
            tree.left = insertRandom(tree.left, key, val); // insert into left tree
            tree.N = 1 + tree.left.N + size(tree.right); // update N
        }
        else {
            tree.right = insertRandom(tree.right, key, val); // insert into right tree
            tree.N = 1 + size(tree.left) + tree.right.N; // update N
        }
        return tree;

    }
    //Searches the tree looking for a key.
    //if the key is found the value of that
    //node is returned
    @Override
    public V search(K key) {
        return searchAux(root, key);
    }

    //Helper function fro search that has the
    //passed in and key so that it can recursively
    //travers the tree finding the right key
    private V searchAux(Node tree, K key) {
        if (tree == null)
            return null;
        int cmp = key.compareTo((K)tree.key);
        if (cmp == 0)
            return (V)tree.val;
        return (cmp < 0) ? searchAux(tree.left, key) : searchAux(tree.right, key);
    }


    //This method finds and removes a node
    //with a certain key. it returns the value
    // of the removed tree
    @Override
    public V remove(K key) {
        Node wacked = new Node(null, null);
        root = removeAux(root, key, wacked);
        return (V)wacked.val;
    }

    private Node removeMin(Node tree, Node min) {
        if (tree.left == null) {
            min.key = tree.key;
            min.val = tree.val;
            return tree.right;
        }
        tree.left = removeMin(tree.left, min);
        return tree;
    }

    //Helper method for remove. Passes in the tree the key that
    //is being looked for and the node that is to be removed
    //it then returns the val of that node or null if nothing was
    //found
    private Node removeAux(Node tree, K key, Node wacked) {
        if (tree == null)
            return null;
        int cmp = key.compareTo((K)tree.key);
        if (cmp == 0) {
            wacked.key = tree.key;
            wacked.val = tree.val;
            if (tree.left == null)
                return tree.right;
            else if (tree.right == null)
                return tree.left;
            else {
                Node node = new Node(null, null);
                tree.right = removeMin(tree.right, node);
                tree.key = node.key;
                tree.val = node.val;
                return tree;
            }
        } else if (cmp < 0) {
            tree.N = tree.left.N + size(tree.right) - 1; // update N
            tree.left = removeAux(tree.left, key, wacked);
        } else {
            tree.N = size(tree.left) + size(tree.right) - 1; //update N
            tree.right = removeAux(tree.right, key, wacked);
        }
        return tree;
    }

    //rotates the tree to the right
    private Node rotateRight(Node tree) {
        assert tree != null;
        assert tree.left != null;
        Node root = tree.left;
        tree.left = root.right;
        root.right = tree;
        return root;
    }

    //rotates the tree to the left
    private Node rotateLeft(Node tree) {
        assert tree != null;
        assert tree.left != null;
        Node root = tree.right;
        tree.right = root.left;
        root.left = tree;
        return root;
    }
    // joins to nodes that are siblings when
    // a parent is deleted
    private Node join(Node X, Node Y) {
        if (X == null) return Y;
        if (Y == null) return X;
        if (rng.nextDouble()*(X.N + Y.N) < X.N) { // flip a weighted coin
            X.right = join(X.right, Y);
            X.N = 1 + size(X.left) + size(X.right);
            return X;
        }
        else {
            Y.left = join(X, Y.left);
            Y.N = 1 + size(Y.left) + size(Y.right);
            return Y;
        }
    }
    //helper method to serialize
    private void serializeAux(Node tree, Vector<String> vec) {
        if (tree == null)
            vec.addElement(null);
        else {
            vec.addElement(tree.key.toString() + ":black");
            serializeAux(tree.left, vec);
            serializeAux(tree.right, vec);
        }
    }
    // makes the tree so that it can be seen
    // in a browser
    public Vector<String> serialize() {
        Vector<String> vec = new Vector<String>();
        serializeAux(root, vec);
        return vec;
    }


    public static void main(String args[]) {

        RandomBSTSymbolTable<Integer,Integer> symtab = new RandomBSTSymbolTable<Integer,Integer>();

        for (int i = 0; i < 100; i++) {
            System.out.println("in for");
            symtab.insert(i, i);
        }

        symtab.remove(45);
        symtab.remove(22);
        symtab.remove(5);
        symtab.remove(70);
        symtab.remove(12);

        Vector<String> st = symtab.serialize();
        TreePrinter treePrinter = new TreePrinter(st);
        try {
            FileOutputStream out = new FileOutputStream("tree1.svg");
            PrintStream ps = new PrintStream(out);
            treePrinter.printSVG(ps);
        } catch (FileNotFoundException e) {}


    }
}

