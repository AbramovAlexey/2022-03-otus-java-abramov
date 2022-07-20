package ru.otus.cache;


public interface MyCache<K, V> {

    void put(K key, V value);

    void remove(K key);

    V get(K key);

    void addListener(MyListener<K, V> listener);

    void removeListener(MyListener<K, V> listener);
}
