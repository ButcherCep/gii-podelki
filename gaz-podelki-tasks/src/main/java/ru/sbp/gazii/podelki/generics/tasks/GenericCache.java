package ru.sbp.gazii.podelki.generics.tasks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenericCache<K, V> {
    private final Map<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();

    public void put(K key, V value, long ttl) {
        if (ttl > 0) {
            CacheEntry<V> entry = new CacheEntry<>(value, ttl);
            cache.put(key, entry);
        } else {
            throw new IllegalArgumentException("TTL must be positive");
        }
    }

    public V get(K key) {
        if (key == null) return null;

        CacheEntry<V> entry = cache.get(key);
        if (entry == null) return null;

        if (entry.isExpired()) {
            remove(key);
            return null;
        }
        return entry.getValue();
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public void cleanUp() {
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    public int size() {
        return cache.size();
    }

    private static class CacheEntry<V> {
        private final V value;
        private final long expireTime;

        public CacheEntry(V value, long ttlMillis) {
            this.value = value;
            this.expireTime = System.currentTimeMillis() + ttlMillis;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }

        public V getValue() {
            return value;
        }
    }
}
