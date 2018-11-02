/**
 * a deque implementation using array.
 * @param <T> the object type of this deque
 * @author le
 */
public class ArrayDeque<T> {

    /*
    invariants:
    1. last item is returned by t[size-1]
    2. user do not access index number >= size
    3. the size of array t do not less than 8
     */
    private int size;
    private T[] t;
    public ArrayDeque() {
        t = (T[]) new Object[8];
        size = 0;
    }

    /**
     * resize the array t to allow more items stored in
     * @param length the length of array t changing to
     */
    private void resizeArray(int length) {
        T[] newArray = (T[]) new Object[length];
        System.arraycopy(t, 0, newArray, 0, size);
        t = newArray;
    }

    /**
     * add item to the first position of the deque.
     * @param item the item added to the first of deque
     */
    public void addFirst(T item) {
        if (size >= t.length) {
            resizeArray(t.length * 2);
        }
        System.arraycopy(t, 0, t, 1, size);
        t[0] = item;
        size += 1;
    }

    /**
     * add a item to the last of deque.
     * @param item the item added to the last of deque
     */
    public void addLast(T item) {
        if (size >= t.length) {
            resizeArray(t.length * 2);
        }
        t[size] = item;
        size += 1;
    }

    /**
     * return if the deque is an empty list.
     * @return boolean value of whether the list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the current size of the deque.
     * @return size of the deque
     */
    public int size() {
        return size;
    }

    /**
     * print out the human readable deque.
     */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.println(t[i] + " ");
        }
    }

    /**
     * remove the first ItemNode in the deque, and return the first Item.
     * @return first item in the deque
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size >= 16 && size < 0.25 * t.length) {
            resizeArray(t.length / 2);
        }
        T first = t[0];
        System.arraycopy(t, 1, t, 0, size - 1);
        size -= 1;
        t[size] = null;
        return first;
    }

    /**
     * remove the last ItemNode in the deque, and return the first Item.
     * @return last item in the deque
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size >= 16 && size < 0.25 * t.length) {
            resizeArray(t.length / 2);
        }
        size -= 1;
        T last = t[size - 1];
        t[size - 1] = null;
        return last;
    }

    /**
     * get the item at the locaton index, if index out of the range of deque,
     *  return null.
     * @param index the location of desired item
     * @return item at location index
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return t[index];
    }
}
