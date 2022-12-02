package com.ca2.ADT;


import java.util.ArrayList;
import java.util.Objects;

public class HashTable <C, V> {

    private static int INITIAL_SIZE = 10;
    private static int MAX_CAPACITY = 80;


    //private HashNode<C, V>[] _v;      // Array of nodes
    private ArrayList<HashNode<C, V>> _v = new ArrayList();
    private int _size;          // Size of array _v
    private int _numElems;      // Number of elements in the table


    public HashTable() {
        this._size = INITIAL_SIZE;
        this._numElems = 0;

        //this._v = (HashNode<C, V>[]) new Object[INITIAL_SIZE];

        for (int i = 0; i < this._size; i++) {
            this._v.add(null);
        }
    }


    public void insert(C key, V value) {
        float occupancy = 100 * ((float) this._numElems) / this._size;
        if (occupancy > MAX_CAPACITY) { this.extend(); }

        int ind = this.hashCode(key) % this._size;

        HashNode<C, V> node = this.searchNode(key, this._v.get(ind));
        if (node != null) {
            node._value = value;
            return;
        }

        this._v.set(ind, new HashNode<>(key, value, this._v.get(ind)));
        this._numElems++;
    }

    private void extend() {
        ArrayList<HashNode<C, V>> vPrev = this._v;
        int sizePrev = this._size;

        this._size *= 2;
        this._v = new ArrayList<>();

        for (int i = 0; i < this._size; i++) {
            this._v.add(null);
        }

        for (int i = 0; i < sizePrev; i++) {
            HashNode<C, V> node = vPrev.get(i);

            while (node != null) {
                HashNode<C, V> aux = node;
                node = node._next;
                int ind = this.hashCode(aux._key) % this._size;

                aux._next = this._v.get(ind);

                this._v.set(ind, aux);
            }
        }

    }


    public void remove(C key) {
        int ind = this.hashCode(key) % this._size;

        HashNode<C, V> prev = this.searchNodePrev(key, this._v.get(ind));
        HashNode<C, V> curr = this.searchNode(key, this._v.get(ind));

        if (curr != null) {
            if (prev != null) {
                prev._next = curr._next;
            } else {
                this._v.set(ind, curr._next);
            }
            this._numElems--;
        }
    }


    public V get(C key) {
        int idx = this.hashCode(key) % this._size;

        HashNode<C, V> node = this.searchNode(key, this._v.get(idx));
        if (node == null) {
            System.out.println("Throw error, invalid key (not found)");
            return null;
        }

        return node._value;
    }


    public boolean exists(C key) {
        int idx = this.hashCode(key) % this._size;

        if (this.searchNode(key, this._v.get(idx)) == null) {
            return false;
        }
        return true;
    }

    private HashNode<C, V> searchNodePrev(C key, HashNode<C, V> first) {
        HashNode<C, V> curr = first;
        HashNode<C, V> prev = null;

        boolean found = false;
        while ((curr != null) && !found) {
            if (curr._key.equals(key)) {
                found = true;
            } else {
                prev = curr;
                curr = curr._next;
            }
        }
        return prev;
    }


    private HashNode<C, V> searchNode(C key, HashNode<C,V> first) {
        HashNode<C, V> curr = first;

        boolean found = false;
        while ((curr != null) && !found) {
            if (curr._key.equals(key)) {
                found = true;
            } else {
                curr = curr._next;
            }
        }
        return curr;
    }



    private int hashCode(C key) {
        return Math.abs(Objects.hashCode(key));
    }



    private class HashNode<C, V> {
        C _key;
        V _value;
        HashNode<C, V> _next;

        HashNode(C key, V value) {
            this._key = key;
            this._value = value;
        }

        HashNode(C key, V value, HashNode<C, V> next) {
            this(key, value);
            this._next = next;
        }
    }



    public Iterator<C, V> begin() {
        int ind = 0;
        HashNode<C, V> curr = this._v.get(ind);

        while ((curr == null) && (ind < this._size - 1)) {
            ++ind;
            curr = this._v.get(ind);
        }

        return new Iterator<C, V>(this, curr, ind);
    }


    public Iterator<C, V> end() {
        return new Iterator<C, V>(this, null, this._size);
    }


    public class Iterator<C, V> {

        public void next() {
            if (this._curr == null) {
                System.out.println("Invalid access error");
                return;
            }

            this._curr = this._curr._next;

            while((this._curr == null) && (this._ind < this._table._size - 1)) {
                this._ind++;
                this._curr = (HashNode<C, V>) this._table._v.get(this._ind);
            }
        }


        public C key() {
            if (this._curr == null) {
                System.out.println("Invalid access error");
                return null;
            }
            return this._curr._key;
        }


        public V value() {
            if (this._curr == null) {
                System.out.println("Invalid access error");
                return null;
            }
            return this._curr._value;
        }


        public boolean equals(Iterator<C, V> other) {
            return this._curr == other._curr;
        }


        public Iterator(HashTable<C, V> table, HashNode<C, V> curr, int ind) {
            this._table = table;
            this._curr = curr;
            this._ind = ind;
        }

        private HashNode<C, V> _curr;
        private int _ind;
        private HashTable<C, V> _table;
    }
}
