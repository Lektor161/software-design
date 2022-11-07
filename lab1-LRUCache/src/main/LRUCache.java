import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCache<K, V> extends AbstractCache<K, V> {
    Map<K, Node> nodeMap = new HashMap<>();
    Node head, tail;
    private final int capacity;

    public LRUCache(int capacity) {
        assert capacity > 0;
        this.capacity = capacity;
    }

    @Override
    protected void doPut(K key, V value) {
        if (head == null) {
            head = tail = new Node(key, value);
            nodeMap.put(key, head);
            return;
        }

        if (nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            node.value = value;
            pushToHead(node);
        } else {
            head = new Node(head, key, value);
            head.prev.next = head;
            nodeMap.put(key, head);
        }

        while (nodeMap.size() > capacity) {
            nodeMap.remove(tail.key);
            tail = tail.next;
            tail.prev = null;
        }
    }

    @Override
    protected Optional<V> doGet(K key) {
        if (!nodeMap.containsKey(key)) {
            return Optional.empty();
        }
        Node node = nodeMap.get(key);
        pushToHead(node);
        return Optional.of(node.value);
    }

    private void pushToHead(Node node) {
        if (head == node) {
            return;
        }
        node.next.prev = node.prev;

        if (node == tail) {
            tail = tail.next;
            tail.prev = null;
        } else {
            node.prev.next = node.next;
        }

        node.next = null;
        node.prev = head;
        head = node;
        head.prev.next = head;
    }

    private class Node {
        Node prev, next;
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public Node(Node prev, K key, V value) {
            this(key, value);
            this.prev = prev;
        }
    }
}
