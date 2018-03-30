package com.song.algorithm.chapter3.section1.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 001844 on 2018/3/30.
 */
public class MyBinarySearchST<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] vals;
    private int size;

    public MyBinarySearchST(int capacity) {
        this.keys = (Key[]) new Comparable[capacity];
        this.vals = (Value[]) new Object[capacity];
    }

    public void put(Key key, Value val) {
        if (key == null || val == null) {
            return;
        }
        //1.是否空的没元素
        if (isEmpty()) {
            keys[0] = key;
            vals[0] = val;
            size++;
        }
        //2.判断是否满了，满了扩容，然后放入，不对，要排序，我擦，找到所在位置，然后将元素统统都后移，然后再覆盖掉
        if (size == keys.length) {//说明满了
            keys = Arrays.copyOf(keys, size * 2 + 1);
            vals = Arrays.copyOf(vals, size * 2 + 1);
        }
        //2.首先判断是否已存在，存在替换掉
        Integer j = null;
        for (int i = 0; i < size; i++) {
            Key temp = keys[i];
            if (temp != null) {
                if (temp.equals(key)) {//是否本就存在，是，替换掉
                    vals[i] = val;
                    return;
                } else if (key.compareTo(temp) < 0) {//加的key比XX小，后面的都进行移动
                    j = i;
                    break;
                }
            }
        }
        if (j == null) {//说明比最后一个还大
            keys[size] = key;
            vals[size] = val;
            size++;
        } else {
            for (int k = size - 1; k >= 0; k--) {
                keys[k + 1] = keys[k];
                vals[k + 1] = vals[k];
            }
            size++;
        }

    }

    private int indexOf(Key key) {
        if (key == null) {
            return -1;
        }
        if (isEmpty()) {
            return -1;
        }
        for (int i = 0; i < size; i++) {
            Key temp = keys[i];
            if (temp != null && temp.equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public Value get(Key key) {
        int index = indexOf(key);
        return index == -1 ? null : vals[index];
    }

    /**
     * 删除，只是置为null？应该是要后面的向前移动
     *
     * @param key
     */
    public void delete(Key key) {
        int index = indexOf(key);
        if (index == -1) {
            return;
        }
        for (int i = index; i < size; i++) {
            if (i == size - 1) {//到达最后一个，将点的key和val设置为null
                keys[i] = null;
                vals[i] = null;
                size--;
            } else {
                keys[i] = keys[i + 1];
                vals[i] = vals[i + 1];
            }
        }
    }


    public boolean contains(Key key) {
        return get(key) != null;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    public int size() {
        return size;
    }


    public Key min() {//最小的val
        return size == 0 ? null : keys[0];
    }


    public Key max() {
        return size == 0 ? null : keys[size - 1];
    }


    /**
     * 比key稍小的或相等的
     * 先要得到key的index，然后index后移一位，注意数组下标越界
     *
     * @param key
     * @return
     */
    public Key floor(Key key) {
        int index = indexOf(key);
        if (index == -1) {
            return null;
        } else if (index == 0) {//此key就是最小的
            return key;
        } else {
            return keys[index - 1];
        }
    }

    /**
     * 比key稍大的或相等的
     *
     * @param key
     * @return
     */
    public Key ceiling(Key key) {
        int index = indexOf(key);
        if (index == -1) {
            return null;
        } else if (index == size - 1) {//此key就是最大的
            return key;
        } else {
            return keys[index + 1];
        }
    }

    /**
     * 比此key小的数量
     *
     * @param key
     * @return
     */
    public int rank(Key key) {
        int index = indexOf(key);
        if (index == -1 || index == 0) {
            return 0;
        } else {
            return index;
        }
    }

    /**
     * Return the kth smallest key in this symbol table.
     *
     * @param k
     * @return
     */
    public Key select(int k) {
        if (k < 0 || k > size - 1) {
            return null;
        }
        return keys[k];
    }

    /**
     * 删除最小的key
     */
    public void deleteMin() {
        if (isEmpty()) {
            return;
        }
        delete(keys[0]);
    }


    public void deleteMax() {
        if (isEmpty()) {
            return;
        }
        delete(keys[size - 1]);
    }


    public int size(Key lo, Key hi) {
        int indexLo = indexOf(lo);
        int indexHi = indexOf(hi);
        if (indexLo == -1 || indexHi == -1) {
            return -1;
        } else if (indexLo >= indexHi) {
            return 0;
        } else {
            return indexHi - indexLo + 1;
        }
    }


    public Iterable<Key> keys(Key lo, Key hi) {
        int indexLo = indexOf(lo);
        int indexHi = indexOf(hi);
        if (indexLo == -1 || indexHi == -1) {
            return null;
        } else if (indexLo >= indexHi) {
            return null;
        } else {

            List<Key> list = new ArrayList<>();
            for (int i = indexLo; i <= indexHi; i++) {
                list.add(keys[i]);
            }
            return new Iterable<Key>() {
                @Override
                public Iterator<Key> iterator() {
                    return list.iterator();
                }
            };
        }
    }


    public Iterable<Key> keys() {
        Iterable<Key> iterable = new Iterable<Key>() {
            @Override
            public Iterator<Key> iterator() {
                return Arrays.asList(keys).iterator();
            }
        };
        return iterable;
    }

    public static void main(String[] args) {
        String[] strs = new String[5];
        System.out.println(strs.length);
        MyBinarySearchST st = new MyBinarySearchST(16);
        String str = "SEARCHEXAMPLE";

        for (int i = 0; i < str.length(); i++) {
            st.put(str.charAt(i), i);
        }
        for (int i = 0; i < str.length(); i++) {
            st.get(str.charAt(i)).equals(i);
        }
    }
}
