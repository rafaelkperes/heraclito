/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.heraclito.generic;

/**
 *
 * @author gia
 * @param <K> key
 * @param <V> value
 * @param <X> complement
 */
public class Triplet<K, V, X> {

    private K key;
    private V value;
    private X complement;

    public Triplet() {
        this.key = null;
        this.value = null;
        this.complement = null;
    }

    public Triplet(K key, V value, X complement) {
        this.key = key;
        this.value = value;
        this.complement = complement;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public X getComplement() {
        return complement;
    }

    public void setComplement(X complement) {
        this.complement = complement;
    }
}
