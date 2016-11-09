/**
 * Created by Emanuel Brici on 10/28/16.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class ScapegoatTree {

    private Node root;
    private int size;
    private int maxSize;
    private double alpha;

    private ScapegoatTree(double alpha, int key){
        this.alpha = alpha;
        root = new Node(key);
    }

    private Node findScapegoat(Node node) {
        int size = 1;
        int height = 0;
        while(node.parent != null) {
            height++;
            Node sibling = findSibling(node);
            int sibSize = size(sibling);
            int totalSize = 1 + size + sibSize;
            if(height > logAlpha(totalSize)) {
                return node.parent;
            }
            node = node.parent;
            size = totalSize;
        }
        return null;
    }

    private int logAlpha(int totalSize) {
        double logOneOverAlpha = Math.log(1/alpha);
        double logTotalSize = Math.log(totalSize);
        return (int) Math.floor(logTotalSize / logOneOverAlpha);         //Math.log(num) / Math.log(base);
    }

    private Node findSibling(Node node) {
        if(node.key < node.parent.key) { // left side of parent
            return node.parent.right;
        }
        else { // right side of parent
            return node.parent.left;
        }
    }

    private int size(Node node) {
        if(node == null) { return 0;}
        else {
            return size(node.left) + size(node.right) + 1;
        }
    }

    private int findHeight(Node node){
        int size = 0;
        while(node.parent != null) {
            node = node.parent;
            size++;
        }
        return size;
    }

    public Node insert(int key) {
        Node newNode = new Node(key);
        if(root == null){
            root = newNode;
            size++;
            return root;
        }
        Node tmp = root;
        while(tmp != null) {
            if(tmp.key == key){
                break;
            }
            if(tmp.key > key) {
                if(tmp.left == null){
                    tmp.left = newNode;
                    newNode.parent = tmp;
                    size++;
                    break;
                }
                tmp = tmp.left;
            }
            if(tmp.key < key) {
                if(tmp.right == null) {
                    tmp.right = newNode;
                    newNode.parent = tmp;
                    size++;
                    break;
                }
                tmp = tmp.right;
            }
        }
        //Do rebuild if needed part of scapegoat
        int height = findHeight(newNode);
        if(height > logAlpha(size)) {
            Node scapegoat = findScapegoat(newNode);
            Node SGParent = scapegoat.parent;
            boolean leftOfSG = SGParent != null && SGParent.left == scapegoat;
            int sizeSG = size(scapegoat);
            Node newTree = reBuildTree(sizeSG, scapegoat);
            newTree.parent = SGParent;
            if (SGParent != null) {
                if (leftOfSG) {
                    SGParent.left = newTree;
                } else {
                    SGParent.right = newTree;
                }
            }
            if (scapegoat == root) {
                root = newTree;
            }
            maxSize =  Math.max(size, maxSize);
        }
        return root;
    }

    private Node reBuildTree(int size, Node scapegoat) {
        List<Node> nodes = new ArrayList<Node>();
        // flatten tree without recursion
        Node tmp = scapegoat;
        boolean finished = false;
        Stack<Node> stack = new Stack<Node>();
        while (!finished) {
            if (tmp != null) {
                stack.push(tmp);
                tmp = tmp.left;
            } else {
                if (!stack.isEmpty()) {
                    tmp = stack.pop();
                    nodes.add(tmp);
                    tmp = tmp.right;
                } else {
                    finished = true;
                }
            }
        }
        // build tree from flattened list of nodes
        return buildHeightBalancedTree(nodes, 0, size - 1);
    }

    private Node buildHeightBalancedTree(List<Node> nodes, int start, int end) {
        int middle = (int) Math.ceil(((double) (start + end)) / 2.0);
        if (start > end) {
            return null;
        }
        Node node = nodes.get(middle);
        //get left node
        Node leftNode = buildHeightBalancedTree(nodes, start, middle - 1);
        node.left = leftNode;
        if (leftNode != null) {
            leftNode.parent = node;
        }
        //get right nodes
        Node rightNode = buildHeightBalancedTree(nodes, middle + 1, end);
        node.right = rightNode;
        if (rightNode != null) {
            rightNode.parent = node;
        }
        return node;
    }

    private void delete(int key) {
        if(root == null){
            return;
        }
        //Not in tree
        Node tmp = search(key);
        if(tmp == null) {
            return;
        }
        //no children
        if(tmp.right == null && tmp.left == null) {
            //look to see what side of parent node is on
            if(tmp.key < tmp.parent.key){ //left side
                tmp.parent.left = null;
            }
            else { //right side
                tmp.parent.right = null;
            }
            size--;
            return;
        }
        //left child only
        if(tmp.right == null && tmp.left != null) {
            //removing root
            if(tmp.parent == null) {
                root = tmp.left;
                return;
            }
            //look to see what side of parent node is on
            if(tmp.key < tmp.parent.key) { //left side
                tmp.parent.left = tmp.left;
                tmp.left.parent = tmp.parent;
            }
            else { //right side
                tmp.parent.right = tmp.left;
                tmp.left.parent = tmp.parent;
            }
            size--;
            return;
        }
        //right child only
        if(tmp.left == null && tmp.right != null) {
            //removing root
            if(tmp.parent == null) {
                root = tmp.right;
                return;
            }
            //look to see what side of parent node is on
            if(tmp.key < tmp.parent.key) { //left side
                tmp.parent.left = tmp.right;
                tmp.right.parent = tmp.parent;
            }
            else { //right side
                tmp.parent.right = tmp.right;
                tmp.right.parent = tmp.parent;
            }
            size--;
            return;
        }
        //two children
        else {
            Node successor = tmp.right;
            while(successor.left != null) {
                successor = successor.left;
            }
            delete(successor.key);
            tmp.key = successor.key;
        }
        //rebuild if necessary part of scapegoat
        int sizeMe = Math.max(size, maxSize);
        if (size <= alpha * maxSize)
        {
            root = reBuildTree(sizeMe, root);
            maxSize = Math.max(size, maxSize);
        }
    }

    private Node search(int key){
        Node tmp = root;
        while(tmp != null) {
            if (tmp.key == key) {
                return tmp;
            }
            //left side of tree
            else if(tmp.key > key) {
                tmp = tmp.left;
            }
            //right side of tree
            else if(tmp.key < key) {
                tmp = tmp.right;
            }
        }
        return null;
    }

    private void serializeAux(Node tree, Vector<String> vec){
        if(tree == null)
            vec.addElement(null);
        else{
            vec.addElement(Integer.toString(tree.key) + ":black");
            serializeAux(tree.left, vec);
            serializeAux(tree.right, vec);
        }
    }

    private Vector<String> serialize() {
        Vector<String> vec = new Vector<String>();
        serializeAux(root, vec);
        return vec;
    }

    public static void main(String[] args) throws IOException {
        FileInputStream fstream = new FileInputStream("tree.txt");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        float alpha;
        int keyInitial;
        if ((strLine = buffer.readLine()) != null) {
            String[] cmd = strLine.split("\\s+");
            if (cmd[0].equals("BuildTree")) {
                alpha = Float.parseFloat(cmd[1]);
                keyInitial = Integer.parseInt(cmd[2]);
                ScapegoatTree SGT = new ScapegoatTree(alpha, keyInitial);
                SGT.size++;
                while ((strLine = buffer.readLine()) != null) {
                    String[] secondaryCommand = strLine.split("\\s+");
                    if (secondaryCommand[0].equals("Insert")) {
                        int key = Integer.parseInt(secondaryCommand[1]);
                        SGT.insert(key);
                    }
                    else if(secondaryCommand[0].equals("Search")) {
                        int key = Integer.parseInt(secondaryCommand[1]);
                        Node result = SGT.search(key);
                        if (result != null) {
                            System.out.println(key + " found when searched in SGT");
                        } else {
                            System.out.println(key + " not found when searched in SGT");
                        }
                    }
                    else if(secondaryCommand[0].equals("Delete")) {
                        int key = Integer.parseInt(secondaryCommand[1]);
                        SGT.delete(key);
                    }
                    else if(secondaryCommand[0].equals("Print")) {
                        Vector<String> st = SGT.serialize();
                        TreePrinter treePrinter = new TreePrinter(st);
                        try {
                            FileOutputStream out = new FileOutputStream("ScapeGoatTree.svg");
                            PrintStream tree = new PrintStream(out);
                            treePrinter.printSVG(tree);
                        } catch (FileNotFoundException e) {
                            System.out.println("Error creating SVG file.");
                        }
                        System.out.println("SVG file created. Open in web browser to view tree.");
                    } else if(secondaryCommand[0].equals("Done")) {
                        buffer.close();
                        System.out.println("Exiting");
                        System.exit(0);
                    }
                }
                buffer.close();
            } else{
                System.out.println("First input must be BuildTree");
            }
        }
    }
}
