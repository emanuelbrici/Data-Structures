/**
 * Created by emanuel on 10/7/14.
 */
public class Node<K,V>{
    K key;
    V val;
    public Node parent;
    public Node left;
    public Node right;


    public Node(K key, V val) {
        this.key = key;
        this.val = val;
        this.parent = null;
        this.left = null;
        this.right = null;

    }

}