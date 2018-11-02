/**
 * a deque implementation using array.
 * @param <T> the object type of this deque
 * @author le
 */
public class ArrayDeque<T> {

    /*
    invariants:
    1. first item is returned by t[(nextFirst + 1) % arrayLength]
    2. last item is returned by t[(nextLast - 1) % arrayLength]
    3. size = ((nextLast - nextFirst) % arrayLength) -1
    4. user only have access to index number < ((nextLast - nextFirst) % arrayLength)
    5. the size of array t do not less than 8
    6. the list start at (nextLast - 1) % arrayLength
     */
    private int size;
    private T[] t;
    private int arrayLength;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque() {
        t = (T[]) new Object[8];
        arrayLength = t.length;
        nextFirst = -1 % arrayLength;
        nextLast = 0;
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
        t[nextFirst] = item;
        nextFirst = (nextFirst - 1) % arrayLength;
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
        t[nextLast] = item;
        nextLast = (nextLast + 1) % arrayLength;
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
