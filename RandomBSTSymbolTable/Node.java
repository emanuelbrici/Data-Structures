/**
 * Created by emanuel on 9/21/14.
 */
public class Node<K,V>{
    K key;
    V val;
    public Node left;
    public Node right;
    int N;


    public Node(K key, V val) {
        this.key = key;
        this.val = val;
        this.left = null;
        this.right = null;
        this.N = 1;
    }

}