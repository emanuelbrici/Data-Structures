/**
 * Created by emanuel on 10/4/14.
 */

public interface SymbolTable<K extends Comparable<K>,V> {
    public void insert(K key, V val);
    public V search(K key);
    //public V remove(K key);
}