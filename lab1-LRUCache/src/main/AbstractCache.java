import java.util.Objects;
import java.util.Optional;

public abstract class AbstractCache<K, V> implements Cache<K, V> {
    @Override
    public void put(K key, V value) {
        assert Objects.nonNull(key);
        assert Objects.nonNull(value);
        doPut(key, value);
        assert get(key).equals(Optional.of(value));
    }

    @Override
    public Optional<V> get(K key) {
        assert Objects.nonNull(key);
        return doGet(key);
    }

    protected abstract void doPut(K key, V value);
    protected abstract Optional<V> doGet(K key);
}
