package com.ca2.ADT;

public class Hash {


    public static int hash(int key) { return key; }

    public static int hash(char key) { return key; }

    public static int hash(String key) {
        final int p = 53; // includes upper and lower case letters
        final int m = (int) 1e9 + 9;
        int hash_value = 0;
        int p_pow = 1;
        for (char c: key.toCharArray()) {
            hash_value = (hash_value + (c - 'a' + 1) * p_pow) % m;
            p_pow = (p_pow * p) % m;
        }

        assert(hash_value > 0);

        return hash_value;
    }
}
