package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            return p.value;
        } else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else if (key.compareTo(p.key) > 0) {
            return getHelper(key, p.right);
        }
        return null;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, this.root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            //BSTMap<K,V> bstMap = new BSTMap<K, V>();
            this.size += 1;
            return new Node(key, value);
        }
        if (p.key == key) {
            p.value = value;
        }
        if (key.compareTo(p.key) < 0){
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (this.root == null) {
            this.root = putHelper(key, value, this.root);
        } else {
            putHelper(key, value, this.root);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<K>();
        keySetHelper(keys, this.root);
        return keys;
    }

    private void keySetHelper(HashSet<K> keys, Node p) {
        if (p != null) {
            keys.add(p.key);
            keySetHelper(keys, p.left);
            keySetHelper(keys, p.right);
        }
    }


    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */

    @Override
    public V remove(K key) {
//        Node[] l = removeHelper(key, this.root);
//        this.root.value = l[1].value;
//        this.root.key = l[1].key;
        throw new UnsupportedOperationException();
    }

/*
    private Node[] removeHelper(K key, Node p) {
        //l[0] stores returned Node
        //l[1] stores node to replace the deleted node
        //Node[] l = new Node[2];
        if (key.compareTo(key) == 0) {
            if (p.left == null && p.right == null) {
                l[0] = null;
            } else if (p.left != null) {
                Node[] ll = removeHelper(key, p.left);
                //replace p.node to max node under p.left
                l[0] = ll[0];
            } else {
                //replace p.node to min node under p.right
            }
        } else if ( key.compareTo(key) < 0) {
            Node[] ll = removeHelper(key, p.left);
            p.left = ll[0];

        } else if (key.compareTo(key) > 0) {
            return removeHelper(key, p.right);
        }
        l[0] = p;
        return l;
    }
*/
    /**
     * delete min or max node under a node
     * @return the deleted node
     */
    private Node delete(Node p, boolean min) {
        //ArrayList<Node> l = new ArrayList<Node>();
//        if (p.left == null && p.right == null) {
//            return p;
//        }
//        if (min) {
//            if (p.left != null) {
//                p.left = delete(p.left, min);
//            } else {
//                p.right = delete(p.right, min);
//            }
//        } else {
//            if (p.right != null) {
//                p.right = delete(p.right, min);
//            } else {
//                p.left = delete(p.left, min);
//            }
//        }
//        l.add(p);
//        return l;
        throw new UnsupportedOperationException();
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);

        System.out.println(bstmap.keySet());
    }
}
