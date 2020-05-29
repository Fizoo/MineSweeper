package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;


public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {
    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        this.map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int capacity = Math.max(16, (int) (collection.size() / .75f) + 1);
        this.map = new HashMap<>(capacity);
        for (E e : collection) {
            this.map.put(e, PRESENT);
        }
    }

    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    @Override
    public Iterator iterator() {
        return this.map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return this.map.remove(o) == null;
    }

    @Override
    public Object clone() throws InternalError {
        try {
            AmigoSet copy = (AmigoSet) super.clone();
            copy.map = (HashMap) map.clone();
            return copy;
        } catch (Exception e) {
            throw new InternalError(e);
        }
    }

    private void writeObject(ObjectOutputStream o) throws IOException {
        o.defaultWriteObject();
        o.writeInt(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        o.writeFloat(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));
        o.writeInt(map.size());
        for (E e : map.keySet()) {
            o.writeObject(e);
        }

    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int capacity = ois.readInt();
        float loadFactor = ois.readFloat();
        int size = ois.readInt();

        map = new HashMap<>(capacity, loadFactor);

        for (int i = 0; i < size; i++) {
            E e = (E) ois.readObject();
            map.put(e, PRESENT);
        }

    }
}
