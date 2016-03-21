/**
 * Created by emanuel on 10/26/14.
 */
public interface FrequencyTable<K> {
    void click(K key);
    int count(K key);
}
