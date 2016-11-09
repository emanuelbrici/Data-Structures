/**
 * Created by Emanuel Brici on 10/28/16.
 */

public class Node {
    public Node parent;
    public Node left;
    public Node right;
    public int key;

    public Node(int data){
        parent = null;
        left = null;
        right = null;
        key = data;
    }
}
